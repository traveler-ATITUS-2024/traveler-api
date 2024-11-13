# Traveler API

![Java](https://img.shields.io/badge/Java-21-007396?style=for-the-badge&logo=java&logoColor=white) 
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white) 
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white) 
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-CC0200?style=for-the-badge&logo=flyway&logoColor=white)

## Desenvolvido por
![Gabriel Brocco de Oliveira](https://img.shields.io/badge/Gabriel%20Brocco%20de%20Oliveira-000000?style=for-the-badge)
 
##

## Documentação da API

A documentação completa da API está disponível [aqui](https://traveler-api-n420.onrender.com/swagger-ui/index.html).


## Descrição

O **Traveler API** é uma aplicação desenvolvida em Java com o framework Spring Boot, focada em gerenciar e rastrear despesas durante viagens. O projeto permite que os usuários registrem suas viagens, associem despesas às viagens, categorizem essas despesas, e obtenham relatórios detalhados.

## Funcionalidades

- Cadastro de usuários e autenticação com JWT.
- Registro de viagens com histórico e detalhes.
- Cadastro e associação de despesas a viagens.
- Relatório do valor total de despesas por viagem.
- Suporte para categorias de despesas (alimentação, transporte, hospedagem, etc.).
- API documentada com Swagger.
- Configuração de CORS para permitir acesso frontend.
- Configuração de migrations, para criação e versionamento do banco de dados.


## Configuração do Ambiente

### Pré-requisitos

- [Java 21](https://www.oracle.com/java/technologies/javase-jdk21-downloads.html)
- [Maven 3.8+](https://maven.apache.org/download.cgi)
- [Docker](https://www.docker.com/)
- [PostgreSQL 16](https://www.postgresql.org/)

### Instalação e Execução

1. **Clone o repositório**:

   ```bash
   git clone https://github.com/traveler-ATITUS-2024/traveler-api.git
   cd traveler-api

2. **Configure os arquivos .env e config.json com seus dados**:

   ```bash
   .env
   .config.json

3. **Execute a aplicação com o Docker**:

   ```bash
   docker compose up --build -d
   
4. **Se estiver local, acesse a api no endereço**:

   ```bash
   http://localhost:8080
