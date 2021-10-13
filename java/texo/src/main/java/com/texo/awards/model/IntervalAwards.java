package com.texo.awards.model;

import java.util.List;

public class IntervalAwards {
	private List<ProducerAward> min;
	private List<ProducerAward> max;

	public IntervalAwards(final List<ProducerAward> min, final List<ProducerAward> max) {
		super();
		this.min = min;
		this.max = max;
	}

	public List<ProducerAward> getMin() {
		return min;
	}

	public void setMin(List<ProducerAward> min) {
		this.min = min;
	}

	public List<ProducerAward> getMax() {
		return max;
	}

	public void setMax(List<ProducerAward> max) {
		this.max = max;
	}

	@Override
	public String toString() {
		return "IntervalAwards [min=" + min + ", max=" + max + "]";
	}
}
