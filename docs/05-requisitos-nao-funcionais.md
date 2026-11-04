# 05 — Requisitos Não Funcionais

Alinhados aos critérios da rubrica do manual (reprodutibilidade, qualidade, transparência, acessibilidade).

| ID | Categoria | Requisito | Como será verificado |
|---|---|---|---|
| **RNF01** | Reprodutibilidade | A aplicação deve rodar em máquina limpa seguindo apenas o README, sem instalar banco externo (H2 embutido). | Executar o README em outro computador/ambiente limpo antes da entrega. |
| **RNF02** | Desempenho | O fluxo principal deve responder em tempo adequado para demonstração ao vivo (percepção de resposta imediata na navegação local). | Teste manual do cenário de demonstração. |
| **RNF03** | Segurança / Privacidade | Não expor características sensíveis de objetos nas listagens; não armazenar dados pessoais reais; não versionar segredos. | Revisão do detalhe/listagem e do repositório antes da entrega. |
| **RNF04** | Segurança | Validar entradas do fluxo principal e tratar erros previsíveis sem quebrar a aplicação. | Testes do caminho principal e de entradas inválidas. |
| **RNF05** | Usabilidade | Telas claras, rótulos compreensíveis e linguagem direta para o público estudante. | Teste com uma pessoa de fora da equipe. |
| **RNF06** | Acessibilidade | Contraste suficiente, não depender só de cor para indicar status/erro, campos identificados e navegação por teclado quando aplicável. | Checagem visual e de marcação. |
| **RNF07** | Manutenibilidade | Código em camadas, com separação de responsabilidades e nomes claros (ver Etapa 2B — arquitetura e POOA). | Revisão de código da equipe. |
| **RNF08** | Confiabilidade | O cenário demonstrado deve ser estável e não depender de improvisos; dados de demonstração previsíveis. | Ensaio do pitch com os mesmos dados. |
| **RNF09** | Transparência | Declarar uso de IA (Anexo E), componentes de terceiros, licenças e partes reutilizadas. | Documento `declaracao-ia-terceiros` na entrega. |

> Observação: autenticação robusta não é meta do MVP. A proteção no protótipo é proporcional (papel de moderador simplificado), e isso está declarado como limitação em `03-definicao-do-mvp.md`.
