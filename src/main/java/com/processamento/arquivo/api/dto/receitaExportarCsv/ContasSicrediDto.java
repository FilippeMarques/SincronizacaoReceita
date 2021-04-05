package com.processamento.arquivo.api.dto.receitaExportarCsv;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class ContasSicrediDto {

	@NotBlank
	@Pattern(regexp = "(\\d{6})")
	private String conta;

	@NotBlank
	@Pattern(regexp = "(\\d{4})")
	private String agencia;

	@NotBlank
	@Pattern(regexp = "(A|B|I|P)")
	private String status;

	@NotBlank
	private String saldo;

}