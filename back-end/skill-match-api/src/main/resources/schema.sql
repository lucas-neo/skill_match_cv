-- Schema para Skill Match API

-- Tabela de Candidatos
CREATE TABLE IF NOT EXISTS candidato (
    id VARCHAR(255) PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    localizacao VARCHAR(255),
    habilidades_csv TEXT,
    anos_de_experiencia INTEGER
);

-- Tabela de Vagas
CREATE TABLE IF NOT EXISTS vaga (
    id VARCHAR(255) PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    localizacao VARCHAR(255),
    habilidades_requeridas_csv TEXT,
    anos_de_experiencia_minimos INTEGER
);

-- Índices para melhorar performance
CREATE INDEX IF NOT EXISTS idx_candidato_localizacao ON candidato(localizacao);
CREATE INDEX IF NOT EXISTS idx_vaga_localizacao ON vaga(localizacao);
CREATE INDEX IF NOT EXISTS idx_candidato_email ON candidato(email);
