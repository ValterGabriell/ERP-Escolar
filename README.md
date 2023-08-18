<h1 align="center">Sistema de frequência para o cursinho Logos da UFPA.</h1>


## Indíce
<!--ts-->
   * [Sobre o projeto](#sobre-o-projeto)
   * [Endpoints](#endpoints)
   * [Testes](#testes)
   * [Creditos](#creditos)
<!--te-->
  
<h1>Sobre o projeto</h1>

Este é um projeto que deve automatizar o sistema de frequência de alunos no cursinho gratuíto da UFPA. Os domínios estão divididos da seguinte maneira: </br>
<img style="border-radius: 50%;" src="https://github.com/ValterGabriell/FrequenciaAlunos_UFPALogos/assets/63808405/a697be0a-6667-4d42-afed-0813628d9da6"/>
<br>
Cada aluno, ao ser criado, cria também uma frequência para ele, sem atribuição por relacionamento, mas como objeto separado, levando o mesmo id do aluno. A escolha por essa abordagem veio para que o dominío de frequência ficasse completamente isolado do domínio de aluno, tendo em vista que
mesmo que um aluno seja excluído, ainda possamos gerar planilhas que possuam a frequência desse aluno salva lá. O cursinho possui uma rotatividade grande e poderíamos atrapalhar a criação de planilhas antigas caso a frequência fosse excluída também sempre que um aluno é deletado.
<h1>Endpoints</h1>
<h3>BASE URL</h3>

```bash
http://localhost:8080/
``` 
<h1>POST</h1>

<h2>Cadastrar estudante</h2>

<table>
  <tr>
    <th>Request</th>
    <th>Response</th>
  </tr>
  <tr>
    <td>/students</td>
    <td>realizar inserção de um estudante no Database</td>
  </tr> 
  </table>
  
  <h3>Request esperada</h3></br>

```bash
{
	"cpf":"233456789",
	"username":"name"
}
```

<h3>Resposta esperada</h3></br>

```bash
{
	"cpf": "233456789",
	"username": "valter",
	"frequency": {
		"id": "233456789"
	}
}
```


<h2>Validar frequencia do estudante</h2>

<table>
  <tr>
    <th>Request</th>
    <th>Response</th>
    <th>Query</th>
    
  </tr>
  <tr>
    <td>/frequency</td>
    <td>realizar a validação de frequencia de um estudante</td>
    <td>studentId</td>
  </tr> 
  </table>
 

<h3>Resposta esperada</h3></br>

```bash
{
	"message": "Frequência para brito válidada! - Dia: 2023-04-21"
}
```

<h2>Justificar falta e adicionar o dia na frequencia do estudante</h2>

<table>
  <tr>
    <th>Request</th>
    <th>Response</th>
    <th>Query</th>
    <th>Query</th>
  </tr>
  <tr>
    <td>/frequency</td>
    <td>realizar a validação de frequencia de um estudante</td>
    <td>studentId</td>
    <td>date</td>
  </tr> 
  </table>
 

<h3>Resposta esperada</h3></br>

```bash
{
	"message": "Frequência para brito justificada! - Dia: 2023-04-21"
}
```

</br>


<h1>GET</h1>


<h2>Recuperar todos os estudantes</h2>
<table>
  <tr>
    <th>Request</th>
    <th>Response</th>
  </tr>
  <tr>
    <td>/students/get-all</td>
    <td>Retorna todos os estudantes</td>
  </tr>
</table>

<h3>Resposta esperada</h3></br>

```
[
	{
		"cpf": "42345678912",
		"username": "silva"
	},
	{
		"cpf": "12345678912",
		"username": "gabriel"
	},
	{
		"cpf": "32345678912",
		"username": "gabriel"
	},
	{
		"cpf": "62345678912",
		"username": "brito"
	}
]

```

<h2>Recuperar os dias que o estudante viu aula</h2>
<table>
  <tr>
    <th>Request</th>
    <th>Response</th>
    <th>Query</th>
  </tr>
  <tr>
    <td>/frequency</td>
    <td>Retorna os dias que o estudante viu aula</td>
    <td>studentId</td>
  </tr>
</table>

<h3>Resposta esperada</h3></br>

```
{
	"studentId": "62345678912",
	"daysListThatStudentGoToClass": [
		{
			"date": "2023-04-21",
			"justified": false
		},
		{
			"date": "2023-04-15",
			"justified": true
		}
	]
}

Quando justified for false, significa que o aluno foi para a aula no dia em questão, quando for true, significa que o aluno faltou, porém a sua falta foi justificada.

```



<h2>Exportar tabela do excel</h2>
<table>
  <tr>
    <th>Request</th>
    <th>Response</th>
  </tr>
  <tr>
    <td>/frequency/sheet</td>
    <td>Baixa a tabela do dia atual</td>
  </tr>
</table>


<h2>Exportar tabela do excel para dia específico</h2>
<table>
  <tr>
    <th>Request</th>
    <th>Response</th>
    <th>Query</th>
  </tr>
  <tr>
    <td>/frequency/sheet</td>
    <td>Baixa a tabela do dia específico</td>
    <td>date (AAAA-MM-DD)</td>
  </tr>
</table>






<h2>Gerar QRCode para validação</h2>
<table>
  <tr>
    <th>Request</th>
    <th>Response</th>
    <th>Query</th>
  </tr>
  <tr>
    <td>/qrcode/generate</td>
    <td>Retorna o QRcode que representa o ID e o Nome do estudante</td>
    <td>studentId</td>
  </tr>
</table>

<h3>Resposta esperada</h3></br>

![image](https://user-images.githubusercontent.com/63808405/232524532-3a0ce398-9446-4969-b300-fcfcb28d60e0.png)



</br>

<h1>PUT</h1>

<h2>Atualizar falta de estudante</h2>

<table>
  <tr>
    <th>Request</th>
    <th>Response</th>
    <th>Query</th>
    <th>Query</th>
  </tr>
  <tr>
    <td>/frequency</td>
    <td>realizar a atualizacao de uma justificativa no Database</td>
    <td>studentId</td>
    <td>date</td>
  </tr> 
  </table>
  
  <h3>Request esperada</h3></br>

```bash
{
	"message": "Justificativa para gabriel atualizada! - Dia: 2023-05-06"
}
```
</br>


<h1>PATCH</h1>

<h2>Atualizar estudante</h2>

<table>
  <tr>
    <th>Request</th>
    <th>Response</th>
    <th>Query</th>
  </tr>
  <tr>
    <td>/students</td>
    <td>realizar a atualizacao de um estudante no Database</td>
    <td>studentId</td>
  </tr> 
  </table>
  
  <h3>Request esperada</h3></br>

```bash
	Novos dados
{
	"cpf": "11111111111",
	"username": "gabriel"
}
```
</br>





<h1>DELETE</h1>

<h2>Deletar estudante</h2>

<table>
  <tr>
    <th>Request</th>
    <th>Response</th>
    <th>Query</th>
  </tr>
  <tr>
    <td>/students</td>
    <td>realizar a delecao de um estudante no Database</td>
    <td>studentId</td>
  </tr> 
  </table>
  
  <h3>Request esperada</h3></br>

```bash
no content
```


</br>


<h1>Testes</h1>

<h3>O nome de usuário não pode ser nulo</h3></br>

```
    @Test
    @DisplayName("A username should be not null and return true when it is")
    void isUsernameNotNull_ReturnTrue_WhenUsernameIsNotNull() {
        Assertions.assertTrue(studentUsernameTest.usernameIsNull());
    }
    
```


<h3>O nome de usuário precisa ter mais de 2 caracteres</h3></br>

```
    @Test
    @DisplayName("A username should have more than 2 characters and return true when it is")
    void isUsernameBiggerThan2Chars() {
        Assertions.assertTrue(studentUsernameTest.usernameHasToBeMoreThan2Chars());
    }
```



<h3>O nome de usuário precisa ter apenas letras</h3></br>

```
   @Test
    @DisplayName("A username should have only letters and no numbers")
    void isUsernameOnlyLetters() {
        Assertions.assertTrue(studentUsernameTest.fieldContainsOnlyLetters("Username"));
    }
```


<h3>O CPF precisa ter exatamente 11 digitos</h3></br>

```
    @Test
    @DisplayName("cpf should have exactly 11 characters and return true when it is")
    void cpfLenght() {
        Assertions.assertTrue(studentCpfTest.isFieldHasNumberExcatlyOfChars(studentCpfTest.getCpf(), 11));
    }
```


<h3>O CPF não pode ser nulo</h3></br>

```
    @Test
    @DisplayName("A cpf should be not null and return true when it is")
    void isCpfNotNull_ReturnTrue_WhenUsernameIsNotNull() {
        Assertions.assertTrue(studentCpfTest.cpfIsNull());
    }
```

<h3>Verificando se o dia já está salvo na frequencia do estudante</h3></br>

```
    @Test
    void verifyIfDayAlreadySavedOnFrequencyAndThrowAnErroIfItIs() {
        List<Days> days = new ArrayList<>();
        LocalDate date = LocalDate.now();
        Days day = new Days(date);
        days.add(day);
        boolean contains = days.contains(day);
        Assertions.assertTrue(contains);
    }
```








<h1>Creditos</h1>

---

<a href="https://www.linkedin.com/in/valter-gabriel">
  <img style="border-radius: 50%;" src="https://user-images.githubusercontent.com/63808405/171045850-84caf881-ee10-4782-9016-ea1682c4731d.jpeg" width="100px;" alt=""/>
  <br />
  <sub><b>Valter Gabriel</b></sub></a> <a href="https://www.linkedin.com/in/valter-gabriel" title="Linkedin">🚀</ a>
 
Made by Valter Gabriel 👋🏽 Get in touch!

[![Linkedin Badge](https://img.shields.io/badge/-Gabriel-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/valter-gabriel/ )](https://www.linkedin.com/in/valter-gabriel/)

