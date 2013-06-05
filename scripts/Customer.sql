-----------------------------------------------------------------------
-- TABELA EMPRESA
-----------------------------------------------------------------------
drop table if exists "Empresa" cascade;

create table "Empresa"
(
  "EmpresaID" integer not null,
  "Nome" character varying(100) not null,
  "Telefone" character varying(10),
  "Email" character varying(30) not null,
  constraint "EmpresaPK" primary key ("EmpresaID" )
);

insert into "Empresa" ("EmpresaID", "Nome", "Email") values (1, 'EMPRESA TESTE', 'empresa@empresa.com');
-----------------------------------------------------------------------

-----------------------------------------------------------------------
-- TABELA ATENDENTE
-----------------------------------------------------------------------
drop table if exists "Atendente" cascade;

create table "Atendente"
(
  "AtendenteID" serial not null,
  "Ativo" boolean not null default true,
  "Nome" character varying(100) not null,
  "Telefone" character varying(10),
  "Email" character varying(30) not null,
  "Login" character varying(20) not null unique,
  "Senha" character varying(256) not null,
  "Nivel" integer not null,
  constraint "AtendentePK" primary key ("AtendenteID" )
);

insert into "Atendente" ("Nome", "Email", "Login", "Senha", "Nivel") values ('Administrador', 'administrador@empresa.com', 'administrador', 'masterkey', 0);
insert into "Atendente" ("Nome", "Email", "Login", "Senha", "Nivel") values ('Supervisor', 'supervisor@empresa.com', 'supervisor', 'masterkey', 1);
-----------------------------------------------------------------------

-----------------------------------------------------------------------
-- TABELA CLIENTE
-----------------------------------------------------------------------
drop table if exists "Cliente" cascade;

create table "Cliente"
(
  "ClienteID" serial not null,
  "Tipo" integer not null, -- 0: Pessoa Física / 1: Pessoa Jurídica
  "Documento" character varying(18) not null, -- CPF / CNPJ
  "Ativo" boolean not null default true,
  "Nome" character varying(100) not null,
  "Logradouro" character varying(50),
  "Numero" character varying(10),    
  "Complemento" character varying(30), 
  "Bairro" character varying(20),
  "CEP" character varying(9),
  "Municipio" character varying(30),
  "UF" character varying(2),
  "Telefone" character varying(10),
  "Email" character varying(30) not null,
  constraint "ClientePK" primary key ("ClienteID" )
);
-----------------------------------------------------------------------

-----------------------------------------------------------------------
-- TABELA CLASSIFICACAO
-----------------------------------------------------------------------
drop table if exists "Classificacao" cascade;

create table "Classificacao"
(
  "ClassificacaoID" serial not null,
  "Descricao" character varying(30) not null,
  constraint "ClassificacaoPK" primary key ("ClassificacaoID" )
);

insert into "Classificacao" ("Descricao") values ('Sem classificação');
-----------------------------------------------------------------------

-----------------------------------------------------------------------
-- TABELA CATEGORIA
-----------------------------------------------------------------------
drop table if exists "Categoria" cascade;

create table "Categoria"
(
  "CategoriaID" serial not null,
  "Descricao" character varying(30) not null,
  constraint "CategoriaPK" primary key ("CategoriaID" )
);

insert into "Categoria" ("Descricao") values ('Elogio');
insert into "Categoria" ("Descricao") values ('Informação');
insert into "Categoria" ("Descricao") values ('Problema');
insert into "Categoria" ("Descricao") values ('Solicitação');
insert into "Categoria" ("Descricao") values ('Sugestão');
-----------------------------------------------------------------------

-----------------------------------------------------------------------
-- TABELA CHAMADO
-----------------------------------------------------------------------
drop table if exists "Chamado" cascade;

create table "Chamado"
(
  "ChamadoID" serial not null,
  "Titulo" character varying(100) not null,
  "ClienteID" integer not null,
  "DataAbertura" date not null,
  "HoraAbertura" time not null,
  "ClassificacaoID" integer not null,
  "CategoriaID" integer not null,
  "AtendenteID" integer not null,
  "Prazo" date not null,
  "Status" integer not null default 0,
  constraint "ChamadoPK" primary key ("ChamadoID" ),
  constraint "ChamadoClienteFK" foreign key ("ClienteID") references "Cliente" ("ClienteID") match simple on update cascade on delete cascade,
  constraint "ChamadoClassificaoFK" foreign key ("ClassificacaoID") references "Classificacao" ("ClassificacaoID") match simple on update cascade on delete cascade,
  constraint "ChamadoCategoriaFK" foreign key ("CategoriaID") references "Categoria" ("CategoriaID") match simple on update cascade on delete cascade,
  constraint "ChamadoAtendenteFK" foreign key ("AtendenteID") references "Atendente" ("AtendenteID") match simple on update cascade on delete cascade
);
-----------------------------------------------------------------------

-----------------------------------------------------------------------
-- TABELA ITERACAO
-----------------------------------------------------------------------
drop table if exists "Iteracao" cascade;

create table "Iteracao"
(
  "IteracaoID" serial not null,
  "ChamadoID" integer not null,
  "Data" date not null,
  "Hora" time not null,
  "AtendenteID" integer not null,
  "Iteracao" character varying(2000) not null,
  constraint "IteracaoPK" primary key ("IteracaoID" ),
  constraint "IteracaoChamadoFK" foreign key ("ChamadoID") references "Chamado" ("ChamadoID") match simple on update cascade on delete cascade,
  constraint "IteracaoAtendenteFK" foreign key ("AtendenteID") references "Atendente" ("AtendenteID") match simple on update cascade on delete cascade
);
-----------------------------------------------------------------------