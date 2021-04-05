# Exportação de dados em arquivo CSV


### Cenário de Negócio:
**Todo dia útil por volta das 6 horas da manhã um colaborador da retaguarda do Sicredi recebe e organiza as informações de 
contas para enviar ao Banco Central. Todas agencias e cooperativas enviam arquivos Excel à Retaguarda. Hoje o Sicredi 
já possiu mais de 4 milhões de contas ativas.
Esse usuário da retaguarda exporta manualmente os dados em um arquivo CSV para ser enviada para a Receita Federal, 
antes as 10:00 da manhã na abertura das agências.**

### Requisito:
**Usar o "serviço da receita" (fake) para processamento automático do arquivo.**

### Funcionalidade:
* Criar uma aplicação SprintBoot standalone. Exemplo: java -jar SincronizacaoReceita <input-file>
* Processa um arquivo CSV de entrada com o formato abaixo.
* Envia a atualização para a Receita através do serviço (SIMULADO pela classe ReceitaService).
* Retorna um arquivo com o resultado do envio da atualização da Receita. Mesmo formato adicionando o resultado em uma 
nova coluna.

### Versões Utilizadas
* Java 11
* Maven 3.6.3
* Spring Boot 2.4.4
* OpenCSV 5.4

### Executar
```./mvnw clean install```

```java -jar target/sincronizacao-receita-api-0.0.1-SNAPSHOT.jar entrada.csv```

### Resultado
**resultado.csv**




