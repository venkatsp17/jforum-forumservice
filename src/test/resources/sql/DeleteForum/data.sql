INSERT INTO category (id, name, description) VALUES (1, 'General Discussion', 'A general category for discussion');

INSERT INTO forum (id, category_id, name, description, moderated, allow_anonymous_posts, display_order)
VALUES (1, 1, 'General Forum', 'This is a general forum', FALSE, TRUE, 1);
