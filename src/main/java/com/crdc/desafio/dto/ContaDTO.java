package com.crdc.desafio.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import lombok.Data;

@Data
public class ContaDTO {
	
	private BigInteger idContaCliente;
	private String cdConta;
	private BigDecimal saldo;
	private String cdCliente;
	private List<TransacaoDTO> transacoes;
}
