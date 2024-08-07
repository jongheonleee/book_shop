package com.fastcampus.ch4.dao.qa;

import com.fastcampus.ch4.dto.qa.QaDto;
import com.fastcampus.ch4.dto.qa.SearchCondition;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QaDaoImp implements QaDao {

    @Autowired
    private SqlSession session;

    private static final String namespace = "com.fastcampus.ch4.QaMapper.";


    @Override
    public int count(String user_id) {
        return session.selectOne(namespace + "count", user_id);
    }

    public QaDto select(int qa_num) {
        return session.selectOne(namespace + "select", qa_num);
    }

    // 사용자 문의 등록시 발생하는 예외 세분화해서 재정의 반환
    @Override
    public int insert(QaDto dto) {
        return session.insert(namespace + "insert", dto);
    }

    @Override
    public List<QaDto> selectByUserId(String user_id) {
        return session.selectList(namespace + "selectByUserId", user_id);
    }

    public List<QaDto> selectByUserIdAndPh(String user_id, SearchCondition sc) {
        Map map = new HashMap();

        map.put("user_id", user_id);
        map.put("offSet", (sc.getPage() - 1) * sc.getPageSize());
        map.put("pageSize", sc.getPageSize());

        return session.selectList(namespace + "selectByUserIdAndPh", map);
    }

    @Override
    public QaDto selectForUpdate(QaDto dto) {
        return session.selectOne(namespace + "selectForUpdate", dto);
    }


    public List<QaDto> selectBySearchCondition(String user_id, SearchCondition sc) {
        Map map = new HashMap();

        map.put("user_id", user_id);
        map.put("option", sc.getOption());
        map.put("titleKeyword", sc.getTitleKeyword());
        map.put("period", sc.getPeriod());
        map.put("offSet", (sc.getPage() - 1) * sc.getPageSize());
        map.put("pageSize", sc.getPageSize());

        return session.selectList(namespace + "selectBySearchCondition", map);
    }

    @Override
    public int update(QaDto dto) {
        return session.update(namespace + "update", dto);
    }

    @Override
    public int delete(QaDto dto) {
        return session.delete(namespace + "delete", dto);
    }

    @Override
    public int deleteAll() {
        return session.delete(namespace + "deleteAll");
    }


}
