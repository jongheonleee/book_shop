DELIMITER $$

CREATE PROCEDURE InsertDummyContributors()
BEGIN
    DECLARE i INT DEFAULT 0;
    DECLARE isbn_val VARCHAR(13);
    DECLARE cb_num_val VARCHAR(30);
    DECLARE name_val VARCHAR(30);
    DECLARE job1_val VARCHAR(30);
    DECLARE job2_val VARCHAR(30);
    DECLARE cont_desc_val LONGTEXT;
    DECLARE wr_chk_val CHAR(1);
    DECLARE reg_id_val VARCHAR(20);
    DECLARE up_id_val VARCHAR(20);
    DECLARE total_books INT DEFAULT 500;
    DECLARE total_contributors INT DEFAULT 1500;
    
    DECLARE next_wr INT DEFAULT 1;
    DECLARE next_trl INT DEFAULT 1;

    -- 책 당 최소 1명의 작가를 추가
    WHILE i < total_books DO
        -- ISBN 값 생성
        SET isbn_val = LPAD(1000000000000 + i, 13, '0');

        -- 고유한 작가 cb_num 생성
        SET cb_num_val = CONCAT('wr', next_wr);
        SET next_wr = next_wr + 1;

        -- 작가 정보 생성
        SET name_val = CONCAT('Writer_', i + 1);
        SET job1_val = '작가';
        SET job2_val = NULL;
        SET cont_desc_val = CONCAT('작가 설명_', i + 1);
        SET wr_chk_val = 'Y';
        SET reg_id_val = CONCAT('Reg_', FLOOR(RAND() * 100) + 1);
        SET up_id_val = CONCAT('Up_', FLOOR(RAND() * 100) + 1);

        -- 기여자 테이블에 작가 정보 삽입
        INSERT INTO writing_contributor (
            cb_num, name, job1, job2, cont_desc, wr_chk, reg_date, reg_id, up_date, up_id
        )
        VALUES (
            cb_num_val, name_val, job1_val, job2_val, cont_desc_val, wr_chk_val, NOW(), reg_id_val, NOW(), up_id_val
        );

        -- 중간 테이블에 ISBN과 작가 정보 삽입 (중복 확인)
        IF NOT EXISTS (
            SELECT 1 FROM book_contributor WHERE isbn = isbn_val AND cb_num = cb_num_val
        ) THEN
            INSERT INTO book_contributor (isbn, cb_num) VALUES (isbn_val, cb_num_val);
        END IF;

        -- 추가 기여자 랜덤으로 추가 (번역가 등)
        IF RAND() < 0.8 THEN
            -- 고유한 번역가 cb_num 생성
            SET cb_num_val = CONCAT('trl', next_trl);
            SET next_trl = next_trl + 1;

            -- 번역가 정보 생성
            SET name_val = CONCAT('Translator_', next_trl);
            SET job1_val = '번역가';
            SET job2_val = NULL;
            SET cont_desc_val = CONCAT('번역가 설명_', next_trl);
            SET wr_chk_val = 'N';

            INSERT INTO writing_contributor (
                cb_num, name, job1, job2, cont_desc, wr_chk, reg_date, reg_id, up_date, up_id
            )
            VALUES (
                cb_num_val, name_val, job1_val, job2_val, cont_desc_val, wr_chk_val, NOW(), reg_id_val, NOW(), up_id_val
            );

            -- 중간 테이블에 ISBN과 번역가 정보 삽입 (중복 확인)
            IF NOT EXISTS (
                SELECT 1 FROM book_contributor WHERE isbn = isbn_val AND cb_num = cb_num_val
            ) THEN
                INSERT INTO book_contributor (isbn, cb_num) VALUES (isbn_val, cb_num_val);
            END IF;
        END IF;

        SET i = i + 1;
    END WHILE;

    -- 추가로 기여자들 임의로 생성 (총 1500명 기여자)
    WHILE i < total_contributors DO
        IF i < total_contributors / 2 THEN
            -- 작가 추가
            SET cb_num_val = CONCAT('wr', next_wr);
            SET next_wr = next_wr + 1;
            SET name_val = CONCAT('Writer_', next_wr);
            SET job1_val = '작가';
            SET wr_chk_val = 'Y';
        ELSE
            -- 번역가 추가
            SET cb_num_val = CONCAT('trl', next_trl);
            SET next_trl = next_trl + 1;
            SET name_val = CONCAT('Translator_', next_trl);
            SET job1_val = '번역가';
            SET wr_chk_val = 'N';
        END IF;

        SET job2_val = IF(i % 5 = 0, '편집자', NULL);
        SET cont_desc_val = CONCAT('기여자 설명_', i + 1);
        SET reg_id_val = CONCAT('Reg_', FLOOR(RAND() * 100) + 1);
        SET up_id_val = CONCAT('Up_', FLOOR(RAND() * 100) + 1);

        -- 기여자 테이블에 정보 삽입
        INSERT INTO writing_contributor (
            cb_num, name, job1, job2, cont_desc, wr_chk, reg_date, reg_id, up_date, up_id
        )
        VALUES (
            cb_num_val, name_val, job1_val, job2_val, cont_desc_val, wr_chk_val, NOW(), reg_id_val, NOW(), up_id_val
        );

        SET i = i + 1;
    END WHILE;
END$$

DELIMITER ;


DROP PROCEDURE IF EXISTS InsertDummyContributors;
delete from book_contributor;
delete from writing_contributor;
CALL InsertDummyContributors();