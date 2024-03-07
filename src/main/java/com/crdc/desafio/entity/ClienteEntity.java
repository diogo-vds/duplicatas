package com.crdc.desafio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "TBCliente")
public class ClienteEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "IdCliente")
	private Integer idCliente;
	@Column(name = "RazaoSocial")
	private String razaoSocialEmpresa;
	@Column(name = "CdEmpresa")
	private String cdEmpresa;
	@Column(name = "Descricao")
	private String descricao;

}
