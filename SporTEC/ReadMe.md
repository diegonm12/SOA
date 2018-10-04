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

## Version de Android de desarrollo
API25: Android 7.1.1 (Nougat)

## Servidor
* Los archivos del servidor se puede encontrar [aqui](https://github.com/diegonm12/SOA/tree/master/SportecServer).
Desde el servidor se pueden acceder 5 modelos de la base de datos en MongoDB, cada uno con sus respectos métodos CRUD. Entre los métodos que se emplearon para el desarrollo de la aplicación están los siguientes:
* Obtiene todos los usuarios que se encuentran en la tabla users:
```
GET: http://192.168.1.146:3000/api/users
```

* Obtiene todos los usuarios que se encuentran en la tabla users:
```
GET: http://192.168.1.146:3000/api/users
```


## Development
```
$ virtualenv foobar
$ . foobar/bin/activate
$ pip install -e .
```

## Referencias
* [https://developer.android.com](https://developer.android.com)
* [https://seegatesite.com/android-tutorial-display-image-to-android-gridview-from-url-using-picasso/](https://seegatesite.com/android-tutorial-display-image-to-android-gridview-from-url-using-picasso/)
* [https://demonuts.com/listview-searchview/](https://demonuts.com/listview-searchview/)
* [https://www.youtube.com/watch?v=9_lKMTXVk64](https://www.youtube.com/watch?v=9_lKMTXVk64)	

## License
[MIT](https://choosealicense.com/licenses/mit/)
