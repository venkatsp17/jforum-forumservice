INSERT INTO category (id, name, description) VALUES 
(1, 'General Discussion', 'A place for general topics.');

INSERT INTO forum (id, category_id, name, description, moderated, allow_anonymous_posts, display_order) VALUES 
(1, 1, 'Announcements', 'Official announcements', TRUE, FALSE, 1);

INSERT INTO topic (id, forum_id, subject, created_at) VALUES
(1, 1, 'Welcome to the forum', CURRENT_TIMESTAMP()),
(2, 1, 'Forum rules', CURRENT_TIMESTAMP());

INSERT INTO post (id, topic_id, forum_id, content, created_at) VALUES
(1, 1, 1, 'First post content', CURRENT_TIMESTAMP()),
(2, 1, 1, 'Reply to first post', CURRENT_TIMESTAMP()),
(3, 2, 1, 'Second post content', CURRENT_TIMESTAMP());
