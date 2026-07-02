# Gerenciador de Memória (Best-Fit)

Atividade da disciplina de **Sistemas Operacionais**: simulador de gerenciamento de memória com alocação, liberação e compactação de blocos.

## Sobre o projeto

O `GerenciadorMemoria` mantém uma **lista encadeada de blocos livres** e implementa:

- **Alocação Best-Fit** — percorre os blocos livres e escolhe o menor bloco capaz de atender ao pedido, reduzindo a fragmentação.
- **Liberação com inserção ordenada** — o bloco liberado volta para a lista de livres mantendo a ordem por endereço.
- **Coalescência (compactação)** — blocos livres adjacentes são automaticamente fundidos em um bloco maior.

| Classe | Descrição |
|---|---|
| `BlocoMemoria` | Nó da lista: endereço inicial, tamanho e ponteiro para o próximo |
| `GerenciadorMemoria` | Lógica de alocação, liberação, compactação e exibição da memória livre |
| `Main` | Demonstração: aloca, esgota a memória, libera e realoca |

Exemplo de saída:

```
Memória livre: [0,14]
Alocado 3 unidades no endereço 0
Alocado 4 unidades no endereço 3
Alocado 5 unidades no endereço 7
Erro: Não há espaço suficiente para alocar 6 unidades.
```

## Tecnologias

- Java
- Maven

## Como executar

```bash
mvn compile exec:java -Dexec.mainClass="org.example.Main"
```

Ou abra o projeto em uma IDE (IntelliJ, Eclipse) e execute a classe `Main`.
