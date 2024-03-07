package com.crdc.desafio.services.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crdc.desafio.dto.DataDTO;
import com.crdc.desafio.dto.ErrorDTO;
import com.crdc.desafio.dto.TransactionDTO;
import com.crdc.desafio.dto.UploadResponseDTO;
import com.crdc.desafio.entity.ContaEntity;
import com.crdc.desafio.entity.TransacaoEntity;
import com.crdc.desafio.enums.StatusEnum;
import com.crdc.desafio.exception.NegocioException;
import com.crdc.desafio.repositorio.ClienteRepository;
import com.crdc.desafio.repositorio.ContaRepository;
import com.crdc.desafio.repositorio.TransacaoRepository;
import com.crdc.desafio.services.DuplicataService;
import com.crdc.desafio.validation.DuplicataValidate;

@Transactional
@Service
public class DuplicataServiceImpl implements DuplicataService{
	
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ContaRepository contaRepository;
	@Autowired
	private TransacaoRepository transacaoRepository;
	@Autowired
	private DuplicataValidate validate; 

	public ClienteRepository getClienteRepository() {
		return clienteRepository;
	}

	public void setClienteRepository(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	public ContaRepository getContaRepository() {
		return contaRepository;
	}

	public void setContaRepository(ContaRepository contaRepository) {
		this.contaRepository = contaRepository;
	}

	public TransacaoRepository getTransacaoRepository() {
		return transacaoRepository;
	}

	public void setTransacaoRepository(TransacaoRepository transacaoRepository) {
		this.transacaoRepository = transacaoRepository;
	}

	@Override
	public UploadResponseDTO processarDuplicata(byte[] file) {

		UploadResponseDTO response = new UploadResponseDTO();
		List<String> duplicatas = this.lerArquivo(file);
		try {
			List<ErrorDTO> errors =  validate.validaArquido(duplicatas);
			if(errors!= null && !errors.isEmpty()) {        	
				response.setStatus(StatusEnum.ERROR.getStatus());
				response.setMessage("O arquivo CNAB possui formato inválido.");
				response.setErrors(errors);
				return response;
			}
		}catch (NegocioException e) {
			response.setStatus(StatusEnum.ERROR.getStatus());
			response.setMessage(e.getMessage());
			return response;
		}
		DataDTO dataDTO = new DataDTO(); 
		duplicatas.forEach(transacao -> {
			if(transacao.charAt(2)=='2') {
				TransactionDTO transactionDTO = this.converterLinhaEmDTO(transacao);				
				this.atualizaSaldo(transactionDTO);
				this.registraTransacao(transactionDTO);
				dataDTO.getTransactions().add(transactionDTO);
			}
				
		});
		response.setData(dataDTO);
		response.setStatus(StatusEnum.SUCCESS.getStatus());
		response.setMessage("Arquivo CNAB enviado e processado com sucesso.");		
		return response;
		
	}
	
	private void atualizaSaldo(TransactionDTO transacao) {
		
		Optional<ContaEntity> optionalContaOrigem = contaRepository.findByCdConta(transacao.getAccountOrigin());
		ContaEntity contaOrigem = optionalContaOrigem.get();
		Optional<ContaEntity> optionalContaDestino = contaRepository.findByCdConta(transacao.getAccountDestination());
		ContaEntity contaDestino = optionalContaDestino.get();		
		contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(transacao.getValue()));
		contaDestino.setSaldo(contaDestino.getSaldo().add(transacao.getValue()));
		
	}
	
	private void registraTransacao(TransactionDTO dto) {
		
		TransacaoEntity entity = new TransacaoEntity();		
		ContaEntity contaOrigem = new ContaEntity();
		ContaEntity contaDestino = new ContaEntity();
		
		Optional<ContaEntity> optionalContaOrigem = contaRepository.findByCdConta(dto.getAccountOrigin());
		if(optionalContaOrigem.isPresent()) {
			contaOrigem = optionalContaOrigem.get();
		}		
		Optional<ContaEntity> optionalContaDestino = contaRepository.findByCdConta(dto.getAccountDestination());
		if(optionalContaDestino.isPresent()) {
			contaDestino = optionalContaDestino.get();
		}
		
		entity.setContaOrigem(contaOrigem);
		entity.setContaDestino(contaDestino);
		entity.setTipoTransacao(dto.getType());
		entity.setValorTransacao(dto.getValue());
		transacaoRepository.save(entity);
	}
		
	private TransactionDTO converterLinhaEmDTO(String transacao) {
		TransactionDTO dto = new TransactionDTO();
		dto.setType(transacao.charAt(3));
		dto.setValue(new BigDecimal(transacao.substring(4, 19)).movePointLeft(2));
		dto.setAccountOrigin(transacao.substring(20, 35));
		dto.setAccountDestination(transacao.substring(36, transacao.length()));
		
		return dto;
	}
	
	private List<String> lerArquivo(byte[] fileData) {
        List<String> duplicata = new ArrayList<String>();
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(fileData);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String linha;
            while ((linha = reader.readLine()) != null) {
            	duplicata.add(linha);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return duplicata;	
	}
	
}
