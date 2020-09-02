CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE roca (
    id uuid DEFAULT uuid_generate_v4(),
    descricao VARCHAR(50) NOT NULL,
    fechado BOOLEAN,
    PRIMARY KEY (id)
);

CREATE TABLE lucro (
    id UUID DEFAULT uuid_generate_v4(),
    data DATE,
    descricao VARCHAR(50) NOT NULL,
    quantidade DECIMAL NOT NULL,
    valor_unitario DECIMAL NOT NULL,
    roca_id UUID NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (roca_id) REFERENCES roca (id)
);

CREATE TABLE despesa (
    id UUID DEFAULT uuid_generate_v4(),
    data DATE,
    descricao VARCHAR(50) NOT NULL,
    quantidade DECIMAL NOT NULL,
    valor_unitario DECIMAL NOT NULL,
    roca_id UUID NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (roca_id) REFERENCES roca (id)
);

CREATE TABLE usuario (
    id UUID DEFAULT uuid_generate_v4(),
	nome VARCHAR(50) NOT NULL,
	username VARCHAR(50) NOT NULL,
	password VARCHAR(150) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO usuario (id, nome, username, password) values ('3e7c2eae-3480-466e-bb8a-4e1ca5a0631e', 'Admin', 'admin', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.');

CREATE TABLE permissao (
    id UUID DEFAULT uuid_generate_v4(),
    code VARCHAR(50) NOT NULL,
	descricao VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE usuario_permissao (
    usuario_id UUID DEFAULT uuid_generate_v4(),
	permissao_id UUID DEFAULT uuid_generate_v4(),
	PRIMARY KEY(usuario_id, permissao_id),
	FOREIGN KEY(usuario_id) REFERENCES usuario(id),
	FOREIGN KEY(permissao_id) REFERENCES permissao(id)
);