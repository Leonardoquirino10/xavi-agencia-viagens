# Cartão CRC — Bilhete

**Autor:** Leonardo Evangelista Quirino · **Projeto:** XAVi — Xulambs Agência de Viagens · **Disciplina:** Programação Modular (2026/2)

| | |
|---|---|
| **Classe** | Bilhete |
| **Descrição** | Produto final de venda adquirido por um cliente. Encadeia um ou mais voos sequenciais, calcula o preço de venda ponderado e valida a continuidade de cidades e datas entre os voos. |

| Responsabilidades | Colaboradores |
|---|---|
| Armazenar os voos que compõem o bilhete | **Voo** — fornece data, preço base, origem e destino de cada trecho |
| Calcular o preço de venda: 60% do voo mais caro + 40% do 2º mais caro + 10% de cada voo adicional | **Cliente** — associa o bilhete ao cliente comprador |
| Validar que a origem de cada novo voo é igual ao destino do voo anterior | **Cidade** — origem inicial e destino final da viagem |
| Validar que voos consecutivos ocorrem no mesmo dia ou em dias consecutivos | **Agência / Relatório** — consome o preço dos bilhetes para total arrecadado e comissão de 6% |
| Informar a cidade de origem inicial e a de destino final | |

## Regra de preço (exemplo: voos de R$ 500, R$ 400 e R$ 300)

| Voos | Cálculo | Resultado |
|---|---|---|
| 1 | 100% do voo | R$ 500,00 |
| 2 | 60% × 500 + 40% × 400 | R$ 460,00 |
| 3+ | 60% × 500 + 40% × 400 + 10% × 300 | R$ 490,00 |
