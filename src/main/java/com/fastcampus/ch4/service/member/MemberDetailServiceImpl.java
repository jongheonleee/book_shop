package com.fastcampus.ch4.service.member;

import com.fastcampus.ch4.dao.member.MemberDao;
import com.fastcampus.ch4.dto.member.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MemberDetailServiceImpl implements MemberDetailService, UserDetailsService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberDto member = memberDao.selectMemberById(username);
        if (member == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
                member.getId(),
                member.getPswd(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
