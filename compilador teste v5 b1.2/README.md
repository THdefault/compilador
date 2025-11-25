# Compilador ELE - Projeto completo e funcional (versão didática)

## Requisitos
- Java 21 (LTS) — o projeto foi atualizado para target Java 21
- Maven

> Nota: o build local exige que o JDK 21 e o Maven estejam instalados e disponíveis no PATH. Se não tiver o Maven instalado, veja as instruções abaixo.

## Como compilar e executar
1. Verifique a versão do Java e do Maven (PowerShell):
   ```powershell
   java -version
   mvn -v
   ```

2. Compilar:
   ```powershell
   mvn -q clean package
   ```

3. Executar o exemplo `exemplo.ele`:
   ```powershell
   mvn -q exec:java -Dexec.args="exemplo.ele"
   ```
4. Se nao funcionar usar snapshot ("usar um desses")
   ```powershell
   mvn package
   java -jar target/compilador-ele-1.0-SNAPSHOT-jar-with-dependencies.jar sample.ele

   mvn -q exec:java "-Dexec.args=exemplo.ele"
   ```

5. tudo vai voltar no terminal, no caso esta retornando / (100/0)
   -se editar os valores no exeplo.ele pode variar o retorno, se o valortes somados forem maior que 101, volta (0) se forem menor (1)

## Instalar JDK 21 no Windows

1. Baixe o JDK 21 (Eclipse Temurin / Microsoft / Adoptium) e instale:
   - Eclipse Temurin: https://adoptium.net
   - Microsoft Build of OpenJDK: https://learn.microsoft.com/en-us/java/openjdk/

2. Configure variáveis (PowerShell temporário para a sessão):
   ```powershell
   $env:JAVA_HOME = 'C:\Program Files\Eclipse Adoptium\jdk-21'
   $env:Path = $env:JAVA_HOME + '\bin;' + $env:Path
   java -version
   ```

3. (Opcional) Permanecer nas variáveis de ambiente do sistema via Painel de Controle > Sistema > Configurações avançadas > Variáveis de Ambiente.

## O que contém
- Um lexer e parser implementados em Java (sem JFlex/CUP).
- AST, type checker, symbol table e interpretador.
- Arquivo `exemplo.ele` com um exemplo de programa.
