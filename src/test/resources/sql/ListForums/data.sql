-- Seed data for categories
INSERT INTO category (id, name, description) VALUES
    (1, 'General Discussion', 'Discuss anything here'),
    (2, 'Technical Support', 'Get help with technical issues');

-- Seed data for forums
INSERT INTO forum (id, category_id, name, description, moderated, allow_anonymous_posts, display_order) VALUES
    (1, 1, 'Introductions', 'Introduce yourself', FALSE, TRUE, 1),
    (2, 1, 'Off-topic', 'Talk about anything', FALSE, TRUE, 2),
    (3, 2, 'Hardware Issues', 'Hardware-related problems', TRUE, FALSE, 1),
    (4, 2, 'Software Issues', 'Software-related problems', TRUE, FALSE, 2);
