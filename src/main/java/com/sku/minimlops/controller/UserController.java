package com.sku.minimlops.controller;

import com.sku.minimlops.exception.Code;
import com.sku.minimlops.exception.dto.DataResponse;
import com.sku.minimlops.exception.dto.Response;
import com.sku.minimlops.model.dto.request.UserJoinRequest;
import com.sku.minimlops.model.dto.request.UserLoginRequest;
import com.sku.minimlops.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "user", description = "사용자 API")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    @Operation(summary = "회원가입", description = "회원가입합니다.")
    public Response join(@RequestBody UserJoinRequest userJoinRequest) {
        userService.join(userJoinRequest.getUsername(), userJoinRequest.getName(), userJoinRequest.getPassword());
        return Response.of(true, Code.OK);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인합니다.")
    public DataResponse<Object> login(@RequestBody UserLoginRequest userLoginRequest) {
        return DataResponse.of(userService.login(userLoginRequest.getUsername(), userLoginRequest.getPassword()));
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "로그아웃합니다.")
    public Response logout(@RequestHeader("Authorization") String authorization, Authentication authentication) {
        String username = authentication.getName();
        String token = authorization.split(" ")[1];
        userService.logout(username, token);
        return Response.of(true, Code.OK);
    }

    @DeleteMapping("/withdrawal")
    @Operation(summary = "회원탈퇴", description = "회원을 탈퇴합니다.")
    public Response deleteUser(Authentication authentication) {
        String username = authentication.getName();
        userService.deleteUser(username);
        return Response.of(true, Code.OK);
    }

    @GetMapping("/name")
    @Operation(summary = "이름 조회", description = "이름을 조회합니다.")
    public DataResponse<Object> getUserName(Authentication authentication) {
        String username = authentication.getName();
        return DataResponse.of(userService.getName(username));
    }
}
