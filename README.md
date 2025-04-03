<h1 align="center">Sistema de Gestão Escolar</h1>


# Arquitetura
A arquitetura utilizada no desenvolvimento da API é a arquitetura em camadas, comum em aplicações Java.
1. **Camada de Controlador**: Esta é a camada que lida com as solicitações HTTP dos clientes. Os controladores em Spring são geralmente anotados com `@RestController` e contêm métodos mapeados para diferentes endpoints da API.

2. **Camada de Serviço**: Esta camada contém a lógica de negócios da aplicação. Ela é responsável por processar os dados recebidos dos controladores e realizar operações correspondentes.

3. **Camada de Repositório**: Esta é a camada que interage diretamente com o banco de dados. Ela contém métodos para operações CRUD (Criar, Ler, Atualizar, Deletar) e consultas personalizadas.

4. **Camada de Modelo ou Domínio**: Esta camada contém as classes de modelo (também conhecidas como entidades ou POJOs - Plain Old Java Objects) que representam as tabelas do banco de dados.

# Diagrama de Classes

![diagrama](https://github.com/ValterGabriell/FrequencySystem-Backend/assets/63808405/e18bbc42-c892-4a2a-b96b-6c1418f20093)


# Documentação da API 

# Swagger
- Link para baixar o YAML: https://drive.google.com/file/d/1QUFxwPZsvtsKspFKOxseVA2wQssx4d__/view?usp=sharing
- Importar esse YAML no site: https://editor.swagger.io/ -> File -> Import File
- URL BASE API: https://sf-homologation.onrender.com/
- DOCUMENTAÇÃO AUXILIAR: https://sf-homologation.onrender.com/swagger-ui/index.html 


Este documento descreve os controladores da API e seus respectivos métodos.

## Índice

1. AdmController
2. AverageController
3. DisciplineController
4. FrequencyController
5. ParentController
6. ProfessorController
7. SchoolClassController

## AdmController

Este controlador lida com as operações relacionadas aos administradores.

- `POST /api/v1/admin`: Insere um novo administrador.
- `GET /api/v1/admin/{cnpj}`: Obtém um administrador pelo CNPJ.
- `GET /api/v1/admin`: Obtém todos os administradores.
- `GET /api/v1/admin/{cnpj}/professors`: Obtém todos os professores por CNPJ.
- `PATCH /api/v1/admin/update-first-name/{cnpj}`: Atualiza o primeiro nome do usuário.
- `PATCH /api/v1/admin/update-second-name/{cnpj}`: Atualiza o segundo nome do usuário.
- `PATCH /api/v1/admin/update-password/{cnpj}`: Atualiza a senha do usuário.
- `DELETE /api/v1/admin/{cnpj}`: Deleta um administrador pelo CNPJ.

## AverageController

Este controlador lida com as operações relacionadas às médias.

- `POST /api/v1/average`: Insere uma nova média.
- `GET /api/v1/average/{studentSkId}`: Obtém a média de um estudante pelo ID.
- `PATCH /api/v1/average/{studentSkId}`: Atualiza a média de um estudante pelo ID do estudante.

## DisciplineController

Este controlador lida com as operações relacionadas às disciplinas.

- `POST /api/v1/discipline/{adminCnpj}`: Insere uma nova disciplina.
- `GET /api/v1/discipline/{skid}`: Obtém uma disciplina pelo ID.
- `GET /api/v1/discipline`: Obtém todas as disciplinas.
- `PUT /api/v1/discipline/update/{skid}`: Atualiza uma disciplina.
- `DELETE /api/v1/discipline/{skid}`: Deleta uma disciplina pelo ID.

## FrequencyController

Este controlador lida com as operações relacionadas às frequências.

- `POST /api/v1/frequency`: Valida a frequência de um estudante.
- `POST /api/v1/frequency`: Justifica a ausência de um estudante.
- `PUT /api/v1/frequency`: Atualiza a ausência de um estudante.
- `GET /api/v1/frequency`: Obtém a lista de dias que um estudante foi para a aula.
- `GET /api/v1/frequency/sheet`: Cria uma planilha para o dia atual.
- `GET /api/v1/frequency/sheet`: Obtém a planilha para um dia específico.
- `GET /api/v1/frequency/month/{month}`: Obtém a lista de dias que um estudante foi para a aula em um mês específico. Formato do month: [JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER]

## ParentController

Este controlador lida com as operações relacionadas aos pais.

- `POST /api/v1/parent/{adminCnpj}`: Insere um novo pai.
- `GET /api/v1/parent/{skid}`: Obtém um pai pelo ID.
- `GET /api/v1/parent`: Obtém todos os pais.
- `PUT /api/v1/parent/update/{skid}`: Atualiza um pai.
- `DELETE /api/v1/parent/{skid}`: Deleta um pai pelo ID.

- ## StudentsController

Este controlador lida com as operações relacionadas aos códigos QR.

- `POST /api/v1/students/{adminCnpj}`: Insere um estudante no banco de dados. O corpo da solicitação deve ser um objeto InsertStudents. O caminho inclui o CNPJ do administrador. Os parâmetros da consulta incluem o ID do inquilino e o identificador dos pais.
- `GET /api/v1/students`: Obtém todos os estudantes do banco de dados. Os parâmetros da consulta incluem o ID do inquilino.
- `GET /api/v1/students/{skid}`:  Obtém um estudante específico pelo ID do SK. Os parâmetros da consulta incluem o ID do inquilino.
- `DELETE /api/v1/students`: Exclui um estudante específico pelo ID do estudante. Os parâmetros da consulta incluem o ID do estudante e o ID do inquilino.


## ProfessorController

Este controlador lida com as operações relacionadas aos professores.

- `POST /api/v1/professor/{adminCnpj}`: Insere um novo professor.
- `GET /api/v1/professor/{skid}`: Obtém um professor pelo ID.
- `PUT /api/v1/professor/update/{skid}`: Atualiza um professor.
- `DELETE /api/v1/professor/{skid}`: Deleta um professor pelo ID.

## SchoolClassController

Este controlador lida com as operações relacionadas às turmas escolares.

- `POST /api/v1/class/{adminId}`: Cria uma nova turma escolar.
- `GET /api/v1/class/{skid}`: Obtém uma turma escolar pelo ID.
- `PATCH /api/v1/class/student/{skid}`: Define um estudante para uma turma escolar.
- `PATCH /api/v1/class/professor/{skid}`: Define um professor para uma turma escolar.
- `GET /api/v1/class/students/{skid}`: Obtém todos os estudantes de uma turma escolar.
- `GET /api/v1/class/professors/{skid}`: Obtém todos os professores de uma turma escolar.
- `GET /api/v1/class`: Obtém todas as turmas escolares.
- `DELETE /api/v1/class/{skid}`: Deleta uma turma escolar pelo ID.

- ## QRCodeController

Este controlador lida com as operações relacionadas aos códigos QR.

- `GET /api/v1/qrcode/generate`: Gera um código QR em formato Base64 para um estudante específico.
- `GET /api/v1/qrcode/image`: Gera um código QR em formato de imagem para um estudante específico.

# A serem implementados

## AdmController
Este controlador lida com as operações relacionadas aos administradores.

1. `PATCH /api/v1/admin/update-email/{cnpj}`: Atualiza o email do administrador.
2. `GET /api/v1/admin/count`: Obtém a contagem total de administradores.
3. `GET /api/v1/admin/recent`: Obtém os administradores recentemente adicionados.
4. `PATCH /api/v1/admin/reset-password/{cnpj}`: Redefine a senha do administrador.

## AverageController
Este controlador lida com as operações relacionadas às médias.

1. `GET /api/v1/average/top/{number}`: Obtém as top 'n' médias.
2. `GET /api/v1/average/bottom/{number}`: Obtém as 'n' médias mais baixas.
3. `GET /api/v1/average/range/{start}/{end}`: Obtém médias dentro de um intervalo específico.
5. `DELETE /api/v1/average/{studentSkId}`: Exclui a média de um estudante pelo ID do estudante.

## DisciplineController
Este controlador lida com as operações relacionadas às disciplinas.

1. `GET /api/v1/discipline/students/{skid}`: Obtém todos os estudantes matriculados em uma disciplina pelo ID da disciplina.
2. `GET /api/v1/discipline/professors/{skid}`: Obtém todos os professores que lecionam uma disciplina pelo ID da disciplina.
3. `POST /api/v1/discipline/enroll/{skid}`: Matricula um estudante em uma disciplina pelo ID da disciplina.
4. `DELETE /api/v1/discipline/unenroll/{skid}`: Desmatricula um estudante de uma disciplina pelo ID da disciplina.

## StudentsController
Este controlador lida com as operações relacionadas aos estudantes.

1. `PATCH /api/v1/students/update-grade/{skid}`: Atualiza a nota de um estudante.

## ProfessorController
Este controlador lida com as operações relacionadas aos professores.

1. `GET /api/v1/professor/classes/{skid}`: Obtém todas as turmas que um professor leciona pelo ID do professor.

## SchoolClassController
Este controlador lida com as operações relacionadas às turmas escolares.

1. `GET /api/v1/class/average/{skid}`: Obtém a média de uma turma escolar pelo ID da turma.



<h1>Creditos</h1>

---

<a href="https://www.linkedin.com/in/valter-gabriel">
  <img style="border-radius: 50%;" src="https://user-images.githubusercontent.com/63808405/171045850-84caf881-ee10-4782-9016-ea1682c4731d.jpeg" width="100px;" alt=""/>
  <br />
  <sub><b>Valter Gabriel</b></sub></a> <a href="https://www.linkedin.com/in/valter-gabriel" title="Linkedin">🚀</ a>


