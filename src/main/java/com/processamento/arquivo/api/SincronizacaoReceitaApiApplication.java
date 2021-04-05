package com.processamento.arquivo.api;

import static com.processamento.arquivo.api.infra.ExportarReceitaCsv.escreveArquivoResultadoExportacaoContasSicrediCsvDto;
import static com.processamento.arquivo.api.infra.ExportarReceitaCsv.processaContasSicrediDto;
import static com.processamento.arquivo.api.infra.ExportarReceitaCsv.processaResultadoExportacaoContasSicrediCsvDto;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.processamento.arquivo.api.service.receitaExportarCsv.ReceitaService;

@SpringBootApplication
public class SincronizacaoReceitaApiApplication {

	@Autowired
	private ReceitaService receitaService;

	private static ReceitaService receitaServiceStatic;

	public static void main(String[] args) {
		processaContasSicrediDto().andThen(processaResultadoExportacaoContasSicrediCsvDto(receitaServiceStatic))
				.andThen(escreveArquivoResultadoExportacaoContasSicrediCsvDto()).apply(args[0]);

		SpringApplication.run(SincronizacaoReceitaApiApplication.class, args);
	}

	@PostConstruct
	public void init() {
		receitaServiceStatic = receitaService;
	}

}
