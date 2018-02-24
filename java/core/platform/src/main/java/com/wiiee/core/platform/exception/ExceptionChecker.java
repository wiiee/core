package com.wiiee.core.platform.exception;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//检查是否有重复的Exception
@Component
public class ExceptionChecker implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    private HashMap<Integer, MyException> exceptions;

    //重复的异常
    private List<String> duplicates;

    public ExceptionChecker() {
        this.exceptions = new HashMap<>();
        this.duplicates = new ArrayList<>();
    }

    @PostConstruct
    public void check() throws MyException {
        Map<String, BaseException> beans = applicationContext.getBeansOfType(BaseException.class);

        beans.forEach((key, value) -> {
            Field[] declaredFields = value.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                    try {
                        Object item = field.get(value);

                        if (item instanceof MyException) {
                            MyException myException = (MyException) item;

                            if (exceptions.containsKey(myException.errorCode)) {
                                duplicates.add(key + "." + field.getName());
                            } else {
                                exceptions.put(myException.errorCode, myException);
                            }
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        if (!CollectionUtils.isEmpty(duplicates)) {
            duplicates.forEach(o -> System.out.println("duplicate exception: " + duplicates));
            throw CoreException.EXCEPTION_SYSTEM;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
