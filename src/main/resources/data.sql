INSERT INTO task (id, description) VALUES (1, 'Go shopping');
INSERT INTO task (id, description) VALUES (2, 'Do homework');

INSERT INTO task_item (id, description, completed, task_id) VALUES (1, 'Milk', false, 1);
INSERT INTO task_item (id, description, completed, task_id) VALUES (2, 'Bread', false, 1);
INSERT INTO task_item (id, description, completed, task_id) VALUES (3, 'Write a report', false, 2);
INSERT INTO task_item (id, description, completed, task_id) VALUES (4, 'Read a book', false, 2);