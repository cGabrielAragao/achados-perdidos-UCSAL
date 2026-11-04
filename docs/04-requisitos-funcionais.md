# 04 — Requisitos Funcionais

Prioridade: **Indispensável** (MVP) · **Desejável** (se houver tempo) · **Fora** (não será feito agora).
Atores: **Visitante** (qualquer pessoa da comunidade), **Moderador**.

| ID | Nome | Descrição | Ator | Prioridade | Critérios de aceitação |
|---|---|---|---|---|---|
| **RF01** | Registrar objeto encontrado | Permitir cadastrar um achado com categoria, descrição, local e data em que foi encontrado. | Visitante | Indispensável | Com os campos obrigatórios preenchidos, o objeto é salvo com status "disponível" e passa a aparecer na busca. |
| **RF02** | Listar objetos disponíveis | Exibir os objetos com status "disponível" em uma listagem. | Visitante | Indispensável | A listagem mostra apenas objetos "disponíveis" e não exibe características sensíveis. |
| **RF03** | Buscar e filtrar | Permitir filtrar a listagem por categoria e por local. | Visitante | Indispensável | Selecionando categoria e/ou local, a lista retorna apenas os itens compatíveis. |
| **RF04** | Ver detalhe com ocultação | Abrir o detalhe de um objeto exibindo informações gerais e **ocultando** características identificadoras. | Visitante | Indispensável | O detalhe mostra categoria, local e data; não exibe o campo de característica sensível reservado à verificação. |
| **RF05** | Solicitar recuperação | Permitir que quem acredita ser o dono abra uma solicitação informando uma prova de posse (texto). | Visitante | Indispensável | É criada uma solicitação vinculada ao objeto, com status "pendente", e o objeto passa a "em análise". |
| **RF06** | Analisar solicitação | Permitir ao moderador ver a solicitação e a prova e aprovar ou recusar. | Moderador | Indispensável | Ao aprovar, a solicitação fica "aprovada"; ao recusar, "recusada" e o objeto volta a "disponível". |
| **RF07** | Concluir devolução | Registrar que o objeto foi devolvido, mudando o status para "recuperado". | Moderador | Indispensável | Objeto "recuperado" sai das buscas e não aceita novas solicitações. |
| **RF08** | Autenticar moderador | Restringir as ações de moderação a um usuário autenticado. | Moderador | Desejável | Ações de RF06/RF07 exigem login; sem login, ficam indisponíveis. |
| **RF09** | Anexar imagem | Permitir anexar uma foto ao registro do objeto. | Visitante | Desejável | A imagem é salva e exibida no detalhe, respeitando a ocultação de dados sensíveis. |
| **RF10** | Histórico de movimentações | Registrar e exibir as mudanças de status de um objeto. | Moderador | Desejável | Cada mudança de status gera um registro com data/hora consultável. |
| **RF11** | Cadastro público de usuários | Autoatendimento de contas para a comunidade. | Visitante | Fora | — (fora do escopo do MVP) |
| **RF12** | Notificações | Avisar automaticamente sobre solicitações/aprovações. | Sistema | Fora | — (fora do escopo do MVP) |

## Rastreabilidade
- RF04/RF05/RF06 realizam o diferencial validado em **H4** (verificação de posse).
- RF01–RF03 cobrem a centralização do registro (dispersão apontada na validação).
- RF07 fecha o ciclo "recuperado" exigido como resultado observável do MVP.
