DROP TABLE professores;
DROP TABLE alunos;

CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL,
    telefone VARCHAR(50),
    logradouro VARCHAR(255),
    numero VARCHAR(50),
    bairro VARCHAR(255),
    cidade VARCHAR(100),
    estado VARCHAR(50),
    cep VARCHAR(20)
);

CREATE TABLE aluno (
    id BIGINT PRIMARY KEY,
    matricula VARCHAR(50) NOT NULL,
    CONSTRAINT fk_aluno_usuario FOREIGN KEY (id) REFERENCES usuario(id)
);

CREATE TABLE professor (
    id BIGINT PRIMARY KEY,
    departamento VARCHAR(255),
    CONSTRAINT fk_professor_usuario FOREIGN KEY (id) REFERENCES usuario(id)
);
