CREATE TABLE IF NOT EXISTS Category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(500),
    isActive BOOLEAN DEFAULT TRUE,
    isDeleted BOOLEAN DEFAULT FALSE,
    createdBy INT,
    createdAt DATETIME,
    updatedBy INT,
    updatedAt DATETIME
);

USE enotes;

INSERT INTO category (name, description, isActive, isDeleted, createdBy, createdAt)
VALUES 
('Personal', 'Personal notes and reminders', TRUE, FALSE, 1, NOW()),
('Work', 'Work-related notes and tasks', TRUE, FALSE, 1, NOW()),
('Study', 'Study material and references', TRUE, FALSE, 1, NOW()),
('Ideas', 'Random ideas and brainstorming', TRUE, FALSE, 1, NOW()),
('Travel', 'Travel plans and experiences', TRUE, FALSE, 1, NOW());


SELECT * FROM Category;


