# Projeto Unisoma Salary

Esta é uma API RESTful que permite gerenciar o salário dos funcionários de uma empresa. Ele suporta operações como o cálculo do aumento salarial com base em várias faixas de salário, cálculo do imposto devido com base no salário atual e a manutenção de informações de funcionários, como nome, CPF, data de nascimento, telefone, endereço e salário.

## Tecnologias Utilizadas

- Spring Boot
- JPA / Hibernate
- Postgres
- JUnit
- Mockito

## Recursos da API

A API fornece os seguintes endpoints:

- `POST api/employer` : Cria um novo funcionário.
- `PUT api/employer/{cpf}/salary` : Atualiza o salário de um funcionário e retorna os detalhes do novo salário.
- `GET api/employer/{cpf}/tax` : Calcula e retorna o imposto devido de um funcionário com base no salário atual.
- `GET api/employer` : Retorna a lista de todos os funcionários com seus detalhes.

## Como Executar

Para executar este projeto localmente, você precisa ter instalado na sua máquina o Docker e o Docker-compose. Siga as etapas abaixo:

1. Clone o repositório para a sua máquina local usando `git clone https://github.com/elitet/unisoma-salary.git`.

2. Navegue até o diretório do projeto usando `cd unisoma-salary`.

3. Execute o comando `docker-compose up --build` para iniciar a aplicação.

A aplicação será iniciada em `http://localhost:3000`, junto com o banco de dados.

## Como Executar os Testes

Para executar os testes, execute o comando `mvn test` no diretório principal do projeto.
