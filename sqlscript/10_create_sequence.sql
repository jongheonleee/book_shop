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