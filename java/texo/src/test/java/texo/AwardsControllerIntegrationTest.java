package texo;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.Gson;
import com.texo.awards.Application;
import com.texo.awards.model.IntervalAwards;
import com.texo.awards.model.ProducerAward;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class AwardsControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;	

	private static final String INTERVAL_WINNERS = "/interval_winners";

	@Test
	void testIntervalWinners() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(INTERVAL_WINNERS).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk());
	}

	@Test
	void testIntervalWinnersResult() throws Exception {
	
		final List<ProducerAward> minList = new ArrayList<>();
		final List<ProducerAward> maxList = new ArrayList<>();
		final IntervalAwards intervalAward = new IntervalAwards(minList, maxList);
		minList.add(new ProducerAward("Joel Silver", 1, 1990, 1991));
		maxList.add(new ProducerAward("Matthew Vaughn", 13, 2002, 2015));
		
		mockMvc.perform(MockMvcRequestBuilders.get(INTERVAL_WINNERS).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andExpect(content().json(new Gson().toJson(intervalAward)));
	}
}