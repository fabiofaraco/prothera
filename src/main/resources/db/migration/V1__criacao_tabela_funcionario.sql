create table funcionario (
    id bigint not null auto_increment,
    nome varchar(100) not null,
    data_nascimento date not null,
    funcao varchar(50) not null,
    salario numeric(38,2) not null,
    primary key (id)
)