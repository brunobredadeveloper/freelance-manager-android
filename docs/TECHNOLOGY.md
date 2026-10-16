# Estratégia tecnológica

## Princípio
Adotar tecnologia moderna quando ela melhora confiabilidade, segurança, acessibilidade ou manutenção — e não apenas por ser recente.

## Stack alvo
- Kotlin + Jetpack Compose + Material 3, incluindo suporte a tema dinâmico.
- MVVM com `StateFlow` e `combine`; estados imutáveis.
- Clean Architecture com contratos de repositórios no domínio.
- Room e Flow como fonte offline de verdade.
- Hilt para composição das dependências (integração pendente).
- WorkManager para trabalhos persistentes e lembretes compatíveis com as restrições do Android (pendente).
- Kotlin Serialization para exportação versionada (pendente).
- Testes unitários, instrumentados e UI Compose.
- CI com artefatos de APK e evidências de execução.

## Segurança e dados
Sem upload de comprovantes reais para o GitHub; anexos privados via Storage Access Framework; backup seguro, criptografia e política de retenção antes de distribuição pública.

## Compatibilidade
As versões de dependências serão estabilizadas conforme resultados reais de CI e testes. **Não presumir que o código compila ou que uma versão é a mais recente sem validação.**

## Evidências
Somente capturas reais de telas executadas e resultados de CI podem ser classificados como validação. Mockups devem ser rotulados como conceitos.
