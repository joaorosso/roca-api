CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS roca (
    id uuid DEFAULT uuid_generate_v4(),
    descricao VARCHAR(50) NOT NULL,
    fechado BOOLEAN,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS lucro (
    id UUID DEFAULT uuid_generate_v4(),
    data DATE,
    descricao VARCHAR(50) NOT NULL,
    quantidade DECIMAL NOT NULL,
    valor_unitario DECIMAL NOT NULL,
    total DECIMAL NOT NULL,
    roca_id UUID NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (roca_id) REFERENCES roca (id)
);

CREATE TABLE IF NOT EXISTS despesa (
    id UUID DEFAULT uuid_generate_v4(),
    data DATE,
    descricao VARCHAR(50) NOT NULL,
    quantidade DECIMAL NOT NULL,
    valor_unitario DECIMAL NOT NULL,
    total DECIMAL NOT NULL,
    roca_id UUID NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (roca_id) REFERENCES roca (id)
);

