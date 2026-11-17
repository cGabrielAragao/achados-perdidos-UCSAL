# Anexo E — Declaração de uso de inteligência artificial e de componentes de terceiros

**DOCUMENTO OBRIGATÓRIO**

> Preencha todas as seções. Se não houve uso de IA, componente de terceiro ou material preexistente, assinale "Não" e registre "Não se aplica" no campo correspondente.
> Este documento deve ser entregue **como documento próprio** (exigência do manual), além de constar no repositório.

**Solução:** Central de Achados e Perdidos — UCSal
**Turma:** ADS-NOT-PIT-4S-T1   **Equipe:** ____________________
**Integrantes:** Caio Gabriel Moreira de Aragão Silva, Diego Barreto Santos de Almeida, Yuan Lucas Borges de Sousa, Gustavo Pereira Quadros, Pedro Paulo Passos Mangueira

## 1. Uso de inteligência artificial

Houve uso de IA?  **( X ) Sim    (  ) Não**

| Ferramenta ou modelo | Finalidade ou tarefa | Parte afetada | Revisão e validação realizadas |
|---|---|---|---|
| Claude (assistente) | Análise do problema, questionário de validação, análise dos resultados, documentação (requisitos, UML, modelo de dados) e revisão de cada incremento | Documentação em `docs/` e condução da análise/validação | Equipe conferiu os números contra os dados da pesquisa e aprovou cada etapa |
| Claude Code (assistente de programação) | Implementação incremental do código Java/Spring a partir da documentação aprovada | Código-fonte em `src/` e testes | Cada incremento revisado arquivo a arquivo e validado por 29 testes automatizados |

**Observações sobre o uso de IA:** o trabalho seguiu etapas — a equipe definiu e validou o problema e a documentação primeiro; a implementação foi incremental, com revisão humana e testes a cada passo. Nenhuma saída de IA foi apresentada como evidência de validação com usuários reais.

## 2. Componentes e recursos de terceiros

Houve uso de componentes de terceiros?  **( X ) Sim    (  ) Não**

| Componente ou recurso | Origem ou link | Versão e licença | Função e alterações realizadas |
|---|---|---|---|
| Spring Boot / Spring Data JPA | https://spring.io | 3.5.3 — Apache-2.0 | Framework web e persistência. Sem modificação |
| Thymeleaf | https://www.thymeleaf.org | (Spring Boot) — Apache-2.0 | Templates das telas. Sem modificação |
| H2 Database | https://www.h2database.com | (Spring Boot) — MPL-2.0 / EPL-1.0 | Banco em arquivo para desenvolvimento/demo. Sem modificação |
| JUnit 5 | https://junit.org/junit5 | (Spring Boot) — EPL-2.0 | Testes. Sem modificação |
| AssertJ | https://assertj.github.io/doc | (Spring Boot) — Apache-2.0 | Asserções nos testes. Sem modificação |
| Mockito | https://site.mockito.org | (Spring Boot) — MIT | Mock no teste web. Sem modificação |

## 3. Materiais ou código preexistentes

Houve material produzido antes do PooaLAB?  **(  ) Sim    ( X ) Não**

- **Identificação e origem:** Não se aplica.
- **Contribuição nova realizada pela equipe:** todo o projeto (problema, validação, documentação, modelagem e implementação) foi produzido durante o PooaLAB 2026.2.

## 4. Declaração da equipe

Declaramos que as informações acima são completas e verdadeiras; que revisamos e testamos as contribuições apoiadas por IA; que identificamos as fontes, licenças e partes preexistentes relevantes; que não apresentamos como autoria própria material de terceiros; e que não compartilhamos dados pessoais, credenciais ou informações restritas de forma indevida.

**Responsável pela submissão:** Caio Gabriel Moreira de Aragão Silva

### Ciência dos integrantes

| Nome completo | Assinatura ou registro de ciência |
|---|---|
| Caio Gabriel Moreira de Aragão Silva | ________________________________ |
| Diego Barreto Santos de Almeida | ________________________________ |
| Yuan Lucas Borges de Sousa | ________________________________ |
| Gustavo Pereira Quadros | ________________________________ |
| Pedro Paulo Passos Mangueira | ________________________________ |

**Data:** ____/____/________
