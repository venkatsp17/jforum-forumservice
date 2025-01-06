INSERT INTO category (id, name, description) VALUES (1, 'Test Category', 'A test category.');
INSERT INTO forum (id, category_id, name, description, moderated, allow_anonymous_posts, display_order) VALUES (1, 1, 'Test Forum', 'A test forum.', FALSE, TRUE, 0);
INSERT INTO forum_limited_time (id, forum_id, limited_time) VALUES (1, 1, 60);
