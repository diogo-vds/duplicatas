package com.crdc.desafio.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.crdc.desafio.entity.ContaEntity;

import lombok.Data;

@Data
public class TransacaoDTO {
	
	private BigInteger idTransacao;
	private char tipoTransacao;
	private BigDecimal valorTransacao;
	private ContaEntity contaOrigem;
	private ContaEntity contaDestino; 
}
