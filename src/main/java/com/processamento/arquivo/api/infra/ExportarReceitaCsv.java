package com.processamento.arquivo.api.infra;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.processamento.arquivo.api.constants.ReceitaExportarCsvConstants;
import com.processamento.arquivo.api.dto.receitaExportarCsv.ContasSicrediDto;
import com.processamento.arquivo.api.dto.receitaExportarCsv.ResultadoExportacaoContasSicrediCsvDto;
import com.processamento.arquivo.api.service.receitaExportarCsv.ReceitaService;

public class ExportarReceitaCsv {

	public static Function<List<ResultadoExportacaoContasSicrediCsvDto>, Boolean> escreveArquivoResultadoExportacaoContasSicrediCsvDto() {
		return (List<ResultadoExportacaoContasSicrediCsvDto> resultadoExportacaoContasSicredi) -> {
			try {
				Writer writer = new FileWriter("resultado.csv");
				ColumnPositionMappingStrategy<ResultadoExportacaoContasSicrediCsvDto> strategy = new ColumnPositionMappingStrategy<>();
				strategy.setType(ResultadoExportacaoContasSicrediCsvDto.class);
				strategy.setColumnMapping(ReceitaExportarCsvConstants.CAMPOS);
				StatefulBeanToCsv<ResultadoExportacaoContasSicrediCsvDto> beanToCsv = new StatefulBeanToCsvBuilder<ResultadoExportacaoContasSicrediCsvDto>(
						writer).withMappingStrategy(strategy).build();
				beanToCsv.write(resultadoExportacaoContasSicredi);
				writer.close();

				Path path = Paths.get("resultado.csv");
				List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
				lines.add(0, ReceitaExportarCsvConstants.CABECALHO);
				Files.write(path, lines, StandardCharsets.UTF_8);
				System.out.println("Arquivo Gerado com Sucesso");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (CsvDataTypeMismatchException e) {
				e.printStackTrace();
			} catch (CsvRequiredFieldEmptyException e) {
				e.printStackTrace();
			}
			return true;
		};
	}

	public static Function<String, List<ContasSicrediDto>> processaContasSicrediDto() {
		return (String nomeArquivo) -> {
			List<ContasSicrediDto> exportacaoContasSicredi = new ArrayList<>();
			try {
				Reader reader = Files.newBufferedReader(Paths.get(nomeArquivo));
				exportacaoContasSicredi = new CsvToBeanBuilder<ContasSicrediDto>(reader).withSeparator(';')
						.withType(ContasSicrediDto.class).build().parse();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return exportacaoContasSicredi;
		};
	}

	public static Function<List<ContasSicrediDto>, List<ResultadoExportacaoContasSicrediCsvDto>> processaResultadoExportacaoContasSicrediCsvDto(
			final ReceitaService receitaService) {
		return (List<ContasSicrediDto> contasSicredi) -> {
			List<ResultadoExportacaoContasSicrediCsvDto> resultadoExportacaoContasSicredi = new ArrayList<>();

			for (ContasSicrediDto contaSicredi : contasSicredi) {

				boolean resultado = false;

				try {
					resultado = ReceitaService.atualizarConta(contaSicredi);
				} catch (RuntimeException | InterruptedException e) {
					e.printStackTrace();
				}

				ResultadoExportacaoContasSicrediCsvDto exportacaoContasSicredi = new ResultadoExportacaoContasSicrediCsvDto();
				exportacaoContasSicredi.setAgencia(contaSicredi.getAgencia());
				exportacaoContasSicredi.setConta(contaSicredi.getConta());
				exportacaoContasSicredi.setSaldo(contaSicredi.getSaldo());
				exportacaoContasSicredi.setStatus(contaSicredi.getStatus());
				exportacaoContasSicredi.setResultado(resultado);
				resultadoExportacaoContasSicredi.add(exportacaoContasSicredi);
			}

			return resultadoExportacaoContasSicredi;
		};
	}
}