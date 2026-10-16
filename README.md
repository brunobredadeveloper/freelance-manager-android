# Freelance Manager Android

Aplicativo Android nativo para gerenciar freelances de pessoas físicas, MEIs e PJs.

## Objetivo
Centralizar clientes, contratos, escopos, entregáveis, evidências, pagamentos previstos e recebidos, histórico e alertas de prazos.

## Stack planejada
- Kotlin e Jetpack Compose (Material 3)
- Clean Architecture, MVVM, SOLID, Coroutines e StateFlow
- Hilt para injeção de dependências
- Room para persistência offline-first
- WorkManager para alertas e tarefas persistentes
- Testes automatizados e GitHub Actions

## Regras importantes
- Dinheiro representado em centavos, sem ponto flutuante.
- Um pagamento previsto não é automaticamente um recebimento confirmado.
- Recebimentos parciais e valores divergentes são suportados.
- Alertas de entregáveis e de pagamentos são independentes.
- Dados reais de clientes, comprovantes e contratos não entram no GitHub público.

## Status
MVP em desenvolvimento: dashboard financeiro, cadastro local de clientes/projetos/contratos/parcelas e registro de recebimentos já possuem código Compose + Room. **Build, testes de integração e APK ainda não foram validados.** Consulte [evidências](docs/EVIDENCES.md) e [checklist de release](docs/RELEASE_CHECKLIST.md).

## Próximos passos
1. Gradle, app Compose e pipeline CI.
2. Modelagem do domínio, repositórios e testes.
3. Room, ViewModels e telas de projetos/pagamentos.
4. Evidências locais e lembretes configuráveis (5/3/1/0 dias).
5. Validação em emulador/dispositivo e publicação de APK de teste.
