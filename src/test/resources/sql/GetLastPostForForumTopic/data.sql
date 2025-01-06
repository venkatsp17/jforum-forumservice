INSERT INTO category (id, name, description)
VALUES (1, 'General Discussion', 'A place to talk about general topics.');

INSERT INTO forum (id, category_id, name, description, moderated, allow_anonymous_posts, display_order)
VALUES (1, 1, 'Announcements', 'Official announcements from the staff.', TRUE, FALSE, 1);

INSERT INTO topic (id, forum_id, subject, created_at)
VALUES (1, 1, 'Welcome to the Forum!', '2021-01-01 10:00:00');

INSERT INTO post (id, topic_id, forum_id, content, created_at, user_id)
VALUES (1, 1, 1, 'This is the first post.', '2021-01-01 10:05:00', 1);

INSERT INTO post (id, topic_id, forum_id, content, created_at, user_id)
VALUES (2, 1, 1, 'This is the second post.', '2021-01-01 11:00:00', 2);

INSERT INTO topic (id, forum_id, subject, created_at)
VALUES (2, 1, 'Community Guidelines', '2021-01-02 09:00:00');

INSERT INTO post (id, topic_id, forum_id, content, created_at, user_id)
VALUES (3, 2, 1, 'Please read the community guidelines.', '2021-01-02 09:05:00', 3);

INSERT INTO post (id, topic_id, forum_id, content, created_at, user_id)
VALUES (4, 2, 1, 'Thank you for the information.', '2021-01-02 12:00:00', 4);
