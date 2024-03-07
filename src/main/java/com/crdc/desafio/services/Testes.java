package com.crdc.desafio.services;

import java.math.BigDecimal;

public class Testes {

	public static void main(String[] args) {
	
		String linha = "100000000100000";
		BigDecimal valor = new BigDecimal(linha);
		valor = valor.movePointLeft(2);
	}
	
}
