# Cartão CRC — Rota

**Autor:** Luca Albertini · **Projeto:** XAVi — Xulambs Agência de Viagens · **Disciplina:** Programação Modular (2026/2)

| | |
|---|---|
| **Classe** | Rota |
| **Descrição** | Ligação fixa entre uma cidade de origem e uma cidade de destino atendidas pela agência. É identificada por um código de quatro dígitos: os dois primeiros indicam a cidade de origem e os dois últimos, a de destino. Serve de base para os voos (um voo é uma rota em uma data). |

| Responsabilidades | Colaboradores |
|---|---|
| Armazenar a cidade de origem e a cidade de destino | **Cidade** (enum) — cidades atendidas e o código de dois dígitos de cada uma |
| Gerar e informar o código de quatro dígitos da rota a partir das cidades | **Cidade** — fornece os códigos de origem e de destino |
| Validar que a origem e o destino são cidades diferentes | **Voo** — usa a rota para saber origem e destino e para formar seu código (XAV + código da rota) |
| Informar a cidade de origem e a cidade de destino | **Agência** — usa as rotas para buscar combinações de voos entre a origem e o destino escolhidos |
| Verificar se liga uma origem e um destino informados | |

## Regra do código (exemplo)

| Rota | Origem | Destino | Código da rota | Código do voo |
|---|---|---|---|---|
| Belo Horizonte – Recife | 13 | 50 | 1350 | XAV1350 |

Uma mesma rota pode dar origem a vários voos, um para cada data.
