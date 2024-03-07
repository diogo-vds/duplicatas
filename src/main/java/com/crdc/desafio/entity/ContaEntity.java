package com.crdc.desafio.entity;

import java.math.BigDecimal;
import java.math.BigInteger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "TBConta")
public class ContaEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "IdContaCliente", length = 9, precision = 10, nullable = true)
	private BigInteger idContaCliente;
	@Column(name = "CdConta")
	private String cdConta;
	@Column(name = "Saldo")
	private BigDecimal saldo;
	@ManyToOne
	@PrimaryKeyJoinColumn
	private ClienteEntity cliente;

}
