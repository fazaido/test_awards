package com.texo.awards.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "Awards")
@EntityListeners(AuditingEntityListener.class)
public class Awards {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "year", nullable = false)
	private Integer year;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "studios", nullable = false)
	private String studios;

	@Column(name = "producer", nullable = false)
	private String producer;

	@Column(name = "winner", nullable = false)
	private Character winner;

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(final Integer year) {
		this.year = year;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getStudios() {
		return studios;
	}

	public void setStudios(final String studios) {
		this.studios = studios;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(final String producer) {
		this.producer = producer;
	}

	public Character getWinner() {
		return winner;
	}

	public void setWinner(final Character winner) {
		this.winner = winner;
	}

	@Override
	public String toString() {
		return "Awards [id=" + id + ", year=" + year + ", title=" + title + ", studios=" + studios + ", producer="
				+ producer + ", winner=" + winner + "]";
	}
}