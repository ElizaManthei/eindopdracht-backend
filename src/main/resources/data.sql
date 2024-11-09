
insert into roles(role_name) values ('CUSTOMER'), ('ADMIN'), ('EMPLOYEE');
insert into users(username, mobile_number, birthday, password) values ('Jas',1000, '1983-03-03', '$2a$12$v3hpM1z6mh.ITK9UdFeeiOHOaRzvrlLCCGQc9tyZi718XWXWmLub6');
--   username: karel, password: appel
insert into users_roles values('Jas', 'CUSTOMER');