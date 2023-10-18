CREATE TABLE users (
                       user_id INT AUTO_INCREMENT PRIMARY KEY,
                       user_name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE posts (
                       post_id INT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       content TEXT NOT NULL,
                       user_id INT,
                       FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE tags (
                      tag_id INT AUTO_INCREMENT PRIMARY KEY,
                      tag_name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE post_tag (
                          post_id INT NOT NULL,
                          tag_id INT NOT NULL,
                          PRIMARY KEY (post_id, tag_id),
                          FOREIGN KEY (post_id) REFERENCES posts(post_id) ON DELETE CASCADE,
                          FOREIGN KEY (tag_id) REFERENCES tags(tag_id) ON DELETE CASCADE
);



INSERT INTO users (user_name, email) VALUES
                                    ('User 1', 'user1@example.com'),
                                    ('User 2', 'user2@example.com');

INSERT INTO posts (title, content, user_id) VALUES
                                                ('Post 1', 'Content for post 1', 1),
                                                ('Post 2', 'Content for post 2', 2);

INSERT INTO tags (tag_name) VALUES
                            ('Tag 1'),
                            ('Tag 2'),
                            ('Tag 3');

INSERT INTO post_tag (post_id, tag_id) VALUES
                                           (1, 1),
                                           (1, 2),
                                           (2, 2);

