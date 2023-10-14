CREATE TABLE users (
                       id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE posts (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       content TEXT NOT NULL,
                       user_id INT,
                       FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE tags (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE post_tag (
                          post_id INT,
                          tag_id INT,
                          PRIMARY KEY (post_id, tag_id),
                          FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
                          FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
);


INSERT INTO users (id, name, email) VALUES
                                    (1,'User 1', 'user1@example.com'),
                                    (2,'User 2', 'user2@example.com');

INSERT INTO posts (id, title, content, user_id) VALUES
                                                (1, 'Post 1', 'Content for post 1', 1),
                                                (2, 'Post 2', 'Content for post 2', 2);

INSERT INTO tags (id, name) VALUES
                            (1, 'Tag 1'),
                            (2, 'Tag 2'),
                            (3, 'Tag 3');

INSERT INTO post_tag (post_id, tag_id) VALUES
                                           (1, 1),
                                           (1, 2),
                                           (2, 2);

