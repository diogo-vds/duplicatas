package com.crdc.desafio.services;

import com.crdc.desafio.dto.UploadResponseDTO;

public interface DuplicataService {
	
	public UploadResponseDTO processarDuplicata(byte[] file);

}
