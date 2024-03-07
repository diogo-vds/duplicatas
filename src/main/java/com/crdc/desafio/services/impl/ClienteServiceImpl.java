package com.crdc.desafio.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crdc.desafio.dto.ClienteDTO;
import com.crdc.desafio.dto.ContaDTO;
import com.crdc.desafio.dto.TransacaoDTO;
import com.crdc.desafio.dto.UploadResponseDTO;
import com.crdc.desafio.entity.ClienteEntity;
import com.crdc.desafio.entity.ContaEntity;
import com.crdc.desafio.entity.TransacaoEntity;
import com.crdc.desafio.enums.StatusEnum;
import com.crdc.desafio.repositorio.ClienteRepository;
import com.crdc.desafio.repositorio.ContaRepository;
import com.crdc.desafio.repositorio.TransacaoRepository;
import com.crdc.desafio.services.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService{
	
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ContaRepository contaRepository;
	@Autowired
	private TransacaoRepository transacaoRepository;

	@Override
	public List<ClienteDTO> listar() {
		List<ClienteEntity> clientes = clienteRepository.findAll();		
		return this.clienteEntityToDTO(clientes);
	}
	
	private List<ClienteDTO> clienteEntityToDTO(List<ClienteEntity> clientes){
		
		List<ClienteDTO> clientesDto = new ArrayList<ClienteDTO>();
		clientes.forEach(cliente ->{
			ClienteDTO clienteDTO = new ClienteDTO();
			clienteDTO.setCdEmpresa(cliente.getCdEmpresa());
			clienteDTO.setDescricao(cliente.getDescricao());
			clienteDTO.setIdCliente(cliente.getIdCliente());
			clienteDTO.setRazaoSocialEmpresa(cliente.getRazaoSocialEmpresa());
			clienteDTO.setContas(this.listarContas(cliente));
			clientesDto.add(clienteDTO);
			
		});
		return clientesDto;
	}
	
	private List<ContaDTO> listarContas(ClienteEntity cliente){
		
		List<ContaDTO> contas = new ArrayList<ContaDTO>();
		Optional<List<ContaEntity>> optional = contaRepository.findByCliente(cliente);		
		if(optional.isPresent()) {
			List<ContaEntity> contasEntity = optional.get();
			contasEntity.forEach(conta ->{
				ContaDTO dto = new ContaDTO();
				dto.setCdConta(conta.getCdConta());
				dto.setCdCliente(cliente.getCdEmpresa());
				dto.setIdContaCliente(conta.getIdContaCliente());
				dto.setSaldo(conta.getSaldo());
				dto.setTransacoes(this.listarTransacoes(conta));
				contas.add(dto);
			});
		}
		return contas;
	}
	
	public List<TransacaoDTO> listarTransacoes(ContaEntity conta){		

		List<TransacaoDTO> transacoes = new ArrayList<TransacaoDTO>();
		Optional<List<TransacaoEntity>> optional = transacaoRepository.findByContaOrigemOrContaDestino(conta, conta);		
		if(optional.isPresent()) {
			List<TransacaoEntity> listTransacao = optional.get();
			listTransacao.forEach( transacao ->{
				TransacaoDTO dto = new TransacaoDTO();
				dto.setContaDestino(transacao.getContaDestino());
				dto.setContaOrigem(transacao.getContaOrigem());
				dto.setIdTransacao(transacao.getIdTransacao());
				dto.setTipoTransacao(transacao.getTipoTransacao());
				dto.setValorTransacao(transacao.getValorTransacao());
				transacoes.add(dto);
			});
		}
		return transacoes;
		
	}



	@Override
	public UploadResponseDTO inserirCliente(ClienteDTO dto) {
		
		ClienteEntity cliente = new ClienteEntity();
		cliente.setRazaoSocialEmpresa(dto.getRazaoSocialEmpresa());		
		cliente.setCdEmpresa("CD"+dto.getRazaoSocialEmpresa());
		cliente.setDescricao(dto.getRazaoSocialEmpresa());
		clienteRepository.save(cliente);
		
		ContaEntity conta = new ContaEntity();
		conta.setCdConta("000123456000001");
		conta.setCliente(cliente);
		conta.setSaldo(new BigDecimal(100000.00));
		UploadResponseDTO dtoResponse = new UploadResponseDTO();
		contaRepository.save(conta);
						
		ContaEntity contaDestino = new ContaEntity();
		contaDestino.setCdConta("345");
		contaDestino.setCliente(cliente);
		contaDestino.setSaldo(new BigDecimal(100000.00));
		contaRepository.save(contaDestino);
		
		ContaEntity conta2 = new ContaEntity();
		conta2.setCdConta("000123456123456");
		conta2.setCliente(cliente);
		conta2.setSaldo(new BigDecimal(100000.00));
		contaRepository.save(conta2);
						
		ContaEntity contaDestino2 = new ContaEntity();
		contaDestino2.setCdConta("0");
		contaDestino2.setCliente(cliente);
		contaDestino2.setSaldo(new BigDecimal(100000.00));
		contaRepository.save(contaDestino2);
		
		ContaEntity conta3 = new ContaEntity();
		conta3.setCdConta("000123456234567");
		conta3.setCliente(cliente);
		conta3.setSaldo(new BigDecimal(100000.00));
		contaRepository.save(conta3);
						
		ContaEntity contaDestino3 = new ContaEntity();
		contaDestino3.setCdConta("9");
		contaDestino3.setCliente(cliente);
		contaDestino3.setSaldo(new BigDecimal(100000.00));
		contaRepository.save(contaDestino3);
		
		dtoResponse.setStatus(StatusEnum.SUCCESS.getStatus());
		dtoResponse.setMessage("Cliente incluido com sucesso");
		return dtoResponse;
	}

}
