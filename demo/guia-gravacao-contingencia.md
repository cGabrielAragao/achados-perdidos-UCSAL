# Guia de gravação — vídeo de contingência do fluxo

O manual pede uma **gravação curta** do fluxo principal como apoio, caso o equipamento falhe na mostra. Ela **não substitui** o MVP executável — é só um seguro. Meta: **1 a 2 minutos**, mostrando o ciclo completo.

## O que gravar

Exatamente o caminho principal do `demo/roteiro-demonstracao.md`:
registrar/escolher um achado → solicitar recuperação com a prova correta → `/moderacao` comparar prova × característica → aprovar → concluir → objeto **RECUPERADO**.

## Antes de gravar (preparação)

1. Resetar o estado: parar o app, apagar a pasta `data/`, subir de novo (`./mvnw spring-boot:run`).
2. Abrir `http://localhost:8080/objetos` e uma segunda aba em `/moderacao`.
3. Zoom do navegador em ~125% e janela maximizada (fica legível no vídeo).
4. Fechar abas e notificações que possam aparecer na tela.
5. Ter em mãos a **prova correta** do objeto escolhido (tabela do roteiro de demo).

## Como gravar no Windows (duas opções)

### Opção A — Xbox Game Bar (nativo, mais rápido)
1. Pressione **Win + G** para abrir a Game Bar.
2. No widget **Capturar**, clique no botão de gravar (ou **Win + Alt + R** para iniciar/parar).
3. Faça o fluxo com calma; narre se quiser (microfone opcional no mesmo widget).
4. Pare com **Win + Alt + R**. O vídeo fica em `Vídeos\Capturas`.
5. Observação: a Game Bar grava a **janela ativa** (o navegador) — evite trocar de janela no meio.

### Opção B — OBS Studio (mais controle)
1. Instale o OBS (gratuito), adicione uma fonte **"Captura de Tela"** ou **"Captura de Janela"** (o navegador).
2. Em Configurações → Saída, deixe resolução **1080p** e **30 fps**.
3. Clique **Iniciar Gravação**, faça o fluxo, **Parar Gravação**.
4. O arquivo sai em `.mp4` por padrão.

## Dicas

- Faça um ensaio antes de gravar "pra valer".
- Movimente o mouse devagar; pause ~1s em cada tela importante (detalhe, moderação, status RECUPERADO).
- Se errar, é mais rápido resetar o banco e regravar do que editar.
- Narração ajuda, mas não é obrigatória; se narrar, use as falas do `docs/pitch-5min.md` (bloco de demonstração).

## Onde salvar

- Exporte como **`demo/contingencia-fluxo.mp4`** dentro do projeto (ou um link, se o arquivo for grande para o repositório — nesse caso, registre o link no README).
- Nome sugerido: `contingencia-fluxo.mp4`.

> Se preferir, dá para gerar um **GIF** curto em vez de vídeo — mesma ideia, mais leve para anexar. Ferramentas como ScreenToGif (Windows) fazem isso direto.
