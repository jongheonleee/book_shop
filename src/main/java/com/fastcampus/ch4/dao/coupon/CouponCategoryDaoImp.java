package com.fastcampus.ch4.dao.coupon;


import com.fastcampus.ch4.dto.coupon.CouponCategoryDto;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CouponCategoryDaoImp implements CouponCategoryDao {

    @Autowired
    private SqlSession session;

    private static final String namespace = "com.fastcampus.ch4.dao.coupon.CouponCategoryMapper.";

    @Override
    public int count() {
        return session.selectOne(namespace + "count");
    }

    public List<CouponCategoryDto> selectAll() {
        return session.selectList(namespace + "selectAll");
    }

    public List<CouponCategoryDto> selectAllByJoin() {
        return session.selectList(namespace + "selectAllByJoin");
    }

    public CouponCategoryDto select(String cate_code) {
        return session.selectOne(namespace + "select", cate_code);
    }

    public List<CouponCategoryDto> selectByCateCode(String cate_code) {
        return session.selectList(namespace + "selectByCateCode", cate_code);
    }

    public int update(CouponCategoryDto dto) {
        return session.update(namespace + "update", dto);
    }


    public int insert(CouponCategoryDto dto) {
        return session.insert(namespace + "insert", dto);
    }

    public int delete(String cate_code) {
        return session.delete(namespace + "delete", cate_code);
    }

    public int deleteAll() {
        return session.delete(namespace + "deleteAll");
    }

}
