DROP TABLE IF EXISTS post_report;
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
    display_order INTEGER,
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
    content VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (topic_id) REFERENCES topic(id),
    FOREIGN KEY (forum_id) REFERENCES forum(id)
);

CREATE TABLE post_report (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id BIGINT NOT NULL,
    report_date TIMESTAMP NOT NULL,
    description VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'UNRESOLVED',
    FOREIGN KEY (post_id) REFERENCES post(id),
    CHECK (status IN ('UNRESOLVED', 'RESOLVED'))
);
