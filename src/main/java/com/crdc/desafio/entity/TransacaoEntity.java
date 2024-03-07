package com.crdc.desafio.entity;

import java.math.BigDecimal;
import java.math.BigInteger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "TBTransacao")
public class TransacaoEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idTransacao")
	private BigInteger idTransacao;
	@Column(name = "TipoTransacao")
	private char tipoTransacao;
	@Column(name = "ValorTransacao")
	private BigDecimal valorTransacao;
	@OneToOne
	@PrimaryKeyJoinColumn
	private ContaEntity contaOrigem;
	@OneToOne
	@PrimaryKeyJoinColumn
	private ContaEntity contaDestino; 

}
