DROP TABLE IF EXISTS tbl_member;

CREATE TABLE tbl_member (
    username    VARCHAR(50) PRIMARY KEY,
    password    VARCHAR(128) NOT NULL,
    email       VARCHAR(128) NOT NULL,
    reg_date    DATETIME default now(),
    update_date DATETIME default now()
);

DROP TABLE IF EXISTS tbl_member_auth;

CREATE TABLE tbl_member_auth (
    username    VARCHAR(50) not null,
    auth        VARCHAR(50) not null,
    primary key(username, auth),
    constraint fk_authorities_users foreign key (username) references tbl_member(username)
);


INSERT INTO tbl_member(username, password, email)
VALUES
    ('admin', '$2a$10$Tnp52pT3CxpcPTiWB.0UW.A66BFZZR6ftbOw/kcbpj9yZj8wQuBZq', 'admin@galapgos.org'),
    ('user0', '$2a$10$Tnp52pT3CxpcPTiWB.0UW.A66BFZZR6ftbOw/kcbpj9yZj8wQuBZq', 'user0@galapgos.org'),
    ('user1', '$2a$10$Tnp52pT3CxpcPTiWB.0UW.A66BFZZR6ftbOw/kcbpj9yZj8wQuBZq', 'user1@galapgos.org'),
    ('user2', '$2a$10$Tnp52pT3CxpcPTiWB.0UW.A66BFZZR6ftbOw/kcbpj9yZj8wQuBZq', 'user2@galapgos.org'),
    ('user3', '$2a$10$Tnp52pT3CxpcPTiWB.0UW.A66BFZZR6ftbOw/kcbpj9yZj8wQuBZq', 'user3@galapgos.org'),
    ('user4', '$2a$10$Tnp52pT3CxpcPTiWB.0UW.A66BFZZR6ftbOw/kcbpj9yZj8wQuBZq', 'user4@galapgos.org');

SELECT * FROM tbl_member;

INSERT INTO tbl_member_auth(username, auth)
VALUES
    ('admin', 'ROLE_ADMIN'),
    ('admin', 'ROLE_MANAGER'),
    ('admin', 'ROLE_MEMBER'),
    ('user0', 'ROLE_MANAGER'),
    ('user0', 'ROLE_MEMBER'),
    ('user1', 'ROLE_MEMBER'),
    ('user2', 'ROLE_MEMBER'),
    ('user3', 'ROLE_MEMBER'),
    ('user4', 'ROLE_MEMBER');

select * from tbl_member_auth order by auth;