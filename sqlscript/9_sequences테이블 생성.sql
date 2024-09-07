-- 시퀀스로 사용할 테이블
-- create TABLE SEQUENCES(
--     name varchar(32),
--     currval BIGINT unsigned
-- )
-- engine = innoDB;


call create_sequence('disc_seq');
call create_sequence('wr_seq');
call create_sequence('trl_seq');