# ApiWeather

Daniel Loholaberry - danloholaberry@gmail.com

El proyecto se debe clonar desde git y ejecutar en un server local.
Clonar el repo -> git clone https://github.com/lhdani/ApiWeather.git desde una consola.
Se debe ejecutar en algún servidor local para poder realizar las pruebas.

El proyecto consta de dos endpoint uno para saber el location key a partir de una ciudad especifica y el otro endpoint para los datos del clima a partir del location key obtenido.
* Endpoint para obtener los datos del clima a partir de un location key -> http://localhost:8080/weather/7893
* Endpoint para obtener el location key a partir de una ciudad -> http://localhost:8080/weather/locationKey/Mardelplata. Este endpoint devuelve un String que se mapea con el campo Key del response del servicio de AccuWeather.
Para acceder a la base H2 se ejecuta la siguiente url -> http://localhost:8080/h2-console. Password esta configurado en el application.properties
