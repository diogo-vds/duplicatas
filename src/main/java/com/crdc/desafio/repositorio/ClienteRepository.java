package com.crdc.desafio.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crdc.desafio.entity.ClienteEntity;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer>{
	
	public ClienteEntity findByIdCliente(Integer idCliente);

}
