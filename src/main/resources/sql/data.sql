-- Initial data insertion for the ForumService application

-- Insert initial users
INSERT INTO `User` (`id`)
VALUES
    (1),
    (2),
    (3);

-- Insert categories
INSERT INTO `Category` (`id`, `name`, `description`)
VALUES
    (1, 'General Discussion', 'A place for general discussion'),
    (2, 'Announcements', 'Official announcements'),
    (3, 'Support', 'Support and help');

-- Insert forums
INSERT INTO `Forum` (`id`, `category_id`, `name`, `description`, `moderated`, `allow_anonymous_posts`, `display_order`)
VALUES
    (1, 1, 'Welcome Forum', 'Introduce yourself here', TRUE, TRUE, 1),
    (2, 2, 'News and Updates', 'Latest news and updates', TRUE, FALSE, 2);

-- Insert topics
INSERT INTO `Topic` (`id`, `forum_id`, `subject`, `created_at`)
VALUES
    (1, 1, 'Welcome to the forums', '2021-01-01 10:00:00'),
    (2, 2, 'New features coming soon', '2021-01-05 12:00:00');

-- Insert posts
INSERT INTO `Post` (`id`, `topic_id`, `forum_id`, `content`, `created_at`, `user_id`)
VALUES
    (1, 1, 1, 'Hello everyone!', '2021-01-01 10:05:00', 1),
    (2, 2, 2, 'Stay tuned for updates.', '2021-01-05 12:10:00', 2);

-- Insert post reports
INSERT INTO `PostReport` (`id`, `post_id`, `report_date`, `description`, `user_id`, `status`)
VALUES
    (1, 1, '2021-01-02 14:00:00', 'Inappropriate content', 2, 'UNRESOLVED');

-- Insert forum limited time entries
INSERT INTO `ForumLimitedTime` (`id`, `forum_id`, `limited_time`)
VALUES
    (1, 1, 3600);

-- Insert moderation logs
INSERT INTO `ModerationLog` (`id`, `log`, `date`, `original_message`, `poster_user`, `post_id`, `topic_id`, `user_id`)
VALUES
    (1, 'Post deleted due to violation', '2021-01-02 15:00:00', 'Inappropriate content', 'alice', 1, 1, 3);
