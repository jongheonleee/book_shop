package com.fastcampus.ch4.service.member;

import com.fastcampus.ch4.dao.member.ShippingAddressDao;
import com.fastcampus.ch4.dto.member.ShippingAddressDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShippingAddressServiceImpl implements ShippingAddressService {

  @Autowired
  private ShippingAddressDao shippingAddressDao;

  @Override
  @Transactional
  public void addAddress(ShippingAddressDto address) {
    // 새 주소를 추가
    shippingAddressDao.insertAddress(address);
  }

  @Override
  @Transactional
  public void updateAddress(ShippingAddressDto address) {
    shippingAddressDao.updateAddress(address);
  }

  @Override
  @Transactional
  public void deleteAddress(String userId) {
    shippingAddressDao.deleteAddress(userId);
  }



  @Override
  public ShippingAddressDto getAddressById(String userId) {
    return shippingAddressDao.selectAddressById(userId);
  }

  @Override
  public List<ShippingAddressDto> getAllAddresses() {
    return shippingAddressDao.selectAllShippingAddresses();
  }

  @Override
  @Transactional
  public void deleteAllAddresses() {
    shippingAddressDao.deleteAllShippingAddresses();
  }
}
