# Partido Politico API


API gerenciador de um partido politico. Mantem as informações dos partidos politicos, dados pessoais
dos seus politicos, assim como projetos de lei e processos a eles relacionados. A exibição de informações sensiveis
como CPF, endereço, telefone, ou detalhes dos processos esta restrito a apenas administradores.

### Funcionalidades

- [x] Registro e autenticação de usuarios
- [x] CRUD dos diferentes cargos políticos.
- [x] Upload de fotos dos candidatos.
- [x] Gestão dos projetos de lei e processos de cada candidato.
- [x] Display de informações organizadas por cargo ou nome (asc, desc), e paginação.

Para fins de teste, o banco de dados será populado com algumas entidades no momento de execução da aplicação.


## 💻 Pré-requisitos

Antes de começar, verifique se você atendeu aos seguintes requisitos:

* Você instalou `Java 11`


## ☕ Usando PartidoPolitico API

Na pasta principal rodar o seguinte comando

```
./mvnw spring-boot:run
```

Entre no seu navegador e acesse este URL para ler a documentação e testar o API:
```
http://localhost:8081/swagger-ui.html
```

Para fins de teste, você pode acessar com um dos usuarios padrão:

**ADMIN**:
* Username: Admin
* Senha: Gft2021

**USER**:
* Username: User
* Senha: Gft2021

Quando tiver o seu Token de usuario, clique no botão `Autenticar` e no campo `api_key()` ingresse o seu token
da seguinte maneira:
```
"Bearer" + " " + token
```  

Testar a API usando Postman (Postman JSON Link)

```
https://www.getpostman.com/collections/db92dc9229a3f18e6946
```

## 📝 Créditos

Esse projeto foi desenvolvido por [Alex López](https://github.com/lop19029) como parte do programa Starter da  [GFT](https://www.gft.com/br/pt).
