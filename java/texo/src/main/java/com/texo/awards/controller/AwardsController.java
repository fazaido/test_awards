package com.texo.awards.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.texo.awards.model.IntervalAwards;
import com.texo.awards.service.AwardsService;

@RestController()
@RequestMapping("awards")
public class AwardsController {

	@Autowired
	private AwardsService awardsService;

	@GetMapping("/intervals")
	public @ResponseBody ResponseEntity<IntervalAwards> getIntervalProducersAwards() throws JsonProcessingException {
		return ResponseEntity.ok(awardsService.getIntervalProducersAwards());
	}
}
