### User API

API encargada del registro y generación de token de acceso para usuarios.

### Servicios

### `POST /user` : Servicio que registra al usuario

JSON de Entrada - Ejemplo:

``` json
{
	"name": "Juan Rodriguez",
	"email": "aaa@dominiso.cl",
	"password": "Hunter23",
	"phones": [
        {
            "number": "1234567",
            "citycode": "1",
            "contrycode": "57"
	    },
        {
            "number": "5656",
            "citycode": "1",
            "contrycode": "57"
	    }
    ]
}
```
JSON de Salida - Ejemplo: 

``` json
{
    "resultado": "SUCCESS",
    "mensaje": "Servicio ejecutado exitosamente",
    "datos": {
        "id": "31a88008-030d-41fb-a8d0-2d77bae2f3fb",
        "created": "2021-10-20T00:53:50.072+00:00",
        "modified": "2021-10-20T00:53:50.072+00:00",
        "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhYWFAZG9taW5pc28uY2wiLCJleHAiOjE2MzQ3MDkyMzAsImlhdCI6MTYzNDY5MTIzMH0.yBSslY-0lS9zKB2svdc5jJi2-BzHHVoAsHkwA7oLayKahMfmfLFGJdyw3mIOQpmT42iL35kBhp81nGDTPa-X9A",
        "last_login": "2021-10-20T00:53:50.072+00:00",
        "isactive": true
    }
}
```

+ Validaciones:
  + El email no debe ir vacio o nulo, y debe tener el formaro aaa@dominio.cl
  + La password debe tener el formato : primera letra mayúscula, letras minusculas y 2 números
  + El email no debe repetirse en otro usuario registrado.

### Levantar aplicación

`./gradlew bootRun`

Por defecto levanta en puerto 8080 y el context path es `/test`.

### Documenrtación Swagger

Para ver la documentación Swagger, la API debe estar arriba, y debe accederse al siguiente link [http://localhost:8080/test/swagger-ui.html](http://localhost:8080/test/swagger-ui.html) 

### Ejecución de pruebas unitarias

`./gradlew clean test`

La ejecución de las pruebas genera un reporte de Jacoco con la cobertura de los test.
Este reporte se puede revisar en la ruta `../build/reports/jacoco/jacocoHtml/index.html`

### Script de Base de Datos

Si bien la API genera las tablas y la base de datos en H2, se dejan los script de creación de las tablas y relaciones por si
se desea migrar la BD: 

[data.sql][src/main/resources/data.sql]

También en este scrip se deja la inserción de un usuario de muestra con el cual se puede validar
la duplicidad de email. El email que se registra es : email@existente.cl

### Diagarmas de solución

#### Lógico
![Screenshot](diagrams/logic.png)
#### Secuencia
![Screenshot](diagrams/sequence.png)

### ** Notas adicionales

+ Considerando que en el registro de usuario se crea un JWT, se optó por implementar
Spring Security en el proyecto con el fin de utilizar este token generado, lo que implica que
  otros servicios que se generen en la API podrán consumirse solo si viaja el header Authorization.
+ Dado el punto anterior se genero un nuevo servicio que obtiene el listado de usuarios. Este
servicio requiere el header Authorización con el token generado en la creación. Un ejemplo de llamada sería:


```
curl --location --request GET 'http://localhost:8080/test/user' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqdWFuQHJvZHJpZ3Vlei5vcmciLCJleHAiOjE2MzQ3MDUyNjIsImlhdCI6MTYzNDY4NzI2Mn0.yaryl5DEZ93t3vnK-zpeZA_qkWNG1-wi1y72mRyjo9t1Bhb5G8y_zHwbcoVgdJJob5jLBwSvSHkPhAyJdkh83w'
```

