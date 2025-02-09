
# 🚀 Desafio Magalog - Sistema de Processamento de Pedidos

O Desafio Magalog é um projeto desenvolvido para processar arquivos de dados contendo informações sobre usuários, pedidos e produtos. O objetivo é agregar esses dados e gerar um JSON estruturado que represente a hierarquia de usuários, seus pedidos e os produtos associados a cada pedido.


## 🛠️ Tecnologias Utilizadas
Este projeto utiliza as seguintes tecnologias:

- Java 21: Linguagem de programação principal.
- Jackson: Biblioteca para serialização e desserialização de JSON.
- Maven : Gerenciador de dependências e build.
- GitHub Actions : Para automação de testes e geração de relatórios de cobertura de código.
## 💡 Funcionalidades

- Parseamento de Arquivos: Leitura e interpretação de arquivos contendo linhas de dados específicas.
- Agregação de Dados: Agrupamento de pedidos e produtos por usuário.
- Serialização JSON: Geração de um arquivo JSON bem formatado representando a estrutura de usuários, pedidos e produtos.


## 📥 Como Rodar o Projeto na Sua Máquina
### Pré-requisitos
- Java 21: Certifique-se de que o Java 21 está instalado no seu sistema.
- Maven: Certifique-se de que o Maven está instalado no seu sistema.

### Passos para Executar

#### Clone o Repositório
```bash
git clone https://github.com/Elisariane/desafio-magalog.git
cd desafio-magalog
```

#### Instale as Dependências
```bash
mvn install
```

#### Execute o Projeto
Para executar o projeto, você precisa fornecer o caminho de um arquivo ou diretório contendo os dados de entrada.
```bash
mvn exec:java -Dexec.args="files-input/data_1.txt"
```
Substitua "files-input/data_1.txt" pelo caminho do arquivo ou diretório que você deseja processar.


## 🧪 Testes
O projeto inclui testes unitários para garantir que todas as funcionalidades estejam funcionando corretamente. Você pode executar os testes localmente usando:

```bash
mvn test
```

### Cenários de Testes
- Parseamento de Linhas Válidas : Verifica se linhas de arquivo são parseadas corretamente.
- Parseamento de Linhas Inválidas : Verifica se linhas com tamanhos incorretos ou dados mal formatados são tratadas adequadamente.
- Agregação de Dados : Verifica se os dados são agrupados corretamente por usuários e pedidos.
- Serialização JSON : Verifica se o JSON gerado está bem formatado e segue a estrutura esperada.
## 📄 Exemplo de Arquivo de Entrada

Um exemplo de linha de arquivo de entrada é mostrado abaixo:

```text
0000000070                              Palmer Prosacco00000007530000000003     1836.7420210308
```

## 🔍 Estrutura do JSON de Saída
O JSON gerado terá a seguinte estrutura:

```json
[
  {
    "user_id": 70,
    "name": "Palmer Prosacco",
    "orders": {
      "753": {
        "order_id": 753,
        "date": "2021-03-08",
        "total": "1836.74",
        "products": [
          {
            "product_id": 3,
            "value": "1836.74"
          }
        ]
      }
    }
  },
  {
    "user_id": 75,
    "name": "Bobbie Batz",
    "orders": {
      "798": {
        "order_id": 798,
        "date": "2021-11-16",
        "total": "1578.57",
        "products": [
          {
            "product_id": 2,
            "value": "1578.57"
          }
        ]
      }
    }
  }
]
```
## 👤 Contato

- Autor : Elisariane
- Email : elisarianeBS@gmail.com
- LinkedIn : [LinkedIn](https://www.linkedin.com/in/elisarianebarbosa/)
