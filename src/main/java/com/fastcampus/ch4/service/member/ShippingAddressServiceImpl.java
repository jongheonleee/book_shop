package com.fastcampus.ch4.service.member;

import com.fastcampus.ch4.dao.member.ShippingAddressDao;
import com.fastcampus.ch4.dto.member.ShippingAddressDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShippingAddressServiceImpl implements ShippingAddressService {

  private static final Logger logger = LoggerFactory.getLogger(ShippingAddressServiceImpl.class);

  @Autowired
  private ShippingAddressDao shippingAddressDao;

  @Override
  @Transactional
  public void addAddress(ShippingAddressDto address) {
    logger.info("Adding shipping address for user ID: {}, Address: {}", address.getUserId(), address.getAddress());
    shippingAddressDao.insertAddress(address);
    logger.info("Shipping address added for user ID: {}", address.getUserId());
  }

  public void updateAddress(ShippingAddressDto address) {
    logger.info("Updating shipping address for user ID: {}, Address: {}", address.getUserId(), address.getAddress());

    // 새 주소가 null이 아닌 경우에만 업데이트
    if (address.getAddress() != null && !address.getAddress().trim().isEmpty()) {
      shippingAddressDao.updateAddress(address);
      logger.info("Shipping address updated for user ID: {}", address.getUserId());
    } else {
      logger.info("No address change for user ID: {}", address.getUserId());
    }
  }

  @Override
  public ShippingAddressDto getAddressById(String userId) {
    logger.info("Fetching shipping address for user ID: {}", userId);
    return shippingAddressDao.selectAddressById(userId);
  }

  @Override
  public List<ShippingAddressDto> getAllAddresses() {
    return shippingAddressDao.selectAllShippingAddresses();
  }

  @Override
  @Transactional
  public void deleteAddress(String userId) {
    logger.info("Deleting shipping address for user ID: {}", userId);
    shippingAddressDao.deleteAddress(userId);
    logger.info("Shipping address deleted for user ID: {}", userId);
  }

  @Override
  @Transactional
  public void deleteAllAddresses() {
    logger.info("Deleting all shipping addresses");
    shippingAddressDao.deleteAllShippingAddresses();
  }
}