DELIMITER $$

CREATE PROCEDURE InsertDummyBookImages()
BEGIN
    DECLARE i INT DEFAULT 0;
    DECLARE isbn_val VARCHAR(13);
    DECLARE img_url_val VARCHAR(100);
    DECLARE img_hrzt_size_val INT;
    DECLARE img_vrtc_size_val INT;
    DECLARE img_file_format_val VARCHAR(30);
    DECLARE img_desc_val VARCHAR(100);
    DECLARE main_img_chk_val CHAR(1);
    DECLARE reg_id_val VARCHAR(20);
    DECLARE up_id_val VARCHAR(20);
    DECLARE img_seq_val INT;
    DECLARE current_isbn BIGINT;
    DECLARE current_isbn_img_count INT DEFAULT 0;
    
    -- 1500개의 더미 데이터 삽입
    WHILE i < 1500 DO
        -- 현재 ISBN 설정 (순차적으로 증가하는 13자리 숫자 문자열)
        SET current_isbn = (i DIV 3) + 1000000000000;
        SET isbn_val = LPAD(current_isbn, 13, '0');

        -- img_seq 값 설정 (1, 2, 3 순서로 반복)
        SET img_seq_val = (i MOD 3) + 1;

        -- img_url 생성 ('https://picsum.photos/id/{id}/200/300', id는 1부터 1500까지)
        SET img_url_val = CONCAT('https://picsum.photos/id/', i + 1, '/200/300');

        -- 기타 값 생성
        SET img_hrzt_size_val = FLOOR(RAND() * 1000) + 100; -- 100~1100
        SET img_vrtc_size_val = FLOOR(RAND() * 1000) + 100; -- 100~1100
        SET img_file_format_val = IF(RAND() < 0.5, 'jpg', 'png'); -- 'jpg' 또는 'png'
        SET img_desc_val = CONCAT('Image description for ISBN ', isbn_val);
        SET reg_id_val = CONCAT('Reg_', FLOOR(RAND() * 100) + 1);
        SET up_id_val = CONCAT('Up_', FLOOR(RAND() * 100) + 1);

        -- main_img_chk 값 설정 (현재 ISBN에 대해 첫 번째 이미지에만 'Y' 설정)
        IF current_isbn_img_count = 0 THEN
            SET main_img_chk_val = 'Y';
        ELSE
            SET main_img_chk_val = 'N';
        END IF;
        
        -- 데이터 삽입
        INSERT INTO book_image (
            img_seq,
            isbn,
            img_url,
            img_hrzt_size,
            img_vrtc_size,
            img_file_format,
            img_desc,
            main_img_chk,
            reg_id,
            up_id
        )
        VALUES (
            img_seq_val,
            isbn_val,
            img_url_val,
            img_hrzt_size_val,
            img_vrtc_size_val,
            img_file_format_val,
            img_desc_val,
            main_img_chk_val,
            reg_id_val,
            up_id_val
        );

        -- 현재 ISBN의 이미지 수 증가
        IF main_img_chk_val = 'Y' THEN
            SET current_isbn_img_count = current_isbn_img_count + 1;
        END IF;

        -- 모든 이미지를 처리한 후, current_isbn_img_count 초기화
        IF i MOD 3 = 2 THEN
            SET current_isbn_img_count = 0;
        END IF;

        SET i = i + 1;
    END WHILE;
END$$

DELIMITER ;



DROP PROCEDURE IF EXISTS InsertDummyBookImages;
delete from book_image;
CALL InsertDummyBookImages();