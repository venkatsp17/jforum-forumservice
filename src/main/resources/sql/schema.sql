-- Schema creation for the ForumService application

-- Create User table
CREATE TABLE `User` (
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    PRIMARY KEY (`id`)
);

-- Create Category table
CREATE TABLE `Category` (
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `description` VARCHAR(255),
    PRIMARY KEY (`id`)
);

-- Create Forum table
CREATE TABLE `Forum` (
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `category_id` BIGINT NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `description` VARCHAR(255),
    `moderated` BOOLEAN,
    `allow_anonymous_posts` BOOLEAN,
    `display_order` INT,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`category_id`) REFERENCES `Category`(`id`)
);

-- Create Topic table
CREATE TABLE `Topic` (
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `forum_id` BIGINT NOT NULL,
    `subject` VARCHAR(255) NOT NULL,
    `created_at` DATETIME NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`forum_id`) REFERENCES `Forum`(`id`)
);

-- Create Post table
CREATE TABLE `Post` (
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `topic_id` BIGINT NOT NULL,
    `forum_id` BIGINT NOT NULL,
    `content` TEXT NOT NULL,
    `created_at` DATETIME NOT NULL,
    `user_id` BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`topic_id`) REFERENCES `Topic`(`id`),
    FOREIGN KEY (`forum_id`) REFERENCES `Forum`(`id`)
);

-- Create PostReport table
CREATE TABLE `PostReport` (
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `post_id` BIGINT NOT NULL,
    `report_date` DATETIME NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    `user_id` BIGINT NOT NULL,
    `status` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`post_id`) REFERENCES `Post`(`id`)
);

-- Create ForumLimitedTime table
CREATE TABLE `ForumLimitedTime` (
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `forum_id` BIGINT NOT NULL UNIQUE,
    `limited_time` BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`forum_id`) REFERENCES `Forum`(`id`)
);

-- Create ModerationLog table
CREATE TABLE `ModerationLog` (
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `log` TEXT NOT NULL,
    `date` DATETIME NOT NULL,
    `original_message` TEXT,
    `poster_user` VARCHAR(255),
    `post_id` BIGINT,
    `topic_id` BIGINT,
    `user_id` BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `User`(`id`)
);
