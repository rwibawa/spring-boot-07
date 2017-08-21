create table account (
  id int identity primary key,
  username varchar(255) not null,
  password varchar (45) not null,
  role varchar (15) not null
);

-- Populate with default accounts
insert into account (username, password, role) values
  ('rwibawa', 'Ch@ng3M3!', 'USER'),
  ('admin', 'admin', 'ADMIN'),
  ('actuator', 'management', 'ACTUATOR');