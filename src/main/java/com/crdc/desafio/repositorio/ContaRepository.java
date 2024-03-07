package com.crdc.desafio.repositorio;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crdc.desafio.entity.ClienteEntity;
import com.crdc.desafio.entity.ContaEntity;

public interface ContaRepository extends JpaRepository<ContaEntity, BigInteger>   {

	public Optional<ContaEntity> findByCdConta(String cdConta);
	
	public Optional<List<ContaEntity>> findByCliente(ClienteEntity cliente);
}
