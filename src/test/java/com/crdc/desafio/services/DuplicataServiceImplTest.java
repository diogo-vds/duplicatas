package com.crdc.desafio.services;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.crdc.desafio.dto.UploadFileDTO;

@ExtendWith(MockitoExtension.class)
public class DuplicataServiceImplTest {

	void processarDuplicatatest()throws Exception{
		
	}
	
	private UploadFileDTO mockUploadFileDTO() {

		UploadFileDTO dto = new UploadFileDTO();		
		String arquivo = "MDAxRW1wcmVzYSBBICAgICAgICAgICAgICAxOTAwMDEwMDAwMDEwMDAwICAgICAgICAgIEVtcHJlc2EgQQowMDJDMTAwMDAwMDAwMTAwMDAwMDAwMDEyMzQ1NjAwMDAwMTIzNDUKMDAyRDIwMDAwMDAwMDIwMDAwMDAwMDAxMjM0NTYxMjM0NTYwMAowMDJUMzAwMDAwMDAwMzAwMDAwMDAwMDEyMzQ1NjIzNDU2Nzg5CjAwMw==";
		dto.setFile(arquivo.getBytes());
		
		
		return dto;		
	}
}
