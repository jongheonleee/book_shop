-- 생성한 시퀀스(테이블)의 다음 값을 가져오는 함수
delimiter $$
    create function `nextval` (the_name VARCHAR(32))
    RETURNS BIGINT unsigned
    MODIFIES SQL DATA
    Deterministic
    begin
        declare ret BIGINT unsigned;
        update SEQUENCES set currval = currval +1 where name = the_name;
        select currval into ret from SEQUENCES where name = the_name limit 1;
        return ret;
    end;
    