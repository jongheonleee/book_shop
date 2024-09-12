package com.fastcampus.ch4.aspect;

import com.fastcampus.ch4.domain.notice.ErrorMessage;
import com.fastcampus.ch4.dto.notice.NoticeDto;
import com.fastcampus.ch4.exception.admin.UnauthorizedAccessException;
import com.fastcampus.ch4.service.admin.AdminService;
import com.fastcampus.ch4.service.notice.NoticeServiceImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import static com.fastcampus.ch4.domain.notice.ErrorMessage.*;

@Aspect
@Component
public class AdminCheckAspect {
    @Around("execution(* com.fastcampus.ch4.service.notice.NoticeServiceImpl.removeAll(..)))")
    public Object methodRemoveAllCheckAdmin(ProceedingJoinPoint joinPoint) throws Throwable {
        String id = (String) joinPoint.getArgs()[0];

        return checkAdmin(joinPoint, id);
    }

    @Around("execution(* com.fastcampus.ch4.service.notice.NoticeServiceImpl.write(..)))")
    public Object methodWriteCheckAdmin(ProceedingJoinPoint joinPoint) throws Throwable {
        NoticeDto noticeDto = (NoticeDto) joinPoint.getArgs()[0];
        String id = noticeDto.getReg_id();

        return checkAdmin(joinPoint, id);
    }

    private Object checkAdmin(ProceedingJoinPoint joinPoint, String id) throws Throwable {
        NoticeServiceImpl target = (NoticeServiceImpl) joinPoint.getTarget();
        AdminService adminService = target.getAdminService();

        if (!adminService.isAdmin(id)) {
            throw new UnauthorizedAccessException(ADMIN_LOGIN_REQUIRED.getMsg());
        }

        return joinPoint.proceed();
    }
}
