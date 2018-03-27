package club.zhcs.thunder.ext.shiro.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.shiro.authz.annotation.Logical;

import club.zhcs.thunder.vo.InstalledRole;

/**
 * 
 * @author kerbores@gmail.com
 *
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SINORequiresRoles {
	Logical logical() default Logical.AND;

	InstalledRole[] value();
}
