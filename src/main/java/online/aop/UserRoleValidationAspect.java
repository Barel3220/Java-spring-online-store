package online.aop;

import java.util.Optional;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import online.dao.AdvancedUserDao;
import online.data.UserEntity;
import online.data.UserRole;

import org.springframework.beans.factory.annotation.Autowired;

@Component
@Aspect
public class UserRoleValidationAspect {
	private AdvancedUserDao advancedUserDao;
	
	@Autowired
	public void setAdvancedUserDao(AdvancedUserDao advancedUserDao) {
		this.advancedUserDao = advancedUserDao;
	}
	
	@Around("@annotation(online.aop.UserRoleValidation) && args(onlineStore, creatorEmail, role, ..)")
	public Object validateRole(ProceedingJoinPoint pjp, String onlineStore, String creatorEmail, String role) throws Throwable {
		Optional<UserEntity> realEntity = this.advancedUserDao.readById(onlineStore + "#" + creatorEmail);
		Object[] arguments = pjp.getArgs();
		
		if (!realEntity.isPresent())  
			arguments[2] = "null";
		else if (realEntity.get().getRole() == UserRole.ADMIN)
			arguments[2] = "admin";
		else if (realEntity.get().getRole() == UserRole.MANAGER)
			arguments[2] = "manager";
		else if (realEntity.get().getRole() == UserRole.CUSTOMER)
			arguments[2] = "customer";
		else
			arguments[2] = "nothing";
		
		try {
			Object real = pjp.proceed(arguments);
			return real;
		} catch (Throwable e) {
			throw e;
		}
	}
}
