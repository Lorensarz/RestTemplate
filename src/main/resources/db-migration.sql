CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL
);
CREATE TABLE posts (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       content TEXT NOT NULL,
                       user_id INT,
                       FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE tags (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE post_tag (
                          post_id INT,
                          tag_id INT,
                          PRIMARY KEY (post_id, tag_id),
                          FOREIGN KEY (post_id) REFERENCES posts(id),
                          FOREIGN KEY (tag_id) REFERENCES tags(id)
);
