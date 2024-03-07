package com.crdc.desafio.validation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crdc.desafio.dto.ErrorDTO;
import com.crdc.desafio.entity.ContaEntity;
import com.crdc.desafio.exception.NegocioException;
import com.crdc.desafio.repositorio.ContaRepository;

@Service
public class DuplicataValidate{
		
	@Autowired
	private ContaRepository contaRepository;
	
	private int l = 0;
	
	public List<ErrorDTO> validaArquido(List<String> duplicata) throws NegocioException {		
		List<ErrorDTO> errors = new ArrayList<ErrorDTO>();
		l = 1 ;
		duplicata.forEach( linha -> {			
			ErrorDTO error;
			if(linha.startsWith("001")) {
				error = validaHeader(linha);
				if(error != null) {
					error.setLine(l);
					errors.add(error);
				}
			}else if(linha.startsWith("002")) {
				error = validaTransacao(linha);
				if(error != null) {
					error.setLine(l);
					errors.add(error);
				}
			}else if(linha.startsWith("003")) {
				error = validaRodape(linha); 
				if(error != null) {
					error.setLine(l);
					errors.add(error);
				}
			}else {
				error = new ErrorDTO();
				error.setLine(l);
				error.setError("Tipo de registro inválido.");
				errors.add(error);
			}
			l = l+1;
		});
		return errors;
	}
	
	private ErrorDTO validaHeader(String linha) {
		if(linha.length() < 49 || linha.length() > 80) {
			throw new NegocioException("Erro ao processar o arquivo CNAB. Certifique-se de que o arquivo esteja no formato posicional correto.");
		}
		String razaoSocial = linha.substring(3, 33);
		String identificadorPessoa = linha.substring(33, 46);		
		if(razaoSocial.isBlank()){;
		return criaErrorDTO("Razao social inválida.");
		}
		if(identificadorPessoa.isBlank()) {
			return criaErrorDTO("Identificador pessoa inválida.");
		}		
		return null;
	}
	
	private ErrorDTO validaTransacao(String linha) {
		if(linha.length() < 37 || linha.length() > 52) {
			throw new NegocioException("Erro ao processar o arquivo CNAB. Certifique-se de que o arquivo esteja no formato posicional correto.");
		}
		char tpTransacao = linha.charAt(3);
		if (tpTransacao != 'C' && tpTransacao != 'D' && tpTransacao != 'T') {
			return criaErrorDTO("Tipo de transação inválido.");
        }		
		try {
			BigDecimal vlTransacao = linha.substring(4, 19).isBlank() ? BigDecimal.ZERO : new BigDecimal(linha.substring(4, 19));
			if(vlTransacao.compareTo(BigDecimal.ZERO)<1) {
				return criaErrorDTO("Valor da transação está fora do formato válido.");
			}
		}catch (Exception e) {
			return criaErrorDTO("Valor da transação está fora do formato válido.");
		}		
		String contaOrigem = linha.length() > 20 ? linha.substring(20, 35) : "";
		if(contaOrigem.isBlank()) {
			return criaErrorDTO("Conta de origem não informada.");
		}else {
			Optional<ContaEntity> optionalContaOrigem = contaRepository.findByCdConta(contaOrigem);
			if(optionalContaOrigem.isEmpty()){
				return criaErrorDTO("Conta de origem inválida.");
			}
		}
		String contaDestino = linha.substring(36, linha.length());
		if(contaDestino.isBlank()) {
			return criaErrorDTO("Conta de destino não informada.");
		}else {
			Optional<ContaEntity> optionalContaDestino = contaRepository.findByCdConta(contaDestino);
			if(optionalContaDestino.isEmpty()){
				return criaErrorDTO("Conta destino inválida.");
			}
		}		
		return null;
	}
	
	private ErrorDTO criaErrorDTO(String msg) {
		ErrorDTO error = new ErrorDTO();
		error.setError(msg);
		return error;
	}
	
	private ErrorDTO validaRodape(String linha) {
		if(linha.length() > 80) {
			throw new NegocioException("Erro ao processar o arquivo CNAB. Certifique-se de que o arquivo esteja no formato posicional correto.");
		}
		return null;
	}
	

}
