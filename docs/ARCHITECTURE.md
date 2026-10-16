# Arquitetura — Freelance Manager

## Camadas
- **domain**: entidades, invariantes, casos de uso e interfaces de repositórios; não depende de Android.
- **data**: persistência Room, mapeadores, repositórios concretos e migrações.
- **presentation**: Compose, ViewModels, StateFlow, navegação e estados de UI.
- **app**: composição de dependências via Hilt.

Fluxo: Compose → ViewModel → UseCase → Repository → Room.

## Modelo de negócio
Cliente possui projetos. Projeto possui contratos. Contrato possui entregáveis e parcelas. Entregáveis possuem evidências (URI local, hash opcional, data, observação e link de PR). Parcelas possuem recebimentos, permitindo pagamentos parciais e excedentes documentados.

## Regras
1. Valores monetários em centavos (Long), moeda BRL.
2. Parcela prevista e recebimento são conceitos distintos.
3. Datas de entregáveis não são inferidas das datas de pagamento.
4. Eventos financeiros não são apagados silenciosamente: usar histórico auditável.
5. Alertas independentes de entregas e recebimentos em D-5, D-3, D-1 e D0.
6. O aplicativo deve funcionar offline, sem exigir CNPJ.

## Privacidade
Contratos e comprovantes não são incluídos em assets do aplicativo ou no repositório. Usar Storage Access Framework e armazenamento privado; autenticação e sincronização são fases futuras.

## Qualidade
Testes de cálculo de saldo, arredondamento, recebimento parcial, datas, migrações Room e Compose UI; Gradle CI, lint e análise estática.
