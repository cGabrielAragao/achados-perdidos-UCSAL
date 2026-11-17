# Central de Achados e Perdidos — UCSal

Aplicação web que centraliza o registro de objetos encontrados no campus da UCSal e organiza a **devolução segura** ao dono, por meio de verificação de posse. Nasceu como projeto acadêmico do POOALAB 2026.2 (disciplina de Programação Orientada a Objetos Avançada). O fluxo principal — registrar um achado, buscá-lo, solicitar a recuperação com uma prova de posse e concluir a devolução sob moderação — está completo e executável.

## Problema, público e proposta de valor

A comunidade da UCSal já **localiza** objetos perdidos com relativa eficácia (achados-e-perdidos físico, equipe de limpeza, grupo da faculdade). A dor real, apontada na validação, não é encontrar — é a **devolução sem controle de posse**: qualquer pessoa pode retirar um item alegando ser o dono, e os registros ficam dispersos. A Central digitaliza o registro em um só lugar e adiciona verificação de posse (informações parciais + prova antes da liberação), reduzindo a apropriação indevida.

- **Público:** estudantes da UCSal (público alcançado na validação).
- **Proposta de valor:** um único lugar para registrar o que foi achado e devolver ao dono certo, com dados sensíveis ocultos e prova de posse conferida por um moderador.

Detalhes e evidências em [`docs/02-problema-e-validacao.md`](docs/02-problema-e-validacao.md).

## Integrantes e turma

**Turma:** ADS-NOT-PIT-4S-T1

- Caio Gabriel Moreira de Aragão Silva
- Diego Barreto Santos de Almeida
- Yuan Lucas Borges de Sousa
- Gustavo Pereira Quadros
- Pedro Paulo Passos Mangueira

Mostra e pitch: **01/12/2026 (ADS)**.

## Tecnologias e justificativas

| Tecnologia | Versão | Por quê |
|---|---|---|
| Java | 21 | Linguagem da disciplina; base para aplicar POOA |
| Spring Boot | 3.5.3 | Produtivo no prazo; injeção de dependência favorece SOLID/GRASP |
| Thymeleaf | (gerenciado pelo Spring Boot) | Renderização server-side simples, sem SPA |
| Spring Data JPA | (gerenciado pelo Spring Boot) | Repositórios como abstração; `Specification` (padrão Strategy nos filtros) |
| H2 | (gerenciado pelo Spring Boot) | Banco **em arquivo**: roda em máquina limpa sem instalar nada |
| Maven | via wrapper (`mvnw`) | Build reprodutível sem instalar o Maven |

Conceitos de POOA aplicados (State no ciclo de vida do objeto, Strategy nos filtros, SOLID/GRASP, Collections & Streams, tratamento de exceções): ver [`docs/07-arquitetura.md`](docs/07-arquitetura.md).

## Pré-requisitos

- **JDK 21** instalado (`java -version` deve indicar 21).
- Não é necessário instalar Maven nem banco de dados — o wrapper (`mvnw`/`mvnw.cmd`) e o H2 embutido cuidam disso.

## Como executar

Clonar o repositório e entrar na pasta que contém o `pom.xml` e o `mvnw`:

```bash
git clone https://github.com/cGabrielAragao/achados-perdidos-UCSAL.git
cd achados-perdidos-UCSAL
```

Na raiz do projeto (a pasta com `pom.xml`/`mvnw`):

**Windows**
```bat
mvnw.cmd spring-boot:run
```

**Linux/macOS**
```bash
./mvnw spring-boot:run
```

A aplicação sobe em **http://localhost:8080**. Na primeira execução, 5 objetos e 5 locais de demonstração são carregados automaticamente (carga idempotente: não duplica nos próximos boots).

### Telas

| Caminho | O que é |
|---|---|
| `/` | Página inicial |
| `/objetos` | Listagem dos objetos disponíveis, com filtro por categoria e local |
| `/objetos/novo` | Formulário para registrar um objeto encontrado |
| `/objetos/{id}` | Detalhe do objeto + formulário para solicitar recuperação |
| `/moderacao` | Área do moderador: aprovar/recusar solicitações e concluir devoluções |
| `/h2-console` | Console do banco H2 (apoio ao desenvolvimento) |

### Dados e credenciais

- **Não há login no MVP.** A área `/moderacao` está aberta; o controle de acesso (RF08) é um item desejável, declarado como limitação e previsto para uma evolução. A própria tela avisa isso.
- Os dados são **fictícios**, só para demonstração — nenhuma informação pessoal real.
- O arquivo do banco (`data/achadosperdidos.mv.db`) é local e **não** é versionado.

## Como executar os testes

```bash
./mvnw test
```

São **29 testes automatizados** (domínio + aplicação + web), cobrindo as transições de status (padrão State), a obrigatoriedade da prova, os filtros de busca, a ocultação da característica sensível e o ciclo completo registrar → solicitar → aprovar → concluir.

## Roteiro do fluxo principal

Passo a passo para reproduzir o fluxo central (ideal para a demonstração): [`demo/roteiro-demonstracao.md`](demo/roteiro-demonstracao.md).

## Limitações conhecidas e próximos passos

- Sem autenticação: a moderação está aberta (RF08 — desejável).
- Sem upload de imagem (RF09) e sem histórico de movimentações persistido em tela (RF10).
- Verificação de posse é **processual** (o moderador compara a prova com a característica sensível), não automática.
- Validação do problema baseada em survey (n=18, só estudantes); entrevistas complementares em andamento (ver Anexo B).

## Fontes, licenças, componentes de terceiros e uso de IA

Componentes de terceiros (bibliotecas de código aberto, usadas conforme suas licenças):

| Componente | Licença |
|---|---|
| Spring Boot / Spring Data JPA | Apache-2.0 |
| Thymeleaf | Apache-2.0 |
| H2 Database | MPL-2.0 / EPL-1.0 |
| JUnit 5 | EPL-2.0 |
| AssertJ | Apache-2.0 |
| Mockito | MIT |

**Uso de inteligência artificial** e detalhamento dos componentes: ver a declaração obrigatória em [`docs/declaracao-ia-terceiros.md`](docs/declaracao-ia-terceiros.md) (Anexo E).

---

# Anexos da entrega

> Os Anexos A–D estão incorporados abaixo (modelos do manual, com as informações identificáveis). O **Anexo E** — declaração de uso de IA e de terceiros — é entregue como **documento próprio**, por exigência do manual: ver `docs/declaracao-ia-terceiros.md`.

## Anexo A — Ficha do problema

**Solução:** Central de Achados e Perdidos — UCSal
**Equipe, turma e integrantes:** Turma ADS-NOT-PIT-4S-T1 — Caio Gabriel Moreira de Aragão Silva, Diego Barreto Santos de Almeida, Yuan Lucas Borges de Sousa, Gustavo Pereira Quadros e Pedro Paulo Passos Mangueira.

| Campo | Preenchimento |
|---|---|
| **Público** | Estudantes da UCSal (público alcançado na validação), na rotina acadêmica no campus. |
| **Situação** | Quando alguém perde ou encontra um objeto nas dependências do campus durante a rotina acadêmica. |
| **Problema** | A pessoa em geral consegue localizar o objeto pelos canais atuais, mas a devolução não tem controle de posse: qualquer um pode retirar um item alegando ser o dono, e os registros ficam dispersos. |
| **Impacto** | Risco de apropriação indevida ("alunos que acham e levam pra casa") e perda definitiva do item em parte dos casos. |
| **Alternativa atual** | Achados-e-perdidos físico na portaria, equipe de limpeza e grupo de mensagens da faculdade (foto do item). |
| **Hipótese crítica** | Para a proposta fazer sentido, a dor relevante precisa estar na **devolução segura**, não na localização. |
| **Evidência** | Pesquisa com 18 estudantes (nov/2026) e relatos abertos (ver Anexo B). |
| **Recorte** | Centralizar o registro de achados e controlar a devolução por verificação de posse. |

## Anexo B — Registro de validação

**Solução:** Central de Achados e Perdidos — UCSal

| Data e perfil | Hipótese | Evidência observada | Decisão |
|---|---|---|---|
| 03/11/2026 — 18 estudantes (pesquisa) | Pessoas perdem objetos no campus | 61% perderam algo em 12 meses | Manter |
| 03/11/2026 — 18 estudantes (pesquisa) | O processo atual é desorganizado/ineficiente | Eficiência média 3,7/5; 73% recuperam sem dificuldade | **Alterar** — foco muda para devolução segura |
| 03/11/2026 — 18 estudantes (pesquisa) | A comunidade usaria um sistema centralizado | Uso declarado 4,6/5 | Manter com ressalva (intenção declarada) |
| 03/11/2026 — 18 estudantes (pesquisa) | Verificação de posse é importante | 89% "acho importante" | Manter |
| __/__/____ — entrevista (5 min) | _(a coletar)_ | _(a coletar)_ | _(a definir)_ |
| __/__/____ — portaria/achados-e-perdidos | _(a coletar)_ | _(a coletar)_ | _(a definir)_ |

**Síntese dos aprendizados:** a localização já funciona pelos canais atuais; a fragilidade real está na devolução sem controle de posse e na dispersão dos registros.
**Mudanças realizadas no problema ou na solução:** o problema foi reenquadrado de "processo desorganizado" para "devolução segura e registro centralizado"; o escopo do MVP foi mantido, agora ancorado em evidência.

## Anexo C — Definição do MVP

**Solução:** Central de Achados e Perdidos — UCSal

- **Usuário principal:** estudante da UCSal (registra um achado e busca um objeto) e o moderador (confere a prova e libera a devolução).
- **Resultado principal:** devolver o objeto ao dono certo, de forma rastreável e com verificação de posse.
- **Fluxo de ponta a ponta:** registrar objeto encontrado → aparece na busca com dados sensíveis ocultos → solicitar recuperação com prova de posse → moderador aprova/recusa → devolução concluída (objeto recuperado).
- **Critérios de pronto:** registrar e ver na busca; filtrar sem expor dado sensível; criar solicitação com prova; moderador aprovar/recusar e o status mudar de forma persistente; tudo rodando em máquina limpa pelo README.
- **Fora do escopo:** cadastro público de usuários, notificações, painel administrativo completo, upload de imagem e qualquer integração com sistemas da UCSal.
- **Maior risco:** confundir intenção declarada (interesse alto) com necessidade real, já que o processo atual funciona.
- **Teste principal:** executar o fluxo completo com dados de demonstração e pedir a alguém de fora da equipe que registre um achado e simule uma recuperação sem ajuda.
- **Limitações conhecidas:** verificação de posse processual (não automática); sem autenticação robusta no MVP; dados apenas fictícios.

## Anexo D — Roteiro do pitch

**Solução:** Central de Achados e Perdidos — UCSal (roteiro detalhado em `docs/pitch-5min.md`)

| Parte | Conteúdo esperado | Anotações da equipe |
|---|---|---|
| Abertura | Nome da solução e uma frase sobre o problema | "Central de Achados e Perdidos. Quase todo mundo já perdeu algo na faculdade — e a pesquisa mostrou que o problema não é o que imaginávamos." |
| Público e contexto | Quem enfrenta o problema e em qual situação | Estudantes da UCSal, na rotina do campus. |
| Evidências | O que foi descoberto e qual hipótese mudou | A localização já funciona (3,7/5; 73% recuperam); 89% querem prova de posse. Redirecionamos de "desorganização" para "devolução segura". |
| Proposta | Qual resultado o MVP permite alcançar | Registrar achado, buscar, solicitar com prova, moderar e concluir a devolução ao dono certo. |
| Demonstração | Execução do fluxo principal com dados preparados | Fluxo ao vivo com os dados de demo (ver `demo/roteiro-demonstracao.md`). |
| Decisões e limites | Justificativa do escopo e limitações | Escopo enxuto (cortamos login/imagem/histórico); sem autenticação na moderação; verificação processual. |
| Aprendizado | Resultado alcançado e próximo passo | Deixar a evidência mudar a direção. Próximo passo: reforçar a validação com entrevistas. |
