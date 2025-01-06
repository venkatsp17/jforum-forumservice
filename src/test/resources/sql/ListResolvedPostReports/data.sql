INSERT INTO category (id, name, description) VALUES 
(1, 'General Discussion', 'Forum for general topics');

INSERT INTO forum (id, category_id, name, description, moderated, allow_anonymous_posts, display_order) VALUES
(1, 1, 'Announcements', 'Official announcements', FALSE, FALSE, 1),
(2, 1, 'Feedback', 'User feedback', TRUE, FALSE, 2);

INSERT INTO topic (id, forum_id, subject, created_at) VALUES
(1, 1, 'Welcome to the forum!', CURRENT_TIMESTAMP),
(2, 2, 'Feedback on recent changes', CURRENT_TIMESTAMP);

INSERT INTO post (id, topic_id, forum_id, content, created_at) VALUES
(1, 1, 1, 'This is the first post.', CURRENT_TIMESTAMP),
(2, 2, 2, 'I like the new features.', CURRENT_TIMESTAMP),
(3, 2, 2, 'I have some suggestions.', CURRENT_TIMESTAMP);

INSERT INTO post_report (id, post_id, report_date, description, user_id, status) VALUES
(1, 3, CURRENT_TIMESTAMP, 'Spam content', 1001, 'UNRESOLVED'),
(2, 2, CURRENT_TIMESTAMP, 'Offensive language', 1002, 'RESOLVED'),
(3, 1, CURRENT_TIMESTAMP, 'Irrelevant post', 1003, 'RESOLVED');
