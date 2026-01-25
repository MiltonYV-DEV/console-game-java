DROP SCHEMA  poo_pf;
CREATE SCHEMA poo_pf;
USE poo_pf;

CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE characters (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  name VARCHAR(50) NOT NULL,
  level INT NOT NULL DEFAULT 1,
  hp INT NOT NULL DEFAULT 100,
  attack INT NOT NULL DEFAULT 10,
  defense INT NOT NULL DEFAULT 5,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_char_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE matches (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  started_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  ended_at TIMESTAMP NULL,
  result ENUM('WIN','LOSE','ABANDONED') NULL,
  total_turns INT DEFAULT 0,
  xp_gained INT DEFAULT 0,
  CONSTRAINT fk_match_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE generated_enemies (
  id INT AUTO_INCREMENT PRIMARY KEY,
  match_id INT NOT NULL,
  prompt_user_text VARCHAR(255) NOT NULL,
  enemy_name VARCHAR(80) NOT NULL,
  enemy_level INT NOT NULL,
  stats_json JSON NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_gen_match FOREIGN KEY (match_id) REFERENCES matches(id)
);

CREATE TABLE battles (
  id INT AUTO_INCREMENT PRIMARY KEY,
  match_id INT NOT NULL,
  enemy_source ENUM('BASE','AI') NOT NULL,
  generated_enemy_id INT NULL,
  result ENUM('WIN','LOSE', 'ABANDONED') DEFAULT 'ABANDONED',
  turns INT NOT NULL DEFAULT 0,
  damage_dealt INT NOT NULL DEFAULT 0,
  damage_taken INT NOT NULL DEFAULT 0,
  CONSTRAINT fk_battle_match FOREIGN KEY (match_id) REFERENCES matches(id),
  CONSTRAINT fk_battle_gen_enemy FOREIGN KEY (generated_enemy_id) REFERENCES generated_enemies(id)
);

# modificacions
ALTER TABLE battles
    MODIFY result ENUM('WIN', 'LOSE', 'ABANDONED') DEFAULT 'ABANDONED';

#Usuarios DEMO
INSERT INTO users (username, password_hash) VALUES
('panchito',   '$2b$12$demo_hash_vyom_1234567890abcdef'),
('pedrito',  '$2b$12$demo_hash_maria_1234567890abcd'),
('luchito', '$2b$12$demo_hash_carlos_1234567890abc'),
('pablito',   '$2b$12$demo_hash_luis_1234567890abca'),
('anita',    '$2b$12$demo_hash_ana_1234567890abcb');

# Characters DEMO
INSERT INTO characters (user_id, name, level, hp, attack, defense) VALUES
(1, 'RangerRojo',     3, 130, 18,  8),
(2, 'RangerAzul',     2, 115, 14,  6),
(3, 'RangerVerde',    5, 160, 22, 12),
(4, 'RangerNegro',    1, 100, 10,  5),
(5, 'Beyota',    4, 145, 20,  9);

# Matches DEMO
INSERT INTO matches (user_id, started_at, ended_at, result, total_turns, xp_gained) VALUES
(1, '2026-01-20 18:10:00', '2026-01-20 18:22:00', 'WIN',       12, 120),
(1, '2026-01-22 21:00:00', NULL,                  'ABANDONED',         0,   0),
(2, '2026-01-21 19:05:00', '2026-01-21 19:19:00', 'LOSE',      10,  30),
(3, '2026-01-18 15:40:00', '2026-01-18 16:05:00', 'ABANDONED',  6,   0),
(4, '2026-01-23 10:00:00', '2026-01-23 10:14:00', 'WIN',        9,  80),
(5, '2026-01-24 09:30:00', NULL,                  'ABANDONED',         0,   0),
(1,'2026-01-22 18:10:00', '2026-01-22 18:12:00', 'WIN', 10, 120),
(1, '2026-01-20 18:10:00', '2026-01-20 18:22:00', 'WIN',       12, 120),
(1, '2026-01-22 21:00:00', NULL,                  'ABANDONED',         0,   0),
(2, '2026-01-21 19:05:00', '2026-01-21 19:19:00', 'LOSE',      10,  30),
(3, '2026-01-18 15:40:00', '2026-01-18 16:05:00', 'ABANDONED',  6,   0),
(4, '2026-01-23 10:00:00', '2026-01-23 10:14:00', 'WIN',        9,  80),
(5, '2026-01-24 09:30:00', NULL,                  'ABANDONED',         0,   0),
(1,'2026-01-22 18:10:00', '2026-01-22 18:12:00', 'WIN', 10, 120),
(1, '2026-01-20 18:10:00', '2026-01-20 18:22:00', 'WIN',       12, 120),
(1, '2026-01-22 21:00:00', NULL,                  'ABANDONED',         0,   0),
(2, '2026-01-21 19:05:00', '2026-01-21 19:19:00', 'LOSE',      10,  30),
(3, '2026-01-18 15:40:00', '2026-01-18 16:05:00', 'ABANDONED',  6,   0),
(4, '2026-01-23 10:00:00', '2026-01-23 10:14:00', 'WIN',        9,  80),
(5, '2026-01-24 09:30:00', NULL,                  'ABANDONED',         0,   0),
(1,'2026-01-22 18:10:00', '2026-01-22 18:12:00', 'WIN', 10, 120),
(1, '2026-01-20 18:10:00', '2026-01-20 18:22:00', 'WIN',       12, 120),
(1, '2026-01-22 21:00:00', NULL,                  'ABANDONED',         0,   0),
(2, '2026-01-21 19:05:00', '2026-01-21 19:19:00', 'LOSE',      10,  30),
(3, '2026-01-18 15:40:00', '2026-01-18 16:05:00', 'ABANDONED',  6,   0),
(4, '2026-01-23 10:00:00', '2026-01-23 10:14:00', 'WIN',        9,  80),
(5, '2026-01-24 09:30:00', NULL,                  'ABANDONED',         0,   0),
(1,'2026-01-22 18:10:00', '2026-01-22 18:12:00', 'WIN', 10, 120);

# Generated_enemies DEMO
INSERT INTO generated_enemies (match_id, prompt_user_text, enemy_name, enemy_level, stats_json) VALUES
(1, 'Un bandido ágil de nivel bajo', 'Bandido Veloz', 2,
 JSON_OBJECT('hp', 95, 'attack', 14, 'defense', 4, 'element', 'none')),

(2, 'Una bruja del pantano con veneno', 'Bruja del Fango', 3,
 JSON_OBJECT('hp', 120, 'attack', 16, 'defense', 6, 'element', 'poison')),

(3, 'Un guardián de sombras con guadaña', 'Segador Umbrío', 4,
 JSON_OBJECT('hp', 135, 'attack', 22, 'defense', 7, 'element', 'dark')),

(4, 'Un dragón bebé de fuego', 'Draco Carmesí', 3,
 JSON_OBJECT('hp', 125, 'attack', 19, 'defense', 5, 'element', 'fire')),

(5, 'Un golem de hielo resistente', 'Gólem Glacial', 5,
 JSON_OBJECT('hp', 190, 'attack', 18, 'defense', 14, 'element', 'ice')),

(6, 'Un asesino sigiloso con dagas', 'Acechador Nocturno', 4,
 JSON_OBJECT('hp', 110, 'attack', 24, 'defense', 5, 'element', 'none')),

(7, 'Un bandido ágil de nivel bajo', 'Bandido Veloz', 2,
 JSON_OBJECT('hp', 95, 'attack', 14, 'defense', 4, 'element', 'none')),

(8, 'Una bruja del pantano con veneno', 'Bruja del Fango', 3,
 JSON_OBJECT('hp', 120, 'attack', 16, 'defense', 6, 'element', 'poison')),

(9, 'Un guardián de sombras con guadaña', 'Segador Umbrío', 4,
 JSON_OBJECT('hp', 135, 'attack', 22, 'defense', 7, 'element', 'dark')),

(10, 'Un dragón bebé de fuego', 'Draco Carmesí', 3,
 JSON_OBJECT('hp', 125, 'attack', 19, 'defense', 5, 'element', 'fire')),

(11, 'Un golem de hielo resistente', 'Gólem Glacial', 5,
 JSON_OBJECT('hp', 190, 'attack', 18, 'defense', 14, 'element', 'ice')),

(12, 'Un asesino sigiloso con dagas', 'Acechador Nocturno', 4,
 JSON_OBJECT('hp', 110, 'attack', 24, 'defense', 5, 'element', 'none')),

(13, 'Un bandido ágil de nivel bajo', 'Bandido Veloz', 2,
 JSON_OBJECT('hp', 95, 'attack', 14, 'defense', 4, 'element', 'none')),

(14, 'Una bruja del pantano con veneno', 'Bruja del Fango', 3,
 JSON_OBJECT('hp', 120, 'attack', 16, 'defense', 6, 'element', 'poison')),

(15, 'Un guardián de sombras con guadaña', 'Segador Umbrío', 4,
 JSON_OBJECT('hp', 135, 'attack', 22, 'defense', 7, 'element', 'dark')),

(16, 'Un dragón bebé de fuego', 'Draco Carmesí', 3,
 JSON_OBJECT('hp', 125, 'attack', 19, 'defense', 5, 'element', 'fire')),

(17, 'Un golem de hielo resistente', 'Gólem Glacial', 5,
 JSON_OBJECT('hp', 190, 'attack', 18, 'defense', 14, 'element', 'ice')),

(18, 'Un asesino sigiloso con dagas', 'Acechador Nocturno', 4,
 JSON_OBJECT('hp', 110, 'attack', 24, 'defense', 5, 'element', 'none')),
(19, 'Un bandido ágil de nivel bajo', 'Bandido Veloz', 2,
 JSON_OBJECT('hp', 95, 'attack', 14, 'defense', 4, 'element', 'none')),

(20, 'Una bruja del pantano con veneno', 'Bruja del Fango', 3,
 JSON_OBJECT('hp', 120, 'attack', 16, 'defense', 6, 'element', 'poison')),

(21, 'Un guardián de sombras con guadaña', 'Segador Umbrío', 4,
 JSON_OBJECT('hp', 135, 'attack', 22, 'defense', 7, 'element', 'dark')),

(22, 'Un dragón bebé de fuego', 'Draco Carmesí', 3,
 JSON_OBJECT('hp', 125, 'attack', 19, 'defense', 5, 'element', 'fire')),

(23, 'Un golem de hielo resistente', 'Gólem Glacial', 5,
 JSON_OBJECT('hp', 190, 'attack', 18, 'defense', 14, 'element', 'ice')),

(24, 'Un asesino sigiloso con dagas', 'Acechador Nocturno', 4,
 JSON_OBJECT('hp', 110, 'attack', 24, 'defense', 5, 'element', 'none')),
(25, 'Un asesino sigiloso con dagas', 'Acechador Nocturno', 4,
 JSON_OBJECT('hp', 110, 'attack', 24, 'defense', 5, 'element', 'none')),
(26, 'Un asesino sigiloso con dagas', 'Acechador Nocturno', 4,
 JSON_OBJECT('hp', 110, 'attack', 24, 'defense', 5, 'element', 'none')),
(27, 'Un asesino sigiloso con dagas', 'Acechador Nocturno', 4,
 JSON_OBJECT('hp', 110, 'attack', 24, 'defense', 5, 'element', 'none')),
(28, 'Un asesino sigiloso con dagas', 'Acechador Nocturno', 4,
 JSON_OBJECT('hp', 110, 'attack', 24, 'defense', 5, 'element', 'none'));

INSERT INTO battles (
  match_id, enemy_source, generated_enemy_id, result,
  turns, damage_dealt, damage_taken
) VALUES
(1, 'AI', 1, 'WIN',       8, 140, 70),
(2, 'AI', 2, 'ABANDONED', 3,  35, 20),
(3, 'AI', 3, 'LOSE',     11, 120, 160),
(4, 'AI', 4, 'WIN',       6, 115, 55),
(5, 'AI', 5, 'WIN',      14, 230, 150),
(6, 'AI', 6, 'LOSE',      9,  98, 130),
(7, 'AI', 1, 'WIN',       8, 140, 70),
(8, 'AI', 2, 'ABANDONED', 3,  35, 20),
(9, 'AI', 3, 'LOSE',     11, 120, 160),
(10, 'AI', 4, 'WIN',       6, 115, 55),
(11, 'AI', 5, 'WIN',      14, 230, 150),
(12, 'AI', 6, 'LOSE',      9,  98, 130),
(13, 'AI', 1, 'WIN',       8, 140, 70),
(14, 'AI', 2, 'ABANDONED', 3,  35, 20),
(15, 'AI', 3, 'LOSE',     11, 120, 160),
(16, 'AI', 4, 'WIN',       6, 115, 55),
(17, 'AI', 5, 'WIN',      14, 230, 150),
(18, 'AI', 6, 'LOSE',      9,  98, 130),
(19, 'AI', 1, 'WIN',       8, 140, 70),
(20, 'AI', 2, 'ABANDONED', 3,  35, 20),
(21, 'AI', 3, 'LOSE',     11, 120, 160),
(22, 'AI', 4, 'WIN',       6, 115, 55),
(23, 'AI', 5, 'WIN',      14, 230, 150),
(24, 'AI', 6, 'LOSE',      9,  98, 130),
(25, 'AI', 1, 'WIN',       8, 140, 70),
(26, 'AI', 2, 'ABANDONED', 3,  35, 20),
(27, 'AI', 3, 'LOSE',     11, 120, 160),
(28, 'AI', 4, 'WIN',       6, 115, 55);



# Querys - Consultas
SELECT * FROM users;
SELECT * FROM characters;
SELECT * FROM matches;
SELECT * FROM battles;

# DROPS
DROP TABLE users;
DROP TABLE characters;
DROP TABLE matches;
DROP Table battles;

# USERS
# Verificar si usuario ya existe
SELECT 1 FROM users WHERE username = ? LIMIT 1;

# Registrar usuario
INSERT INTO users (username, password_hash) VALUES (?, ?);

# Pedir id - username - password_hash con username
SELECT id, username, password_hash FROM users WHERE username = ? LIMIT 1;

# CHARACTERS
# Ingresar un registro de character con valores por defecto
INSERT INTO characters (user_id, name, level, hp, attack, defense)
VALUES (?, ?, 1, 100, 20, 5);

# Recuperar un character por is de usuario
SELECT u.id   as user_id,
       u.username,
       c.id   as character_id,
       c.name as character_name,
       c.level,
       c.hp,
       c.attack,
       c.defense
FROM users u
         JOIN characters c ON c.user_id = u.id
WHERE u.id = ?
LIMIT 1;

# MATCHES
# registrar un matche solo con user_id
INSERT INTO matches (user_id) VALUES (?);

# Actualizar un match con resultados obtenidos
UPDATE matches
SET ended_at    = CURRENT_TIMESTAMP,
    result      = ?,
    total_turns = ?,
    xp_gained   = ?
WHERE id = ?;

# GENERATED_ENEMIES
# Registrar informacion del enemigo generado por inteligencia artificial
INSERT INTO generated_enemies (match_id, prompt_user_text, enemy_name, enemy_level, stats_json)
VALUES (?, ?, ?, ?, CAST(? AS JSON));

# BATTLES
# registrar battle con resultados y informacion de enemigo generado
INSERT INTO battles (match_id, enemy_source, generated_enemy_id, result, turns, damage_dealt, damage_taken)
VALUES (?, 'AI', ?, ?, ?, ?, ?);

# Ranking
# recuperar lista de usuarios con mas wins
SELECT u.id,
       u.username,
       SUM(CASE WHEN m.result = 'WIN' THEN 1 ELSE 0 END)  AS wins,
       SUM(CASE WHEN m.result = 'LOSE' THEN 1 ELSE 0 END) as loses,
       COUNT(m.id)                                        AS total_matches
FROM users u
        LEFT JOIN matches m ON m.user_id = u.id
GROUP BY u.id, u.username
ORDER BY wins DESC, total_matches DESC
LIMIT ?;

# modificaciones
ALTER TABLE characters
  ADD UNIQUE KEY uq_characters_user_id (user_id);

ALTER TABLE generated_enemies
  ADD UNIQUE KEY uq_generated_enemies_match_id (match_id);

