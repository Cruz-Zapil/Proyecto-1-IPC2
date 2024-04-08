CREATE TABLE rol (
id_rol INT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR (50),
descripcion  VARCHAR (100),
pago INT 

);

create table usuario (
id_usuario int auto_increment primary key,
nombre varchar (150),
apellido varchar (150),
username varchar (150),
password_has varchar (250),
estado boolean,
id_rol int,
genero varchar (100),
telefono varchar (15),
edad int,

foreign key (id_rol) references rol(id_rol)

);


create table tarifa (
id_tarifa int auto_increment primary key,
tipo_tarifa varchar(100),
moneda varchar(50),
cant int
);

create table destino (
id_destino int auto_increment primary key,
nombre varchar(100),
direccion varchar(150),
cuota int

);


create table ruta(
id_tarifa int auto_increment primary key,
carretera varchar(150),
estado boolean,
id_destino int,
limite_carga int,
foreign key (id_destino) references destino(id_destino)

);

create table punto_control(
id_punto_control int auto_increment primary key not null,
id_ruta int not null,
limite_paquete int,
moneda varchar(5),
tarifa_local int,
id_tarifa_global int,

foreign key (id_ruta) references ruta (id_ruta),
foreign key (id_tarifa_global) references tarifa (id_tarifa)

);


create table cliente(
id_cliente varchar (50) primary key not null,
nombre varchar(100) not null,
apellido varchar (100) not null,
direccion varchar (200) not null,
genero varchar (100) ,
telefono varchar (50) 
);



create table factura(
id_factura int auto_increment primary key not null,
id_cliente varchar (50) not null,
id_recepcionista int ,
fecha date,
sub_total decimal(10,2),
total decimal(10,2),

foreign key (id_cliente) references cliente (id_cliente),
foreign key (id_recepcionista) references usuario (id_usuario)


);


create table bodega_recep(
id_bodega int auto_increment primary key not null,
id_recepcionista int not null,
id_destino int not null,
estado boolean,

foreign key (id_recepcionista) references usuario(id_usuario),
foreign key (id_destino) references destino (id_destino)
);

create table paquete (
id_paquete int auto_increment primary key not null,
id_cliente varchar (50) not null,
id_destino int not null,
id_ruta int,
peso decimal (10,2),
descripcion varchar (100),
referencia_destiono varchar (150),
estado boolean,
fecha_entrada date not null,
fecha_entrega date,

foreign key (id_cliente) references cliente (id_cliente),
foreign key (id_destino) references destino (id_destino),
foreign key (id_ruta) references ruta(id_ruta)

);


create table registro_bodega(
id_registro_bodega int auto_increment primary key not null,
id_bodega int,
id_paquete int,
tipo_accion varchar (50),
fecha_entraga date not null,
fecha_salida date,

foreign key (id_bodega) references bodega_recep (id_bodega),
foreign key (id_paquete) references paquete (id_paquete)

);


create table detalle_factura(
id_detalle_factura int auto_increment primary key not null,
id_factura int not null,
id_paquete int not null,

foreign key (id_factura) references factura (id_factura),
foreign key (id_paquete) references paquete (id_paquete)
);


create table registro_punto_control (
id_registro_punto int auto_increment primary key not null,
id_paquete int not null,
id_punto_control int not null,
id_ruta int not null,
horas_acumuladas int,
costo_generado decimal (10,2),
fecha_entrada date not null,
fecha_salida date,

foreign key (id_paquete) references paquete (id_paquete),
foreign key (id_punto_control) references punto_control (id_punto_control),
foreign key (id_ruta) references ruta (id_ruta)

);










 


