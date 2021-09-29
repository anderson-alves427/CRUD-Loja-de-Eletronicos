CREATE DATABASE dbeletrocentusx;

USE dbeletrocentusx;

CREATE TABLE tbCliente(
Cpf_Cnpj_Cliente varchar(15) not null PRIMARY KEY,
NomeCliente varchar(40) not null,
RuaCliente varchar(20) not null,
ComplementoCliente varchar(30),
Contato1Cliente varchar(11) not null,
Contato2Cliente varchar(11)
);

INSERT INTO tbCliente
(Cpf_Cnpj_Cliente, NomeCliente, RuaCliente, ComplementoCliente, Contato1Cliente, Contato2Cliente)
values ('1111', 'Anderson Alves', 'Flores, N15', 'Próximo ao Mercamil', '85985695471', '85987546321'),
('2222', 'Fabrício Nogueira', 'Flores, N15', 'Próximo ao lago', '8596874563', '85975486521'),
('3333', 'Mateus', 'Antonio, N15', 'Próximo ao Mercamil', '85965233286', '8595321456');





CREATE TABLE tbAparelho(
IdAparelho integer auto_increment not null PRIMARY KEY,
NomeAparelho varchar(25) not null,
NumeroSerie varchar(15) not null,
Marca varchar(20) not null,
Modelo varchar(20) not null,
Preco real not null
);

INSERT INTO tbAparelho
(NomeAparelho, NumeroSerie, Marca, Modelo, Preco)
values('Microondas', '111', 'Eletrobom', 'A111', 120),
('Refrigerador', '222', 'Consul', 'R222', 1999.9),
('Máquina de lavar', '333', 'Eletrobil', 'ML333', 1500);




CREATE TABLE tbCompra(
IdCompra integer not null auto_increment PRIMARY KEY,
DataCompra date not null,
Cpf_Cnpj_Cliente_Compra varchar(15) not null,
IdAparelhoCompra integer not null,
Observacao varchar(100)
);

ALTER TABLE tbCompra
add constraint fk_tbCompra_tbCliente
foreign key (Cpf_Cnpj_Cliente_Compra)
references tbCliente(Cpf_Cnpj_Cliente);

ALTER TABLE tbCompra
add constraint fk_tbCompra_tbAparelho
foreign key (IdAparelhoCompra)
references tbAparelho(IdAparelho);

select*from tbCompra;
select*from tbCliente;
select*from tbAparelho;

INSERT INTO tbCompra
(DataCompra, Cpf_Cnpj_Cliente_Compra, IdAparelhoCompra)
values('2021/01/10', '1111', 1),
('2021/02/10', '2222', 2),
('2021/03/11', '1111', 2),
('2021/03/11', '3333', 2),
('2021/03/15', '1111', 3);


-- Junçaõ da tabela de compras com as tabelas de aparelho e cliente. Após isso, seleciona determinadas colunas e mostra na ordem crescente dos nomes dos clientes
SELECT NomeCliente as Cliente, NomeAparelho as Aparelho, DataCompra 
FROM ((tbCompra INNER JOIN tbCliente ON Cpf_Cnpj_Cliente_Compra = Cpf_Cnpj_Cliente
)INNER join tbAparelho ON IdAparelhoCompra = IdAparelho) order by NomeCliente ASC;

-- Criação da tabela de Trocas

CREATE TABLE tbTroca(
IdTroca integer not null auto_increment PRIMARY KEY,
Cpf_Cnpj_Cliente_Troca varchar(15) not null,
IdAparelhoTroca integer not null,
IdCompraTroca integer not null,
ObservacaoTroca varchar(100),
DataTroca date not null
);

ALTER TABLE tbTroca
add constraint fk_tbTroca_tbCliente
foreign key (Cpf_Cnpj_Cliente_Troca)
references tbCliente(Cpf_Cnpj_Cliente);

ALTER TABLE tbTroca
add constraint fk_tbTroca_tbAparelho
foreign key (IdAparelhoTroca)
references tbAparelho(IdAparelho);

ALTER TABLE tbTroca
add constraint fk_tbTroca_tbCompra
foreign key (IdCompraTroca)
references tbCompra(IdCompra);


describe tbTroca;
select*from tbCompra;
select*from tbTroca;
select*from tbAparelho;


-- Inserção de dados mediante utilização de INSERT TO, SELECT e passagem de parâmetros em DataTroca
INSERT INTO tbTroca ( DataTroca, Cpf_Cnpj_Cliente_Troca, IdAparelhoTroca, IdCompraTroca) 
SELECT '2021/08/09', Cpf_Cnpj_Cliente_Compra, IdAparelhoCompra, IdCompra FROM tbCompra WHERE idCompra = 1;

INSERT INTO tbTroca ( DataTroca, Cpf_Cnpj_Cliente_Troca, IdAparelhoTroca, IdCompraTroca) 
SELECT '2021/09/10', Cpf_Cnpj_Cliente_Compra, IdAparelhoCompra, IdCompra FROM tbCompra WHERE idCompra = 2;

INSERT INTO tbTroca ( DataTroca, Cpf_Cnpj_Cliente_Troca, IdAparelhoTroca, IdCompraTroca) 
SELECT '2021/10/09', Cpf_Cnpj_Cliente_Compra, IdAparelhoCompra, IdCompra FROM tbCompra WHERE idCompra = 3;




select NomeCliente from tbCliente;

-- Inner join da tabela troca com as tabelas cliente, aparelho e compra, ordenando pela data de troca
SELECT NomeCliente as Cliente, NomeAparelho as Aparelho, DataCompra, DataTroca, ObservacaoTroca as Observação
FROM (((tbTroca INNER JOIN tbCliente ON Cpf_Cnpj_Cliente_Troca = Cpf_Cnpj_Cliente
)INNER JOIN tbAparelho ON IdAparelhoTroca = IdAparelho) INNER JOIN tbCompra ON IdCompraTroca = IdCompra) order by DataTroca desc;



select*from tbCliente;
select*from tbCompra;
describe tbCliente;


-- Store Procedure para incluir clientes na tabela tbCliente
DELIMITER $$
CREATE PROCEDURE inserir_cliente(IN registro varchar(15), IN nome varchar(40), IN rua varchar(20), IN contato1 varchar(11))
BEGIN
    INSERT INTO tbCliente (Cpf_Cnpj_Cliente, NomeCliente, RuaCliente, Contato1Cliente) VALUES (registro, nome, rua, contato1);
END $$
DELIMITER ;


-- Chama store procedure inserir_cliente
CALL inserir_cliente('5555', 'Rosa dos Ventos', 'São Miguel, N15', '85965897852');
CALL inserir_cliente('6666', 'Dora', 'Vale, N17', '85965898987');
CALL inserir_cliente('7777', 'Bil', 'Vale, N20', '85965885987');
CALL inserir_cliente('8888', 'Nara', 'Mil, N20', '85785885987');


-- Faz a contagem de todas as compras efetuadas na data especificada

SELECT COUNT(DataCompra)
FROM tbCompra
WHERE DataCompra = '2021-03-11';




