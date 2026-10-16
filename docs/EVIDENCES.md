# Evidências de implementação

Este documento diferencia código versionado de funcionalidades efetivamente testadas.

| Item | Evidência no repositório | Status |
|---|---|---|
| Regras financeiras | `core/domain/.../Money.kt` | Implementado, aguardando execução CI |
| Casos de teste financeiros | `core/domain/.../MoneyTest.kt` | Escritos, aguardando execução CI |
| Tela inicial Compose | `app/.../MainActivity.kt` | Implementada, aguardando build e screenshot |
| Pipeline Android | `.github/workflows/android.yml` | Configurado, execução ainda não verificada |
| Cadastro de contratos | — | Não implementado |
| Evidências/arquivos | — | Não implementado |
| Alertas | — | Não implementado |

## Procedimento para screenshots
Executar APK em emulador/dispositivo, registrar screenshots **sem dados reais de clientes**, adicionar em `docs/assets/`, descrever fluxo, versão e dispositivo. Não utilizar mockups como prova de execução.

## Execuções CI
Consultar a aba Actions do GitHub. Só declarar build aprovado depois de verificação do run correspondente.
