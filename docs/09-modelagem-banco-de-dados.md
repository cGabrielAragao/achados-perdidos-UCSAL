# 09 — Modelagem do Banco de Dados

Modelo relacional mínimo e coerente com os requisitos. Evita redundância e só inclui entidades com uso real no fluxo.

## Entidades e justificativa
| Entidade | Por que existe | Observação |
|---|---|---|
| **local** | Filtrar por onde o objeto foi encontrado (RF03) e alimentar o dropdown | Tabela de apoio; locais do campus são cadastráveis |
| **objeto** | Núcleo do sistema (RF01–RF07) | Guarda `caracteristica_sensivel`, nunca exibida publicamente |
| **solicitacao_recuperacao** | Processo de devolução com prova (RF05/RF06) | 0..* por objeto; só uma pendente por vez (RN03) |
| **usuario** | Identificar o moderador que decide (RF06/RF08) | *Desejável*; no MVP pode haver um único moderador pré-carregado |
| **historico_status** | Registrar mudanças de status (RF10) | *Desejável*; apoia transparência na mostra |

**Decisão de modelagem:** `categoria` será um **enum** na aplicação (conjunto fixo observado na pesquisa: fone, garrafa/copo, material de estudo, vestuário, documentos, chaves, guarda-chuva, celular, outro), gravado como texto na coluna `objeto.categoria`. Motivo: conjunto estável e pequeno → não compensa uma tabela e um join. `local` fica como tabela porque os locais variam e convém cadastrá-los. (Alternativa: `categoria` como tabela, caso a equipe queira gerenciá-la pela UI — fica registrado como opção.)

## Relacionamentos e cardinalidades
- `local (1) ──< objeto (0..*)` — um local tem vários objetos; um objeto tem um local.
- `objeto (1) ──< solicitacao_recuperacao (0..*)` — um objeto pode ter várias solicitações ao longo do tempo (mas só uma PENDENTE).
- `usuario (1) ──< solicitacao_recuperacao (0..*)` — um moderador decide várias solicitações; `moderador_id` é opcional até a decisão.
- `objeto (1) ──< historico_status (0..*)` — cada mudança de status gera um registro.

## Esboço do esquema SQL (H2)

```sql
-- Tabela de apoio: locais do campus
CREATE TABLE local (
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome      VARCHAR(100) NOT NULL UNIQUE
);

-- Moderadores (desejável; papel simplificado no MVP)
CREATE TABLE usuario (
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome      VARCHAR(100) NOT NULL,
    papel     VARCHAR(20)  NOT NULL DEFAULT 'MODERADOR',
    senha_hash VARCHAR(255)
);

-- Núcleo: objetos encontrados
CREATE TABLE objeto (
    id                    BIGINT AUTO_INCREMENT PRIMARY KEY,
    categoria             VARCHAR(30)  NOT NULL,   -- enum da aplicação
    descricao_publica     VARCHAR(255) NOT NULL,
    caracteristica_sensivel VARCHAR(255) NOT NULL, -- nunca exibida publicamente
    local_id              BIGINT       NOT NULL,
    data_encontro         DATE         NOT NULL,
    status                VARCHAR(20)  NOT NULL DEFAULT 'DISPONIVEL',
    data_registro         TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_objeto_local FOREIGN KEY (local_id) REFERENCES local(id),
    CONSTRAINT ck_objeto_status CHECK (status IN ('DISPONIVEL','EM_ANALISE','RECUPERADO'))
);

-- Solicitações de recuperação
CREATE TABLE solicitacao_recuperacao (
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    objeto_id          BIGINT       NOT NULL,
    prova_posse        VARCHAR(500) NOT NULL,       -- obrigatória (RN04)
    contato_solicitante VARCHAR(120),               -- mínimo, p/ demonstração
    status             VARCHAR(20)  NOT NULL DEFAULT 'PENDENTE',
    data_solicitacao   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_decisao       TIMESTAMP,
    moderador_id       BIGINT,
    CONSTRAINT fk_sol_objeto     FOREIGN KEY (objeto_id)    REFERENCES objeto(id),
    CONSTRAINT fk_sol_moderador  FOREIGN KEY (moderador_id) REFERENCES usuario(id),
    CONSTRAINT ck_sol_status CHECK (status IN ('PENDENTE','APROVADA','RECUSADA'))
);

-- Histórico de status (desejável)
CREATE TABLE historico_status (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    objeto_id       BIGINT      NOT NULL,
    status_anterior VARCHAR(20),
    status_novo     VARCHAR(20) NOT NULL,
    data            TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_hist_objeto FOREIGN KEY (objeto_id) REFERENCES objeto(id)
);
```

## Integridade (resumo)
- Um objeto só pode ter **uma** solicitação `PENDENTE` por vez — garantido na regra de negócio (RN03), reforçável por verificação na aplicação.
- `prova_posse` é obrigatória (RN04).
- `status` restrito por `CHECK` ao conjunto válido (coerente com o padrão State da aplicação).
- Dados de demonstração serão fictícios (RNF03), carregados por script em `demo/`.

> Observação: com Spring Data JPA, as tabelas podem ser geradas por `ddl-auto` a partir das entidades; este SQL serve como referência do modelo e para a carga inicial/`import.sql`.
