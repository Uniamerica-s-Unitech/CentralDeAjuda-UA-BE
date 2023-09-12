create table marca(
	id serial primary key,
	nome varchar(50)
)

create table modelo(
	id serial primary key,
	nome varchar(50),
	marca_id integer references marca(id) 
)

create table notebooks (
	id serial primary key,
	patrimonio varchar (50),
	modelo_id integer references modelo(id)
)

create table aluno (
	id serial primary key,
	nome varchar(100),
	RA varchar(6) unique
)

create table ticket(
	id serial primary key,
	aluno_id integer references aluno(id),
	patrimonio_id integer references notebooks(id),
	data_de_entrega timestamp,
	data_de_devolucao timestamp,
	comentrio varchar(250)
)


