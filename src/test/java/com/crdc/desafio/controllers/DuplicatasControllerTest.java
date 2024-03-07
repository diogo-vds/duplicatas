package com.crdc.desafio.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.crdc.desafio.dto.UploadResponseDTO;
import com.crdc.desafio.enums.StatusEnum;
import com.crdc.desafio.repositorio.ClienteRepository;
import com.crdc.desafio.repositorio.ContaRepository;
import com.crdc.desafio.repositorio.TransacaoRepository;
import com.crdc.desafio.services.ClienteService;
import com.crdc.desafio.services.DuplicataService;
import com.crdc.desafio.validation.DuplicataValidate;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(DuplicatasController.class)
@AutoConfigureMockMvc(addFilters = false)
public class DuplicatasControllerTest {

	@MockBean
	private DuplicataService duplicataService;
	@MockBean
	private ClienteService clienteService;
	@MockBean
	private ClienteRepository clienteRepository;
	@MockBean
	private ContaRepository contaRepository;
	@MockBean
	private TransacaoRepository transacaoRepository;
	@MockBean
	private DuplicataValidate validate; 
	@Autowired
	private MockMvc mockMvc;
	@Autowired	
	private ObjectMapper objectMapper;
	
	private final String BASE_URL = "/v1/duplicatas/"; 

	@Test
	void testUpload() throws Exception{
		
		UploadResponseDTO dto = new UploadResponseDTO();
		dto.setStatus(StatusEnum.SUCCESS.getStatus());
		dto.setMessage("Sucesso");
		when(duplicataService.processarDuplicata(any())).thenReturn(dto);		
		mockMvc.perform(post(BASE_URL+"upload").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
				.content(objectMapper.writeValueAsString(dto)))
		.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());
		
		verify(duplicataService).processarDuplicata(any());
	}
}
