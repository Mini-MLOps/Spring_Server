package com.sku.minimlops.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sku.minimlops.exception.Code;
import com.sku.minimlops.exception.GeneralException;
import com.sku.minimlops.exception.dto.ErrorResponse;
import com.sku.minimlops.service.UserDetailsServiceImpl;
import com.sku.minimlops.util.JwtUtil;
import com.sku.minimlops.util.RedisUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("Authorization : {}", authorization);

        try {
            // Token이 없을 경우 block
            if (authorization == null || !authorization.startsWith("Bearer ")) {
                throw new GeneralException(Code.UNAUTHORIZED, "Wrong Authorization");
            }

            // Token 꺼내기
            String token = authorization.split(" ")[1];

            // 로그아웃 되었는지 확인
            if (redisUtil.hasKeyBlackList(token)) {
                throw new GeneralException(Code.UNAUTHORIZED, "Logged out token detected");
            }

            // Token Expired 되었는지 확인
            if (jwtUtil.isTokenExpired(token)) {
                throw new GeneralException(Code.UNAUTHORIZED, "Token has expired");
            }

            // username Token에서 꺼내기
            String username = jwtUtil.getUsername(token);
            log.info("Username : {}", username);

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 권한 부여
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            // Detail 추가
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);
        } catch (GeneralException e) {
            response.setStatus(e.getErrorCode().getCode());
            response.setContentType("application/json; charset=UTF-8");

            response.getWriter().write(new ObjectMapper().writeValueAsString(
                    ErrorResponse.of(e.getErrorCode(), e.getErrorCode().getMessage() + " - " + e.getMessage()))
            );
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String[] excludePath = {"/api/users/login", "/api/users/join", "/swagger-ui.html", "/swagger-ui/**",
                "/v3/api-docs/**", "/swagger-ui/index.html", "api/models/train-complete", "api/models/deploy-complete"};
        String path = request.getRequestURI();
        return Arrays.stream(excludePath).anyMatch(path::endsWith);
    }
}
