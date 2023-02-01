package com.cognizant.FEMS;

import com.cognizant.FEMS.controller.FEMsController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
class FemsApplicationTests {

	@Autowired
	private FEMsController controller;

	@Test
	void contextLoads() {
		Assertions.assertThat(controller).isNotNull();
	}
}
