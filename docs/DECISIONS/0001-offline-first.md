# ADR 0001 — Offline-first

**Status:** Aceita para o MVP

## Contexto
Freelancers precisam consultar contratos e registrar pagamentos mesmo sem conectividade.

## Decisão
Room será a fonte local de verdade; domínio puro Kotlin não dependerá de Room, Firebase ou Compose. A sincronização é opcional e posterior.

## Consequências
- Funciona sem conta e sem CNPJ.
- Repositórios abstratos permitem testar casos de uso sem Android.
- Migrações de banco e estratégia de backup serão obrigatórias antes da versão de produção.
- Anexos exigirão tratamento de permissões URI e política de retenção.
