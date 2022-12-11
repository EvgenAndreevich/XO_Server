create table games (
id SERIAL PRIMARY KEY,
player_x INTEGER REFERENCES users (id),
player_o INTEGER REFERENCES users (id),
field VARCHAR,
status VARCHAR);


--    buyer_contact INTEGER,
--	    CONSTRAINT fk_contact
--        FOREIGN KEY(buyer_contact)
--        REFERENCES contact(buyer_id),