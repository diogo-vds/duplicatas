package com.crdc.desafio.repositorio;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crdc.desafio.entity.ContaEntity;
import com.crdc.desafio.entity.TransacaoEntity;

public interface TransacaoRepository extends JpaRepository<TransacaoEntity, BigInteger> {
	
	public Optional<List<TransacaoEntity>> findByContaOrigemOrContaDestino(ContaEntity contaOrigem, ContaEntity contaDestino);

}
