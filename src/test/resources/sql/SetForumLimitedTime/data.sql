INSERT INTO category (id, name, description)
VALUES (1, 'General Discussion', 'A general discussion category');

INSERT INTO forum (id, category_id, name, description, moderated, allow_anonymous_posts, display_order)
VALUES (1, 1, 'General Forum', 'A forum for general topics', FALSE, TRUE, 1);
