INSERT INTO category (id, name, description) VALUES (1, 'General', 'General discussions');

INSERT INTO forum (id, category_id, name, description, moderated, allow_anonymous_posts, display_order)
VALUES (1, 1, 'General Forum', 'Forum for general topics', FALSE, TRUE, 1);

INSERT INTO topic (id, forum_id, subject, created_at)
VALUES (1, 1, 'Welcome to the forum', CURRENT_TIMESTAMP);

INSERT INTO post (id, topic_id, forum_id, content, created_at)
VALUES (1, 1, 1, 'This is the first post.', CURRENT_TIMESTAMP);
