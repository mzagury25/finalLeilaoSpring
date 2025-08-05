
CREATE TABLE `usuario` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `nome` VARCHAR(100) NOT NULL,
    `cpf` VARCHAR(14) UNIQUE NOT NULL,
    `email` VARCHAR(100) UNIQUE NOT NULL,
    `data_nascimento` DATE,
    `senha` VARCHAR(255) NOT NULL
);

CREATE TABLE `leilao` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `titulo` VARCHAR(100) NOT NULL,
    `lance_minimo` DECIMAL(12, 2) NOT NULL,
    `data_inicio` TIMESTAMP NOT NULL,
    `data_fim` TIMESTAMP NOT NULL,
    `estado` VARCHAR(20) NOT NULL
);

CREATE TABLE `objeto` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `nome` VARCHAR(100) NOT NULL,
    `descricao` TEXT,
    `foto` VARCHAR(255),
    `leilao_id` INT NOT NULL,
    FOREIGN KEY (`leilao_id`) REFERENCES `leilao`(`id`) ON DELETE CASCADE
);

CREATE TABLE `lance` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `valor` DECIMAL(12, 2) NOT NULL,
    `data` DATETIME NOT NULL,
    `usuario_id` INT NOT NULL,
    `leilao_id` INT NOT NULL,
    FOREIGN KEY (`usuario_id`) REFERENCES `usuario`(`id`),
    FOREIGN KEY (`leilao_id`) REFERENCES `leilao`(`id`) ON DELETE CASCADE
);

