package com.sku.minimlops.model.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_log_id")
	private Long id;

	@ManyToOne()
	@JoinColumn(name = "model_id")
	private Model model;

	private String input;

	@OneToMany(mappedBy = "userLog", fetch = FetchType.LAZY)
	private List<Result> results = new ArrayList<>();

	private Boolean satisfaction;

	@CreatedDate
	private LocalDateTime requestDate;

	public void setGood() {
		satisfaction = true;
	}

	public void setBad() {
		satisfaction = false;
	}
}
