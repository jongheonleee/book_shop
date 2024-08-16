package com.fastcampus.ch4.dao.member;

import com.fastcampus.ch4.dto.member.ShippingAddressDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShippingAddressDaoImpl implements ShippingAddressDao {

  private final SqlSession sqlSession;

  @Autowired
  public ShippingAddressDaoImpl(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }

  @Override
  public void insertAddress(ShippingAddressDto address) {
    sqlSession.insert("com.fastcampus.ch4.dao.member.ShippingAddressDao.insertAddress", address);
  }

  @Override
  public void updateAddress(ShippingAddressDto address) {
    sqlSession.update("com.fastcampus.ch4.dao.member.ShippingAddressDao.updateAddress", address);
  }

  @Override
  public void deleteAddress(String userId) {
    sqlSession.delete("com.fastcampus.ch4.dao.member.ShippingAddressDao.deleteAddress", userId);
  }


  @Override
  public ShippingAddressDto selectAddressById(String userId) {
    return sqlSession.selectOne("com.fastcampus.ch4.dao.member.ShippingAddressDao.selectAddressById", userId);
  }

  @Override
  public List<ShippingAddressDto> selectAllShippingAddresses() {
    return sqlSession.selectList("com.fastcampus.ch4.dao.member.ShippingAddressDao.selectAllShippingAddresses");
  }

  @Override
  public void deleteAllShippingAddresses() {
    sqlSession.delete("com.fastcampus.ch4.dao.member.ShippingAddressDao.deleteAllShippingAddresses");
  }
}
