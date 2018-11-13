# Infraestructura SporTEC 
Mediante la implementación de containers de docker, que ejecutan servidores graphql 
(micro-servicios) y la integración de un API gateway utilizando nginx, se creo la infraestructura para la aplicación SPORTEC.

## Instalación
Para poder ejecutar la aplicación se debe descargar el archivo que contiene  los archivos del código , se debe descomprimir los archivos y en las carpertas correspondientes a cada
micro-servicio se deben correr los containers mediante comandos que se mencionaran más adelante
en la parte de ejecucción.

### Requerimientos
* Node.js
* Express.js
* MongoDB
* MySQL
* Docker
* Babel
* Nginx

## Ejecucuón

* Primero se dene ejecutar la docker machine mediante los comandos:
```
$ docker-machine start Manager
$ docker-machine env Manager
$ eval $(docker-machine env Manager)
```


* En la carpeta descargada y descomprimida ejecutar los siguientes comandos, para entrar a la 
carpeta correspondiente en este caso el servicio news y poner a correr el container.
```
$ cd news-microservice
$ docker-compose up -d
```

* Luego entrar a la carpeta del servicio sports y poner a correr el container.
```
$ cd sport-microservice
$ docker-compose up -d
```

* Luego entrar a la carpeta del servicio sports y poner a correr el container.
```
$ cd user-microservice
$ docker-compose up -d
```

## Funcionamiento de ciertos queries.

* Agrega un usuario en la tabla users
POST: http://sportec/users/add/
BODY: {
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
POST: http://sportec/users/update/
BODY: {
		"name": "Nuevo nombre",
		"email": "ldnm12@gmail.com",
		"password": "nueva contrasena",
		"profilePicture": "https://image.flaticon.com/icons/png/128/74/74245.png",
		"sessionInit": "1",
		"type": "user"
	}
```


```
* Obtiene todas las noticias que se encuentran en la tabla news:
```
GET: http://sportec/news/

BODY: {allNews{
		  sport
  		  title
  		  content
          important
          image
          type
}
}
```

## Referencias
* [https://medium.com/infocentric/setup-a-graphql-api-with-apollo-2-0-sequelize-and-express-js-608d1365d776](https://medium.com/infocentric/setup-a-graphql-api-with-apollo-2-0-sequelize-and-express-js-608d1365d776)
* [https://www.youtube.com/watch?v=C82btqFgcqI](https://www.youtube.com/watch?v=C82btqFgcqI)
* [https://github.com/dockerfile/ubuntu/blob/master/Dockerfile](https://github.com/dockerfile/ubuntu/blob/master/Dockerfile)
* [https://www.youtube.com/watch?v=hP77Rua1E0c](https://www.youtube.com/watch?v=hP77Rua1E0c)


