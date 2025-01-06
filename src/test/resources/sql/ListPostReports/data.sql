INSERT INTO category (id, name, description) VALUES (1, 'General Discussion', 'All topics related to general discussion.');

INSERT INTO forum (id, category_id, name, description, moderated, allow_anonymous_posts, display_order) VALUES (1, 1, 'Introductions', 'Introduce yourself to the community.', TRUE, FALSE, 1);

INSERT INTO topic (id, forum_id, subject, created_at) VALUES (1, 1, 'Welcome to the forum!', '2023-10-01 10:00:00');

INSERT INTO post (id, topic_id, forum_id, content, created_at) VALUES (1, 1, 1, 'Hello everyone!', '2023-10-01 10:05:00');

INSERT INTO post_report (id, post_id, report_date, description, user_id, status) VALUES (1, 1, '2023-10-01 11:00:00', 'Inappropriate content', 1001, 'UNRESOLVED');

INSERT INTO post_report (id, post_id, report_date, description, user_id, status) VALUES (2, 1, '2023-10-02 11:00:00', 'Spam', 1002, 'RESOLVED');
