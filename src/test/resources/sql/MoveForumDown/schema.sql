DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS topic;
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
    display_order INT,
    CONSTRAINT fk_forum_category_id FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE topic (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    forum_id BIGINT NOT NULL,
    subject VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_topic_forum_id FOREIGN KEY (forum_id) REFERENCES forum(id)
);

CREATE TABLE post (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    topic_id BIGINT NOT NULL,
    forum_id BIGINT NOT NULL,
    content VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_post_topic_id FOREIGN KEY (topic_id) REFERENCES topic(id),
    CONSTRAINT fk_post_forum_id FOREIGN KEY (forum_id) REFERENCES forum(id)
);
