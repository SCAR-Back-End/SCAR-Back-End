# SCAR Backend (RFID)

Backend do Sistema de Controle de Acesso RFID. A aplicação expõe endpoints para autenticação, cadastro e manutenção de usuários e chaves, consulta de logs e leitura de crachás RFID com decisão automática de retirada ou devolução.

## Principais recursos

- Autenticação por matrícula com validação de usuário ativo
- CRUD completo de usuários
- CRUD completo de chaves
- Leitura de RFID com fluxo de retirada, devolução ou negação
- Registro e consulta de logs de uso
- API REST com CORS liberado para todos os domínios
- Persistência em MySQL com Spring Data JPA

## Stack

- Java 21
- Spring Boot 3.3.5
- Spring Web
- Spring Data JPA
- Spring Validation
- MySQL Connector/J
- Lombok

## Requisitos

- JDK 21
- MySQL 8+
- Maven ou o wrapper do projeto (`mvnw` / `mvnw.cmd`)

## Configuração

As configurações ficam em `src/main/resources/application.properties` e usam variáveis de ambiente:

- `PORT` — porta do servidor, padrão `8080`
- `DB_URL` — URL de conexão com o MySQL
- `DB_USERNAME` — usuário do banco
- `DB_PASSWORD` — senha do banco

Exemplo no PowerShell:

```powershell
$env:PORT="8080"
$env:DB_URL="jdbc:mysql://localhost:3306/chavesrfid?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Sao_Paulo"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="sua_senha"
```

> Observação: o projeto está configurado com `spring.jpa.hibernate.ddl-auto=update`, então as tabelas são criadas/atualizadas automaticamente.

## Como executar

```powershell
./mvnw spring-boot:run
```

No Windows, você também pode usar:

```powershell
.\mvnw.cmd spring-boot:run
```

A API ficará disponível em `http://localhost:8080`.

## Endpoints

### Autenticação

#### POST `/api/auth/login`

Autentica um usuário pela matrícula e senha.

**Request**

```json
{
  "matricula": "20240001",
  "senha": "20240001"
}
```

**Respostas possíveis**

- `200 OK` — login realizado com sucesso
- `400 Bad Request` — matrícula ou senha não informada
- `401 Unauthorized` — senha inválida
- `403 Forbidden` — usuário inativo
- `404 Not Found` — usuário não encontrado

**Response de sucesso**

```json
{
  "id": 1,
  "nome": "Maria Silva",
  "matricula": "20240001",
  "perfil": "ADMIN",
  "ativo": true
}
```

### Usuários

Base: `/api/usuario`

- `GET /api/usuario` — lista todos os usuários
- `GET /api/usuario/{id}` — busca usuário por ID
- `POST /api/usuario` — cria usuário
- `PUT /api/usuario/{id}` — atualiza usuário
- `DELETE /api/usuario/{id}` — desativa usuário

**Payload de criação/atualização**

```json
{
  "nome": "Maria Silva",
  "uidRfid": "04AABBCCDD",
  "matricula": "20240001",
  "perfil": "ADMIN",
  "ativo": true
}
```

> No `POST`, o backend define `ativo=true` automaticamente.

### Chaves

Base: `/api/chave`

- `GET /api/chave` — lista todas as chaves
- `GET /api/chave/{id}` — busca chave por ID
- `POST /api/chave` — cria chave
- `PUT /api/chave/{id}` — atualiza chave
- `DELETE /api/chave/{id}` — remove chave

**Payload de criação/atualização**

```json
{
  "nomeSala": "Sala 101",
  "status": "DISPONIVEL"
}
```

### RFID

#### POST `/api/rfid/scan`

Processa a leitura do UID do crachá.

**Request**

```json
{
  "uid": "04AABBCCDD"
}
```

**Comportamento**

- Se o usuário não for encontrado, o retorno é `NEGADO`
- Se o usuário já tiver uma retirada pendente, o sistema registra uma `DEVOLUCAO`
- Caso contrário, o sistema registra uma `RETIRADA` na primeira chave disponível
- Se não houver chaves disponíveis, o retorno é `NEGADO`

**Respostas possíveis**

- `200 OK` — retirada ou devolução concluída
- `403 Forbidden` — negado

**Response de exemplo**

```json
{
  "tipo": "RETIRADA",
  "mensagem": "Chave da sala Sala 101 retirada com sucesso",
  "usuario": "Maria Silva"
}
```

### Logs

Base: `/api/logs`

- `GET /api/logs` — lista todos os logs em ordem decrescente de data
- `GET /api/logs/ativos` — lista as retiradas pendentes

**Exemplo de retorno de `/api/logs/ativos`**

```json
[
  {
    "chaveId": 1,
    "nomeSala": "Sala 101",
    "usuarioId": 2,
    "nomeUsuario": "Maria Silva",
    "perfil": "ADMIN",
    "dataHora": "2026-06-15T10:30:00"
  }
]
```

## Modelo de dados

- `usuarios`: `id`, `nome`, `uid_rfid`, `matricula`, `perfil`, `ativo`
- `chaves`: `id`, `nome_sala`, `status`
- `logs`: `id`, `usuario_id`, `chave_id`, `tipo`, `data_hora`

## Observações

- Os endpoints estão com CORS liberado para qualquer origem.
- Para testar o fluxo RFID, cadastre pelo menos um usuário ativo com `uidRfid` e algumas chaves com status `DISPONIVEL`.
- O fluxo de login usa a matrícula como senha, conforme a regra implementada no backend.

## Testes

```powershell
./mvnw test
```

