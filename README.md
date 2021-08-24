# BasesIICaso1
Antes de realizar el caso, se investigó y se desarrolló el ORM del lenguaje de Java, Spring. Se programó mediante orientación a objetos la inserción y la consulta de la tabla sd_owners del diseño de SQL compartido por el profesor en las clases anteriores, pero con una modificación en la tabla sd_designs de un campo agregado llamado [parentdesignid] que hace referencia a la llave primaria de la misma tabla, esto debido al primer quiz, pero de igual manera este campo no se tomará en cuenta a las funcionalidades que nos interesan en este caso. Es importante añadir que las clases se llaman igual a las tablas a las que insertan para que sea más fácil identificar para Spring a cuál tabla debe hacerle modificaciones.

###Instrucciones:
- Como implementar un objeto que representa una relacion 1 a N
- Como manipular object pooling para reducir la cantidad de conexiones 
- Como implementar una transacción que afecte a más de una tabla

#• Como respuesta al primer punto, se detallan los pasos llevados a cabo a continuación:

Para la implementación de un objeto que representa una relación 1 a N, se tiene la relación entre la clase sd_owners y la clase sd_designs, donde un Owner tiene a N Designs (ver las clases sd_owners.java y sd_designs.java).

Puede observar en la línea 38 de la clase sd_owners la asignación de la relación OneToMany entre esta con el atributo llamado “Owner”  de la clase sd_designs, mientras que en la línea 39 se inicializa el atributo que enlista los designs del correspondiente owner, llamado ownerDesigns. 

Por otro lado, en la clase sd_designs, específicamente en la línea 24 y 25 se indica que el atributo Owner (línea 26) tiene una relación ManyToOne respecto al Owner, así como que el atributo “ownerid” es llave foránea de sd_designs.

Con la relación especificada en los párrafos anteriores, basta que en la clase sd_owners_controller, de la línea 70 a la 76, se añada un método que realice una instancia de sd_owners, buscando el owner por el String del email (parámetro powneremail), y el mismo ORM se encarga de rellenar el atributo ownerDesigns con los sd_designs asociados al owner respectivo.

Se realiza la prueba en el buscador mediante la url http://localhost:8080/api/cargarDesignsPerOwner?powneremail=atorr@gmail.com para obtener como salida cada uno de los designs asociados con el owner que posee el email atorr@gmail.com.

#• Como respuesta al segundo punto, se detallan los pasos llevados a cabo a continuación:

1. Cabe destacar de antemano que Hibernate/Spring ya nos provee de un connection pooling pero no nos dan las herramientas directas para poder modificarlas por nuestra cuenta, por lo que se implementó la librería de c3p0 que nos permite determinar el mínimo, máximo de conexiones, timeouts, etc. que es necesaria para tener un manejo explícito del connection pooling.

2. Posteriormente definimos la clase HibernateUtil, la cual se encarga de administrar bajo el patrón Singleton la creación de sesiones que realziarán las conexiones a la base de datos. Esta tiene un SessionFactory que permite crear distintas sessions pero nosotros solo creamos un único factory que a pesar de poder manejar distintas sesiones solo creamos 1 en todo el programa. 

Por lo que, como se puede ver en el archivo sd_owners_services.java en la línea 39-40 obtenemos la instancia de la session que se encarga de realizar el manejo del connection pooling. Esto se logra al declarar un atributo de caracter private y static, es decir, para toda instancia de la clase HibernateUtil que acceda al SessionFactory siempre será el mismo valor. 

Cabe destacar que en el archivo hibernate.cfg.xml es donde se encuentran las configuraciones del SessionFactory utilizadas para crear la session, esta se ubica en la carpeta de resources.

3. Gracias a lo descrito anteriormente, tenemos un connnection pooling donde cada solitiud que llega al API es manejada por un único objeto en cuanto a las conexiones se refiere. Esta fue utilizada junto con la transacción en el archivo de sd_owners_services.java.


 
#• Como respuesta al tercer punto, se detallan los pasos llevados a cabo a continuación:

Una vez listo el pooling de conexiones de la instrucción anterior, se procede a realizar la codificación de la transacción. 
a.	En primer lugar, en la clase del controlador sd_owners_controller se añade el método llamado addOwnerProblemDesign (de la línea 56 a la 69 de sd_owners_controller), mapeado mediante la instrucción @GetMapping(path = "/addproblemAndDesign") para ser accedido en el Browser, el cual recibe por parámetro los valores de más relevancia: el título del diseño (designtitle), la descripción del diseño (designdescription), el título del problema (problemtitle), la descripción del problema (problemdescription) y el email del owner al que se le hará referencia (powneremail). Seguidamente, solamente se encarga de instanciar los objetos Ownerdesign y Ownerproblem a ser procesados en la transacción. Se obtiene el Owner mediante el método abstracto de Spring findByEmail de la línea 67. Se procede a llamar a la transacción en la clase de sd_owners_services en la línea 68.

b.	En sd_owners_services se añadió un método adicional llamado addOwnerProblemDesigns (de la línea 37 a la 56), el cual recibe por parámetro las instancias de los objetos a ser transaccionados, tales como pOwner, pProblem y pDesign. Se le indica en la línea 37 que es un método transaccional mediante la instrucción @Transactional. Se inicializa la transacción (tran, de tipo Transaction) como nula, y después se realiza un try catch para la ejecución de la transacción. Dentro del try, se obtiene el pool de conexiones trabajado en la instrucción anterior para seguidamente obtener la sesión con la que se está trabajando. Acto seguido, se asigna a tran la inicialización de la transacción. Se procede a efectuar las tareas transaccionales: se le agrega al Owner el Design y el Problem a insertar y se guarda mediante la instrucción “session.save(pOwner);” los cambios realizados a esta instancia en la base de datos. Si todo salió bien, se hace commit a los cambios y se termina la transacción. En la parte del catch, se validan que no hayan errores antes de iniciar la transacción para hacer rollback, se obtiene y se imprime el error generado durante la ejecución.


3. Se realiza la prueba en el buscador mediante la url: http://localhost:8080/api/addproblemAndDesign?designtitle=t%C3%ADtulo%20del%20dise%C3%B1o%20por%20tran&designdescription=descripci%C3%B3n%20del%20dise%C3%B1o%20por%20tran&problemtitle=t%C3%ADtulo%20del%20problema%20por%20tran&problemdescription=descripci%C3%B3n%20del%20problema%20por%20tran&powneremail=andrey@encleta.com