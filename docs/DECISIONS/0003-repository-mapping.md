# ADR 0003 — Implementação de repositórios Room

**Status:** Em implementação

## Escolha
Mapear entidades Room para modelos de domínio nos adaptadores `RoomProjectRepository` e `RoomFinanceRepository`. O domínio não conhece entidades Room.

## Observações
- Leituras reativas usam `Flow.map`.
- Escritas usam `suspend` e restrições de chave estrangeira do SQLite.
- Identificadores de recebimento são preservados na entidade persistida.
- Para cadastrar um projeto, seu cliente deve existir previamente.
- Ainda falta DI Hilt, fluxo de cadastro de cliente, testes de integração, migrações e UI.

## Riscos
`ProjectStatus.valueOf` pressupõe valores persistidos válidos; futuras migrações devem garantir compatibilidade. Erros de persistência devem ser mapeados para estados de UI amigáveis.
