# Alô Governo - Backend

Este é o repositório do sistema "Alô Governo", desenvolvido com Spring Boot e JPA. O sistema permite que os usuários façam publicações e comentários, além de apoiar publicações e realizar o upload de mídias com URLs pré-assinadas da AWS S3.

## Requisitos

Certifique-se de que as seguintes ferramentas estão instaladas em seu ambiente:

- [Java 17+](https://adoptium.net/)
- [Maven 3.8+](https://maven.apache.org/install.html)
- [PostgreSQL](https://www.postgresql.org/download/)

## Configuração do Ambiente Local

### Clonando o repositório

1. Clone o repositório para sua máquina local:

    ```bash
    git clone https://github.com/cesarfreire/alogoverno-backend.git
    cd alogoverno-backend
    ```

### Configuração do Banco de Dados

1. Instale e configure o PostgreSQL localmente.
2. Crie um banco de dados para o projeto:

    ```sql
    CREATE DATABASE alogoverno;
    ```

3. Configure as credenciais de acesso ao banco de dados no arquivo `application.properties` ou `application.yml`:

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/alogoverno
    spring.datasource.username=seu-usuario
    spring.datasource.password=sua-senha
    spring.jpa.hibernate.ddl-auto=update
    ```

### Configuração da AWS S3 para Upload de Mídias

1. Crie um bucket no AWS S3 e configure as permissões de acesso.
2. No arquivo `application.properties`, adicione suas credenciais de acesso da AWS:

    ```properties
   alogoverno.app.aws.credentials.accessKey=SEU_ACCESS_KEY
   alogoverno.app.aws.credentials.secretKey=SUA_SECRET_KEY
   alogoverno.app.aws.s3.bucket=nome-do-seu-bucket
    ```

## Executando a Aplicação Localmente

1. Após configurar as dependências e o banco de dados, você pode rodar a aplicação localmente usando o Maven:

    ```bash
    ./mvnw spring-boot:run
    ```

2. A aplicação estará disponível em [http://localhost:8080](http://localhost:8080).

## Endpoints Principais da API

Este projeto está configurado com o Swagger para documentação da API. 
Para visualizar os endpoints disponíveis na API, acesse a documentação Swagger em [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).

## Rodando Testes

Para rodar os testes da aplicação, execute o comando:

```bash
./mvnw test
