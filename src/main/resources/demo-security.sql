create database demo_security;
use demo_security;

create table product
(
	id			int					primary key auto_increment,
    `name`		varchar(255)		unique not null,
	price		decimal(12,3)		not null,
    slug		varchar(255)		not null,
    quantity	int					not null,
    isActived	bit					default 1 not null
);

create table `role`
(
	id			int					primary key auto_increment,
    `name`		varchar(255)		unique not null
);

create table `user`
(
	id			int					primary key auto_increment,
    username	varchar(10)			unique not null,
    `password`	varchar(100)		not null,
    roleId		int					references `role`(id)
);

insert into product(`name`, price, slug, quantity) values
('Product 1',		1500000,		'product-1',		10),
('Product 2',		3000000,		'product-2',		7),
('Product 3',		7500000,		'product-3',		2);

insert into `role`(`name`) values
('Admin'),
('User');

insert into `user`(username, `password`, roleId) values
('duynt',		'$2a$12$UUWKVzQ3zMs7d/ITtENadOai0UE5bPEMHD1o8IsT0TpZiGYucdssa',		2), -- pw: duynt
('admin',		'$2a$12$BxgXR3DGcemD0fnapRhk2erTMc9K1S8B8UOr0x9kcYobJlV5BnKmy',		1); -- pw: admin