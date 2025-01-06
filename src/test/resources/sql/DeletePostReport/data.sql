INSERT INTO category (id, name, description) VALUES (1, 'General Discussion', 'Discussion on general topics.');

INSERT INTO forum (id, category_id, name, description, moderated, allow_anonymous_posts, display_order)
VALUES (1, 1, 'Intro Forum', 'Introduce yourself here.', FALSE, TRUE, 1);

INSERT INTO topic (id, forum_id, subject, created_at)
VALUES (1, 1, 'Welcome to the forum!', CURRENT_TIMESTAMP);

INSERT INTO post (id, topic_id, forum_id, content, created_at)
VALUES (1, 1, 1, 'Hello everyone!', CURRENT_TIMESTAMP);

INSERT INTO post_report (id, post_id, report_date, description, user_id, status)
VALUES (1, 1, CURRENT_TIMESTAMP, 'Spam content', 100, 'UNRESOLVED');
