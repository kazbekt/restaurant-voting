package ru.javaops.restaurantvoting.app.util;

import lombok.experimental.UtilityClass;
import org.hibernate.proxy.HibernateProxy;

@UtilityClass
public class HibernateProxyHelper {

    public static Class getClassWithoutInitializingProxy(Object object) {
        return (object instanceof HibernateProxy proxy) ?
                proxy.getHibernateLazyInitializer().getPersistentClass() : object.getClass();
    }
}
