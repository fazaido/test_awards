# test_awards

- PRÉ REQUISITOS 
Linux com maven, make e java 16 instalados.

- EXECUTAR no terminal
cd configure
make

obs. comando make:
- lê o arquivo CSV dos filmes e cria sql inserts no arquivo data.sql com base no movielist.csv
- roda aplicação Spring Boot, que automaticamente executa os inserts contidos no data.sql do diretório resources do projeto Java.

- TESTE DE INTEGRACAO
Executar classe: AwardsControllerIntegrationTest.java
