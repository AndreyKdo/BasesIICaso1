# BasesIICaso1
Antes de realizar el caso, se investigó y se desarrolló el ORM del lenguaje de Java, Spring. Se programó mediante orientación a objetos la inserción y la consulta de la tabla sd_owners del diseño de SQL compartido por el profesor en las clases anteriores, pero con una modificación en la tabla sd_designs de un campo agregado llamado [parentdesignid] que hace referencia a la llave primaria de la misma tabla, esto debido al primer quiz, pero de igual manera este campo no se tomará en cuenta a las funcionalidades que nos interesan en este caso. Es importante añadir que las clases se llaman igual a las tablas a las que insertan para que sea más fácil identificar para Spring a cuál tabla debe hacerle modificaciones.

Instrucciones:
• Como implementar un objeto que representa una relacion 1 a N 
• Como manipular object pooling para reducir la cantidad de conexiones 
• Como implementar una transacción que afecte a más de una tabla

• Como respuesta al primer punto, se detallan los pasos llevados a cabo a continuación:
1.	Se programa la clase sd_designs para realizar el objeto correspondiente a esta tabla, donde se indica mediante las instrucciones de Spring que dicha clase es una entidad para una tabla de la base de datos. También se le indican los atributos correspondientes a las columnas de la tabla. Se hacen getters y setters por defecto.
    a.	En el constructor se reciben los atributos que poseen mayor relevancia en la tabla, tales como el title y la description. La creationdate se asigna como una instancia del objeto Date() de la librería java.util.Date.
    b.	Se presta especial atención al atributo de la clase llamado Owner, el cual es de tipo sd_owners, es decir, es un objeto que se refiere a un registro de la tabla sd_owners de la base de datos. Con dicho atributo, mediante la instrucción de Spring @ManyToOne, se indica que con este atributo se tiene una relación Muchos a Uno, y con la instrucción @JoinColumn(name=”ownerid”) se indica que se hace esta relación con la columna ownerid de la tabla sd_owners, como indicándole que el atributo ownerid de sd_owners es FK de la tabla sd_designs.
    
2.	Se agrega en la clase sd_owners el atributo ownerDesigns, el cual es de tipo List<sd_designs>, es decir, es una lista de los diseños asociados al owner correspondiente. A dicho atributo, se le indica mediante la instrucción de Spring @OneToMany(mappedBy = "Owner") que tiene una relación N a 1 con el atributo Owner del objeto sd_designs.
    a.	Se agrega un método en dicha clase llamado addDesigns(sd_designs Design) el cual se encarga de agregar al atributo o lista ownerDesigns un nuevo Design recibido por parámetro. También se encarga de asignar a dicho Design el Owner relacionado (el objeto actual o this). 
    
3.	En la interfaz de sd_owners_repository se agrega un método que trae por defecto el ORM Spring llamado findByEmail, encargado de buscar por un String recibido por parámetro un registro de la tabla que se le indique. Se retorna un objeto de tipo sd_owners. Spring se encarga de transformar el registro obtenido de la base de datos en el objeto que le corresponda.

4.	En la clase sd_owners_services se agrega también el método de findByEmail para llamar a la interfaz mencionada en el punto anterior. Así también se agrega el método de save(sd_owners owner) que ya está agregado en la interfaz sd_owners_repository por defecto por Spring, dicho método se encarga de guardar en la base de datos los cambios realizados en un objeto de tipo sd_owners. 

5.	En la clase sd_owners_controller se agrega el método encargado de traficar la inserción del design, llamado addDesign. Recibe por parámetro el title y description de este, además el email del owner del design correspondiente. En este método se lleva a cabo el procedimiento siguiente:
    a.	La búsqueda del owner por el email mediante la instrucción sd_owners Owner = controllerOwner.findByEmail(powneremail);
    b.	La creación del diseño mediante la instrucción sd_designs Ownerdesign = new sd_designs(ptitle,pdescription);
    c.	Se asigna la relación existente entre sd_owners y sd_designs, llamando al método del Owner mencionado en el punto 2.a mediante la instrucción Owner.addDesigns(Ownerdesign);.
    d.	Se guardan los cambios en la base de datos mediante el método save() explicado en el punto 4, con la instrucción controllerOwner.save(Owner);.

6.	Se realiza la prueba en el buscador con la instrucción de prueba http://localhost:8080/api/adddesign?&ptitle=adios&pdescription=adiostambien&powneremail=atorr@gmail.com. 



*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*Respuesta al punto 2 por aquí/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/


 
• Como respuesta al tercer punto, se detallan los pasos llevados a cabo a continuación:
1.	Al tener que afectar a más de una tabla, se realizó una clase llamada sd_problems, que se encarga de ser el objeto que hace referencia a la tabla del mismo nombre, de la misma manera en la que se realizó la primer instrucción del Caso ya que tiene una relación ManyToOne respecto a sd_owners. Por su parte, sd_owners también posee el atributo que enlista los N sd_problems que le pertenezcan.

2.	 Una vez listo el pooling de conexiones de la instrucción anterior, se procede a realizar la codificación de la transacción. 
	a.	En primer lugar, en la clase del controlador sd_owners_controller se añade el método llamado addOwnerProblemDesign, mapeado mediante la instrucción @GetMapping(path = "/addproblemAndDesign") para ser accedido en el Browser, el cual recibe por parámetro los valores de más relevancia: el título del diseño (designtitle), la descripción del diseño (designdescription), el título del problema (problemtitle), la descripción del problema (problemdescription) y el email del owner al que se le hará referencia (powneremail). Seguidamente, solamente se encarga de instanciar los objetos Ownerdesign y Ownerproblem a ser procesados en la transacción. Se obtiene el Owner mediante el findByEmail explicado en el desarrollo de la primera instrucción del Caso. Se procede a llamar a la transacción en la clase de sd_owners_services.
	b.	En sd_owners_services se añadió un método adicional llamado addOwnerProblemDesigns, el cual recibe por parámetro las instancias de los objetos a ser transaccionados, tales como pOwner, pProblem y pDesign. Se le indica en la parte superior que es un método transaccional mediante la instrucción @Transactional. Se inicializa la transacción (tran, de tipo Transaction) como nula, y después se realiza un try catch para la ejecución de la transacción. Dentro del try, se obtiene el pool de conexiones trabajado en la instrucción anterior para seguidamente obtener la sesión con la que se está trabajando. Acto seguido, se asigna a tran la inicialización de la transacción. Se procede a efectuar las tareas transaccionales: se le agrega al Owner el Design y el Problem a insertar y se guarda mediante la instrucción session.save(pOwner); los cambios realizados a esta instancia en la base de datos. Si todo salió bien, se hace commit a los cambios y se termina la transacción. En la parte del catch, se valida que no hayan errores antes de iniciar la transacción para hacer rollback, se obtiene y se imprime el error generado durante la ejecución.
