CREATE TABLE User(
  sid INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
  name VARCHAR(20) NOT NULL,
  id VARCHAR(20) NOT NULL,
  pass VARCHAR(40) NOT NULL,
  gender VARCHAR(1) NOT NULL,
  email VARCHAR(30) NOT NULL
); 

CREATE INDEX idx_id ON User (id);

CREATE TABLE Area_tag (
  tag_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(20) NOT NULL
);

CREATE TABLE user_area(
    user_sid INT NOT NULL,
    tag_id INT NOT NULL,
    FOREIGN KEY (user_sid) references USER(sid)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (tag_id) references tag(tag_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE 
);

CREATE TABLE  user_authority(
    user_sid INT NOT NULL,
    authority_name VARCHAR(20) NOT NULL,
    FOREIGN KEY (user_sid) references USER(sid)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)