# PMDM - TAREA 3
## AUTOR
Alberto García de Haro

## DESCRIPCIÓN
APP para mantener los pokemons capturados. Los datos de los pokemon se obtienen mediante la API. 
Se gestiona el login con Firebase.
Se gestiona la base de datos con Firestore.

## Screenshots
### Pantalla de login
Se puede hacer login mediante correo electrónico y mediante la cuenta de Google.
![image](https://github.com/user-attachments/assets/4b7f9afa-12a7-4edc-82dc-e07796af4c44)

Si queremos que se cierre sesión al salir, hay que marcarlo en las opciones.


### RecyclerView: Pokemon Capturados
Se muestran los pokemon capturados. 
Al lado del Pokemon hay un botón de borrado; si se pulsa se borra el pokemon de la Base de Datos.
![image](https://github.com/user-attachments/assets/0422eac0-5fba-4435-9562-0d731b8fb3df)

Al hacer click sobre un Pokemon, se muestra su detalle.
![image](https://github.com/user-attachments/assets/62fe9167-70ee-49c4-a451-954cb45b56fb)


### RecyclerView: Pokedex
La pokedex muestra todos los pokemons obtenidos de la API, según el parámetro LÍMITE de los ajustes.
Si el pokemon NO está capturado, se muestra de espaldas. En caso contrario se muestra de frente.

![image](https://github.com/user-attachments/assets/d8388cc0-0a03-4e9a-bb47-ed8b02e85572)


### Pantalla de ajustes
Se pueden modificar los siguientes ajustes:
- Switch para ocultar los pokemon capturados (no se mostrarán en la pokedex)
- Switch para idioma inglés / español
- Entrada para modificar el límite de los pokemon, por defecto 150
- Checkbox para cerrar sesión al salir de la APP. Habría que loguearse otra vez.

![image](https://github.com/user-attachments/assets/a5f557ce-a0eb-4b34-917c-9543604119b1)



## Problemas encontrados

### Problema con la API Retrofit
No recupera datos del tipo de Pokemon, en concreto para la altura y el peso.

### Problema con Firebase - Firestore
Se queda parado el debugueador cuando intento hacer más de una consulta, no he podido probar la consulta de los datos del Pokemon.

## TO-DO
- Mejorar el aspecto del recycler view de pokemons capturados
- Almacenar también los tipos de pokemon
