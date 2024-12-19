CREATE TABLE IF NOT EXISTS roles
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name TEXT
);

CREATE TABLE IF NOT EXISTS users
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    username VARCHAR(50) UNIQUE  NOT NULL,
    email    VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255)        NOT NULL,
    role_id  BIGINT REFERENCES roles (id) ON DELETE CASCADE ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS dinatoons
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name           VARCHAR(255) NOT NULL,
    genre          VARCHAR(100),
    image_url      TEXT,
    total_chapters INT,
    description    TEXT
);

CREATE TABLE IF NOT EXISTS user_lists
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_id   BIGINT REFERENCES users (id) ON DELETE CASCADE ON UPDATE NO ACTION,
    list_name VARCHAR(100) NOT NULL,
    UNIQUE (user_id, list_name)
);

CREATE TABLE IF NOT EXISTS user_dinatoons
(
    id              BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_id         BIGINT REFERENCES users (id) ON DELETE CASCADE ON UPDATE NO ACTION,
    dinatoon_id     BIGINT REFERENCES dinatoons (id) ON DELETE CASCADE ON UPDATE NO ACTION,
    list_id         BIGINT REFERENCES user_lists (id) ON DELETE CASCADE ON UPDATE NO ACTION,
    user_rating     INT,
    current_chapter INT DEFAULT 1,
    UNIQUE (user_id, dinatoon_id, list_id)
);

CREATE INDEX idx_user_id ON user_dinatoons (user_id);
CREATE INDEX idx_manga_id ON user_dinatoons (dinatoon_id);
CREATE INDEX idx_list_id ON user_dinatoons (list_id);