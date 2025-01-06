INSERT INTO category (id, name, description) VALUES (1, 'General Discussion', 'A place for general topics');

INSERT INTO forum (id, category_id, name, description, moderated, allow_anonymous_posts, display_order)
VALUES
  (1, 1, 'Introductions', 'Introduce yourself', FALSE, TRUE, 1),
  (2, 1, 'Announcements', 'Official announcements', TRUE, FALSE, 2),
  (3, 1, 'Off-topic', 'Discuss anything', FALSE, TRUE, 3);
