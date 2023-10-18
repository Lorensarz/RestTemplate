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