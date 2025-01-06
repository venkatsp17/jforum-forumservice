INSERT INTO category (id, name, description) VALUES 
    (1, 'Test Category', 'A category for testing.');

INSERT INTO forum (id, category_id, name, description, moderated, allow_anonymous_posts, display_order) VALUES 
    (1, 1, 'Test Forum', 'A forum for testing purposes.', FALSE, TRUE, 1);

INSERT INTO topic (id, forum_id, subject, created_at) VALUES 
    (1, 1, 'Test Topic', CURRENT_TIMESTAMP);

INSERT INTO post (id, topic_id, forum_id, content, created_at) VALUES 
    (1, 1, 1, 'Test post content.', CURRENT_TIMESTAMP);
