/**
 * rallen(wzf)
 */
package cn.game.rjserver.framework.response.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
//将在运行期也保留注释，因此可以通过反射机制读取注解的信息。
@Target(ElementType.TYPE)
// 类，接口（包括注解类型）或enum声明 
public @interface Cmd {

	int cmdid();

	String desc();

}