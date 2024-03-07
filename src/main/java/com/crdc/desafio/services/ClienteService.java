package com.crdc.desafio.services;

import java.util.List;

import com.crdc.desafio.dto.ClienteDTO;
import com.crdc.desafio.dto.UploadResponseDTO;

public interface ClienteService {

	public UploadResponseDTO inserirCliente(ClienteDTO dto);
	public List<ClienteDTO> listar();
	
}
