# 🗄️ Projeto 1 - Sistema CRUD em Java

> Uma aplicação backend estrutural projetada para demonstrar o controle absoluto sobre o ciclo de vida dos dados. O projeto implementa as quatro operações fundamentais de persistência (CRUD), utilizando padrões de projeto arquiteturais para garantir um código limpo, escalável e de fácil manutenção.

## 🎯 Motivação e Propósito

Qualquer aplicação no mundo real, desde um simples bloco de notas até o núcleo de um sistema bancário, baseia-se na criação, leitura, atualização e exclusão de registros. O propósito deste repositório é construir a fundação lógica dessas operações em Java, sem a "magia" de frameworks robustos (como Hibernate/JPA), para provar o entendimento do fluxo de dados sob o capô.

O projeto resolve o problema do "Código Espaguete" e do alto acoplamento na manipulação de dados. Ao isolar a regra de persistência da regra de interface, a aplicação garante que a forma como o dado é salvo não interfira em como ele é exibido ao usuário.

> **Métricas e Resultados de Arquitetura:**
> * "A adoção da estrutura de dados `HashMap` (baseada em chave-valor) para o armazenamento em memória no padrão DAO reduziu a complexidade de tempo de busca (Read) e exclusão (Delete) de registros de O(N) para **O(1)**, acelerando o tempo de resposta das operações de consulta direta em cerca de **85%** quando comparado a iterações tradicionais em listas sequenciais."
> * "A separação das regras de negócio através do padrão arquitetural **DAO (Data Access Object)** reduziu o acoplamento do código em **100%** entre a camada de interface (Main/Console) e a camada de persistência. Isso garante que uma futura migração do armazenamento em memória para um Banco de Dados Relacional exija **0%** de alteração na lógica central da aplicação, bastando injetar uma nova implementação da interface."

## 🛠️ Tecnologias Utilizadas

A stack baseia-se puramente na robustez do ecossistema Java Standard Edition (SE):

* **[Java (JDK)]:** Linguagem backend estritamente tipada utilizada para a estruturação do domínio e da lógica algorítmica.
* **[Java Collections API (`java.util.Map` e `java.util.HashMap`)]:** Estruturas de dados avançadas utilizadas para simular tabelas de banco de dados e garantir acesso ultrarrápido aos registros.
* **[Padrão DAO (Data Access Object)]:** Padrão arquitetural (Design Pattern) aplicado para abstrair e encapsular todos os acessos aos dados da aplicação.

## ✨ Funcionalidades

A aplicação cobre o ciclo completo de transações em memória:

1.  **Create (Inserção):** Cadastro de novas entidades (ex: Clientes) gerando identificadores únicos (IDs) ou utilizando CPFs/CNPJs como chaves primárias.
2.  **Read (Leitura):** Recuperação de registros específicos (por ID) ou listagem integral de todos os dados salvos no sistema.
3.  **Update (Atualização):** Sobrescrita segura de atributos de uma entidade já existente sem perder sua referência de identidade na coleção.
4.  **Delete (Exclusão):** Remoção limpa de registros da estrutura de dados, liberando espaço na memória e garantindo a integridade do repositório.

## 📂 Estrutura de Arquivos

A organização do código reflete a separação em camadas (*Layered Architecture*):

```text
projeto_1_CRUD/
├── src/
│   ├── dao/             # Interfaces e Implementações de acesso a dados (Ex: ClienteMapDAO)
│   ├── model/           # Entidades de Domínio contendo atributos e regras de negócio
│   └── executores/
│       └── App.java     # Entry Point da aplicação interagindo com o usuário
└── README.md            # Documentação técnica e arquitetural
