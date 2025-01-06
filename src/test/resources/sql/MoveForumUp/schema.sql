DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS topic;
DROP TABLE IF EXISTS forum;
DROP TABLE IF EXISTS category;

CREATE TABLE category (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR NOT NULL,
  description VARCHAR
);

CREATE TABLE forum (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  category_id BIGINT NOT NULL,
  name VARCHAR NOT NULL,
  description VARCHAR,
  moderated BOOLEAN NOT NULL,
  allow_anonymous_posts BOOLEAN NOT NULL,
  display_order INT NOT NULL,
  FOREIGN KEY (category_id) REFERENCES category (id)
);

CREATE TABLE topic (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  forum_id BIGINT NOT NULL,
  subject VARCHAR NOT NULL,
  created_at TIMESTAMP NOT NULL,
  FOREIGN KEY (forum_id) REFERENCES forum (id)
);

CREATE TABLE post (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  topic_id BIGINT NOT NULL,
  forum_id BIGINT NOT NULL,
  content VARCHAR NOT NULL,
  created_at TIMESTAMP NOT NULL,
  FOREIGN KEY (topic_id) REFERENCES topic (id),
  FOREIGN KEY (forum_id) REFERENCES forum (id)
);
