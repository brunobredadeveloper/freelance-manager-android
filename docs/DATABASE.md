# Persistência local — Room v1

O módulo `core:database` contém cinco tabelas: clientes, projetos, contratos, parcelas e recebimentos.

## Integridade
- Chaves estrangeiras explícitas e índices nas relações.
- `RESTRICT` impede excluir registros que possuam dependentes.
- Recebimentos são transações individuais, não flags booleanas.
- Valores em centavos (`Long`); datas civis em `epochDay`.
- `Flow` observa alterações sem polling.

## Limites da etapa
As entidades e o DAO foram escritos, mas ainda não estão ligados às telas e ainda não há execução de testes de integração Room confirmada. O banco **não** deve usar migração destrutiva em produção. Antes de release, adicionar migrações, testes instrumentados e validação de dados monetários na camada de domínio.

## Evolução
Próxima rodada: repositórios, mapeadores, casos de uso, Hilt e ViewModels; em seguida, telas Compose com estados de carregamento, erro e vazio.
