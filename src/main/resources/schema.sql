DROP TABLE IF EXISTS lance;
DROP TABLE IF EXISTS objeto;
DROP TABLE IF EXISTS leilao;
DROP TABLE IF EXISTS usuario;

CREATE TABLE usuario (
                         id SERIAL PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         cpf VARCHAR(14) UNIQUE NOT NULL,
                         email VARCHAR(100) UNIQUE NOT NULL,
                         data_nascimento DATE,
                         senha VARCHAR(255) NOT NULL
);

CREATE TABLE leilao (
                        id SERIAL PRIMARY KEY,
                        titulo VARCHAR(100) NOT NULL,
                        lance_minimo NUMERIC(12, 2) NOT NULL,
                        data_inicio TIMESTAMPTZ NOT NULL,
                        data_fim TIMESTAMPTZ NOT NULL,
                        estado VARCHAR(20) NOT NULL CHECK (estado IN ('AGENDADO', 'CANCELADO', 'FINALIZADO', 'EM_ANDAMENTO'))
);

CREATE TABLE objeto (
                        id SERIAL PRIMARY KEY,
                        nome VARCHAR(100) NOT NULL,
                        descricao TEXT,
                        foto VARCHAR(255),
                        leilao_id INTEGER NOT NULL REFERENCES leilao(id) ON DELETE CASCADE
);

CREATE TABLE lance (
                       id SERIAL PRIMARY KEY,
                       valor NUMERIC(12, 2) NOT NULL,
                       data TIMESTAMPTZ NOT NULL,
                       usuario_id INTEGER NOT NULL REFERENCES usuario(id),
                       leilao_id INTEGER NOT NULL REFERENCES leilao(id) ON DELETE CASCADE
);
