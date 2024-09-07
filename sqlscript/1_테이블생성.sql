--
-- BOOK
--
DROP TABLE IF EXISTS book_shop.translator;
DROP TABLE IF EXISTS book_shop.publisher;
DROP TABLE IF EXISTS book_shop.writer;
DROP TABLE IF EXISTS book_shop.book_md_history;

DELETE FROM book_shop.`book_contributor`;
DROP TABLE IF EXISTS book_shop.`book_contributor`;
CREATE TABLE `book_contributor` (
	`cb_num`	varchar(30)	NOT NULL,
	`isbn`	varchar(30)	NOT NULL
);

DELETE FROM book_shop.`writing_contributor`;
DROP TABLE IF EXISTS book_shop.`writing_contributor`;
CREATE TABLE `writing_contributor`
(
    `cb_num`    varchar(30) NOT NULL,
    `name`      varchar(30) NOT NULL,
    `job1`      varchar(30) NOT NULL,
    `job2`      varchar(30) NULL,
    `cont_desc` longtext    NULL,
    `wr_chk`    char(1)     NULL     DEFAULT 'N',
    `reg_date`  datetime    NOT NULL DEFAULT now(),
    `reg_id`    varchar(20) NOT NULL,
    `up_date`   datetime    NOT NULL DEFAULT now(),
    `up_id`     varchar(20) NOT NULL
);

DROP TABLE IF EXISTS book_shop.book_disc_hist;
CREATE TABLE `book_disc_hist`
(
    `disc_seq`        int(5)       NOT NULL,
    `isbn`            varchar(30)  NOT NULL,
    `papr_disc`       double       NOT NULL,
    `e_disc`          double       NOT NULL,
    `disc_start_date` datetime     NOT NULL,
    `disc_end_date`   datetime     NOT NULL,
    `comt`            varchar(100) NULL,
    `reg_date`        datetime     NOT NULL DEFAULT now(),
    `reg_id`          varchar(20)  NOT NULL,
    `up_date`         datetime     NOT NULL DEFAULT now(),
    `up_id`           varchar(20)  NOT NULL
);

DROP TABLE IF EXISTS book_shop.book_image;
CREATE TABLE `book_image`
(
    `img_seq`         int(10)      NOT NULL,
    `isbn`            varchar(30)  NOT NULL,
    `img_url`         varchar(100) NOT NULL,
    `img_hrzt_size`   int(10)      NULL,
    `img_vrtc_size`   int(10)      NULL,
    `img_file_format` varchar(30)  NULL,
    `img_regi_day`    datetime     NOT NULL DEFAULT now(),
    `img_desc`        varchar(100) NULL,
    `main_img_chk`    char(1)      NULL     DEFAULT 'N',
    `reg_date`        datetime     NOT NULL DEFAULT now(),
    `reg_id`          varchar(20)  NOT NULL,
    `up_date`         datetime     NOT NULL DEFAULT now(),
    `up_id`           varchar(20)  NOT NULL
);

DROP TABLE IF EXISTS book_shop.book;
CREATE TABLE `book`
(
    `isbn`           varchar(30)  NOT NULL,
    `cate_num`       varchar(30)  NOT NULL,
    `title`          varchar(30)  NOT NULL,
    `pub_name`       varchar(30)  NOT NULL,
    `pub_date`       varchar(50)  NOT NULL,
    `sale_stat`      varchar(30)  NOT NULL DEFAULT "판매중",
    `sale_vol`       int(30)      NOT NULL DEFAULT 0,
    `papr_pric`      int(10)      NOT NULL,
    `e_pric`         int(10)      NOT NULL,
    `papr_point`     double(5, 2) NOT NULL DEFAULT 5,
    `e_point`        double(5, 2) NOT NULL DEFAULT 5,
    `tot_page_num`   int(10)      NOT NULL,
    `tot_book_num`   int(10)      NOT NULL,
    `sale_com`       varchar(200) NULL,
    `cont`           longtext     NULL,
    `rating`         double(5, 2) NULL,
    `info`           longtext     NULL COMMENT '본문',
    `intro_award`    longtext     NULL,
    `rec`            longtext     NULL,
    `pub_review`     longtext     NULL,
    `pre_start_page` int(10)      NOT NULL,
    `pre_end_page`   int(10)      NOT NULL,
    `ebook_url`      varchar(100) NOT NULL,
    `book_reg_date`  datetime     NOT NULL DEFAULT now(),
    `regi_id`        varchar(30)  NOT NULL DEFAULT 'admin',
    `reg_date`       datetime     NOT NULL DEFAULT now(),
    `reg_id`         varchar(20)  NOT NULL DEFAULT 'admin',
    `up_date`        datetime     NOT NULL DEFAULT now(),
    `up_id`          varchar(20)  NOT NULL DEFAULT 'admin'
);

DROP TABLE IF EXISTS book_shop.category;
CREATE TABLE `category`
(
    `cate_num`       varchar(30)  NOT NULL,
    `name`           varchar(30)  NOT NULL,
    `lev`            int(5)       NOT NULL DEFAULT 1,
    `last_cate_chk`  char(1)      NOT NULL DEFAULT 'N',
    `cur_layr_num`   varchar(30)  NOT NULL DEFAULT 1,
    `whol_layr_name` varchar(255) NULL,
    `reg_date`       datetime     NOT NULL DEFAULT now(),
    `reg_id`         varchar(20)  NOT NULL,
    `up_date`        datetime     NOT NULL DEFAULT now(),
    `up_id`          varchar(20)  NOT NULL
);





-- ALTER TABLE book_shop.`writing_contributor` ADD CONSTRAINT `PK_WRITING_CONTRIBUTOR` PRIMARY KEY (
-- 	`cb_num`
-- );

-- ALTER TABLE `writing_contributer` ADD CONSTRAINT `PK_WRITING_CONTRIBUTER` PRIMARY KEY (  `cb_num` );

-- ALTER TABLE `book_md_history` ADD CONSTRAINT `PK_BOOK_MD_HISTORY` PRIMARY KEY (
-- 	`md_seq`,
-- 	`isbn`
-- );

-- ALTER TABLE `category` ADD CONSTRAINT `PK_CATEGORY` PRIMARY KEY (
-- 	`cate_num`
-- );

-- ALTER TABLE `book_disc_hist` ADD CONSTRAINT `PK_BOOK_DISC_HIST` PRIMARY KEY (
-- 	`disc_seq`,
-- 	`isbn`
-- );

-- ALTER TABLE `book` ADD CONSTRAINT `PK_BOOK` PRIMARY KEY (
-- 	`isbn`
-- );

-- ALTER TABLE `book_image` ADD CONSTRAINT `PK_BOOK_IMAGE` PRIMARY KEY (
-- 	`img_seq`,
-- 	`isbn`
-- );

-- ALTER TABLE `book_contributor` ADD CONSTRAINT `PK_BOOK_CONTRIBUTOR` PRIMARY KEY (
-- 	`cb_num`,
-- 	`isbn`
-- );

-- ALTER TABLE `book_md_history` ADD CONSTRAINT `FK_book_TO_book_md_history_1` FOREIGN KEY (
-- 	`isbn`
-- )
-- REFERENCES `book` (
-- 	`isbn`
-- );

-- ALTER TABLE `book_disc_hist` ADD CONSTRAINT `FK_book_TO_book_disc_hist_1` FOREIGN KEY (
-- 	`isbn`
-- )
-- REFERENCES `book` (
-- 	`isbn`
-- );

-- ALTER TABLE `book` ADD CONSTRAINT `FK_category_TO_book_1` FOREIGN KEY (
-- 	`cate_num`
-- )
-- REFERENCES `category` (
-- 	`cate_num`
-- );

-- ALTER TABLE `book_image` ADD CONSTRAINT `FK_book_TO_book_image_1` FOREIGN KEY (
-- 	`isbn`
-- )
-- REFERENCES `book` (
-- 	`isbn`
-- );

-- ALTER TABLE `book_contributor` ADD CONSTRAINT `FK_writing_contributer_TO_book_contributor_1` FOREIGN KEY (
-- 	`cb_num`
-- )
-- REFERENCES `writing_contributor` (
-- 	`cb_num`
-- );

-- ALTER TABLE `book_contributor` ADD CONSTRAINT `FK_book_TO_book_contributor_1` FOREIGN KEY (
-- 	`isbn`
-- )
-- REFERENCES `book` (
-- 	`isbn`
-- );

--
-- QA
--


-- 테이블이 없어서 DELETE 에서 에러가 날 수도 있습니다.
-- DELETE FROM qa_state;
-- DELETE FROM qa;
-- DELETE FROM qa_cate;
DROP TABLE IF EXISTS book_shop.qa_state;
DROP TABLE IF EXISTS book_shop.qa;
DROP TABLE IF EXISTS book_shop.qa_cate;
DROP TABLE IF EXISTS book_shop.code;


-- auto-generated definition
create table qa_cate
(
    qa_cate_num varchar(30)      not null
        primary key,
    name        varchar(30)      not null,
    comt        varchar(500)     null,
    reg_date    datetime         null,
    reg_id      varchar(20)      null,
    up_date     datetime         null,
    up_id       varchar(20)      null,
    chk_use     char default 'Y' not null,
    constraint chk_name_length
        check (char_length(`name`) >= 2)
);

-- auto-generated definition
create table qa
(
    qa_num        int auto_increment
        primary key,
    qa_cate_num   varchar(30)               not null,
    admin         varchar(30)               null,
    title         varchar(30)               not null,
    content       longtext                  not null,
    created_at    datetime                  null,
    chk_repl      char        default 'N'   null,
    reg_repl_date varchar(50)               null,
    email         varchar(50)               null,
    tele_num      varchar(30)               null,
    phon_num      varchar(30)               null,
    img1          varchar(250)              null,
    img2          varchar(250)              null,
    img3          varchar(250)              null,
    comt          varchar(500)              null,
    reg_date      datetime                  null,
    reg_id        varchar(20)               null,
    up_date       datetime                  null,
    up_id         varchar(20)               null,
    user_id       varchar(30)               not null,
    state         varchar(30) default '준비중' null,
    constraint qa___fk
        foreign key (qa_cate_num) references qa_cate (qa_cate_num),
    constraint chk_content_length
        check (char_length(`content`) >= 3),
    constraint chk_title_length
        check (char_length(`title`) >= 3)
);

-- auto-generated definition
create table qa_state
(
    qa_stat_seq  int auto_increment
        primary key,
    qa_stat_code varchar(30) not null,
    name         varchar(30) not null,
    reg_date     datetime    null,
    reg_id       varchar(20) null,
    up_date      datetime    null,
    up_id        varchar(20) null,
    qa_num       int         not null,
    appl_begin   varchar(30) not null,
    appl_end     varchar(30) not null,
    constraint qa_state___fk
        foreign key (qa_num) references qa (qa_num),
    constraint min_length_check
        check (char_length(`name`) >= 2)
);

-- auto-generated definition
create table code
(
    code_id   int auto_increment
        primary key,
    cate_num  varchar(30)  not null,
    code      varchar(30)  not null,
    code_name varchar(30)  null,
    ord       int          null,
    chk_use   char         null,
    comt      varchar(250) null,
    reg_date  datetime     null,
    reg_id    varchar(20)  null,
    up_date   datetime     null,
    up_id     varchar(20)  null
);

-- 
-- FAQ
-- 

-- faq_cate_code 생성하는 테이블
DROP TABLE IF EXISTS book_shop.faq_cate;
CREATE TABLE `book_shop`.`faq_cate`
(
    `cate_code`   VARCHAR(20) NOT NULL,
    `name`        VARCHAR(45) NOT NULL,
    `use_chk`     CHAR(1)     NOT NULL,
    `parent_code` VARCHAR(20) NOT NULL,
    `reg_date`    DATETIME    NOT NULL DEFAULT now(),
    `reg_id`      VARCHAR(20) NOT NULL,
    `up_date`     DATETIME    NOT NULL DEFAULT now(),
    `up_id`       VARCHAR(20) NOT NULL,
    PRIMARY KEY (`cate_code`),
    UNIQUE INDEX `cate_code_UNIQUE` (`cate_code` ASC) VISIBLE
);

insert into faq_cate (cate_code, name, use_chk, parent_code, reg_date, reg_id, up_date, up_id)
values ('00', '전체', 'Y', '0', now(), '110111', now(), '110111'),
       ('0010', '회원', 'Y', '00', now(), '110111', now(), '110111'),
       ('001010', '회원가입/탈퇴', 'Y', '0010', now(), '110111', now(), '110111'),
       ('001020', '회원정보', 'Y', '0010', now(), '110111', now(), '110111'),
       ('001030', '회원등급/혜택', 'Y', '0010', now(), '110111', now(), '110111'),
       ('0020', '주문/결제', 'Y', '00', now(), '110111', now(), '110111'),
       ('002010', '주문', 'Y', '0020', now(), '110111', now(), '110111'),
       ('002020', '결제', 'Y', '0020', now(), '110111', now(), '110111'),
       ('002030', '마일리지/예치금', 'Y', '0020', now(), '110111', now(), '110111'),
       ('0030', '반품/교환/환불', 'Y', '00', now(), '110111', now(), '110111'),
       ('003010', '반품/교환/환불', 'Y', '0030', now(), '110111', now(), '110111'),
       ('003020', '주문취소/변경', 'Y', '0030', now(), '110111', now(), '110111')
;

-- faq 테이블 생성하는 코드
DROP TABLE IF EXISTS book_shop.faq;
CREATE TABLE `book_shop`.`faq`
(
    `faq_seq`       INT          NOT NULL AUTO_INCREMENT,
    `faq_catg_code` VARCHAR(20)  NOT NULL,
    `title`         VARCHAR(100) NOT NULL,
    `cont`          LONGTEXT     NOT NULL,
    `dsply_chk`     CHAR(1)      NOT NULL DEFAULT 'Y',
    `view_cnt`      INT          NOT NULL DEFAULT 0,
    `reg_date`      DATETIME     NOT NULL DEFAULT now(),
    `reg_id`        VARCHAR(20)  NOT NULL,
    `up_date`       DATETIME     NOT NULL DEFAULT now(),
    `up_id`         VARCHAR(20)  NOT NULL,
    PRIMARY KEY (`faq_seq`),
    UNIQUE INDEX `faq_seq_UNIQUE` (`faq_seq` ASC) VISIBLE
);

INSERT INTO faq (faq_catg_code, title, cont, dsply_chk, view_cnt, reg_date, reg_id, up_date, up_id)
VALUES ('001010', '회원 가입은 어떻게 하나요?', '회원 가입은 홈페이지 오른쪽 상단의 "회원 가입" 버튼을 클릭하고, 필요한 정보를 입력한 후 "가입하기" 버튼을 클릭하면 됩니다.', 'Y',
        120, '2024-02-07 00:00:00', '110111', '2024-08-07 00:00:00', '110111'),
       ('001010', '비밀번호를 잊어버렸어요.', '비밀번호를 잊으셨다면 "비밀번호 찾기" 페이지로 이동하여, 등록된 이메일 주소를 입력하시면 비밀번호 재설정 링크가 발송됩니다.', 'Y', 85,
        '2024-02-07 00:00:00', '110222', '2024-08-07 00:00:00', '110222'),
       ('002010', '상품을 반품하고 싶어요.',
        '상품을 반품하려면 고객센터로 연락하시거나, "내 주문" 페이지에서 반품 요청을 할 수 있습니다. 반품 정책에 따라 다를 수 있으니 자세한 내용은 반품 정책을 확인해 주세요.', 'N', 56,
        '2024-05-07 14:30:00', '110333', '2024-08-07 14:30:00', '110333'),
       ('002030', '배송 기간은 얼마나 걸리나요?', '배송 기간은 보통 3~5일 정도 소요됩니다. 특정 지역이나 상품에 따라 다를 수 있으니, 자세한 사항은 배송 안내 페이지를 참고해 주세요.',
        'Y', 95, '2024-08-07 14:30:00', '220111', '2024-08-07 14:30:00', '220111'),
       ('003010', '문의 사항이 있을 때 어떻게 해야 하나요?', '문의 사항이 있을 경우, 고객센터로 연락하시거나, "문의하기" 페이지를 통해 질문을 남기시면 신속하게 답변드리겠습니다.', 'Y',
        72, '2024-08-07 14:30:00', '220222', '2024-08-07 14:30:00', '220222');
        
-- admin 테이블 생성하는 코드
-- DROP TABLE IF EXISTS book_shop.admin;
-- CREATE TABLE `book_shop`.`admin`
-- (
--     `id`       VARCHAR(30) NOT NULL,
--     `auth`     INT         NOT NULL,
--     `reg_date` DATETIME    NOT NULL DEFAULT now(),
--     `reg_id`   VARCHAR(20) NOT NULL,
--     `up_date`  DATETIME    NOT NULL DEFAULT now(),
--     `up_id`    VARCHAR(20) NULL,
--     PRIMARY KEY (`id`),
--     UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE
-- );

--
-- Member
--

-- Table structure for table `admin`
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`
(
    `id`       varchar(30) NOT NULL,
    `auth`     INT, 
    `reg_date` datetime    NOT NULL,
    `reg_id`   varchar(20) NOT NULL,
    `up_date`  datetime    NOT NULL,
    `up_id`    varchar(20) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE
);

INSERT INTO admin (id, auth, reg_date, reg_id, up_date, up_id)
VALUES ('110111', 1, now(), '000000', now(), '000000'),
       ('110222', 1, now(), '000000', now(), '000000'),
       ('110333', 1, now(), '000000', now(), '000000'),
       ('220111', 2, now(), '000000', now(), '000000'),
       ('220222', 2, now(), '000000', now(), '000000');

-- Table structure for table `info_change_history`

DROP TABLE IF EXISTS `info_change_history`;

CREATE TABLE `info_change_history`
(
    `chg_id`   int         NOT NULL,
    `chg_time` varchar(50) DEFAULT NULL,
    `chg_info` varchar(10) DEFAULT NULL,
    `bfor_chg` varchar(50) DEFAULT NULL,
    `aftr_chg` varchar(50) DEFAULT NULL,
    `reg_date` datetime    NOT NULL,
    `reg_id`   varchar(20) NOT NULL,
    `up_date`  datetime    NOT NULL,
    `up_id`    varchar(20) NOT NULL,
    `id`       varchar(30) NOT NULL
);

--
-- Table structure for table `logn_history`
--

DROP TABLE IF EXISTS `logn_history`;
CREATE TABLE `logn_history`
(
    `logn_id`   int         NOT NULL AUTO_INCREMENT,
    `logn_time` datetime    NOT NULL,
    `ip_addr`   varchar(45) NOT NULL,
    `reg_date`  datetime    NOT NULL,
    `reg_id`    varchar(50) NOT NULL,
    `up_date`   datetime    DEFAULT NULL,
    `up_id`     varchar(50) DEFAULT NULL,
    `id`        varchar(50) NOT NULL,
    PRIMARY KEY (`logn_id`)
);


--
-- Dumping data for table `logn_history`
--

LOCK TABLES `logn_history` WRITE;
INSERT INTO `logn_history`
VALUES (1, '2024-08-15 23:29:58', '0:0:0:0:0:0:0:1', '2024-08-15 23:29:58', 'chlgockd90', '2024-08-15 23:29:58',
        'chlgockd90', 'chlgockd90'),
       (2, '2024-08-15 23:30:49', '0:0:0:0:0:0:0:1', '2024-08-15 23:30:49', 'chlgockd90', '2024-08-15 23:30:49',
        'chlgockd90', 'chlgockd90'),
       (3, '2024-08-15 23:41:23', '0:0:0:0:0:0:0:1', '2024-08-15 23:41:23', 'chlgockd90', '2024-08-15 23:41:23',
        'chlgockd90', 'chlgockd90'),
       (4, '2024-08-15 23:42:40', '0:0:0:0:0:0:0:1', '2024-08-15 23:42:40', 'chlgockd90', '2024-08-15 23:42:40',
        'chlgockd90', 'chlgockd90'),
       (5, '2024-08-15 23:49:08', '0:0:0:0:0:0:0:1', '2024-08-15 23:49:08', 'chlgockd90', '2024-08-15 23:49:08',
        'chlgockd90', 'chlgockd90'),
       (6, '2024-08-15 23:59:31', '0:0:0:0:0:0:0:1', '2024-08-15 23:59:31', 'chlgockd90', '2024-08-15 23:59:31',
        'chlgockd90', 'chlgockd90'),
       (7, '2024-08-15 23:59:48', '0:0:0:0:0:0:0:1', '2024-08-15 23:59:48', 'chlgockd90', '2024-08-15 23:59:48',
        'chlgockd90', 'chlgockd90'),
       (8, '2024-08-16 00:01:14', '0:0:0:0:0:0:0:1', '2024-08-16 00:01:14', 'chlgockd90', '2024-08-16 00:01:14',
        'chlgockd90', 'chlgockd90'),
       (9, '2024-08-16 00:01:21', '0:0:0:0:0:0:0:1', '2024-08-16 00:01:21', 'chlgockd90', '2024-08-16 00:01:21',
        'chlgockd90', 'chlgockd90'),
       (10, '2024-08-16 00:01:33', '0:0:0:0:0:0:0:1', '2024-08-16 00:01:33', 'chlgockd90', '2024-08-16 00:01:33',
        'chlgockd90', 'chlgockd90');
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`
(
    `id`             varchar(50)  NOT NULL,
    `pswd`           varchar(255) NOT NULL,
    `email`          varchar(255) NOT NULL,
    `phn_numb`       varchar(15)  DEFAULT NULL,
    `join_date`      datetime     DEFAULT NULL,
    `fail_logn_atmt` int          DEFAULT '0',
    `acnt_lock`      char(1)      DEFAULT 'N',
    `is_mail_vrfi`   char(1)      DEFAULT 'N',
    `mail_tokn`      varchar(255) DEFAULT NULL,
    `name`           varchar(255) DEFAULT NULL,
    `brth_date`      date         DEFAULT NULL,
    `is_acnt_actv`   char(1)      DEFAULT 'N',
    `ads_rece_mthd`  varchar(255) DEFAULT NULL,
    `grad`           varchar(50)  DEFAULT NULL,
    `is_canc`        char(1)      DEFAULT 'N',
    `reg_date`       datetime     DEFAULT NULL,
    `reg_id`         varchar(50)  DEFAULT NULL,
    `up_date`        datetime     DEFAULT NULL,
    `up_id`          varchar(50)  DEFAULT NULL,
    PRIMARY KEY (`id`)
);

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
INSERT INTO `member`
VALUES ('chlgockd90', 'GOAKgoak90@', 'buraldongja@naver.com', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL);
UNLOCK TABLES;

--
-- Table structure for table `shipping_address`
--

DROP TABLE IF EXISTS `shipping_address`;
CREATE TABLE `shipping_address`
(
    `id`         varchar(30) NOT NULL,
    `addr`       varchar(50) NOT NULL,
    `regi_addr1` varchar(50) DEFAULT NULL,
    `regi_addr2` varchar(50) DEFAULT NULL,
    `rcnt_addr1` varchar(50) DEFAULT NULL,
    `rcnt_addr2` varchar(50) DEFAULT NULL,
    `reg_date`   datetime    NOT NULL,
    `reg_id`     varchar(20) NOT NULL,
    `up_date`    datetime    NOT NULL,
    `up_id`      varchar(20) NOT NULL
);

--
-- Table structure for table `term`
--

DROP TABLE IF EXISTS `term`;
CREATE TABLE `term`
(
    `term_id`   int NOT NULL AUTO_INCREMENT,
    `term_name` varchar(255) DEFAULT NULL,
    `term_cont` text,
    `rqrd`      char(1)      DEFAULT NULL,
    `reg_date`  datetime     DEFAULT NULL,
    `reg_id`    varchar(50)  DEFAULT NULL,
    `up_date`   datetime     DEFAULT NULL,
    `up_id`     varchar(50)  DEFAULT NULL,
    PRIMARY KEY (`term_id`)
);


--
-- Dumping data for table `term`
--

LOCK TABLES `term` WRITE;
INSERT INTO `term`
VALUES (20, '약관 1', '내용 1', 'Y', '2024-08-16 18:47:06', 'admin', '2024-08-16 18:47:06', 'admin'),
       (21, '약관 2', '내용 2', 'N', '2024-08-16 18:47:06', 'admin', '2024-08-16 18:47:06', 'admin');
UNLOCK TABLES;

--
-- Table structure for table `term_agree`
--

DROP TABLE IF EXISTS `term_agree`;
CREATE TABLE `term_agree`
(
    `term_id`  int          NOT NULL,
    `is_agrd`  varchar(1)   DEFAULT NULL,
    `reg_date` datetime     DEFAULT NULL,
    `reg_id`   varchar(255) DEFAULT NULL,
    `up_date`  datetime     DEFAULT NULL,
    `up_id`    varchar(255) DEFAULT NULL,
    `id`       varchar(255) NOT NULL,
    PRIMARY KEY (`id`, `term_id`)
);

--
-- Dumping data for table `term_agree`
--

LOCK TABLES `term_agree` WRITE;
INSERT INTO `term_agree`
VALUES (1, 'Y', '2024-08-16 18:47:06', 'member1', '2024-08-16 18:47:06', 'member1', 'member1'),
       (1, 'Y', '2024-08-16 19:33:47', 'testUser', '2024-08-16 19:33:47', 'testUser', 'testUser'),
       (1, 'Y', '2024-08-16 19:33:46', 'testUser1', '2024-08-16 19:33:46', 'testUser1', 'testUser1'),
       (2, 'N', '2024-08-16 19:33:46', 'testUser2', '2024-08-16 19:33:46', 'testUser2', 'testUser2');
UNLOCK TABLES;

--
-- cart & order
--

-- cart
DROP TABLE IF EXISTS book_shop.cart;
CREATE TABLE `cart`
(
    `cart_seq`   int         NOT NULL AUTO_INCREMENT,
    `userId`     varchar(30) DEFAULT NULL,
    `reg_date`   datetime    NOT NULL,
    `reg_id`     varchar(20) NOT NULL,
    `up_date`    datetime    NOT NULL,
    `up_id`      varchar(20) NOT NULL,
    `created_at` varchar(50) NOT NULL,
    PRIMARY KEY (`cart_seq`)
);

-- cart_prod
DROP TABLE IF EXISTS book_shop.cart_prod;
CREATE TABLE `cart_prod`
(
    `cart_seq`       int          NOT NULL,
    `isbn`           varchar(30)  NOT NULL,
    `prod_type_code` varchar(255) NOT NULL COMMENT 'common code table',
    `item_quan`      int          NOT NULL,
    `reg_date`       datetime     NOT NULL,
    `reg_id`         varchar(20)  NOT NULL,
    `up_date`        datetime     NOT NULL,
    `up_id`          varchar(20)  NOT NULL,
    `created_at`     varchar(50)  NOT NULL,
    `updated_at`     varchar(50)  NOT NULL,
    CONSTRAINT `notzero` CHECK ((`item_quan` > 0))
);


-- order
DROP TABLE IF EXISTS book_shop.order;
CREATE TABLE `order` (
	`ord_seq`	int(15)	NOT NULL	COMMENT 'auto_increment',
	`ord_stat`	varchar(255)	NOT NULL	COMMENT 'common code table',
	`deli_stat`	varchar(255)	NOT NULL	COMMENT 'common code table',
	`pay_stat`	varchar(255)	NOT NULL	COMMENT 'common code table',
	`delivery_fee`	int(15)	NULL,
	`total_prod_pric`	int(15)	NULL,
	`total_disc_pric`	int(15)	NULL,
	`total_ord_pric`	int(15)	NULL,
	`created_at`	varchar(50)	NOT NULL,
	`updated_at`	varchar(50)	NOT NULL,
	`reg_date`	datetime	NOT NULL,
	`reg_id`	varchar(20)	NOT NULL,
	`up_date`	datetime	NOT NULL,
	`up_id`	varchar(20)	NOT NULL,
	`id`	varchar(30)	NOT NULL
);

-- order_prod
DROP TABLE IF EXISTS book_shop.order_prod;
CREATE TABLE `order_prod` (
	`ord_prod_num`	int(15)	NOT NULL,
	`ord_num`	varchar(100)	NOT NULL,
	`isbn`	varchar(30)	NOT NULL,
	`prod_type_code`	varchar(255)	NOT NULL	COMMENT 'common code table',
	`ord_stat`	varchar(255)	NOT NULL	COMMENT 'common code table',
	`deli_stat`	varchar(255)	NOT NULL	COMMENT 'common code table',
	`pay_stat`	varchar(255)	NOT NULL	COMMENT 'common code table',
	`book_title`	varchar(30)	NULL,
	`item_quan`	int(15)	NULL,
	`point_perc`	double(5,2)	NULL,
	`point_pric`	int(15)	NULL,
	`basic_pric`	int(15)	NULL,
	`disc_perc`	double	NULL,
	`disc_pric`	double	NULL,
	`sale_pric`	int(15)	NULL,
	`ord_pric`	int(15)	NULL,
	`img_seq`	int(10)	NULL,
	`reg_date`	datetime	NOT NULL,
	`reg_id`	varchar(20)	NOT NULL,
	`up_date`	datetime	NOT NULL,
	`up_id`	varchar(20)	NOT NULL,
	`updated_at`	varchar(50)	NULL,
	`created_at`	varchar(50)	NULL
);

-- order_history
DROP TABLE IF EXISTS book_shop.order_history;
CREATE TABLE `order_history` (
	`ord_hist_seq`	int(15)	NOT NULL,
	`ord_seq`	int(15)	NOT NULL	COMMENT 'auto_increment',
	`chg_start_date`	varchar(50)	NOT NULL,
	`chg_end_date`	varchar(50)	NOT NULL,
	`chg_reason`	varchar(2000)	NULL,
	`ord_stat`	int(15)	NULL	COMMENT 'common code table',
	`deli_stat`	int(15)	NULL	COMMENT 'common code table',
	`pay_stat`	int(15)	NULL	COMMENT 'common code table',
	`delivery_fee`	int(15)	NULL,
	`total_prod_pric`	int(15)	NULL,
	`total_disc_pric`	int(15)	NULL,
	`total_ord_pric`	int(15)	NULL,
	`created_at`	varchar(50)	NOT NULL,
	`updated_at`	varchar(50)	NOT NULL,
	`reg_date`	datetime	NOT NULL	COMMENT 'system column',
	`reg_id`	varchar(30)	NOT NULL	COMMENT 'system column',
	`up_date`	datetime	NOT NULL	COMMENT 'system column',
	`up_id`	varchar(30)	NOT NULL	COMMENT 'system column'
);

-- order_prod_status_history
DROP TABLE IF EXISTS book_shop.order_prod_status_history;
CREATE TABLE `order_prod_status_history` (
	`ord_prod_stat_hist_seq`	int(15)	NOT NULL,
	`ord_prod_num`	int(15)	NOT NULL,
	`chg_start_date`	varchar(50)	NOT NULL,
	`chg_end_date`	varchar(50)	NOT NULL,
	`ord_stat`	int(15)	NULL	COMMENT 'common code table',
	`deli_stat`	int(15)	NULL	COMMENT 'common code table',
	`pay_stat`	int(15)	NULL	COMMENT 'common code table',
	`reg_date`	datetime	NOT NULL,
	`reg_id`	varchar(20)	NOT NULL,
	`up_date`	datetime	NOT NULL,
	`up_id`	varchar(20)	NOT NULL
);

-- payment
DROP TABLE IF EXISTS book_shop.payment;
CREATE TABLE `payment` (
	`pay_seq`	int(15)	NOT NULL,
	`ord_seq`	int(15)	NOT NULL	COMMENT 'auto_increment',
	`pay_stat`	int(15)	NOT NULL	COMMENT 'common code table',
	`pay_key`	varchar(255)	NOT NULL	COMMENT 'toss data',
	`pay_method`	varchar(255)	NOT NULL	COMMENT 'toss data',
	`pay_summary`	varchar(255)	NULL,
	`total_pay_pric`	int(15)	NOT NULL,
	`requested_at`	varchar(50)	NULL	COMMENT 'toss data',
	`approved_at`	varchar(50)	NULL	COMMENT 'toss data',
	`created_at`	varchar(50)	NOT NULL,
	`req_date`	varchar(50)	NULL,
	`reg_date`	varchar(50)	NOT NULL	COMMENT 'system column',
	`reg_id`	varchar(30)	NOT NULL	COMMENT 'system column',
	`up_date`	varchar(50)	NOT NULL	COMMENT 'system column',
	`up_id`	varchar(30)	NOT NULL	COMMENT 'system column'
);

-- payment_history
DROP TABLE IF EXISTS book_shop.payment_history;
CREATE TABLE `payment_history` (
	`pay_hist_seq`	int(15)	NOT NULL,
	`pay_seq`	int(15)	NOT NULL,
	`ord_seq`	int(15)	NOT NULL	COMMENT 'auto_increment',
	`chg_start_date`	varchar(50)	NOT NULL,
	`chg_end_date`	varchar(50)	NOT NULL,
	`chg_reason`	varchar(2000)	NULL,
	`chg_id`	varchar(30)	NULL,
	`pay_stat`	int(15)	NOT NULL	COMMENT 'common code table',
	`pay_key`	varchar(255)	NULL	COMMENT 'toss data',
	`pay_method`	varchar(255)	NOT NULL	COMMENT 'toss data',
	`pay_summary`	varchar(255)	NULL,
	`total_pay_pric`	int(15)	NOT NULL,
	`requested_at`	varchar(50)	NULL	COMMENT 'toss data',
	`approved_at`	varchar(50)	NULL	COMMENT 'toss data',
	`created_at`	varchar(50)	NOT NULL,
	`req_date`	varchar(50)	NULL,
	`reg_date`	varchar(50)	NOT NULL	COMMENT 'system column',
	`reg_id`	varchar(30)	NOT NULL	COMMENT 'system column',
	`up_date`	varchar(50)	NOT NULL	COMMENT 'system column',
	`up_id`	varchar(30)	NOT NULL	COMMENT 'system column'
);



