CREATE TABLE trainers (
                          id RAW(16) DEFAULT SYS_GUID(),
                          name varchar(256) NOT NULL,

                          CONSTRAINT trainer_id_pk PRIMARY KEY (id)
);

CREATE TABLE pokemons (
                          id RAW(16) DEFAULT SYS_GUID(),
                          trainer_id RAW(16) NOT NULL,
                          name varchar(256) NOT NULL,

                          CONSTRAINT pokemons_id_pk PRIMARY KEY (id),
                          CONSTRAINT pokemon_trainer_id_fk FOREIGN KEY (trainer_id) REFERENCES trainers(id)
)