# Central de Achados e Perdidos — UCSal

> **Em construção (Incremento 1 — esqueleto).** Este README será finalizado na Etapa 5 (estabilização), conforme o README mínimo exigido pelo manual POOALAB.

Sistema web para registrar objetos encontrados no campus da UCSal, buscá-los e recuperá-los de forma organizada e **com verificação de posse**, reduzindo a retirada indevida de itens.

- **Disciplina:** Programação Orientada a Objetos Avançada (POOA) — UCSal, 2026.2
- **Atividade:** POOALAB — MVP
- **Entrega final:** 23/11/2026 · **Mostra/pitch (ADS):** 01/12/2026

## Como executar

Pré-requisito: **JDK 21** instalado (o wrapper do Maven baixa o restante automaticamente).

```bash
./mvnw spring-boot:run
```

Acesse `http://localhost:8080`. O banco H2 roda em modo arquivo (`data/achadosperdidos.mv.db`), então os dados persistem entre execuções. Esse diretório não é versionado (ver `.gitignore`).

No Windows (cmd/PowerShell), use `mvnw.cmd spring-boot:run`.

## Estrutura do repositório

```text
central-achados-perdidos/
├── README.md
├── pom.xml
├── mvnw / mvnw.cmd
├── docs/
│   ├── 01-visao-geral.md ... 09-modelagem-banco-de-dados.md
│   └── validacao/
├── diagrams/
├── src/
│   ├── main/java/br/ucsal/achadosperdidos/
│   │   ├── apresentacao/      # Controllers + DTOs de formulário
│   │   ├── aplicacao/         # Services
│   │   ├── dominio/           # Entidades, State, Strategy, exceções
│   │   └── infraestrutura/    # Repositories JPA, config
│   ├── main/resources/
│   │   ├── templates/         # Thymeleaf
│   │   ├── static/css/
│   │   └── application.properties
│   └── test/java/...
└── demo/       (dados e roteiro de demonstração — a criar)
```

## Status do desenvolvimento

- [x] Incremento 1 — esqueleto Spring Boot + H2 executável.
- [ ] Incremento 2 — entidades `Objeto`/`Local`/`Categoria` + repositórios + dados de demonstração.
- [ ] Incremento 3 em diante — ver `CLAUDE_CODE_GUIDE.md`, seção 15.

## Integrantes

> Preencher nomes completos e turma antes da entrega.
