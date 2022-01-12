
<h2>Desenvolvendo um sistema de gerenciamento de pessoas em API REST com Spring Boot</h2>

### Tópicos:
 
* Criação de modelo de dados para o mapeamento de entidades em bancos de dados
* Desenvolvimento de operações de gerenciamento de usuários (Cadastro, leitura, atualização e remoção de pessoas de um sistema).
* Relação de cada uma das operações acima com o padrão arquitetural REST.
* Desenvolvimento de testes unitários para validação das funcionalidades.

sistema para o gerenciamento de pessoas de uma empresa através de uma API REST, criada com o Spring Boot.

* Desenvolvimento de testes unitários para validação das funcionalidades
* Implantação do sistema na nuvem através do Heroku

#### *Pagina web para gerenciamento de pessoas desenvolvida com thymeleaf, HTML e BootStrap* 

Para executar o projeto no terminal, digite o seguinte comando:

```shell script
mvn spring-boot:run 
```
Após executar o comando acima, basta apenas abrir o seguinte endereço e visualizar a execução do projeto (resultado paginado):

```
http://localhost:8080/api/v1/people
```

Endpoints: 

```
/all
/{id}
/cpf/{id}

```
São necessários os seguintes pré-requisitos para a execução do projeto:

* Java 11 ou versões superiores.
* Maven 3.6.3 ou versões superiores.
* SpringSource Tool Suite ou sua IDE favorita.

Ferramentas (versionamento, teste e implantação):

* Postman
* Controle de versão GIT.
* GitHub para o armazenamento do projeto na nuvem.
* Heroku para o deploy do projeto na nuvem

### Esta versão da API agrega os seguintes valores:

* Padrão MVC.
* Busca paginada por pessoas e seus respectivos telefones e busca em formato de lista pré-ordenada por nome.
* Busca por CPF.
* Excessões personalizadas a nivel de serviço.
* H2-Database semeado para testar requisições.
* DTOs manuais e automáticos (Mapper)
* **Lombok removido, getters e setters criados manualmente, construtores personalizados.**

Abaixo, links relevantes sobre os tópicos abordados e sobre construções no projeto:

* [Site oficial do Spring](https://spring.io/)
* [Site oficial do Spring Initialzr, para setup do projeto](https://start.spring.io/)
* [Site oficial do Heroku](https://www.heroku.com/)
* [Site oficial do GIT](https://git-scm.com/)
* [Site oficial do GitHub](http://github.com/)
* [Documentação oficial do Lombok](https://projectlombok.org/)
* [Documentação oficial do Map Struct](https://mapstruct.org/)
* [Referência para o padrão arquitetural REST](https://restfulapi.net/)

##### Melhorias? entre em contato.