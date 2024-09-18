CREATE TABLE Usuario (
                         id_usuario SERIAL PRIMARY KEY,
                         nome VARCHAR(255) NOT NULL,
                         email VARCHAR(255) UNIQUE NOT NULL,
                         senha VARCHAR(255) NOT NULL,
                         data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE Status (
                        id_status SERIAL PRIMARY KEY,
                        status VARCHAR(255) NOT NULL
);


CREATE TABLE Viagem (
                        id_viagem SERIAL PRIMARY KEY,
                        usuario_id INT NOT NULL,
                        nome VARCHAR(255) NOT NULL,
                        status_id INT NOT NULL,
                        data_ida DATE NOT NULL,
                        data_volta DATE,
                        valor_prv INT NOT NULL,
                        valor_real INT,
                        latitude DECIMAL (10,8),
                        longitude DECIMAL (11,8),
                        CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES Usuario(id_usuario),
                        CONSTRAINT fk_status FOREIGN KEY (status_id) REFERENCES Status(id_status)
);


CREATE TABLE Categoria (
                           id_categoria SERIAL PRIMARY KEY,
                           nome VARCHAR(255) NOT NULL
);


CREATE TABLE Despesa (
                         id_despesa SERIAL PRIMARY KEY,
                         categoria_id INT NOT NULL,
                         viagem_id INT NOT NULL,
                         nome VARCHAR(255) NOT NULL,
                         descricao TEXT,
                         data DATE NOT NULL,
                         valor DECIMAL(10, 2) NOT NULL,
                         CONSTRAINT fk_categoria FOREIGN KEY (categoria_id) REFERENCES Categoria(id_categoria),
                         CONSTRAINT fk_viagem FOREIGN KEY (viagem_id) REFERENCES Viagem(id_viagem)
);