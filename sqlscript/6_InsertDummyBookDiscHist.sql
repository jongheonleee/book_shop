DELIMITER $$

CREATE PROCEDURE InsertDummyBookDiscHist()
BEGIN
    DECLARE i INT DEFAULT 0;
    DECLARE isbn_val VARCHAR(13);
    DECLARE papr_disc_val DOUBLE;
    DECLARE e_disc_val DOUBLE;
    DECLARE disc_start_date_val DATETIME;
    DECLARE disc_end_date_val DATETIME;
    DECLARE reg_date_val DATETIME;
    DECLARE reg_id_val VARCHAR(20);
    DECLARE up_id_val VARCHAR(20);
    DECLARE period_type INT;
    DECLARE disc_seq_val INT;

    -- isbn 목록 설정
    DECLARE isbn_start BIGINT DEFAULT 1000000000000;
    DECLARE isbn_end BIGINT DEFAULT 1000000000499;
    DECLARE total_isbns INT DEFAULT 500;
    
    -- 각 isbn에 대해 3개의 레코드를 생성
    WHILE i < 1500 DO
        -- isbn 값 설정 (순차적으로 증가하는 13자리 숫자 문자열)
        SET isbn_val = LPAD(isbn_start + (i DIV 3) MOD total_isbns, 13, '0');
        
        -- disc_seq 값 설정 (1, 2, 3 순서로 반복)
        SET disc_seq_val = (i MOD 3) + 1;

        -- 할인율 설정 (0~80 사이의 정수값을 갖는 실수)
        SET papr_disc_val = FLOOR(RAND() * 81); 
        SET e_disc_val = FLOOR(RAND() * 81);

        -- 기간 유형 설정 (0: 과거, 1: 현재 포함, 2: 미래)
        SET period_type = i MOD 3;

        IF period_type = 0 THEN
            -- 과거 기간 설정
            SET disc_start_date_val = NOW() - INTERVAL (FLOOR(RAND() * 365) + 365) DAY;
            SET disc_end_date_val = disc_start_date_val + INTERVAL (FLOOR(RAND() * 30) + 1) DAY;
        ELSEIF period_type = 1 THEN
            -- 현재 포함 기간 설정
            SET disc_start_date_val = NOW() - INTERVAL FLOOR(RAND() * 30) DAY;
            SET disc_end_date_val = NOW() + INTERVAL (FLOOR(RAND() * 30) + 1) DAY;
        ELSE
            -- 미래 기간 설정
            SET disc_start_date_val = NOW() + INTERVAL (FLOOR(RAND() * 30) + 1) DAY;
            SET disc_end_date_val = disc_start_date_val + INTERVAL (FLOOR(RAND() * 30) + 1) DAY;
        END IF;

        -- reg_date 설정 (disc_start_date보다 이전)
        SET reg_date_val = disc_start_date_val - INTERVAL (FLOOR(RAND() * 10) + 1) DAY;

        -- 등록자, 수정자 ID 생성
        SET reg_id_val = CONCAT('User_', FLOOR(RAND() * 100) + 1);
        SET up_id_val = CONCAT('Up_', FLOOR(RAND() * 100) + 1);

        -- 데이터 삽입
        INSERT INTO book_disc_hist (
            disc_seq,
            isbn,
            papr_disc,
            e_disc,
            disc_start_date,
            disc_end_date,
            comt,
            reg_date,
            reg_id,
            up_date,
            up_id
        )
        VALUES (
            disc_seq_val,
            isbn_val,
            papr_disc_val,
            e_disc_val,
            disc_start_date_val,
            disc_end_date_val,
            CONCAT('Comment_', i + 1),
            reg_date_val,
            reg_id_val,
            NOW(),
            up_id_val
        );

        SET i = i + 1;
    END WHILE;
END$$

DELIMITER ;


-- 기존 프로시저가 있으면 삭제
DROP PROCEDURE IF EXISTS InsertDummyBookDiscHist;
delete from book_disc_hist;
-- 프로시저 호출하여 1500개의 더미 데이터 삽입
CALL InsertDummyBookDiscHist();

call create_sequence('disc_seq');
