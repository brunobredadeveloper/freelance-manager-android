# Regras financeiras verificáveis

## Unidade monetária
Persistir valores em centavos com `Long`. Exemplo: R$ 1.500,00 = 150000 centavos. A formatação BRL é responsabilidade da apresentação.

## Definições
- **Contratado**: soma das parcelas previstas.
- **Recebido**: soma das transações efetivamente registradas.
- **Pendente**: contratado menos recebido, podendo ser negativo quando houver pagamento excedente.
- **Recebimento parcial**: mais de uma transação associada à mesma parcela.
- **Comprovante**: evidência anexada ao recebimento, nunca substitui o registro financeiro.

## Casos testados em código
- Pagamento parcial.
- Pagamento de outra parcela não reduz saldo indevidamente.
- Pagamento excedente aparece como saldo negativo.
- Recebimento negativo é inválido.
- Consolidado de múltiplos contratos/parcelas.
- IDs duplicados e recebimentos órfãos são rejeitados.

Os testes foram escritos; execução na CI deve ser conferida separadamente. Exemplos reais de contratos e comprovantes não são versionados.
