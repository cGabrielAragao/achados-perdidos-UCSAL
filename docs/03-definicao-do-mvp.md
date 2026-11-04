# 03 — Definição do MVP (Anexo C)

**Solução:** Central de Achados e Perdidos — UCSal

- **Usuário principal:** estudante da UCSal (como quem registra um achado, como quem busca um objeto perdido) e o **moderador** (quem confere a prova e libera a devolução).
- **Resultado principal:** devolver o objeto ao dono certo, de forma rastreável e com verificação de posse.

## Fluxo de ponta a ponta
```
registrar objeto encontrado (categoria, descrição, local, data)
  → objeto aparece nas buscas com dados sensíveis ocultos
  → quem perdeu busca/filtra e abre um candidato
  → solicita recuperação informando uma prova de posse
  → moderador analisa e aprova ou recusa
  → se aprovado e devolvido, status passa a "recuperado"
```

## Escopo do MVP

### Indispensável (entra no MVP)
- Registrar objeto encontrado.
- Listar/buscar objetos com filtro por categoria e local.
- Exibir detalhe com **ocultação de características sensíveis**.
- Solicitar recuperação com um campo de prova de posse.
- Moderador aprova/recusa a solicitação.
- Atualização do status do objeto (disponível → em análise → recuperado).

### Desejável (se houver tempo)
- Upload de imagem do objeto.
- Autenticação de usuário (começar com login mínimo de moderador).
- Histórico de movimentações do objeto.

### Fora do escopo inicial
- Cadastro público de usuários.
- Notificações (e-mail/push).
- Painel administrativo completo / relatórios.
- Qualquer integração com sistemas ou bases institucionais da UCSal.

## Critérios de pronto (fluxo funcional)
1. É possível registrar um achado e vê-lo aparecer na busca.
2. A busca filtra por categoria e local e não expõe dados sensíveis na listagem.
3. Uma solicitação de recuperação é criada com prova de posse.
4. O moderador consegue aprovar/recusar e o status muda de forma visível e persistente.
5. O cenário completo roda em máquina limpa seguindo o README.

## Maior risco
Confundir **intenção declarada** (alto interesse na survey) com necessidade real, dado que o processo atual já funciona. Mitigação: manter o diferencial (devolução segura) no centro e reforçar evidência com entrevistas.

## Teste principal
Executar o fluxo completo com dados de demonstração e pedir a uma pessoa de fora da equipe que registre um achado e simule uma recuperação, observando se conclui sem ajuda.

## Limitações conhecidas
- Verificação de posse é **processual** (moderador avalia a prova), não automática.
- Sem autenticação robusta no MVP; papéis simplificados.
- Dados apenas de demonstração, sem informação pessoal real.
