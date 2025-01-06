DROP TABLE IF EXISTS forum;
DROP TABLE IF EXISTS category;

CREATE TABLE category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE forum (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    moderated BOOLEAN,
    allow_anonymous_posts BOOLEAN,
    display_order INTEGER,
    FOREIGN KEY (category_id) REFERENCES category(id)
);
