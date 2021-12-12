package org.emeritus.gamemanager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class GameManagerApplicationTests {
	@Autowired
	private WebApplicationContext context;

	protected MockMvc mvc;
	
	@BeforeEach
	public void setup() {
		this.mvc = webAppContextSetup(this.context).build();
	}

	@Test
	public void postMatchMockTest() throws Exception{
		UUID red_player = UUID.randomUUID();
		UUID black_player = UUID.randomUUID();
		HashMap<String,String> player = new HashMap<>();
		player.put("redPlayerString", red_player.toString());
		player.put("blackPlayerString", black_player.toString());
		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(player);
		MvcResult result = mvc.perform(post("/api/play/register/")
			.content(json)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn();
		String resultString = result.getResponse().getContentAsString();
		assertEquals(36, resultString.length());
	}

	@Test
	public void getBoardTest() throws Exception{
		UUID red_player = UUID.randomUUID();
		UUID black_player = UUID.randomUUID();
		HashMap<String,String> player = new HashMap<>();
		player.put("redPlayerString", red_player.toString());
		player.put("blackPlayerString", black_player.toString());
		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(player);
		MvcResult result = mvc.perform(post("/api/play/register/")
			.content(json)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn();
		String gameID = result.getResponse().getContentAsString();
		result = mvc.perform(get("/api/play/status/"+gameID))
			.andExpect(status().isOk())
			.andReturn();
	}

	@Test
	public void postMove() throws Exception{
		UUID red_player = UUID.randomUUID();
		UUID black_player = UUID.randomUUID();
		HashMap<String,String> player = new HashMap<>();
		player.put("redPlayerString", red_player.toString());
		player.put("blackPlayerString", black_player.toString());
		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(player);
		MvcResult result = mvc.perform(post("/api/play/register/")
			.content(json)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn();
		HashMap<String,String> move = new HashMap<>();
		move.put("gameId",result.getResponse().getContentAsString());
		move.put("start", Integer.toString(22));
		move.put("end", Integer.toString(18));
		json = om.writeValueAsString(move);
		mvc.perform(post("/api/play/move")
		   .content(json)
		   .contentType(MediaType.APPLICATION_JSON))
		   .andExpect(status().isOk())
		   .andReturn();
	}
	
}
