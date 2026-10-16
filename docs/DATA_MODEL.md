# Modelo de dados v1

| Entidade | Campos principais |
|---|---|
| Client | id, name, contact, optionalTaxId |
| Project | id, clientId, title, description, status |
| Contract | id, projectId, title, amountMinor, currency, signedAt, documentUri |
| Deliverable | id, contractId, title, scope, dueDate, status, acceptedAt |
| Evidence | id, deliverableId, kind, uriOrUrl, description, createdAt |
| Installment | id, contractId, deliverableId?, amountMinor, dueDate, notes |
| Receipt | id, installmentId, amountMinor, receivedAt, evidenceId? |
| AuditEntry | id, entityType, entityId, action, occurredAt, payload |

O saldo de cada parcela é calculado como valor previsto menos soma de recebimentos. Valor excedente não é descartado; deve aparecer na conciliação. Histórico preserva mudanças de escopo, aceite, valores e datas.

## Exemplo fictício
Contrato de R$ 1.000,00, duas parcelas de R$ 500,00. A primeira recebe R$ 300,00 e depois R$ 200,00: saldo zero, sem marcar a segunda como paga.
