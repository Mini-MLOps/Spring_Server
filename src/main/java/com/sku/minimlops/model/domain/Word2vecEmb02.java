package com.sku.minimlops.model.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "word2vec_emb_02")
public class Word2vecEmb02 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "word2vec_emb_02_id")
	private Long id;

	@ManyToOne()
	@JoinColumn(name = "movie_id")
	private Movie movie;

	@Column(columnDefinition = "TEXT")
	private String vector;
}
