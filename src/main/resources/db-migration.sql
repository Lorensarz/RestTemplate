CREATE TABLE Users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL
);
CREATE TABLE Posts (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       content TEXT NOT NULL,
                       user_id INT,
                       FOREIGN KEY (user_id) REFERENCES Users(id)
);

CREATE TABLE Tags (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE Post_Tag (
                          post_id INT,
                          tag_id INT,
                          PRIMARY KEY (post_id, tag_id),
                          FOREIGN KEY (post_id) REFERENCES Posts(id),
                          FOREIGN KEY (tag_id) REFERENCES Tags(id)
);
