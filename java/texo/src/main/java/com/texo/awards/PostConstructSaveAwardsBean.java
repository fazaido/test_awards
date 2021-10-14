package com.texo.awards;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.texo.awards.model.Awards;
import com.texo.awards.service.AwardsService;

@Component
public class PostConstructSaveAwardsBean {

	@Autowired
	private AwardsService awardsService;

	@Value("${awards.csv}")
	private String PATH_CSV;

	private static final Logger logger = LogManager.getLogger(PostConstructSaveAwardsBean.class);

	private static final int POSITION_YEAR = 0;
	private static final int POSITION_TITLE = 1;
	private static final int POSITION_STUDIOS = 2;
	private static final int POSITION_PRODUCERS = 3;
	private static final String QUOTES = "'";
	private static final int FIRST_LINE = 1;
	private static final int LAST_POSITION = 4;
	private static final int LAST_ELEMENT = 5;
	private static final int TOTAL_ELEMENTS = LAST_ELEMENT;
	private static final String COMMA = ",";
	private static final String CSV_REPLACE_PRODUCER_2 = " and ";
	private static final String CSV_REPLACE_PRODUCER_1 = ", and";
	private static final char WINNER_VALUE_YES = 'Y';
	private static final char WINNER_VALUE_NO = 'N';
	private static final String CSV_WINNER_YES = "yes";
	private static final String CSV_SEPARATOR = ";";

	@PostConstruct
	public void init() {
		try {
			saveAwardsFromCSV();
		} catch (final URISyntaxException | IOException e) {
			logger.error("Problem to load movie list (file movielist.csv of resource)" + e.getMessage());
			e.printStackTrace();
		}
	}

	public void saveAwardsFromCSV() throws URISyntaxException, IOException {
		String[] line;
		Character winner;
		String auxProducer;
		String[] producers;
		Integer reads = 0;
		Awards aw;
		final List<Awards> listCreateAward = new ArrayList<>();		
		final List<String> rows = Files.readAllLines(
				new File(getClass().getClassLoader().getResource(PATH_CSV).toURI()).toPath(), StandardCharsets.UTF_8);

		logger.info("rows read on csv: " + rows.size());

		for (String row : rows) {
			reads++;
			if (reads != FIRST_LINE) { // ignore description line
				row = row.replaceAll(QUOTES, QUOTES + QUOTES);
				line = row.split(CSV_SEPARATOR);
				winner = WINNER_VALUE_NO;
				if (line.length == TOTAL_ELEMENTS && line[LAST_POSITION].equals(CSV_WINNER_YES)) {
					winner = WINNER_VALUE_YES;
				}
				auxProducer = line[POSITION_PRODUCERS].replaceAll(CSV_REPLACE_PRODUCER_1, COMMA)
						.replaceAll(CSV_REPLACE_PRODUCER_2, COMMA);
				producers = auxProducer.split(COMMA);
				for (final String producer : producers) {
					aw = new Awards();
					// year;title;studios;producers;winner
					aw.setYear(Integer.parseInt(line[POSITION_YEAR]));
					aw.setTitle(line[POSITION_TITLE].strip());
					aw.setStudios(line[POSITION_STUDIOS].strip());
					aw.setProducer(producer.strip());
					aw.setWinner(winner);
					listCreateAward.add(aw);
				}
			}
		}

		final Integer numberAwards = awardsService.saveAll(listCreateAward);
		logger.info("Generated " + numberAwards + " from " + PATH_CSV);
	}
}
