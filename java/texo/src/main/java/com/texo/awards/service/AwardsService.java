package com.texo.awards.service;

import java.util.List;

import com.texo.awards.model.Awards;
import com.texo.awards.model.IntervalAwards;

public interface AwardsService {
	IntervalAwards getIntervalProducersAwards();

	Integer saveAll(List<Awards> awards);
}