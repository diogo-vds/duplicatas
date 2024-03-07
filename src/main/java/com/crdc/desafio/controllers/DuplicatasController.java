package com.crdc.desafio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crdc.desafio.dto.ClienteDTO;
import com.crdc.desafio.dto.UploadFileDTO;
import com.crdc.desafio.dto.UploadResponseDTO;
import com.crdc.desafio.services.ClienteService;
import com.crdc.desafio.services.DuplicataService;


@RestController
@RequestMapping("/v1/duplicatas")
public class DuplicatasController {
	
	@Autowired
	DuplicataService duplicataService;
	@Autowired
	ClienteService clienteService;
	
	@PostMapping("/upload")
	public ResponseEntity<UploadResponseDTO>  upload(@RequestBody UploadFileDTO uploadFileDTO) {
		
		UploadResponseDTO dto = duplicataService.processarDuplicata(uploadFileDTO.getFile());
		
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping("/inserirCliente")
	public ResponseEntity<UploadResponseDTO>  inserirCliente(@RequestBody ClienteDTO cliente) {		
		UploadResponseDTO dto = clienteService.inserirCliente(cliente);		
		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping("/listar")
	public ResponseEntity<List<ClienteDTO>>  listar() {		
		List<ClienteDTO> dto = clienteService.listar();		
		return ResponseEntity.ok().body(dto);
	}

}
