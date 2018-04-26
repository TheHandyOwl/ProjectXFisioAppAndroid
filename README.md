# Proyecto Final

# V KeepCoding Startup Engineering Master

## Proyecto: FisioApp

### Equipo: Project X

## Sinposis
Aplicación móvil pensada para cualquier trabajador autónomo que ofrezca un servicio a domicilio.

La aplicación permite la gestión de las citas que pueda tener el profesional, así como la publicación de productos y/o servicios.

## Tecnologías
- Android Studio
- Kotlin
- SQLite

## Librerías externas
- [GSon](https://github.com/google/gson) - Parseo de datos
- [Retrofit](http://square.github.io/retrofit/) - Gestión de peticiones HTTP
- [Picasso](http://square.github.io/picasso/)- Descarga y cacheo de imágenes

## Instalación
### Generar fichero para Google API Key

Si intentas ejecutar la aplicación, verás un error porque no existe el recurso que contiene la API key válida para ejecutar Google Maps.

Genera un nuevo fichero en la siguiente ruta:
tuCarpeta/app/src/main/res/value/google_maps_api_key.xml

Puedes ver un ejemplo para el fichero `google_maps_api_key.xml` aqui:

```
xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
<string name="GOOGLE_MAPS_API_KEY">YourAPIKeyHere</string>
</resources>
```

Si está sbuscando este fichero, no lo encontrarás, debes crear el tuyo propio.

### Verificar el nombre/IP de tu servicor backend.

El nombre `localhost` no funciona. Por esta razón, debes cambiar el nombre / IP de tus servidores en el fichero:
tuCarpeta/repository/src/build.gradle

Puedes ver un ejemplo aquí:
```
apply plugin: 'com.android.library'
apply plugin: 'kotlin-android'

android {
       buildConfigField "String", "HTTP_SERVER", "\"http://fisioapp.styleapps.es\""
}
```

Si cambias las rutas de los endpoints, debes cambiar también este fichero.

## Implementación

### Orientación
- La aplicación actualmente sólo puede ser usada con el dispositivo en modo 'portrait'.

### Localización
- La app está 'localizada'.  
Si el idioma del dispositivo es Español ("es"), los textos se mostrarán en español.  
De lo contrario, siempre se mostrarán en Inglés.

### Cache - DDBB
- Los datos se alamcenan en una base de datos SQLite a medida que se obtienen de la red.
- Las imágenes se descargan y cachean mediante Picasso, librería antes enlazada.

## Citas
- Esta pantalla cuenta de dos secciones:
    - Calendario, para una fácil visualización del mes y selección de una fecha en concreto.
    - Listado, donde se mostrarán las citas que el profesional pueda tener para la fecha seleccionada.  
    Si para dicha fecha no tuviese ninguna cita concertada, el listado se mostrará vacío.
    
## Detalle Cita
- Al tocar una cita en el listado de citas, se navegará hacia una pantalla donde se muestra un detalle de la misma.
- Los campos mostrados son:
    - Nombre cliente
    - Dirección del tratamiento
    - Precio del producto seleccionado
    - Dos switch: Confirmada y Cancelada
    - Información extra
- Al principio de la pantalla se muestra una imagen estática de GoogleMaps, con un pin enseñando la ubicación de la cita.  
En la versión de Android, al pulsarse esta imagen se abre la aplicación de GoogleMaps con indicaciones para llegar al sitio señalado.  
(Pendiente implementar en iOS).

    
## Producto/Servicios
- Estas secciones funcionan de igual forma.  
Ambas muestran un listado de productos y/o servicios ofrecidos por el profesional.
- Las celdas muestran el nombre y el precio.  
En caso de ser un producto, también se mostrará una imagen del mismo.
- El usuario dispone de un botón que le permite crear un nuevo producto/servicio.

## Detalle Producto/Servicio
- Al pulsar sobre un producto/servicio se navega a una pantalla detalle del mismo.
- Los campos mostrados son:
    - Nombre
    - Descripción
    - Precio
    - Flag si está activo

## Nuevo Producto/Servicio
- Esta ventana se muestra cuando el usuario toca el botón para crear un nuevo producto/servicio.
- Los campos a introducir son:
    - Nombre
    - Descripción
    - Precio
    - Imagen (si fuese un producto; pendiente de implementar).
- El usuario dispone de una opción 'Activo'.  
Si esta opción se selecciona, el producto/servicio creado será publicado y visible al público; de lo contrario, el profesional podrá dejar el producto/servicio creado y guardado pero no será visible al público.
