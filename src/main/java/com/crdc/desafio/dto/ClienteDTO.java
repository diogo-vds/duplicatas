package com.crdc.desafio.dto;

import java.util.List;

import lombok.Data;

@Data
public class ClienteDTO {
	
	private Integer idCliente;
	private String razaoSocialEmpresa;
	private String cdEmpresa;
	private String descricao;
	private List<ContaDTO> contas;
	
}
