package com.crdc.desafio.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TransactionDTO {

	private char type;
    private BigDecimal value;
    private String accountOrigin;
    private String accountDestination;
    
}
