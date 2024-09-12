DELIMITER $$

CREATE PROCEDURE InsertDummyBooks()
BEGIN
    DECLARE i INT DEFAULT 0;
    DECLARE isbn_val VARCHAR(13);
    DECLARE cate_num_val VARCHAR(30);
    DECLARE title_val VARCHAR(30);
    DECLARE papr_pric_val INT;
    DECLARE pub_name_val VARCHAR(30);
    DECLARE e_pric_val INT;
    DECLARE pub_date_val DATETIME;
    DECLARE tot_page_num_val INT;
    DECLARE tot_book_num_val INT;
    DECLARE papr_point_val DOUBLE(5,2);
    DECLARE e_point_val DOUBLE(5,2);
    DECLARE rating_val DOUBLE(5,2);
    DECLARE sale_vol_val INT;
    DECLARE pre_start_page_val INT;
    DECLARE pre_end_page_val INT;
    DECLARE ebook_url_val VARCHAR(100);
    DECLARE book_reg_date_val DATETIME;
    DECLARE regi_id_val VARCHAR(30);
    DECLARE reg_id_val VARCHAR(20);
    DECLARE up_id_val VARCHAR(20);

    -- 새로 추가된 필드 선언
    DECLARE sale_com_val VARCHAR(100);
    DECLARE cont_val VARCHAR(100);
    DECLARE info_val VARCHAR(100);
    DECLARE intro_award_val VARCHAR(100);
    DECLARE rec_val VARCHAR(100);
    DECLARE pub_review_val VARCHAR(100);

    -- cate_num 목록 설정
    DECLARE cate_num_list VARCHAR(30);
    DECLARE cate_num_count INT DEFAULT 56;

    DECLARE cate_nums CURSOR FOR 
        SELECT cate_num FROM (
            SELECT '01' AS cate_num UNION ALL
            SELECT '0101' UNION ALL
            SELECT '010101' UNION ALL
            SELECT '010102' UNION ALL
            SELECT '010103' UNION ALL
            SELECT '010104' UNION ALL
            SELECT '010105' UNION ALL
            SELECT '0102' UNION ALL
            SELECT '010201' UNION ALL
            SELECT '010202' UNION ALL
            SELECT '010203' UNION ALL
            SELECT '010204' UNION ALL
            SELECT '010205' UNION ALL
            SELECT '0103' UNION ALL
            SELECT '010301' UNION ALL
            SELECT '010302' UNION ALL
            SELECT '010303' UNION ALL
            SELECT '010304' UNION ALL
            SELECT '010305' UNION ALL
            SELECT '02' UNION ALL
            SELECT '0201' UNION ALL
            SELECT '020101' UNION ALL
            SELECT '020102' UNION ALL
            SELECT '020103' UNION ALL
            SELECT '0202' UNION ALL
            SELECT '020201' UNION ALL
            SELECT '020202' UNION ALL
            SELECT '020203' UNION ALL
            SELECT '0203' UNION ALL
            SELECT '020301' UNION ALL
            SELECT '020302' UNION ALL
            SELECT '020303' UNION ALL
            SELECT '03' UNION ALL
            SELECT '0301' UNION ALL
            SELECT '030101' UNION ALL
            SELECT '030102' UNION ALL
            SELECT '030103' UNION ALL
            SELECT '0302' UNION ALL
            SELECT '030201' UNION ALL
            SELECT '030202' UNION ALL
            SELECT '03020201' UNION ALL
            SELECT '03020202' UNION ALL
            SELECT '03020203' UNION ALL
            SELECT '030203' UNION ALL
            SELECT '03020301' UNION ALL
            SELECT '03020302' UNION ALL
            SELECT '03020303' UNION ALL
            SELECT '0303' UNION ALL
            SELECT '030301' UNION ALL
            SELECT '030302' UNION ALL
            SELECT '03030201' UNION ALL
            SELECT '03030202' UNION ALL
            SELECT '03030203' UNION ALL
            SELECT '030303' UNION ALL
            SELECT '03030301' UNION ALL
            SELECT '03030302' UNION ALL
            SELECT '03030303'
        ) AS cate_nums;

    OPEN cate_nums;

    -- 500개의 더미 데이터 삽입
    WHILE i < 500 DO
        -- isbn 생성 (순차적으로 증가하는 13자리 숫자 문자열)
        SET isbn_val = LPAD(1000000000000 + i, 13, '0');

        -- cate_num 값을 랜덤하게 선택
        SET cate_num_val = '';
        FETCH cate_nums INTO cate_num_val;
        IF i MOD cate_num_count = 0 THEN
            CLOSE cate_nums;
            OPEN cate_nums;
        END IF;

        -- title, pub_name, ebook_url 생성
        SET title_val = CONCAT('Title_', i + 1);
        SET pub_name_val = CONCAT('Publisher_', FLOOR(RAND() * 100) + 1);
        SET ebook_url_val = CONCAT('http://ebook.com/', LPAD(FLOOR(RAND() * 1000000), 6, '0'));

        -- 가격 생성 (천단위에서 만단위, 백원이하 0)
        SET papr_pric_val = (FLOOR(RAND() * 90) + 1) * 1000;
        SET e_pric_val = (FLOOR(RAND() * 90) + 1) * 1000;

        -- 포인트 생성 (5~10 정수값)
        SET papr_point_val = FLOOR(RAND() * 6) + 5;
        SET e_point_val = FLOOR(RAND() * 6) + 5;

        -- rating 생성 (0~10 사이, 소수점 첫째자리까지)
        SET rating_val = ROUND(RAND() * 10, 1);

        -- 기타 숫자값 생성
        SET tot_page_num_val = FLOOR(RAND() * 900) + 100; -- 100~1000 페이지
        SET tot_book_num_val = FLOOR(RAND() * 5) + 1; -- 1~100 권
        SET sale_vol_val = FLOOR(RAND() * 1000); -- 0~999 판매량

        -- 날짜 생성
        SET pub_date_val = NOW() - INTERVAL FLOOR(RAND() * 1000) DAY;
        SET book_reg_date_val = pub_date_val + INTERVAL FLOOR(RAND() * 365) DAY;

        -- pre_start_page와 pre_end_page 생성
        SET pre_start_page_val = FLOOR(RAND() * (tot_page_num_val - 20)) + 1;
        SET pre_end_page_val = pre_start_page_val + FLOOR(RAND() * 20) + 1;

        -- 등록자, 수정자 ID 생성
        SET regi_id_val = CONCAT('User_', FLOOR(RAND() * 100) + 1);
        SET reg_id_val = CONCAT('Reg_', FLOOR(RAND() * 100) + 1);
        SET up_id_val = CONCAT('Up_', FLOOR(RAND() * 100) + 1);

        -- sale_stat 설정 (판매중이 95% 확률로 설정)
        SET @sale_stat_val = IF(FLOOR(RAND() * 100) < 5, '판매중지', '판매중');

        -- 새로 추가된 필드 값 설정
        SET sale_com_val = CONCAT('sale_com', i);
        SET cont_val = CONCAT('cont', i);
        SET info_val = CONCAT('info', i);
        SET intro_award_val = CONCAT('intro_award', i);
        SET rec_val = CONCAT('rec', i);
        SET pub_review_val = CONCAT('pub_review', i);

        -- 데이터 삽입
        INSERT INTO book (
            isbn,
            cate_num,
            title,
            papr_pric,
            pub_name,
            e_pric,
            pub_date,
            tot_page_num,
            tot_book_num,
            papr_point,
            e_point,
            rating,
            sale_vol,
            pre_start_page,
            pre_end_page,
            ebook_url,
            book_reg_date,
            regi_id,
            reg_id,
            up_id,
            sale_stat,
            sale_com,
            cont,
            info,
            intro_award,
            rec,
            pub_review
        )
        VALUES (
            isbn_val,
            cate_num_val,
            title_val,
            papr_pric_val,
            pub_name_val,
            e_pric_val,
            pub_date_val,
            tot_page_num_val,
            tot_book_num_val,
            papr_point_val,
            e_point_val,
            rating_val,
            sale_vol_val,
            pre_start_page_val,
            pre_end_page_val,
            ebook_url_val,
            book_reg_date_val,
            regi_id_val,
            reg_id_val,
            up_id_val,
            @sale_stat_val,
            sale_com_val,
            cont_val,
            info_val,
            intro_award_val,
            rec_val,
            pub_review_val
        );

        SET i = i + 1;
    END WHILE;

    CLOSE cate_nums;
END$$

DELIMITER ;

DROP PROCEDURE IF EXISTS InsertDummyBooks;
delete from book;
CALL InsertDummyBooks();