# Evidências de implementação

Este documento distingue código publicado de funcionalidades executadas e validadas.

| Item | Código / evidência | Situação |
|---|---|---|
| Regras financeiras | `core/domain/.../Money.kt` e `PortfolioSummary.kt` | Implementadas; testes ainda não executados/confirmados |
| Testes financeiros | `core/domain/src/test/` | Escritos; CI pendente de validação |
| Tela inicial Compose | `app/.../MainActivity.kt` | Código publicado; screenshot real pendente |
| Banco local Room | `core/database/.../FinanceDatabase.kt` | Entidades, DAO e esquema v1 publicados; integração pendente |
| Pipeline CI | `.github/workflows/android.yml` | Configurado; execução não verificada |
| Cadastro de contratos | — | Não implementado |
| Upload de evidências | — | Não implementado |
| Lembretes | — | Não implementado |

## Política de evidências
Screenshots só depois de executar o aplicativo em dispositivo ou emulador. Capturas devem usar dados fictícios, identificar build e dispositivo e ficar em `docs/assets/`. Falhas de CI precisam ser documentadas e corrigidas antes de declarar release pronto.
