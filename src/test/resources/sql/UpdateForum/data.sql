INSERT INTO category (id, name, description) VALUES
(1, 'General Discussion', 'A place for general topics.'),
(2, 'Announcements', 'Important announcements and news.');

INSERT INTO forum (id, category_id, name, description, moderated, allow_anonymous_posts, display_order) VALUES
(1, 1, 'Introductions', 'Introduce yourself to the community.', FALSE, TRUE, 1);
