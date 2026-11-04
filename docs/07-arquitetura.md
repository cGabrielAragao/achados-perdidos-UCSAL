# 07 — Arquitetura e Tecnologias

## Stack aprovada
| Camada | Tecnologia | Justificativa |
|---|---|---|
| Linguagem | **Java 21** | Linguagem da disciplina; permite demonstrar POOA. |
| Framework web | **Spring Boot 3.x** | Produtivo no prazo; injeção de dependência favorece SOLID/GRASP. |
| Interface | **Thymeleaf** (HTML/CSS) | Renderização server-side simples; sem complexidade de SPA. |
| Persistência | **Spring Data JPA** | Repositórios como abstração; expõe a Specification API (Strategy real). |
| Banco | **H2** (arquivo) | Embutido → roda em máquina limpa sem instalar nada (RNF01). |
| Build | **Maven** | Padrão, reprodutível. |
| Versionamento | **Git/GitHub** | Exigência de entrega. |

> PostgreSQL fica como evolução pós-MVP. H2 em modo arquivo preserva os dados entre execuções e elimina a maior fonte de atrito de reprodutibilidade na mostra.

## Arquitetura em camadas
```
┌─────────────────────────────────────────────┐
│ Apresentação — Controllers + templates Thymeleaf
│   recebe requisições, não contém regra de negócio
├─────────────────────────────────────────────┤
│ Aplicação/Serviço — ObjetoService, SolicitacaoService
│   orquestra casos de uso, transações, validações
├─────────────────────────────────────────────┤
│ Domínio — Objeto, EstadoObjeto (State), Solicitacao,
│   FiltroBusca (Strategy), exceções de domínio
│   concentra as regras (RN01–RN11)
├─────────────────────────────────────────────┤
│ Infraestrutura — Repositories (Spring Data JPA) + H2
└─────────────────────────────────────────────┘
```
Dependências apontam **para dentro** (apresentação → serviço → domínio ← infraestrutura via interfaces). Isso sustenta DIP e mantém o domínio testável sem web nem banco.

## Alternativas avaliadas (decisão da equipe)

**Como implementar o ciclo de status do objeto** — este é o ponto onde o padrão **State** entra:

| Opção | Prós | Contras | Recomendação |
|---|---|---|---|
| **A. Enum com guarda de transição** | Simples; fácil com JPA; já demonstra máquina de estados + encapsulamento | "State" fica implícito, menos visível como padrão GoF | Base segura |
| **B. State GoF (interface + estados concretos)** | Padrão explícito e demonstrável no pitch; polimorfismo real; pontua melhor no bônus | Mais classes; exige mapear enum↔estado ao carregar do banco | **Recomendada**, por mirar o +5 |

**Decisão (conforme seu aval):** Opção **B** — State GoF. A entidade `Objeto` guarda um `enum StatusObjeto` persistido no banco; ao carregar, uma *factory* devolve o objeto de estado correspondente (`Disponivel`, `EmAnalise`, `Recuperado`). As transições (RN03–RN09) só existem nos estados, não em `setStatus` público → é impossível pular etapa por engano. Isso é o que será mostrado ao vivo na mostra.

## Conceitos de POOA — onde, por quê, e como demonstrar
Cada item abaixo tem encaixe real no sistema (não inserido artificialmente).

| Conceito | Onde será aplicado | Problema que resolve | Como melhora o código | Como demonstrar na apresentação |
|---|---|---|---|---|
| **Encapsulamento** | `Objeto` esconde `status` e `caracteristicaSensivel`; só métodos de domínio alteram estado | Evita estado inconsistente e vazamento de dado sensível | Invariantes garantidas em um só lugar | Mostrar que não existe `setStatus` público |
| **State (GoF)** | Ciclo `DISPONIVEL → EM_ANALISE → RECUPERADO` | Transições inválidas (RN03–RN09) | Cada estado conhece só suas transições (polimorfismo) | Tentar uma transição inválida e ver a exceção |
| **Strategy (GoF)** | `FiltroBusca` por categoria, por local e combinado via **Specification** do Spring Data | Busca flexível sem `if` aninhado | Novo filtro = nova estratégia, sem tocar no serviço (OCP) | Alternar filtros na tela e no código |
| **SOLID** | SRP (camadas), DIP (serviço→interface de repositório), OCP (filtros) | Acoplamento e rigidez | Troca de implementação sem efeito cascata | Apontar a interface `ObjetoRepository` |
| **GRASP** | Controller; Information Expert (`Objeto` decide sua transição); Creator (factory de estado/solicitação) | Responsabilidade mal distribuída | Alta coesão, baixo acoplamento | Explicar por que a regra mora na entidade |
| **Collections & Streams** | Montagem/filtragem/ordenação das listagens e contagem por categoria | Manipulação de listas | Código declarativo e enxuto | Mostrar o `stream()` que ordena a listagem |
| **Tratamento de exceções** | `TransicaoInvalidaException`, `ProvaObrigatoriaException` + `@ControllerAdvice` | Erros do fluxo principal (RNF04) | Erros tratados num ponto, UI não quebra | Submeter solicitação sem prova |
| **Interfaces/abstrações** | Repositórios, `EstadoObjeto`, `FiltroBusca` | Dependência de implementação concreta | Testabilidade e substituição | Mostrar os contratos |
| **Arquitetura em camadas** | Estrutura de pacotes `apresentacao/aplicacao/dominio/infraestrutura` | Mistura de responsabilidades | Navegação e manutenção | Abrir a árvore de pacotes |

> **Herança/polimorfismo** aparecem de forma legítima no State (estados implementam uma interface comum); não criaremos hierarquias só para "ter herança".

## Estrutura de pacotes
```
br.ucsal.achadosperdidos
├── apresentacao/      (controllers, DTOs de formulário)
├── aplicacao/         (services)
├── dominio/
│   ├── objeto/        (Objeto, StatusObjeto, estados, exceções)
│   ├── solicitacao/   (SolicitacaoRecuperacao)
│   └── busca/         (FiltroBusca - specifications)
└── infraestrutura/    (repositories JPA, config, carga de demo)
```
