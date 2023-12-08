<h1 align="center">
    Simplex
</h1>

## 💻 Grupo

- [Fábio Augusto Araújo Santos](https://github.com/fabio-aug)
- [Luana Assis Silva](https://github.com/luanaassis)

## 📰 Introdução

Desenvolvimento do Simplex para problemas "bem comportados" de minimização e maximização.

## 📂 Estrutura

O ambiente de trabalho utilizado foi o Visual Studio Code. Os arquivos foram organizados da seguinte forma:

- `/src`: pasta destinada aos códigos fontes do projeto.
- `/.vscode`: pasta destinada a configuração do projeto caso use a extensão ['Extension Pack for Java'](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack).

Obs: para rodar usando a extensão é necessário algumas alterações no código.

## 🏃 Instruções para compilação e execução (Apêndice)

- Para compilar o projeto, basta inserir a seguinte linha de comando no terminal dentro da pasta `src`:

      javac src\Simplex.java

- Para executar o projeto, é necessário inicialmente um arquivo com as informações do modelo a ser processado. Este arquivo deve ser incluído na pasta raíz.

- A estrutura do arquivo deve seguir o seguinte padrão:
    - A primeira linha é um valor numérico que identifica se é um problema de minimização (-1) ou maximização (1);
    - A segunda linha é um valor numérico (x) que identifica quantas são as variáveis do problema;
    - A terceira linha é um valor numérico (x) que identifica quantas são as restrições do problema;
    - A quarta linha contém x valores numéricos que identificam os coeficientes das variáveis na função objetivo;
    - A demais linhas a partir da quarta contém x+1 valores numéricos que identificam os coeficientes das variáveis nas restrições do problema seguido do valor limitante de cada restrição (lado direito da equação).

- Após isso, basta inserir a seguinte linha de comando no terminal para realizar a execução:
        
        java src\Simplex.java <nomeArquivoEntrada>
