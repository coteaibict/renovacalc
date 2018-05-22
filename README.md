# renovacalc
Projeto em parceria com Embrapa, CTBE, MME e ANP.

# Build

Rodar `npm install` no diretório anp-renovacalc-client. Isto irá instalar as
dependências do projeto.  

Rodar `mvn package` neste diretório. Isto irá criar o arquivo
anp-renovacalc-service-1.0.0.war neste diretório. Ele já inclui todo o
necessário para o projeto. Não é necessário instalar nenhuma dependência do
angular no ambiente de produção, pois o client será compilado para html/js e
colocando dentro do war.

Assume que irá ser servido em www.site.com/anp-renovacalc-service-1.0.0 

Caso vá ao ar com outro sufixo, dois arquivos devem ser modificados, para setar o
base-href:

anp-renovacalc-client/pom.xml

```
<configuration>
    <workingDirectory>.</workingDirectory>
    <executable>ng</executable>
    <arguments>
        <argument>build</argument>
        <argument>--prod</argument>
        <argument>--aot</argument>
        <argument>--base-href</argument>
        <argument>/anp-renovacalc-service-1.0.0/</argument>
    </arguments>
</configuration>
```

Mudar o último argumento para o novo sufixo. Isto irá compilar os arquivos do
angualar para a base correta. Caso o projeto seja servido na raiz
(www.site.com), apagar os dois ultimos argumentos.

Também é necessário mudar esse valor no environment do angular, para que ele encontre o servidor da API corretamente:

anp-renovacalc-client/src/environments/environment.prod.ts

```
export const environment = {
  production: true,
  baseHref: '/anp-renovacalc-service-1.0.0/'
};
```

Mudar o "baseHref" para o novo sufixo. Caso o projeto seja servido na raiz, setar baseHref como ''.
