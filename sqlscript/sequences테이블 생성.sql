-- 시퀀스로 사용할 테이블
create TABLE SEQUENCES(
    name varchar(32),
    currval BIGINT unsigned
)
engine = innoDB;


-- 시퀀스로 사용할 프로시저 생성
-- 'IN' 으로 시퀀스 명을 받음
-- call [프로시저명]('[시퀀스명]')
delimiter $$
    create procedure `create_sequence` (IN the_name text)
    modifies sql data
    deterministic
    begin
        delete from SEQUENCES where name = the_name;
        insert into SEQUENCES values(the_name, 0);
    end;


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
    
call create_sequence('disc_seq');
call create_sequence('wr_seq');
call create_sequence('trl_seq');