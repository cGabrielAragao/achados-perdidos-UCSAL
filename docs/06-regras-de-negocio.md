# 06 — Regras de Negócio

Regras que garantem a coerência do fluxo e, principalmente, a **devolução segura** (diferencial validado).

## Ciclo de vida do objeto
| Status | Significado | Transições permitidas |
|---|---|---|
| `DISPONIVEL` | Registrado e visível nas buscas | → `EM_ANALISE` (ao receber solicitação) |
| `EM_ANALISE` | Tem solicitação de recuperação pendente | → `DISPONIVEL` (se recusada) · → `RECUPERADO` (se aprovada e devolvida) |
| `RECUPERADO` | Devolvido ao dono; encerrado | — (estado final) |

## Regras

- **RN01** — Um objeto registrado inicia sempre como `DISPONIVEL`.
- **RN02** — Apenas objetos `DISPONIVEL` aparecem nas listagens e buscas públicas.
- **RN03** — Ao abrir uma solicitação de recuperação, o objeto passa a `EM_ANALISE` e deixa de aceitar novas solicitações enquanto houver uma pendente.
- **RN04** — Toda solicitação de recuperação exige uma **prova de posse** (descrição de característica não exibida publicamente). Sem prova, a solicitação não é aceita.
- **RN05** — As **características sensíveis** do objeto (aquelas que só o dono saberia) **nunca** são exibidas nas listagens ou no detalhe público; servem de base para a verificação.
- **RN06** — Somente o **moderador** pode aprovar ou recusar uma solicitação e concluir a devolução.
- **RN07** — Se a solicitação for **recusada**, o objeto volta a `DISPONIVEL` e fica apto a novas solicitações.
- **RN08** — Se for **aprovada** e a devolução registrada, o objeto passa a `RECUPERADO`, estado final e irreversível no MVP.
- **RN09** — Um objeto `RECUPERADO` não aparece em buscas nem aceita solicitações.
- **RN10** — Campos obrigatórios no registro: categoria, descrição, local e data do encontro. Data não pode ser futura.
- **RN11** — Nenhum dado pessoal real é armazenado; identificação do solicitante é mínima e voltada à demonstração.

## Rastreabilidade
- RN03–RN09 implementam o ciclo de status (base para o padrão **State** a avaliar na Etapa 2B).
- RN04/RN05 materializam a verificação de posse (H4).
- RN06 concentra a decisão de devolução no moderador, reduzindo apropriação indevida (dor central validada).
