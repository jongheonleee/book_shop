package com.fastcampus.ch4.dao.member;

import com.fastcampus.ch4.dto.member.ShippingAddressDto;

import java.util.List;

public interface ShippingAddressDao {

  void insertAddress(ShippingAddressDto address);

  void updateAddress(ShippingAddressDto address);

  void deleteAddress(String userId);

  ShippingAddressDto selectAddressById(String userId);

  List<ShippingAddressDto> selectAllShippingAddresses();

  void deleteAllShippingAddresses();

}
