# Leilão Spring

Aplicação web de um leilão em Java utilizando 

## Pré - requisitos

Antes de iniciar a aplicação, confira se tem instalado:
- Java 
- Docker 
- Maven 
- IntelliJ preferencialmente

## Iniciando

### 1. Clone o repositório

```
git clone https://github.com/mzagury25/finalLeilaoSpring.git
```
### 2. Setup do banco de dados

Esse projeto utiliza MySQL rodando num container Docker. Para iniciar o banco de dados:

Inicie a instância MySQL com a seguinte configuração:
- Nome do banco de dados: BDLeilao
- Porta: 3306
- Usuário: user
- Senha: leilaopass

### 3. Rodando o projeto

```bash
mvn clean install
```
```
mvn spring-boot:run
```
`http://localhost:8080`


## Stack utilizada:
- Java 21
- Spring MVC
- Spring Data JPA
- MySQL 8.0.1
- Docker
