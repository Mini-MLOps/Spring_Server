package com.sku.minimlops.model.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;

@Entity
@Getter
public class Movie {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "movie_id")
	private Long id;

	private String code;

	private String title;

	@Column(columnDefinition = "TEXT")
	private String plot;

	private String summary;

	private String poster;

	private LocalDate releaseDate;

	private LocalDate collectionDate;

	@OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
	private List<EmbeddingVector> embeddingVectors = new ArrayList<>();

	@OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
	private List<Result> results = new ArrayList<>();
}
