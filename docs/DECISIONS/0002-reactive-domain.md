# ADR 0002 — Domínio reativo e contratos de repositório

**Status:** Aceita para MVP

## Decisão
O domínio define interfaces `ProjectRepository` e `FinanceRepository`. O caso de uso `ObservePortfolio` combina fluxos de parcelas e recebimentos com `combine`, mantendo a lógica de consolidação em Kotlin puro.

`RegisterReceipt` valida identificadores e valores antes de delegar a persistência. A camada de dados implementará as interfaces usando Room.

## Benefícios
- Isolamento entre regras de negócio e tecnologia de persistência.
- Reatividade sem polling e ViewModels capazes de expor StateFlow.
- Substituição por fakes em testes unitários.
- Possibilidade futura de sincronização sem acoplar o domínio ao Firebase.

## Limites
O contrato ainda não garante atomicidade de transações no banco. A implementação Room deve aplicar transações, restrições de integridade e testes para concorrência.
