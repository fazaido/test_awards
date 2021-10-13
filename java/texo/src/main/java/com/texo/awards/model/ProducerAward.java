package com.texo.awards.model;

public class ProducerAward {
	private String producer;
	private Integer interval;
	private Integer previousWin;
	private Integer followingWin;

	public ProducerAward(String producer, Integer interval, Integer previousWin, Integer followingWin) {
		super();
		this.producer = producer;
		this.interval = interval;
		this.previousWin = previousWin;
		this.followingWin = followingWin;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(final String producer) {
		this.producer = producer;
	}

	public Integer getInterval() {
		return interval;
	}

	public void setInterval(final Integer interval) {
		this.interval = interval;
	}

	public Integer getPreviousWin() {
		return previousWin;
	}

	public void setPreviousWin(final Integer previousWin) {
		this.previousWin = previousWin;
	}

	public Integer getFollowingWin() {
		return followingWin;
	}

	public void setFollowingWin(final Integer followingWin) {
		this.followingWin = followingWin;
	}

	@Override
	public String toString() {
		return "ProducerAward [producer=" + producer + ", interval=" + interval + ", previousWin=" + previousWin
				+ ", followingWin=" + followingWin + "]";
	}
}
