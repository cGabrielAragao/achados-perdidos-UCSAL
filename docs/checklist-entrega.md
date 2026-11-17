# Checklist de submissão — POOALAB (entrega 23/11/2026)

Repositório: https://github.com/cGabrielAragao/achados-perdidos-UCSAL

Marque cada item ao concluir. Os de prioridade **alta** são os que mais pesam na nota ou travam a entrega.

## 1. Repositório e código
- [ ] Projeto (código `src/`, `pom.xml`, `mvnw`/`mvnw.cmd`) enviado ao GitHub
- [ ] `docs/`, `diagrams/`, `demo/`, `README.md` e `CLAUDE_CODE_GUIDE.md` versionados
- [ ] `.gitignore` ativo (sem `target/`, sem `data/*.db`, sem segredos)
- [ ] Repositório acessível aos colegas/professor (visibilidade conferida)
- [ ] Último commit reflete a versão final (código + docs)

## 2. Reprodutibilidade — **alta**
- [ ] Um colega clonou em **máquina limpa** (só com JDK 21) e rodou `./mvnw spring-boot:run`
- [ ] A aplicação subiu em `localhost:8080` com os 5 objetos de demonstração
- [ ] `./mvnw test` passou (29 testes) nesse ambiente
- [ ] O README foi seguido sem passos "extras" não documentados

## 3. Documentação
- [ ] README completo e com o link de clone correto
- [ ] `docs/01` a `docs/09` conferidos
- [ ] Diagramas (`diagrams/`) abrindo corretamente

## 4. Validação — **alta**
- [ ] Anexo B com as evidências da pesquisa (feito)
- [ ] (Reforço) 2 linhas das entrevistas preenchidas no Anexo B, se realizadas
- [ ] CSV da pesquisa versionado em `docs/validacao/` (feito)
- [ ] Nenhuma evidência inventada; limitações declaradas (feito)

## 5. Anexos
- [ ] Anexo A (ficha do problema) — no README, com **nomes/turma preenchidos**
- [ ] Anexo B (validação) — no README
- [ ] Anexo C (definição do MVP) — no README
- [ ] Anexo D (roteiro do pitch) — no README
- [ ] **Anexo E** (declaração de IA) — **documento próprio**, impresso, com os checkboxes marcados, **nomes e assinaturas** dos integrantes e data

## 6. Preenchimentos
- [x] Nomes completos e turma no **README** (Anexo A) e na **capa dos slides** — feito
- [x] Nomes no **Anexo E** (digitados) — feito; faltam apenas as **assinaturas** à mão
- [x] Turma e data da mostra confirmadas: **ADS-NOT-PIT-4S-T1 · 01/12/2026**

## 7. Pitch e mostra — **alta**
- [ ] Deck finalizado (capa com equipe/turma) e exportado em PDF/PPTX
- [ ] Pitch **ensaiado com cronômetro** (≤ 5 min); bloco da demo ajustado no tempo
- [ ] Divisão de falas definida entre os integrantes
- [ ] Ambiente de demo testado: banco resetado, 2 abas (`/objetos` e `/moderacao`), zoom ~125%, notificações off

## 8. Contingência — fora do escopo (decisão da equipe)
- [x] A equipe decidiu **fechar sem o vídeo de contingência**. O `demo/guia-gravacao-contingencia.md` fica como material opcional, caso queiram gravar depois.

## 9. Submissão no Classroom — **23/11**
- [ ] Link do repositório entregue na tarefa do Classroom
- [ ] Anexo E (documento próprio) anexado conforme pedido pelo professor
- [ ] Pitch/PDF anexado, se solicitado
- [ ] Conferir avisos oficiais do Classroom (prevalecem sobre o manual)
- [ ] Entrega feita **antes** do prazo (evitar o último minuto)

---

### Ordem sugerida nos próximos dias
1. Push ao GitHub → teste em máquina limpa por um colega.
2. Entrevista (17/11) → fechar as linhas do Anexo B.
3. Ensaio do pitch com cronômetro.
4. Imprimir e assinar o Anexo E.
5. Submissão no Classroom até 23/11.
