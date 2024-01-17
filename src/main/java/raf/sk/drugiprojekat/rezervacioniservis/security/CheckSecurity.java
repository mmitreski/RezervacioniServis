package raf.sk.drugiprojekat.rezervacioniservis.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckSecurity {
    String[] roles() default {};
    boolean client_id_required() default false;
    boolean admin_id_required() default false;
    boolean manager_id_required() default false;
}
