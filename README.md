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
- URL BASE API: https://sfsystem.onrender.com
- DOCUMENTAÇÃO AUXILIAR: https://documenter.getpostman.com/view/30151744/2s9YeAAaHP


Este documento descreve os controladores da API e seus respectivos métodos.

## AdmController

Este controlador lida com as operações relacionadas aos administradores.

### `POST /api/v1/admin/insert`

Endpoint para inserir um novo administrador.

**Requisição:**
```
{
  "firstName": "Nome",
  "secondName": "Sobrenome",
  "password": "Senha",
  "cnpj": "12345678901234",
  "contacts": [
    {
      "type": "email",
      "value": "exemplo@email.com"
    },
    {
      "type": "telefone",
      "value": "123456789"
    }
  ]
}
```

**Resposta (sucesso):**
```
{
  "cnpj": "12345678901234",
  "skid": "..."
  "firstName": "Nome",
  "secondName": "Sobrenome",
  "links": {...}
}
```

### `GET /api/v1/admin/login`

Endpoint para realizar o login do administrador.

**Requisição:**
```
{
  "cnpj": "12345678901234",
  "password": "Senha"
}
```

**Resposta (sucesso):**
```
"API_KEY"
```

### `DELETE /v1/logout`

Endpoint para efetuar o logout do administrador.

**Requisição:**
```
{
  "tenant": 1
}
```

**Resposta (sucesso):**
```
HTTP 204 No Content
```

### `GET /v1/{cnpj}`

Endpoint para obter informações de um administrador pelo CNPJ.

**Requisição:**
```
{
  "tenant": 1
}
```

**Resposta (sucesso):**
```
{
  "cnpj": "12345678901234",
  "skid": "...",
  "firstName": "Nome",
  "secondName": "Sobrenome",
  "links": {...}
}
```

### `GET /v1/{cnpj}/professors`

Endpoint para obter todos os professores associados a um administrador pelo CNPJ.

**Requisição:**
```
{
  "tenant": 1
}
```

**Resposta (sucesso):**
```
[
  {
    "professorId": "123",
    "name": "Professor1",
    "email": "professor1@email.com"
  },
  {
    "professorId": "456",
    "name": "Professor2",
    "email": "professor2@email.com"
  }
]
```

### `PATCH /v1/update-first-name/{cnpj}`

Endpoint para atualizar o primeiro nome de um administrador.

**Requisição:**
```
{
  "firstName": "NovoNome"
}
```

**Resposta (sucesso):**
```
{
  "cnpj": "12345678901234",
  "skid": "...",
  "firstName": "NovoNome",
  "secondName": "Sobrenome",
  "links": {...}
}
```

### `PATCH /v1/update-second-name/{cnpj}`

Endpoint para atualizar o segundo nome de um administrador.

**Requisição:**
```
{
  "secondName": "NovoSobrenome"
}
```

**Resposta (sucesso):**
```
{
  "cnpj": "12345678901234",
  "skid": "...",
  "firstName": "Nome",
  "secondName": "NovoSobrenome",
  "links": {...}
}
```

### `PATCH /v1/update-password/{cnpj}`

Endpoint para atualizar a senha de um administrador.

**Requisição:**
```
{
  "password": "NovaSenha"
}
```

**Resposta (sucesso):**
```
{
  "cnpj": "12345678901234",
  "skid": "...",
  "firstName": "Nome",
  "secondName": "Sobrenome",
  "links": {...}
}
```

### `DELETE /v1/{cnpj}`

Endpoint para excluir um administrador pelo CNPJ.

**Requisição:**
```
{
  "tenant": 1
}
```

**Resposta (sucesso):**
```
HTTP 204 No Content
```

## AverageController

Este controlador lida com as operações relacionadas às médias.

### `POST /api/v1/average`

Endpoint para inserir uma nova média.

**Requisição:**
```
{
  "studentSkId": "ID_DO_ESTUDANTE",
  "disciplineSkId": "ID_DA_DISCIPLINA",
  "average": 8.5,
  "evaluation": 1
}
```

**Resposta (sucesso):**
```
"Média inserida com sucesso."
```

### `GET /api/v1/average/{studentSkId}`

Endpoint para obter as médias de um estudante.

**Requisição:**
```
{
  "tenant": 1
}
```

**Resposta (sucesso):**
```
[
  {
    "studentSkId": "ID_DO_ESTUDANTE",
    "disciplineSkId": "ID_DA_DISCIPLINA",
    "average": 8.5,
    "evaluation": 1
  },
  {
    "studentSkId": "ID_DO_ESTUDANTE",
    "disciplineSkId": "ID_DA_DISCIPLINA",
    "average": 7.5,
    "evaluation": 2
  }
]
```

### `GET /api/v1/average/{studentSkId}/total`

Endpoint para obter a média total de um estudante.

**Requisição:**
```
{
  "tenant": 1
}
```

**Resposta (sucesso):**
```
"7.8"
```

### `PATCH /api/v1/average/{studentSkId}`

Endpoint para atualizar a média de um estudante.

**Requisição:**
```
{
  "studentSkId": "ID_DO_ESTUDANTE",
  "disciplineSkId": "ID_DA_DISCIPLINA",
  "average": 9.0,
  "evaluation": 3
}
```

**Resposta (sucesso):**
```
"Média atualizada com sucesso."
```

Os campos `ID_DO_ESTUDANTE` e `ID_DA_DISCIPLINA` devem ser substituídos pelos identificadores reais do estudante e da disciplina, respectivamente.

## DisciplineController

Este controlador lida com as operações relacionadas às disciplinas.

### `POST /api/v1/discipline`

Endpoint para inserir uma nova disciplina.

**Requisição:**
```
{
  "name": "Nome da Disciplina",
  "description": "Descrição da Disciplina",
  "professorId": "ID_DO_PROFESSOR",
  "adminId": "ID_DO_ADMINISTRADOR"
}
```

**Resposta (sucesso):**
```
{
  "data": "ID_GERADO_PARA_A_DISCIPLINA"
}
```

### `PUT /api/v1/discipline/{skid}`

Endpoint para atualizar uma disciplina existente.

**Requisição:**
```
{
  "name": "Novo Nome da Disciplina",
  "description": "Nova Descrição da Disciplina",
  "professorId": "NOVO_ID_DO_PROFESSOR",
  "adminId": "NOVO_ID_DO_ADMINISTRADOR"
}
```

**Resposta (sucesso):**
```
"Mudanças aplicadas com sucesso."
```

### `GET /api/v1/discipline`

Endpoint para obter todas as disciplinas.

**Requisição:**
```
{
  "tenantId": 1
}
```

**Resposta (sucesso):**
```
[
  {
    "skid": "ID_DA_DISCIPLINA",
    "name": "Nome da Disciplina",
    "description": "Descrição da Disciplina",
    "professorId": "ID_DO_PROFESSOR",
    "adminId": "ID_DO_ADMINISTRADOR"
  },
  {
    "skid": "ID_DA_DISCIPLINA",
    "name": "Nome da Disciplina",
    "description": "Descrição da Disciplina",
    "professorId": "ID_DO_PROFESSOR",
    "adminId": "ID_DO_ADMINISTRADOR"
  }
]
```

### `GET /api/v1/discipline/{skid}`

Endpoint para obter uma disciplina específica.

**Requisição:**
```
{
  "tenantId": 1
}
```

**Resposta (sucesso):**
```
{
  "skid": "ID_DA_DISCIPLINA",
  "name": "Nome da Disciplina",
  "description": "Descrição da Disciplina",
  "professorId": "ID_DO_PROFESSOR",
  "adminId": "ID_DO_ADMINISTRADOR"
}
```

### `DELETE /api/v1/discipline/{skid}`

Endpoint para excluir uma disciplina.

**Requisição:**
```
{
  "tenantId": 1
}
```

**Resposta (sucesso):**
```
HTTP 204 No Content
```

Os campos `ID_DO_PROFESSOR`, `ID_DO_ADMINISTRADOR` e `ID_DA_DISCIPLINA` devem ser substituídos pelos identificadores reais do professor, administrador e disciplina, respectivamente.

## FrequencyController

Este controlador lida com as operações relacionadas às frequências.
### `POST /api/v1/frequency/validate`

Endpoint para validar a frequência de um estudante.

**Requisição:**
```json
{
  "studentSkId": "ID_DO_ESTUDANTE",
  "tenant": 1
}
```

**Resposta (sucesso):**
```json
{
  "message": "Frequência validada com sucesso.",
  "dayListThatStudentGoToClasses": [
    {
      "date": "2023-01-01",
      "attended": true
    },
    {
      "date": "2023-01-02",
      "attended": false
    }
  ]
}
```

### `POST /api/v1/frequency/justify`

Endpoint para justificar uma ausência de um estudante.

**Requisição:**
```json
{
  "justifyAbscenceDesc": "Descrição da Justificativa",
  "studentSkId": "ID_DO_ESTUDANTE",
  "date": "2023-01-01",
  "tenant": 1
}
```

**Resposta (sucesso):**
```json
{
  "message": "Ausência justificada com sucesso.",
  "dayListThatStudentGoToClasses": [
    {
      "date": "2023-01-01",
      "attended": false,
      "justified": true
    },
    {
      "date": "2023-01-02",
      "attended": false
    }
  ]
}
```

### `PATCH /api/v1/frequency/update`

Endpoint para atualizar uma ausência de um estudante.

**Requisição:**
```json
{
  "studentSkId": "ID_DO_ESTUDANTE",
  "date": "2023-01-01",
  "tenant": 1
}
```

**Resposta (sucesso):**
```json
{
  "message": "Ausência atualizada com sucesso.",
  "dayListThatStudentGoToClasses": [
    {
      "date": "2023-01-01",
      "attended": true
    },
    {
      "date": "2023-01-02",
      "attended": false
    }
  ]
}
```

### `GET /api/v1/frequency/list`

Endpoint para obter a lista de dias em que um estudante compareceu às aulas.

**Requisição:**
```json
{
  "studentSkId": "ID_DO_ESTUDANTE",
  "tenant": 1
}
```

**Resposta (sucesso):**
```json
{
  "dayListThatStudentGoToClasses": [
    {
      "date": "2023-01-01",
      "attended": true
    },
    {
      "date": "2023-01-02",
      "attended": false
    }
  ]
}
```

### `GET /api/v1/frequency/sheet`

Endpoint para criar uma folha de frequência para o dia atual.

**Requisição:**
```json
{
  "tenant": 1
}
```

**Resposta (sucesso):**
Retorna um arquivo Excel como resposta.

### `GET /api/v1/frequency/sheet-by-date`

Endpoint para obter uma folha de frequência para uma data específica.

**Requisição:**
```json
{
  "date": "2023-01-01",
  "tenant": 1
}
```

**Resposta (sucesso):**
Retorna um arquivo Excel como resposta.
[JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER]
### `GET /api/v1/frequency/month/{month}`

Endpoint para obter os dias em que um estudante assistiu às aulas em um mês específico.

**Requisição:**
```json
{
  "studentSkId": "ID_DO_ESTUDANTE",
  "month": "2023-01",
  "tenant": 1
}
```

**Resposta (sucesso):**
```json
[
  {
    "date": "2023-01-01",
    "attended": true
  },
  {
    "date": "2023-01-02",
    "attended": false
  }
]
```

## ModulesController

### `POST /api/v1/modules/insert`

Endpoint para inserir módulos.

**Requisição:**
```json
{
  "modules": ["PROFESSOR", "STUDENTS", "QRCODE"]
}
```

**Resposta (sucesso):**
```json
"Módulos adicionados!"
```

### `GET /api/v1/modules/get`

Endpoint para obter todos os módulos.

**Resposta (sucesso):**
```json
[
  "PROFESSOR",
  "STUDENTS",
  "QRCODE"
]
```

### `PATCH /api/v1/modules/update`

Endpoint para atualizar módulos.

**Requisição:**
```json
{
  "modules": ["PROFESSOR", "STUDENTS", "QRCODE"]
}
```

**Resposta (sucesso):**
```json
"Módulos atualizados!"
```

### `DELETE /api/v1/modules/delete`

Endpoint para deletar todos os módulos.

**Resposta (sucesso):**
```json
"Módulos deletados!"
```



## ParentController

Este controlador lida com as operações relacionadas aos pais.

### `GET /api/v1/parents/all`

Endpoint para obter todos os pais.

**Requisição:**
```json
{
  "tenantId": 1,
  "page": 1,
  "size": 10
}
```

**Resposta (sucesso):**
```json
{
  "content": [
    {
      "skid": "ID_DO_PAI_1",
      "firstName": "Nome do Pai 1",
      "lastName": "Sobrenome do Pai 1",
      "identifierNumber": "Número de Identificação 1",
      "contacts": [
        {
          "type": "EMAIL",
          "value": "pai1@example.com"
        },
        {
          "type": "PHONE",
          "value": "123456789"
        }
      ],
      "students": [
        {
          "studentSkid": "ID_DO_ALUNO_1",
          "studentFirstName": "Nome do Aluno 1"
        },
        {
          "studentSkid": "ID_DO_ALUNO_2",
          "studentFirstName": "Nome do Aluno 2"
        }
      ],
      "links": {
        "self": "URL_DO_PAI_1"
      }
    },
    {
      "skid": "ID_DO_PAI_2",
      "firstName": "Nome do Pai 2",
      "lastName": "Sobrenome do Pai 2",
      "identifierNumber": "Número de Identificação 2",
      "contacts": [
        {
          "type": "EMAIL",
          "value": "pai2@example.com"
        },
        {
          "type": "PHONE",
          "value": "987654321"
        }
      ],
      "students": [
        {
          "studentSkid": "ID_DO_ALUNO_3",
          "studentFirstName": "Nome do Aluno 3"
        },
        {
          "studentSkid": "ID_DO_ALUNO_4",
          "studentFirstName": "Nome do Aluno 4"
        }
      ],
      "links": {
        "self": "URL_DO_PAI_2"
      }
    }
  ],
  "links": {
    "self": "URL_DA_LISTA_DE_PAIS"
  }
}
```

### `PUT /api/v1/parents/update/{skid}`

Endpoint para atualizar informações de um pai.

**Requisição:**
```json
{
  "firstName": "Novo Nome do Pai",
  "lastName": "Novo Sobrenome do Pai",
  "password": "NovaSenha123",
  "identifierNumber": "NovoNúmeroIdentificação",
  "contacts": [
    {
      "type": "EMAIL",
      "value": "novopai@example.com"
    },
    {
      "type": "PHONE",
      "value": "987654321"
    }
  ],
  "students": [
    {
      "studentSkid": "ID_DO_ALUNO_1",
      "studentFirstName": "Novo Nome do Aluno 1"
    },
    {
      "studentSkid": "ID_DO_ALUNO_2",
      "studentFirstName": "Novo Nome do Aluno 2"
    }
  ]
}
```

**Resposta (sucesso):**
```json
"Informações do pai atualizadas!"
```

### `DELETE /api/v1/parents/delete/{skid}`

Endpoint para excluir um pai.

**Resposta (sucesso):**
```json
"Pai excluído com sucesso!"
```

Certifique-se de ajustar os valores conforme necessário, substituindo "1" pelo identificador real do inquilino (tenantId), "ID_DO_PAI_1", "ID_DO_PAI_2", "ID_DO_ALUNO_1", "ID_DO_ALUNO_2", "ID_DO_ALUNO_3" e "ID_DO_ALUNO_4" pelos identificadores reais dos pais e alunos.

## StudentsController

### `POST /api/v1/students/{adminCnpj}`

Endpoint para inserir estudantes no banco de dados.

**Requisição:**
```json
{
  "studentId": "ID_DO_ESTUDANTE",
  "bornYear": 2000,
  "firstName": "Nome do Estudante",
  "secondName": "Sobrenome do Estudante",
  "email": "estudante@example.com"
}
```

**Resposta (sucesso):**
```json
{
  "message": "Estudante adicionado com sucesso!"
}
```

### `GET /api/v1/students/all`

Endpoint para obter todos os estudantes.

**Requisição:**
```json
{
  "tenant": 1
}
```

**Resposta (sucesso):**
```json
[
  {
    "studentIdentifier": "ID_DO_ESTUDANTE_1",
    "username": "Nome de Usuário 1",
    "email": "estudante1@example.com",
    "skid": "ID_DO_ESTUDANTE_1",
    "startDate": "2022-01-01T12:00:00",
    "adminId": "ID_DO_ADMIN_1",
    "schoolClass": "Classe Escolar 1"
  },
  {
    "studentIdentifier": "ID_DO_ESTUDANTE_2",
    "username": "Nome de Usuário 2",
    "email": "estudante2@example.com",
    "skid": "ID_DO_ESTUDANTE_2",
    "startDate": "2022-01-01T12:00:00",
    "adminId": "ID_DO_ADMIN_2",
    "schoolClass": "Classe Escolar 2"
  }
]
```

### `GET /api/v1/students/{skid}`

Endpoint para obter um estudante por ID.

**Requisição:**
```json
{
  "tenant": 1
}
```

**Resposta (sucesso):**
```json
{
  "studentIdentifier": "ID_DO_ESTUDANTE",
  "username": "Nome de Usuário",
  "email": "estudante@example.com",
  "skid": "ID_DO_ESTUDANTE",
  "startDate": "2022-01-01T12:00:00",
  "adminId": "ID_DO_ADMIN",
  "schoolClass": "Classe Escolar"
}
```

### `DELETE /api/v1/students/delete`

Endpoint para excluir um estudante.

**Requisição:**
```json
{
  "studentSkId": "ID_DO_ESTUDANTE",
  "tenant": 1
}
```

**Resposta (sucesso):**
```json
"Estudante excluído com sucesso!"
```

Certifique-se de ajustar os valores conforme necessário, substituindo "1" pelo identificador real do inquilino (tenant) e "ID_DO_ESTUDANTE_1" e "ID_DO_ESTUDANTE_2" pelos identificadores reais dos estudantes.


## ProfessorController

### `POST /api/v1/professor/{adminCnpj}`

Endpoint para inserir um novo professor.

**Requisição:**
```json
{
  "firstName": "Nome",
  "lastName": "Sobrenome",
  "identifierNumber": "NúmeroIdentificador",
  "password": "Senha",
  "average": 0.0,
  "tenantId": 1,
  "startDate": "2023-01-01T00:00:00",
  "finishedDate": "2023-12-31T23:59:59",
  "contacts": [
    {
      "type": "EMAIL",
      "value": "email@example.com"
    },
    {
      "type": "PHONE",
      "value": "+123456789"
    }
  ],
  "schoolClasses": [
    {
      "name": "NomeDaTurma",
      "secondName": "SegundoNomeDaTurma",
      "period": "Manhã",
      "year": 2023,
      "professorsSkId": ["skid1", "skid2"]
    }
  ]
}
```

**Resposta (sucesso):**
```json
{
  "skid": "ID_DO_PROFESSOR"
}
```

### `GET /api/v1/professor/{skid}`

Endpoint para obter informações de um professor pelo ID.

**Resposta (sucesso):**
```json
{
  "skid": "ID_DO_PROFESSOR",
  "firstName": "Nome",
  "lastName": "Sobrenome",
  "average": 0.0,
  "identifierNumber": "NúmeroIdentificador",
  "tenant": 1,
  "startDate": "2023-01-01T00:00:00",
  "finishedDate": "2023-12-31T23:59:59",
  "contacts": [
    {
      "type": "EMAIL",
      "value": "email@example.com"
    },
    {
      "type": "PHONE",
      "value": "+123456789"
    }
  ]
}
```

### `GET /api/v1/professor`

Endpoint para obter todos os professores paginados.

**Requisição:**
- Parâmetros:
  - `tenantId` (obrigatório): ID do inquilino.
  - `page` (opcional): Número da página.
  - `size` (opcional): Tamanho da página.

**Resposta (sucesso):**
Lista paginada de professores.

### `PUT /api/v1/professor/update/{skid}`

Endpoint para atualizar as informações de um professor.

**Requisição:**
```json
{
  "firstName": "NovoNome",
  "lastName": "NovoSobrenome",
  "average": 0.0,
  "startDate": "2023-01-01T00:00:00",
  "finishedDate": "2023-12-31T23:59:59",
  "contacts": [
    {
      "type": "EMAIL",
      "value": "novo_email@example.com"
    },
    {
      "type": "PHONE",
      "value": "+987654321"
    }
  ]
}
```

**Resposta (sucesso):**
Mensagem de confirmação.

### `DELETE /api/v1/professor/{skid}`

Endpoint para excluir um professor pelo ID.

**Resposta (sucesso):**
Sem conteúdo (No Content).

## SchoolClassController

Este controlador lida com as operações relacionadas às turmas escolares.

### `POST /api/v1/school-classes/{adminId}`

Endpoint para criar uma nova turma.

**Requisição:**
```json
{
  "name": "Nome da Turma",
  "secondName": "Segundo Nome da Turma",
  "period": "Manhã",
  "year": 2023,
  "professorsSkId": ["ID_DO_PROFESSOR_1", "ID_DO_PROFESSOR_2"]
}
```

**Resposta (sucesso):**
```json
{
  "message": "Turma criada com sucesso!"
}
```

### `GET /api/v1/school-classes/{skid}`

Endpoint para obter uma turma por ID.

**Requisição:**
```json
{
  "tenant": 1
}
```

**Resposta (sucesso):**
```json
{
  "name": "Nome da Turma",
  "secondName": "Segundo Nome da Turma",
  "period": "Manhã",
  "year": 2023,
  "professorsSkId": ["ID_DO_PROFESSOR_1", "ID_DO_PROFESSOR_2"]
}
```

### `PATCH /api/v1/school-classes/student/{skid}`

Endpoint para adicionar um estudante a uma turma.

**Requisição:**
```json
{
  "tenant": 1,
  "studentSkId": "ID_DO_ESTUDANTE"
}
```

**Resposta (sucesso):**
```json
{
  "message": "Estudante adicionado à turma com sucesso!"
}
```

### `PATCH /api/v1/school-classes/professor/{skid}`

Endpoint para adicionar um professor a uma turma.

**Requisição:**
```json
{
  "tenant": 1,
  "professorSkId": "ID_DO_PROFESSOR"
}
```

**Resposta (sucesso):**
```json
{
  "message": "Professor adicionado à turma com sucesso!"
}
```

### `GET /api/v1/school-classes/students/{skid}`

Endpoint para obter todos os estudantes de uma turma.

**Requisição:**
```json
{
  "tenant": 1
}
```

**Resposta (sucesso):**
```json
[
  {
    "studentIdentifier": "ID_DO_ESTUDANTE_1",
    "username": "Nome de Usuário 1",
    "email": "estudante1@example.com",
    "skid": "ID_DO_ESTUDANTE_1"
  },
  {
    "studentIdentifier": "ID_DO_ESTUDANTE_2",
    "username": "Nome de Usuário 2",
    "email": "estudante2@example.com",
    "skid": "ID_DO_ESTUDANTE_2"
  }
]
```

### `GET /api/v1/school-classes/professors/{skid}`

Endpoint para obter todos os professores de uma turma.

**Requisição:**
```json
{
  "tenant": 1
}
```

**Resposta (sucesso):**
```json
[
  {
    "professorIdentifier": "ID_DO_PROFESSOR_1",
    "username": "Nome de Usuário 1",
    "email": "professor1@example.com",
    "skid": "ID_DO_PROFESSOR_1"
  },
  {
    "professorIdentifier": "ID_DO_PROFESSOR_2",
    "username": "Nome de Usuário 2",
    "email": "professor2@example.com",
    "skid": "ID_DO_PROFESSOR_2"
  }
]
```

### `GET /api/v1/school-classes/all`

Endpoint para obter todas as turmas.

**Requisição:**
```json
{
  "tenant": 1
}
```

**Resposta (sucesso):**
```json
[
  {
    "name": "Nome da Turma 1",
    "secondName": "Segundo Nome da Turma 1",
    "period": "Manhã",
    "year": 2023,
    "professorsSkId": ["ID_DO_PROFESSOR_1", "ID_DO_PROFESSOR_2"]
  },
  {
    "name": "Nome da Turma 2",
    "secondName": "Segundo Nome da Turma 2",
    "period": "Tarde",
    "year": 2023,
    "professorsSkId": ["ID_DO_PROFESSOR_3", "ID_DO_PROFESSOR_4"]
  }
]
```

### `DELETE /api/v1/school-classes/{skid}`

Endpoint para excluir uma turma.

**Requisição:**
```json
{
  "tenant": 1
}
```

**Resposta (sucesso):**
```json
"Turma excluída com sucesso!"
```

Certifique-se de ajustar os valores conforme necessário, substituindo "1" pelo identificador real do inquilino (tenant), "ID_DO_PROFESSOR_1", "ID_DO_ESTUDANTE_1", "ID_DO_TURMA_1", etc., pelos identificadores reais dos professores, estudantes e turmas.



- ## QRCodeController

### `GET /api/v1/qrcode/generate`

Endpoint para gerar um código QR em formato base64.

**Requisição:**
```json
{
  "studentSkId": "ID_DO_ESTUDANTE",
  "tenant": 1
}
```

**Resposta (sucesso):**
```json
"Base64_do_Código_QR"
```

### `GET /api/v1/qrcode/image`

Endpoint para gerar e obter um código QR como imagem PNG.

**Requisição:**
```json
{
  "studentSkId": "ID_DO_ESTUDANTE",
  "tenant": 1
}
```

**Resposta (sucesso):**
Imagem PNG representando o código QR.

Certifique-se de ajustar os valores conforme necessário, substituindo "1" pelo identificador real do inquilino (tenant) e "ID_DO_ESTUDANTE" pelo identificador real do estudante.





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
 
Made by Valter Gabriel 👋🏽 Get in touch!

[![Linkedin Badge](https://img.shields.io/badge/-Gabriel-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/valter-gabriel/ )](https://www.linkedin.com/in/valter-gabriel/)

