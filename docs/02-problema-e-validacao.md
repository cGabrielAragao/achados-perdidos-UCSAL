# 02 — Problema e Validação

## Ficha do problema (Anexo A do manual)

| Campo | Preenchimento |
|---|---|
| **Público** | Estudantes da UCSal (público alcançado na validação). |
| **Situação** | Perda ou descoberta de objetos nas dependências do campus durante a rotina acadêmica. |
| **Problema** | A pessoa consegue, na maioria das vezes, localizar o objeto pelos canais atuais, mas a **devolução não tem controle de posse**: qualquer um pode retirar um item alegando ser o dono; os registros ficam dispersos entre portaria e grupos de mensagem. |
| **Impacto** | Risco de apropriação indevida ("alunos que acham e levam pra casa") e perda definitiva de itens em parte dos casos. |
| **Alternativa atual** | Achados-e-perdidos físico na portaria, equipe de limpeza e grupo de mensagens da faculdade (foto do item). |
| **Hipótese crítica** | Para a proposta fazer sentido, a dor relevante precisa estar na **devolução segura**, não na localização. |
| **Evidência** | Pesquisa com 18 estudantes (nov/2026) + relatos abertos. Ver abaixo e `validacao/`. |
| **Recorte** | Centralizar o registro de achados e controlar a devolução por verificação de posse. |

## Hipóteses e o que a validação mostrou

Pesquisa aplicada em 03/11/2026 via Google Forms, divulgada no grupo geral da faculdade. **n = 18 respostas, 100% estudantes.**

| Hipótese | Medida | Resultado | Decisão |
|---|---|---|---|
| **H1** — perdas ocorrem | "Perdeu algo nos últimos 12 meses?" | **11/18 (61,1%)** sim (6 uma vez; 5 mais de uma vez) | **Manter** — problema ocorre. |
| **H2** — processo atual é ineficiente | Eficiência percebida (1–5) + desfecho da última tentativa | Média **3,67** (mediana 4). Dos 15 que tentaram, **11 (73%) recuperaram sem dificuldade**, 4 não conseguiram, 0 "com dificuldade" | **Alterar** — a hipótese **não se sustenta**. O processo atual funciona na maioria dos casos. |
| **H3** — usariam um sistema centralizado | Chance de uso (1–5) | Média **4,56** (13 marcaram 5) | **Manter com ressalva** — interesse alto, mas é intenção declarada. |
| **H4** — verificação de posse é importante | "Mostrar dados parciais + exigir prova?" | **16/18 (88,9%)** "acho importante"; 2 "tanto faz"; 0 "não" | **Manter** — sustenta o diferencial do MVP. |

### Distribuições (dados brutos conferidos no CSV)
- **Eficiência percebida (P7):** 1→1, 2→1, 3→6, 4→5, 5→5 (n=18).
- **Chance de uso (P8):** 1→1, 4→4, 5→13 (n=18).
- **Objetos mais citados (P4):** Fone de ouvido 6, Garrafa/copo 6, "Outro" 4, Material de estudo 3, Vestuário 3, Carteira/documentos 2, Celular 1, Chaves 1, Guarda-chuva 1.

### Relatos abertos relevantes (anonimizados)
- Mochila perdida recuperada pela equipe de limpeza; dona localizou o achados-e-perdidos e o item estava lá.
- Vários itens (garrafa, marmiteira, cartão de metrô, chaves) sempre recuperados no achados-e-perdidos **ou** no grupo da faculdade via foto; única perda definitiva foi um cartão.
- **"O real problema está em alguns alunos que acham as coisas e levam pra casa. (...) a parte de pegar o item pode melhorar para evitar."** → aponta diretamente para a verificação de posse.

## Decisão decorrente da validação (a virada)
> **Hipótese inicial:** o processo de achados e perdidos é desorganizado e ineficiente.
> **Evidência:** a localização já funciona para a maioria (eficiência média 3,67; 73% recuperam sem dificuldade).
> **Decisão:** **redirecionar** o problema de "localização" para **"devolução segura"** — centralizar o registro e controlar a retirada por verificação de posse.

Essa mudança é registrada formalmente no Anexo B (`validacao/anexo-b-registro-validacao.md`).

## Plano de reforço da evidência (a executar pela equipe)
Para fortalecer a confiabilidade (survey mede intenção, não comportamento), ainda nesta semana:
1. **2–3 entrevistas de 5 min** com quem já perdeu/encontrou algo.
2. **Conversa com a portaria / achados-e-perdidos físico** para mapear o processo real e onde ele falha na devolução.

> Não fabricar respostas, números ou depoimentos. Registrar cada evidência em uma linha do Anexo B, sem identificar pessoas.

## Limitações declaradas (transparência — exigência do manual)
- Amostra pequena (**n = 18**) e **somente estudantes**; sem professores/funcionários.
- Divulgação em grupo da faculdade → **autosseleção**.
- H3 mede **intenção declarada**, não uso real.
- A pergunta "você encontrou algo?" não entrou no formulário; a visão de quem acha vem apenas dos relatos.

## Evidências versionadas
- `validacao/respostas-pesquisa.csv` — respostas brutas e anônimas do formulário.
- `validacao/anexo-b-registro-validacao.md` — registro de validação e decisões.
