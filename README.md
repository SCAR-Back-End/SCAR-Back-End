# SCAR Backend (RFID)

Backend do Sistema de Controle de Acesso RFID. Exponha endpoints para leitura de crachas e registro de retiradas/devolucoes de chaves, com persistencia em MySQL via JPA.

## Principais recursos

- Leitura de RFID e decisao de acesso (retirada/devolucao/negado)
- Registro de logs de uso de chaves
- Persistencia em MySQL usando Spring Data JPA
- API REST com CORS liberado para todos os dominios

## Stack

- Java 21
- Spring Boot 3.3.x (Web, Data JPA, Validation)
- MySQL (driver runtime)
- Lombok

## Requisitos

- JDK 21
- MySQL 8+
- Maven (ou use o wrapper `mvnw`/`mvnw.cmd`)

## Configuracao

As configuracoes padrao estao em `src/main/resources/application.properties`.
Recomenda-se sobrescrever as credenciais do banco por variaveis de ambiente.

Variaveis suportadas pelo Spring Boot:

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`

Exemplo (PowerShell):

```powershell
$env:SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/chavesrfid?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Sao_Paulo"
$env:SPRING_DATASOURCE_USERNAME="root"
$env:SPRING_DATASOURCE_PASSWORD="sua_senha"
```

## Como rodar

```powershell
./mvnw spring-boot:run
```

A API fica disponivel em `http://localhost:8080`.

## Endpoints

### POST `/api/rfid/scan`

Entrada:

```json
{
  "uid": "04AABBCCDD"
}
```

Respostas:

- `200 OK` (RETIRADA ou DEVOLUCAO)
- `403 FORBIDDEN` (NEGADO)

Exemplo de resposta:

```json
{
  "tipo": "RETIRADA",
  "mensagem": "Chave da sala 101 retirada com sucesso",
  "usuario": "Maria Silva"
}
```

### GET `/api/logs`

Retorna a lista de logs cadastrados.

## Modelo de dados (tabelas)

- `usuarios`: `id`, `nome`, `uid_rfid`, `perfil`
- `chaves`: `id`, `nome_sala`, `status`
- `logs`: `id`, `usuario_id`, `chave_id`, `tipo`, `data_hora`

## Observacoes

- O projeto utiliza `spring.jpa.hibernate.ddl-auto=update`, entao o schema e criado/atualizado automaticamente.
- Para testar `POST /api/rfid/scan`, cadastre registros em `usuarios` e `chaves`.

## Testes

```powershell
./mvnw test
```

