package com.crdc.desafio.dto;

import java.util.ArrayList;

import lombok.Data;

@Data
public class DataDTO {

	private ArrayList<TransactionDTO> transactions = new ArrayList<>();
	
}
