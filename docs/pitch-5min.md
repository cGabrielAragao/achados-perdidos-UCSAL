# Roteiro do Pitch — 5 minutos

Guia de apresentação da Central de Achados e Perdidos para a mostra. Tempo-alvo: **5 minutos**. A força do nosso pitch é a **virada guiada por evidência** — ela é o fio condutor, não um detalhe.

> Dividam as falas entre os integrantes e ensaiem com cronômetro. Marquem quem fala cada bloco nos `[ ]`.

## Estrutura por tempo

### 0:00 – 0:30 · Abertura `[ ]`
- Nome do sistema, equipe e o problema em **uma frase**.
- Fala sugerida: *"Somos a equipe [X]. Quase todo mundo já perdeu algo na faculdade — e a nossa pesquisa mostrou que o problema não é bem o que a gente imaginava."*

### 0:30 – 1:30 · Problema e evidências — a virada `[ ]`
- Hipótese inicial: o achados-e-perdidos é desorganizado e ineficiente.
- O que a pesquisa (n=18) mostrou: a **localização já funciona** na maioria dos casos (eficiência média 3,7/5; 73% recuperam sem dificuldade).
- O achado real: **89% acham importante** haver prova de posse; um relato resumiu — *"o problema são alunos que acham e levam pra casa; a parte de pegar o item precisa melhorar."*
- Decisão: **redirecionamos o problema** de "localização" para **devolução segura**.
- Seja honesto sobre os limites: amostra pequena, só estudantes, intenção declarada.

### 1:30 – 2:00 · A proposta (MVP) `[ ]`
- O que é: uma central que registra achados e controla a devolução por verificação de posse.
- Público: estudantes da UCSal. Valor: o dono certo recupera o item; dados sensíveis ficam ocultos.

### 2:00 – 3:30 · Demonstração ao vivo `[ ]` (o coração do pitch)
Siga o `demo/roteiro-demonstracao.md`:
1. Listagem + filtro por categoria (mostrar a busca).
2. Detalhe — destacar que a característica sensível **não** aparece.
3. Solicitar recuperação com a prova correta → objeto sai da busca.
4. `/moderacao`: prova e característica **lado a lado** (única tela que mostra o dado sensível).
5. Aprovar → concluir devolução → objeto **RECUPERADO**.
- Se der tempo (10s): mostrar que prova vazia dá mensagem amigável, não erro.

### 3:30 – 4:15 · Decisões técnicas e POOA `[ ]`
- Arquitetura em camadas; domínio concentra as regras.
- **State** no ciclo de vida do objeto (transição inválida é bloqueada no próprio estado).
- **Strategy** nos filtros de busca (via Specification).
- Tratamento global de erros; **29 testes** automatizados.
- Decisão de escopo: recorte enxuto, **cortamos os opcionais** (login, imagem, histórico) para entregar o fluxo central completo e estável.

### 4:15 – 4:45 · Limites e próximos passos `[ ]`
- Sem autenticação na moderação (previsto como evolução).
- Validação a reforçar com entrevistas.
- Verificação de posse é processual, não automática.

### 4:45 – 5:00 · Aprendizado e fechamento `[ ]`
- A principal lição: **deixar a evidência mudar a direção** em vez de defender a ideia inicial.
- Encerramento curto e direto. Agradecer.

## Checklist antes de apresentar
- [ ] Banco resetado (pasta `data/` apagada) e app rodando em `localhost:8080`.
- [ ] Duas abas abertas: `/objetos` e `/moderacao`.
- [ ] Zoom do navegador em ~125% para legibilidade no projetor.
- [ ] Notificações do sistema desativadas.
- [ ] **Gravação de contingência** pronta (ver `demo/guia-gravacao-contingencia.md`), caso o equipamento falhe.
- [ ] Prova correta do objeto escolhido anotada (tabela do roteiro de demo).

## Sugestão de slides (mínimos)
1. Capa: nome + equipe.
2. O problema e a virada (antes → evidência → depois).
3. A proposta em uma tela.
4. (Demonstração ao vivo — sem slide.)
5. Arquitetura e POOA (1 diagrama: use `diagrams/classes` ou o de sequência).
6. Limites, próximos passos e aprendizado.
