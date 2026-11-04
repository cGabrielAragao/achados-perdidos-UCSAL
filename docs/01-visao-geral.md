# 01 — Visão Geral

## Nome provisório
**Central de Achados e Perdidos — UCSal** (nome de trabalho; pode ser ajustado para o pitch).

## Descrição em uma frase
Plataforma web que centraliza o registro de objetos encontrados no campus da UCSal e organiza a devolução ao dono por meio de **verificação de posse**, reduzindo a apropriação indevida.

## Problema (enquadramento validado)
A comunidade acadêmica já localiza objetos perdidos com relativa eficácia por meios informais (achados-e-perdidos físico, equipe de limpeza, grupo de mensagens da faculdade). A dor real **não é encontrar**, e sim a **devolução segura**: hoje a retirada não tem controle de quem é o verdadeiro dono, e os registros ficam dispersos entre portaria e grupos.

> Este enquadramento substitui a hipótese inicial ("o processo é desorganizado/ineficiente"), que **não foi sustentada** pela validação. Ver `02-problema-e-validacao.md`.

## Público-alvo
- **Primário:** estudantes da UCSal (único público alcançado na validação).
- **Potencial (não validado):** professores e funcionários. Não assumimos esse público como confirmado.

## Proposta de valor
Um único lugar para registrar o que foi encontrado e devolver ao dono certo, com informações sensíveis ocultas e prova de posse antes da liberação — algo que os canais atuais (grupo, portaria) não oferecem.

## Objetivo geral
Entregar um MVP funcional e executável que cubra, de ponta a ponta, o ciclo "objeto encontrado → busca → solicitação de recuperação com prova → aprovação → recuperado".

## Objetivos específicos
1. Registrar objetos encontrados com categoria, descrição, local e data.
2. Permitir busca e filtro por categoria e local, exibindo apenas informações não sensíveis.
3. Processar solicitações de recuperação com uma prova de posse.
4. Permitir que um moderador aprove ou recuse a solicitação e atualize o status.
5. Demonstrar aplicação pertinente de conceitos de POOA (ver `06` e documentos da Etapa 2B).

## Diferenciais frente às alternativas atuais
| Alternativa atual | Limitação | Como a Central resolve |
|---|---|---|
| Grupo de WhatsApp da faculdade | Sem controle de posse; informação se perde no histórico | Registro persistente + verificação de posse |
| Achados-e-perdidos físico (portaria) | Não pesquisável remotamente; retirada sem checagem robusta | Busca online + fluxo de aprovação |
| Perguntar a colegas | Alcance limitado, informal | Centralização visível para a comunidade |

## Cenários principais de uso
1. Alguém encontra um fone no laboratório, registra, e o item passa a aparecer nas buscas com dados parciais.
2. Quem perdeu busca por categoria/local, encontra um candidato e solicita a recuperação informando uma prova.
3. O moderador confere a prova, aprova e o item muda para *recuperado*.

## Status das decisões
- ✅ **Aprovado:** recorte do MVP, stack (Spring Boot + Thymeleaf + H2), enquadramento no problema de devolução segura.
- 🟡 **Pendente de validação:** público além de estudantes; necessidade real percebida (intenção declarada é alta, mas o processo atual já funciona).
- 🟡 **Pendente de decisão (Etapa 2B):** modelo de dados final, padrões de POOA, detalhe de arquitetura.
