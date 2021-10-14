package com.texo.awards.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.mutable.MutableInt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.texo.awards.model.Awards;
import com.texo.awards.model.IntervalAwards;
import com.texo.awards.model.ProducerAward;
import com.texo.awards.repository.AwardsRepository;
import com.texo.awards.service.AwardsService;

@Service("SubscriptionService")
public class AwardsServiceImpl implements AwardsService {

	@Autowired
	private AwardsRepository awardsRepository;

	private static final Logger logger = LogManager.getLogger(AwardsServiceImpl.class);

	@Override
	public IntervalAwards getIntervalProducersAwards() {

		final List<Awards> listWinnersMoreOneAward = awardsRepository.getAllReWinners();
		final IntervalAwards intervalAwards = new IntervalAwards(new ArrayList<>(), new ArrayList<>());

		final MutableInt maxInterval = new MutableInt(-1), minInterval = new MutableInt(-1);
		String nextProducer = null;
		Integer lastYear = null;

		final Iterator<Awards> iterator = listWinnersMoreOneAward.iterator();
		if (iterator.hasNext()) {
			final Awards award = iterator.next();
			nextProducer = award.getProducer();
			lastYear = award.getYear();
			Awards nextAward;

			while (iterator.hasNext()) {
				nextAward = iterator.next();

				// se mudou Produtor, reinicia calculo do intervalo de anos vencidos
				if (!nextProducer.equals(nextAward.getProducer())) {
					nextProducer = nextAward.getProducer();
					lastYear = nextAward.getYear();

				} else { // se continua no produtor, calcula intervalo
					final ProducerAward producer = new ProducerAward(nextProducer, (nextAward.getYear() - lastYear),
							lastYear, nextAward.getYear());
					calcInterval(producer, intervalAwards.getMin(), intervalAwards.getMax(), minInterval, maxInterval);
					lastYear = nextAward.getYear();
				}
			}
		}

		logger.debug("intervalAward: " + intervalAwards);
		return intervalAwards;
	}

	private void calcInterval(ProducerAward prod, List<ProducerAward> minList, List<ProducerAward> maxList,
			MutableInt minInterval, MutableInt maxInterval) {
		final Integer interval = prod.getInterval();
		if (minInterval.intValue() == -1 || interval == minInterval.intValue()) {
			minInterval.setValue(interval);
			minList.add(prod);
		} else {
			if (interval < minInterval.intValue()) {
				minInterval.setValue(interval);
				minList.clear();
				minList.add(prod);
			}
		}
		if (maxInterval.intValue() == -1 || interval == maxInterval.intValue()) {
			maxInterval.setValue(interval);
			maxList.add(prod);
		} else {
			if (interval > maxInterval.intValue()) {
				maxInterval.setValue(interval);
				maxList.clear();
				maxList.add(prod);
			}
		}
	}

	@Override
	public Integer saveAll(List<Awards> awards) {
		return awardsRepository.saveAll(awards).size();
	}
}
