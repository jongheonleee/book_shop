INSERT INTO book_shop.code (
    cate_num, code, code_name, ord, chk_use, comt, reg_date, reg_id, up_date, up_id
) VALUES
    (80, 'ord-stat-01', '주문 대기', 1, 'Y', NULL, NOW(), 'admin', NOW(), 'admin'),
    (80, 'ord-stat-02', '주문 완료', 2, 'Y', NULL, NOW(), 'admin', NOW(), 'admin'),
    (80, 'ord-stat-03', '주문 취소', 3, 'Y', NULL, NOW(), 'admin', NOW(), 'admin'),
    
    (81, 'deli-stat-01', '배송 대기', 1, 'Y', NULL, NOW(), 'admin', NOW(), 'admin'),
    (81, 'deli-stat-02', '배송 준비중', 2, 'Y', NULL, NOW(), 'admin', NOW(), 'admin'),
    (81, 'deli-stat-03', '배송 중', 3, 'Y', NULL, NOW(), 'admin', NOW(), 'admin'),
    (81, 'deli-stat-04', '배송 완료', 4, 'Y', NULL, NOW(), 'admin', NOW(), 'admin'),
    
    (82, 'cancle-stat-01', '취소 신청', 1, 'Y', NULL, NOW(), 'admin', NOW(), 'admin'),
    (82, 'cancle-stat-02', '취소 완료', 2, 'Y', NULL, NOW(), 'admin', NOW(), 'admin'),
    
    (83, 'refund-stat-01', '반품 신청', 1, 'Y', NULL, NOW(), 'admin', NOW(), 'admin'),
    (83, 'refund-stat-02', '반품 승인', 2, 'Y', NULL, NOW(), 'admin', NOW(), 'admin'),
    (83, 'refund-stat-03', '반품 완료', 3, 'Y', NULL, NOW(), 'admin', NOW(), 'admin'),
    
    (90, 'pay-stat-01', '결제 대기', 1, 'Y', NULL, NOW(), 'admin', NOW(), 'admin'),
    (90, 'pay-stat-02', '결제 실패', 2, 'Y', NULL, NOW(), 'admin', NOW(), 'admin'),
    (90, 'pay-stat-03', '결제 완료', 3, 'Y', NULL, NOW(), 'admin', NOW(), 'admin'),
    (90, 'pay-stat-04', '결제 취소', 4, 'Y', NULL, NOW(), 'admin', NOW(), 'admin');