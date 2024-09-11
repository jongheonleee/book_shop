package com.fastcampus.ch4.service.member;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface MemberDetailService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
