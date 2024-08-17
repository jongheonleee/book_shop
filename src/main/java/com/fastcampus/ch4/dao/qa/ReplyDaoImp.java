package com.fastcampus.ch4.dao.qa;


import com.fastcampus.ch4.dto.qa.ReplyDto;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ReplyDaoImp {

    @Autowired
    private SqlSession session;

    private static final String namespace = "com.fastcampus.ch4.ReplMapper.";

    public int insert(ReplyDto dto) {
        return session.insert(namespace + "insert", dto);
    }

    public int deleteAll() {
        return session.delete(namespace + "deleteAll");
    }

    public int count() {
        return session.selectOne(namespace + "count");
    }

    public ReplyDto select(int qa_num) {
        return session.selectOne(namespace + "select", qa_num);
    }

    public List<ReplyDto> selectAll() {
        return session.selectList(namespace + "selectAll");
    }

    public int delete(int qa_num) {
        return session.delete(namespace + "delete", qa_num);
    }

    public int update(ReplyDto dto) {
        return session.update(namespace + "update", dto);
    }


}
