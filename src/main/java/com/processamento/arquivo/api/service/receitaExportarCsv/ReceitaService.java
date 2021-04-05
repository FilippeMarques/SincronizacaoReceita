package com.processamento.arquivo.api.service.receitaExportarCsv;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.stereotype.Service;

import com.processamento.arquivo.api.dto.receitaExportarCsv.ContasSicrediDto;

@Service
public class ReceitaService {
	public static boolean atualizarConta(ContasSicrediDto contasSicrediDto)
			throws RuntimeException, InterruptedException {

		ContasSicrediDto contasSicredi = new ContasSicrediDto();
		contasSicredi.setAgencia(contasSicrediDto.getAgencia());
		contasSicredi.setConta(contasSicrediDto.getConta().replace("-", ""));
		contasSicredi.setStatus(contasSicrediDto.getStatus());
		contasSicredi.setSaldo(contasSicrediDto.getSaldo().replace(",", "."));

		boolean isContaSicrediValida = validarContasSicredi(contasSicredi);

		// Simula tempo de resposta do serviço (entre 1 e 5 segundos)
		long wait = Math.round(Math.random() * 4000) + 1000;
		Thread.sleep(wait);

		// Simula cenario de erro no serviço (0,1% de erro)
		long randomError = Math.round(Math.random() * 1000);
		if (randomError == 500) {
			throw new RuntimeException("Error");
		}

		return isContaSicrediValida;
	}

	public static boolean validarContasSicredi(final ContasSicrediDto contasSicredi) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		return validator.validate(contasSicredi).isEmpty();
	}

}