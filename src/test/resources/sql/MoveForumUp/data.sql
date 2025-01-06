INSERT INTO category (id, name, description) VALUES
(1, 'General Discussion', 'A general discussion category');

INSERT INTO forum (id, category_id, name, description, moderated, allow_anonymous_posts, display_order) VALUES
(1, 1, 'Forum One', 'First forum', TRUE, FALSE, 1),
(2, 1, 'Forum Two', 'Second forum', FALSE, TRUE, 2);
