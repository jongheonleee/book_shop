package com.fastcampus.ch4.service.member;

import com.fastcampus.ch4.dto.member.ShippingAddressDto;

import java.util.List;

public interface ShippingAddressService {
  void addAddress(ShippingAddressDto shippingAddress);
  void updateAddress(ShippingAddressDto shippingAddress);
  ShippingAddressDto getAddressById(String userId);
  List<ShippingAddressDto> getAllAddresses();
  void deleteAddress(String userId);
  void deleteAllAddresses();
}
