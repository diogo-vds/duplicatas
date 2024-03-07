package com.crdc.desafio.enums;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StatusEnumTest {

	@Test
	void recuperarPorStatus() {
		String status = StatusEnum.SUCCESS.getStatus();
		Assertions.assertEquals("success", status);
	}
	
}
