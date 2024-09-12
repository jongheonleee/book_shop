DROP TABLE IF EXISTS notice_cate;

CREATE TABLE notice_cate (
    ntc_cate_id INT PRIMARY KEY AUTO_INCREMENT,  -- Primary key with auto-increment
    ntc_cate_code VARCHAR(20) NOT NULL,          -- Category code, VARCHAR(20)
    name VARCHAR(20) NOT NULL,                   -- Name, VARCHAR(20)
    use_chk CHAR(1) NOT NULL DEFAULT 'Y',        -- Use check, CHAR(1) with default 'Y'
    reg_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- Registration date, default current timestamp
    reg_id VARCHAR(20) NOT NULL,                 -- Registered by, VARCHAR(20)
    up_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- Updated date, default current timestamp, auto-updated
    up_id VARCHAR(20) NOT NULL                   -- Updated by, VARCHAR(20)
);

DROP TABLE IF EXISTS notice;

CREATE TABLE notice (
    ntc_seq INT PRIMARY KEY AUTO_INCREMENT,     -- Primary key with auto-increment
    ntc_cate_id INT NOT NULL,                   -- Category ID, INT, NOT NULL (Foreign key can be added if needed)
    title VARCHAR(100) NOT NULL,                -- Title, VARCHAR(100), NOT NULL
    cont LONGTEXT,                              -- Content, LONGTEXT, allows NULL
    pin_top_chk CHAR(1) NOT NULL DEFAULT 'N',   -- Pin to top check, CHAR(1), default 'N'
    pin_top_end_date VARCHAR(50) DEFAULT NULL,  -- Pin to top end date, VARCHAR(50), allows NULL
    dspl_chk CHAR(1) NOT NULL DEFAULT 'Y',      -- Display check, CHAR(1), default 'Y'
    view_cnt INT NOT NULL DEFAULT 0,            -- View count, INT, default 0
    reg_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- Registration date, default current timestamp
    reg_id VARCHAR(20) NOT NULL,                -- Registered by, VARCHAR(20)
    up_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- Updated date, auto-updated
    up_id VARCHAR(20) NOT NULL                  -- Updated by, VARCHAR(20)
);