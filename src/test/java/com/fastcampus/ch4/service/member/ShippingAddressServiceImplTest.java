//package com.fastcampus.ch4.service.member;
//
//import com.fastcampus.ch4.dao.member.ShippingAddressDao;
//import com.fastcampus.ch4.dto.member.ShippingAddressDto;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.UUID;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
//public class ShippingAddressServiceImplTest {
//
//    @Autowired
//    private ShippingAddressService shippingAddressService;
//
//    @Autowired
//    private ShippingAddressDao shippingAddressDao;
//
//    private String generateUniqueUserId() {
//        return "userTest_" + UUID.randomUUID().toString().substring(0, 8);
//    }
//
//    @Before
//    public void setUp() {
//        // 데이터베이스를 초기화합니다.
//        shippingAddressDao.deleteAllShippingAddresses();
//    }
//
//    private ShippingAddressDto createTestAddress(String userId, String addr) {
//        ShippingAddressDto address = new ShippingAddressDto();
//        address.setUserId(userId);
//        address.setAddress(addr); // 필수 주소 값 설정
//        address.setRegiAddress1("123 Main St");
//        address.setRegiAddress2("Apt 1");
//        address.setRcntAddress1("123 Main St");
//        address.setRcntAddress2("Apt 1");
//        address.setRegDate(LocalDateTime.now());
//        address.setRegId("admin");
//        address.setUpDate(LocalDateTime.now());
//        address.setUpId("admin");
//        return address;
//    }
//
//    @Test
//    public void testAddAddress() {
//        String uniqueUserId = generateUniqueUserId();
//        ShippingAddressDto address = createTestAddress(uniqueUserId, "123 Main St");
//
//        shippingAddressService.addAddress(address);
//
//        ShippingAddressDto retrievedAddress = shippingAddressDao.selectAddressById(uniqueUserId);
//        assertNotNull(retrievedAddress);
//        assertEquals("123 Main St", retrievedAddress.getRegiAddress1());
//    }
//
//    @Test
//    public void testUpdateAddress() {
//        String uniqueUserId = generateUniqueUserId();
//        ShippingAddressDto address = createTestAddress(uniqueUserId, "123 Main St");
//
//        shippingAddressService.addAddress(address);
//
//        address.setRegiAddress1("456 Elm St");
//        shippingAddressService.updateAddress(address);
//
//        ShippingAddressDto updatedAddress = shippingAddressDao.selectAddressById(uniqueUserId);
//        assertNotNull(updatedAddress);
//        assertEquals("456 Elm St", updatedAddress.getRegiAddress1());
//    }
//
//    @Test
//    public void testDeleteAddress() {
//        String uniqueUserId = generateUniqueUserId();
//        ShippingAddressDto address = createTestAddress(uniqueUserId, "123 Main St");
//
//        shippingAddressService.addAddress(address);
//        shippingAddressService.deleteAddress(uniqueUserId);
//
//        ShippingAddressDto deletedAddress = shippingAddressDao.selectAddressById(uniqueUserId);
//        assertNull(deletedAddress);
//    }
//
//    @Test
//    public void testGetAddressById() {
//        String uniqueUserId = generateUniqueUserId();
//        ShippingAddressDto address = createTestAddress(uniqueUserId, "123 Main St");
//
//        shippingAddressService.addAddress(address);
//
//        ShippingAddressDto retrievedAddress = shippingAddressService.getAddressById(uniqueUserId);
//        assertNotNull(retrievedAddress);
//        assertEquals("123 Main St", retrievedAddress.getRegiAddress1());
//    }
//
//    @Test
//    public void testGetAllAddresses() {
//        String uniqueUserId1 = generateUniqueUserId();
//        String uniqueUserId2 = generateUniqueUserId();
//        ShippingAddressDto address1 = createTestAddress(uniqueUserId1, "123 Main St");
//        ShippingAddressDto address2 = createTestAddress(uniqueUserId2, "789 Oak St");
//
//        shippingAddressService.addAddress(address1);
//        shippingAddressService.addAddress(address2);
//
//        List<ShippingAddressDto> addresses = shippingAddressService.getAllAddresses();
//        assertNotNull(addresses);
//        assertEquals(2, addresses.size());
//    }
//
//    @Test
//    public void testDeleteAllAddresses() {
//        String uniqueUserId1 = generateUniqueUserId();
//        String uniqueUserId2 = generateUniqueUserId();
//        ShippingAddressDto address1 = createTestAddress(uniqueUserId1, "123 Main St");
//        ShippingAddressDto address2 = createTestAddress(uniqueUserId2, "789 Oak St");
//
//        shippingAddressService.addAddress(address1);
//        shippingAddressService.addAddress(address2);
//
//        shippingAddressService.deleteAllAddresses();
//
//        List<ShippingAddressDto> addresses = shippingAddressService.getAllAddresses();
//        assertNotNull(addresses);
//        assertEquals(0, addresses.size());
//    }
//}
