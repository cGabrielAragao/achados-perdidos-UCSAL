# CLAUDE_CODE_GUIDE.md

Guia principal para a implementação da **Central de Achados e Perdidos — UCSal** com o Claude Code. Trabalhe de forma **incremental**, respeitando este documento e a pasta `docs/`. **Não implemente nada fora do escopo aprovado.**

---

## 1. Contexto
Projeto acadêmico do POOALAB 2026.2 (disciplina POOA, UCSal). Entrega: 23/11/2026. O MVP precisa rodar ao vivo em máquina limpa e demonstrar aplicação pertinente de conceitos de POOA.

## 2. Problema e público
A comunidade da UCSal já **localiza** objetos perdidos (achados-e-perdidos físico + grupo da faculdade), mas a **devolução não tem controle de posse**. Público validado: estudantes. O sistema centraliza o registro e controla a devolução por verificação de posse. Detalhes em `docs/02-problema-e-validacao.md`.

## 3. Objetivo do MVP
Executar de ponta a ponta: **registrar achado → buscar → solicitar recuperação com prova → moderador aprova/recusa → RECUPERADO**. Escopo completo em `docs/03-definicao-do-mvp.md`.

## 4. Stack
Java 21 · Spring Boot 3.x · Spring Data JPA · Thymeleaf · H2 (arquivo) · Maven. Sem PostgreSQL no MVP. Sem front-end SPA.

## 5. Arquitetura
Camadas: `apresentacao → aplicacao → dominio ← infraestrutura`. Regras de negócio moram no **domínio**. Detalhes e justificativas em `docs/07-arquitetura.md`.

## 6. Estrutura de diretórios esperada
```
src/main/java/br/ucsal/achadosperdidos/
├── apresentacao/      # Controllers + DTOs de formulário
├── aplicacao/         # Services (ObjetoService, SolicitacaoService)
├── dominio/
│   ├── objeto/        # Objeto, StatusObjeto, EstadoObjeto + Disponivel/EmAnalise/Recuperado, exceções
│   ├── solicitacao/   # SolicitacaoRecuperacao, StatusSolicitacao
│   └── busca/         # FiltroBusca (Strategy via Specification)
└── infraestrutura/    # Repositories JPA, config, carga de dados de demo
src/main/resources/
├── templates/         # Thymeleaf
├── static/            # CSS
├── application.properties
└── import.sql         # dados de demonstração fictícios
src/test/java/...      # testes
```

## 7. Entidades e relacionamentos
Ver `docs/09-modelagem-banco-de-dados.md`. Resumo: `objeto` (núcleo, com `caracteristica_sensivel` nunca exibida), `local` (apoio), `solicitacao_recuperacao` (com `prova_posse` obrigatória), `usuario` (moderador, desejável), `historico_status` (desejável). `categoria` é **enum** na aplicação.

## 8. Requisitos funcionais prioritários (ordem de valor)
Implementar primeiro os **Indispensáveis**: RF01, RF02, RF03, RF04, RF05, RF06, RF07. Só depois os Desejáveis (RF08 auth, RF09 imagem, RF10 histórico). **Não** implementar RF11/RF12. Detalhe em `docs/04-requisitos-funcionais.md`.

## 9. Regras de negócio (inegociáveis)
Ver `docs/06-regras-de-negocio.md`. Pontos críticos:
- Objeto nasce `DISPONIVEL`; só objetos `DISPONIVEL` aparecem na busca.
- Solicitação exige `prova_posse` (senão `ProvaObrigatoriaException`).
- Transições de status só pelos estados (padrão State); nunca um `setStatus` público.
- Só o moderador aprova/recusa e conclui a devolução.
- `caracteristica_sensivel` jamais aparece em listagem ou detalhe público.

## 10. Padrões de implementação (mirando o bônus POOA — demonstrar, não decorar)
- **State (GoF):** interface `EstadoObjeto` com `Disponivel`, `EmAnalise`, `Recuperado`. `Objeto` delega transições ao estado. Persistir `StatusObjeto` (enum) e reconstruir o estado via factory ao carregar.
- **Strategy (GoF):** `FiltroBusca` → `Specification<Objeto>` (Spring Data). Filtros por categoria e por local combináveis, sem `if` aninhado no service.
- **SOLID/GRASP:** services dependem de interfaces de repositório (DIP); entidade é Information Expert das próprias regras; novas estratégias de filtro sem alterar o service (OCP).
- **Collections & Streams** nas listagens/ordenação.
- **Exceções de domínio** + `@ControllerAdvice` global para não quebrar a UI.

## 11. Convenções de código
- Nomes e **comentários em português**.
- Pacotes por camada/contexto (ver seção 6).
- Entidades sem setters que violem invariantes; preferir métodos de intenção (`solicitarRecuperacao`, `confirmarRecuperado`).
- DTOs de formulário na camada de apresentação; não expor entidade direto no template quando houver dado sensível.
- Commits pequenos e frequentes, em português.

## 12. Estratégia de testes
- **Unitários de domínio** (prioridade): transições do State (válidas e inválidas), obrigatoriedade da prova, ocultação do dado sensível.
- **Teste de serviço** do fluxo criar→analisar→recuperar.
- **Roteiro de teste manual** do caminho principal em `demo/` (para a mostra).
- Testar primeiro o caminho feliz, depois erros previsíveis (prova vazia, transição inválida, objeto inexistente).

## 13. Critérios de aceitação (do MVP)
Ver `docs/03-definicao-do-mvp.md`, seção "Critérios de pronto". Resumo: registrar → aparece na busca; busca filtra e não vaza dado sensível; solicitação criada com prova; moderador aprova/recusa e o status muda de forma persistente; tudo roda seguindo o README em ambiente limpo.

## 14. NÃO implementar (fora do escopo)
Cadastro público de usuários, notificações, painel administrativo completo/relatórios, qualquer integração com sistemas ou bases da UCSal, upload de imagem antes de o fluxo central estar 100%.

## 15. Ordem recomendada de desenvolvimento
1. Esqueleto Spring Boot + H2 + "hello" executável (valida reprodutibilidade cedo).
2. Entidades `Objeto`, `Local`, enum `Categoria` + repositórios + `import.sql` com dados de demo.
3. RF01 (registrar) e RF02/RF03 (listar/filtrar) com ocultação (RF04).
4. Padrão **State** e transições (RN03–RN09) com testes de domínio.
5. RF05 (solicitar com prova) e RF06/RF07 (aprovar/recusar/recuperar).
6. Padrão **Strategy** nos filtros + Streams nas listagens.
7. Tratamento de exceções global + validações.
8. (Desejável) RF08 auth de moderador, depois RF10 histórico, depois RF09 imagem.
9. Dados de demonstração estáveis + roteiro em `demo/`.
10. README final + testes passando em ambiente limpo.

> Regra de ouro: **proteger o fluxo principal**. Se o tempo apertar, reduzir Desejáveis, nunca o caminho central.
