create table history_owner(
    owner_id int not null references owners(id),
    car_id int not null references car(id)
);