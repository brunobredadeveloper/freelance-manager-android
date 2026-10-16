# Evidências de implementação — Freelance Manager Android

> Regra: código enviado ao GitHub **não equivale** a build aprovado ou funcionalidade validada em dispositivo.

| Funcionalidade | Fonte | Situação |
|---|---|---|
| Domínio financeiro e pagamentos parciais | `core/domain/src/main/.../Money.kt`, `PortfolioSummary.kt` | Código publicado |
| Testes de cálculo financeiro | `core/domain/src/test/` | Escritos; execução ainda não confirmada |
| Política de alertas D-5/D-3/D-1/D0 | `ReminderPolicy.kt`, `ReminderPolicyTest.kt` | Regra e testes escritos; agendamento Android pendente |
| Persistência Room | `core/database/src/main/.../FinanceDatabase.kt` | Esquema e DAO publicados; migrações/testes pendentes |
| Repositórios Room | `RoomRepositories.kt`, `RoomClientRepository.kt` | Implementados; validação pendente |
| Dashboard financeiro Compose | `app/src/main/.../MainActivity.kt` | Conectado ao Room; sem screenshot validada |
| Cadastro de clientes e projetos | `WorkspaceViewModel.kt`, `MainActivity.kt` | Fluxo implementado; build e teste manual pendentes |
| Contratos e parcelas | — | Apenas modelos e DAO; tela não implementada |
| Anexos e evidências | — | Não implementado |
| CI Android | `.github/workflows/android.yml` | Configurado; resultado de execução não confirmado |
| APK de release | — | Não disponível |

## Como coletar evidências reais
1. Conferir execução de `Android CI` na aba Actions e registrar URL, SHA, data e resultado.
2. Instalar APK gerado em emulador/dispositivo.
3. Criar cliente e projeto **fictícios**, reiniciar app e verificar persistência.
4. Capturar telas anonimizadas em `docs/assets/` com dispositivo e versão.
5. Documentar passos de reprodução, comportamento esperado e resultado observado.
6. Não adicionar CPF, contrato verdadeiro, comprovante bancário ou dados privados em repositório público.

## Pendências prioritárias
Build verde, testes Room instrumentados, validação de cadastro, tela de contratos/parcelas, anexos seguros, notificações reais, backup, acessibilidade e documentação final.
