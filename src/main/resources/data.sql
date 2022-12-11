create table users (
id SERIAL PRIMARY KEY,
name_player VARCHAR(50));

create table games (
id SERIAL PRIMARY KEY,
player_x INTEGER REFERENCES users (id),
player_o INTEGER REFERENCES users (id),
field VARCHAR,
status VARCHAR);