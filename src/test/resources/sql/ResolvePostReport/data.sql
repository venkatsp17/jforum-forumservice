INSERT INTO category (id, name, description)
VALUES (1, 'Test Category', 'This is a test category');

INSERT INTO forum (id, category_id, name, description, moderated, allow_anonymous_posts, display_order)
VALUES (1, 1, 'Test Forum', 'This is a test forum', FALSE, TRUE, 1);

INSERT INTO topic (id, forum_id, subject, created_at)
VALUES (1, 1, 'Test Topic', CURRENT_TIMESTAMP);

INSERT INTO post (id, topic_id, forum_id, content, created_at)
VALUES (1, 1, 1, 'This is a test post', CURRENT_TIMESTAMP);

INSERT INTO post_report (id, post_id, report_date, description, user_id, status)
VALUES (1, 1, CURRENT_TIMESTAMP, 'Test report description', 100, 'UNRESOLVED');
