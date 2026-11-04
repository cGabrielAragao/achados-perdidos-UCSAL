# 08 — Modelagem UML

Os diagramas estão em Mermaid (renderizam no GitHub) e também exportados em PNG na pasta `diagrams/`. Cada elemento corresponde a uma necessidade real já definida nos requisitos e regras.

## Diagrama de Casos de Uso
Atores: **Visitante** (comunidade) e **Moderador**.

```mermaid
flowchart LR
    V(("Visitante"))
    M(("Moderador"))
    subgraph Sistema["Central de Achados e Perdidos"]
        UC1["Registrar objeto encontrado"]
        UC2["Buscar/filtrar objetos"]
        UC3["Ver detalhe (dados parciais)"]
        UC4["Solicitar recuperação (com prova)"]
        UC5["Analisar solicitação (aprovar/recusar)"]
        UC6["Concluir devolução (recuperado)"]
    end
    V --- UC1
    V --- UC2
    V --- UC3
    V --- UC4
    M --- UC5
    M --- UC6
    UC4 -.->|estende| UC3
```

## Diagrama de Classes (domínio)

```mermaid
classDiagram
    class Objeto {
        -Long id
        -String descricaoPublica
        -String caracteristicaSensivel
        -Categoria categoria
        -Local local
        -LocalDate dataEncontro
        -StatusObjeto status
        +solicitarRecuperacao(prova) SolicitacaoRecuperacao
        +aprovarDevolucao()
        +recusarSolicitacao()
        +confirmarRecuperado()
    }
    class EstadoObjeto {
        <<interface>>
        +aoSolicitar(Objeto)
        +aoAprovar(Objeto)
        +aoRecusar(Objeto)
        +aoRecuperar(Objeto)
    }
    class Disponivel
    class EmAnalise
    class Recuperado
    class SolicitacaoRecuperacao {
        -Long id
        -String provaPosse
        -StatusSolicitacao status
        -LocalDateTime dataSolicitacao
        -LocalDateTime dataDecisao
    }
    class FiltroBusca {
        <<interface>>
        +toSpecification() Specification~Objeto~
    }
    class FiltroPorCategoria
    class FiltroPorLocal
    class Categoria {
        <<enumeration>>
    }
    class Local {
        -Long id
        -String nome
    }
    class Usuario {
        -Long id
        -String nome
        -Papel papel
    }

    EstadoObjeto <|.. Disponivel
    EstadoObjeto <|.. EmAnalise
    EstadoObjeto <|.. Recuperado
    Objeto --> EstadoObjeto : estado atual
    Objeto "1" --> "0..*" SolicitacaoRecuperacao
    Objeto --> Categoria
    Objeto "0..*" --> "1" Local
    SolicitacaoRecuperacao "0..*" --> "0..1" Usuario : decidida por
    FiltroBusca <|.. FiltroPorCategoria
    FiltroBusca <|.. FiltroPorLocal
```

Observações:
- `caracteristicaSensivel` nunca é exposta na apresentação (RN05); serve de base para conferir a `provaPosse`.
- `EstadoObjeto` concentra as transições (padrão **State**); `Objeto` delega para o estado atual.
- `FiltroBusca` é o padrão **Strategy** sobre a Specification API do Spring Data.

## Diagrama de Atividades — fluxo principal

```mermaid
flowchart TD
    A([Início]) --> B[Visitante registra objeto encontrado]
    B --> C[Objeto fica DISPONIVEL e aparece na busca]
    C --> D[Outro visitante busca/filtra]
    D --> E{Encontrou candidato?}
    E -->|Não| D
    E -->|Sim| F[Abre detalhe com dados parciais]
    F --> G[Solicita recuperação informando prova]
    G --> H[Objeto vai para EM_ANALISE]
    H --> I[Moderador compara prova x característica sensível]
    I --> J{Prova confere?}
    J -->|Não| K[Recusa: objeto volta a DISPONIVEL]
    K --> D
    J -->|Sim| L[Aprova e registra devolução]
    L --> M[Objeto vira RECUPERADO]
    M --> N([Fim])
```

## Diagrama de Sequência — solicitar e aprovar recuperação

```mermaid
sequenceDiagram
    actor V as Visitante
    participant C as SolicitacaoController
    participant S as SolicitacaoService
    participant O as Objeto (domínio)
    participant R as Repositórios
    actor M as Moderador

    V->>C: POST solicitar(objetoId, prova)
    C->>S: criarSolicitacao(objetoId, prova)
    S->>R: buscar Objeto
    R-->>S: Objeto (DISPONIVEL)
    S->>O: solicitarRecuperacao(prova)
    O->>O: estado.aoSolicitar() → EM_ANALISE
    alt prova vazia
        O-->>S: ProvaObrigatoriaException
        S-->>C: erro tratado
        C-->>V: mensagem de validação
    else prova válida
        O-->>S: SolicitacaoRecuperacao(PENDENTE)
        S->>R: salvar objeto + solicitação
        S-->>C: ok
        C-->>V: confirmação
    end

    M->>C: aprovar(solicitacaoId)
    C->>S: aprovar(solicitacaoId)
    S->>O: confirmarRecuperado()
    O->>O: estado.aoAprovar()/aoRecuperar() → RECUPERADO
    S->>R: persistir
    S-->>M: devolução concluída
```

## Rastreabilidade UML → requisitos
- Casos de uso UC1–UC6 ↔ RF01–RF07.
- `EstadoObjeto` e as transições ↔ RN03–RN09.
- `caracteristicaSensivel`/`provaPosse` ↔ RF04/RF05 e H4 (diferencial validado).
