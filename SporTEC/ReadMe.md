# SporTEC 
SporTEC es una aplicacion de noticias de deportes que consume recursos
de un servidor web creado en Node.js y utiliza MongoDB como el repositorio de datos
NoSQL.

## Instalación
Para poder ejecutar la aplicación se debe descargar el archivo que contiene  los archivos del cógido 
de la aplicación, luego se debe ejecutar el servidor [sportecServer](https://github.com/diegonm12/SOA/tree/master/SportecServer)
para que se puedan hacer los requests al servidor, además en MongoDB debe ejecutarse el script de base de datos para popular la misma.
Una vez que se tenga la base populada por medio del script, se debe ejecutar el servidor node y finalmente se
debe ejecutar la aplicación en donde ya tendrá todo lo necesario para que la aplicación funcione y pueda consumir 
los datos de la base en MongoDB mediante el servidor. 

### Requerimientos
* Android Studio
* Node.js
* Express.js
* MongoDB


## Version de Android de desarrollo
API25: Android 7.1.1 (Nougat)

## Servidor
* Los archivos del servidor se puede encontrar [aqui](https://github.com/diegonm12/SOA/tree/master/SportecServer).
Desde el servidor se pueden acceder 5 modelos de la base de datos en MongoDB, cada uno con sus respectos métodos CRUD. Entre los métodos que se emplearon para el desarrollo de la aplicación están los siguientes:
* Obtiene todos los usuarios que se encuentran en la tabla users:
```
GET: http://192.168.1.146:3000/api/users
```

* Obtiene el usuario según sea el correo:
```
GET: http://192.168.1.146:3000/api/users/<correoUsuario>
```

* Agrega un usuario a la tabla users
```
POST: http://192.168.1.146:3000/api/users/
BODY: {
		"favSport": [
			"Natacion"
		],
		"name": "Luis Diego",
		"email": "ldnm12@gmail.com",
		"password": "contrasena",
		"profilePicture": "https://image.flaticon.com/icons/png/128/74/74245.png",
		"sessionInit": "1",
		"type": "user"
	}
```

* Actualiza un usuario en la tabla users
```
PUT: http://192.168.1.146:3000/api/users/
BODY: {
		"favSport": [
			"Natacion"
			"nuevo deporte"
		],
		"name": "Nuevo nombre",
		"email": "ldnm12@gmail.com",
		"password": "nueva contrasena",
		"profilePicture": "https://image.flaticon.com/icons/png/128/74/74245.png",
		"sessionInit": "1",
		"type": "user"
	}
```

* Busca contenido en las tablas correspondientes, según sea la palabra que va luego del search (este metodo se implemento para ser utilizado por el buscador de contenido).
```
http://192.168.1.146:3000/api/content?search=<palabra_que_se_busca_en_el_buscador_contenido>
```
* Obtiene todas las noticias que se encuentran en la tabla news:
```
GET: http://192.168.1.146:3000/api/news
```

* Obtiene la noticia que se especifica según el id de la noticia en la base de datos:
```
GET: http://192.168.1.146:3000/api/news/<id_noticia>
```
* Obtiene todos los deportes que se encuentran en la tabla sports:
```
GET: http://192.168.1.146:3000/api/sports
```

* Obtiene el deporte que se especifica según el id del mismo en la base de datos:
```
GET: http://192.168.1.146:3000/api/sports/<id_sport>
```

* Obtiene todos los equipos que se encuentran en la tabla teams:
```
GET: http://192.168.1.146:3000/api/teams
```

* Obtiene el equipo que se especifica según el id del mismo en la base de datos:
```
GET: http://192.168.1.146:3000/api/teams/<id_team>
```

* Actualiza un equipo en la tabla teams
```
PUT: http://192.168.1.146:3000/api/teams/
BODY: {
		"members": [
			"Fernando",
			"Marcela",
			"Raul"
		],
		"request": [
			"Diego Navarro"
		],
		"name": "Los Mejengueros",
		"image": "https://image.flaticon.com/icons/png/128/166/166344.png",
		"type": "team",
		"sport": "Futbol"
	}
```

## Referencias
* [https://developer.android.com](https://developer.android.com)
* [https://seegatesite.com/android-tutorial-display-image-to-android-gridview-from-url-using-picasso/](https://seegatesite.com/android-tutorial-display-image-to-android-gridview-from-url-using-picasso/)
* [https://demonuts.com/listview-searchview/](https://demonuts.com/listview-searchview/)
* [https://www.youtube.com/watch?v=9_lKMTXVk64](https://www.youtube.com/watch?v=9_lKMTXVk64)	


