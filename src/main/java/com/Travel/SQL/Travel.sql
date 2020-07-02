CREATE TABLE USERS(
  userSid INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(20) NOT NULL,
  id VARCHAR(20) NOT NULL,
  password VARCHAR(40) NOT NULL,
  gender VARCHAR(1) NOT NULL,
  email VARCHAR(30) NOT NULL,
  nickname VARCHAR(15) NOT NULL
); 

CREATE INDEX idx_id ON USERS (id);

CREATE TABLE AREAS (
  tagId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(20) NOT NULL
);

CREATE TABLE USER_AREA(
    userSid INT NOT NULL,
    tagId INT NOT NULL,
    FOREIGN KEY (userSid) references USERS(userSid)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (tagId) references AREAS(tagId)
    ON DELETE CASCADE
    ON UPDATE CASCADE 
);

CREATE TABLE  USER_AUTHORITY(
    userSid INT NOT NULL,
    authorityName VARCHAR(20) NOT NULL,
    FOREIGN KEY (userSid) references USERS(userSid)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE FEEDS(
    feedSid INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    userSid INT NOT NULL,
    likes INT NOT NULL,
    contents VARCHAR(4000),
    upload_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userSid) references USERS (userSid)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE PLANS(
    planSid INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    feedSid INT NOT NULL,
    date VARCHAR(20),
    FOREIGN KEY (feedSid) references FEEDS (feedSid)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE INDEX idx_feedforplan ON PLANS (feedSid);

CREATE TABLE DAILYPLANS(
    planSid INT NOT NULL,
    time VARCHAR(20) NOT NULL,
    plan VARCHAR(20) NOT NULL,
    FOREIGN KEY (planSid) references PLANS (planSid)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE INDEX idx_plan ON DAILYPLANS (planSid);

CREATE TABLE FEED_IMAGE(
    feedSid INT NOT NULL,
    image_path VARCHAR(100) NOT NULL,
    FOREIGN KEY (feedSid) references FEEDS (feedSid)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE INDEX idx_feedforimage ON FEED_IMAGE (feedSid);

CREATE TABLE FEED_AREA(
    feedSid INT NOT NULL,
    tagId INT NOT NULL,
    FOREIGN KEY (feedSid) references FEEDS (feedSid)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (tagId) references AREAS (tagId)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE INDEX idx_feedforarea ON FEED_AREA (tagId);
CREATE INDEX idx_areaforfeed ON FEED_AREA (feedSid);