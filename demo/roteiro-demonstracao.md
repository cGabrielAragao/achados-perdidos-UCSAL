# Roteiro de demonstração — fluxo principal

Guia para reproduzir o fluxo central ao vivo na mostra (≈ 2 minutos). O objetivo é mostrar o **loop completo**: registrar/encontrar um achado → solicitar com prova → o moderador confere → devolução concluída.

## Antes de começar

1. Subir a aplicação na raiz do projeto: `./mvnw spring-boot:run` (ou `mvnw.cmd spring-boot:run` no Windows).
2. Abrir **http://localhost:8080/objetos** — devem aparecer 5 objetos de demonstração.
3. Deixar uma segunda aba aberta em **http://localhost:8080/moderacao**.
4. Desativar notificações e deixar as abas prontas (ver checklist do manual).

## Dados de demonstração (o apresentador precisa saber a "prova" certa)

A característica sensível **não aparece** nas telas públicas — ela é a prova que o dono deveria saber. Para simular uma recuperação bem-sucedida, use exatamente o texto da coluna "Característica sensível (prova correta)".

| Objeto | Local | Característica sensível (prova correta) |
|---|---|---|
| Fone de ouvido branco, sem fio | Laboratório de Informática | Possui um pequeno adesivo azul na parte interna do estojo |
| Garrafa de água de plástico, cor verde | Cantina | Tem o nome "Marina" escrito a caneta no fundo da garrafa |
| Caderno universitário capa dura (Cálculo) | Sala de Aula 12 | Na contracapa há um bilhete colado escrito "procurar sala 12" |
| Molho com três chaves e chaveiro de borracha | Biblioteca Central | O chaveiro de borracha tem o formato de uma caveira pequena |
| Moletom cinza com capuz, tamanho M | Quadra Esportiva | Tem um pequeno rasgo de costura na manga esquerda, perto do punho |

## Roteiro (caminho principal)

1. **Mostrar a listagem** (`/objetos`) e aplicar um filtro — ex.: categoria **Fone de ouvido** — para evidenciar a busca (padrão Strategy).
2. **Abrir o detalhe** do fone. Destacar que a tela mostra categoria, descrição, local e data, mas **nunca** a característica sensível.
3. **Solicitar a recuperação:** no formulário "Acha que esse objeto é seu?", digitar a prova correta do fone (*"Possui um pequeno adesivo azul na parte interna do estojo"*) e enviar. O objeto sai da listagem pública (foi para **EM_ANÁLISE**).
4. **Ir para `/moderacao`:** a solicitação aparece com a **prova** e a **característica sensível lado a lado** — esta é a única tela que mostra o dado sensível, para o moderador comparar.
5. **Aprovar** a solicitação → fica "aguardando devolução" (o objeto ainda está EM_ANÁLISE).
6. **Concluir a devolução** → o objeto passa a **RECUPERADO** e some das buscas públicas. Fluxo fechado.

## Opcional (robustez, ~20s)

- Tentar **solicitar sem prova** → mensagem amigável ("É obrigatório informar uma prova de posse…"), **sem** erro 500.
- Tentar **aprovar duas vezes** ou concluir uma solicitação não aprovada → alerta amigável (regra de devolução segura — RN08).

## Reiniciar o estado entre ensaios

Como o H2 persiste em arquivo, um objeto recuperado continua recuperado no próximo boot. Para restaurar os 5 objetos ao estado inicial:

1. Parar a aplicação.
2. Apagar a pasta `data/` (o arquivo `achadosperdidos.mv.db`).
3. Subir de novo — a carga de demonstração recria os 5 objetos como DISPONÍVEL.

> Alternativa em dia de mostra: usar um **objeto diferente** a cada execução (são 5), evitando apagar o banco na hora.

## Contingência (exigência do manual)

- Manter uma **gravação curta** e atualizada deste mesmo fluxo, como apoio caso o equipamento falhe. A gravação **não substitui** o MVP executável.
- A aplicação roda **100% local** (sem internet/nuvem), então não depende de rede na hora da apresentação.
