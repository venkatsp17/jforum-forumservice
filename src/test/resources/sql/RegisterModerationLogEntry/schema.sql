-- Drop tables in the correct order
DROP TABLE IF EXISTS moderation_log;
DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS topic;
DROP TABLE IF EXISTS forum;
DROP TABLE IF EXISTS category;

-- Create tables in the correct order
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
    FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE topic (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    forum_id BIGINT NOT NULL,
    subject VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (forum_id) REFERENCES forum(id)
);

CREATE TABLE post (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    topic_id BIGINT NOT NULL,
    forum_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (topic_id) REFERENCES topic(id),
    FOREIGN KEY (forum_id) REFERENCES forum(id)
);

CREATE TABLE moderation_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    log TEXT NOT NULL,
    date TIMESTAMP NOT NULL,
    original_message VARCHAR(255),
    poster_user VARCHAR(255),
    post_id BIGINT,
    topic_id BIGINT,
    user_id BIGINT NOT NULL
    -- Foreign key constraint to 'user' table is not defined as per instructions
);
