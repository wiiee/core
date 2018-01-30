package com.wiiee.core.web.interceptor;

import com.wiiee.core.platform.annotation.Layout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by wang.na on 2017/3/20.
 */
@Component
public class ThymeleafInterceptor extends HandlerInterceptorAdapter {
    private static final String DEFAULT_LAYOUT = "layout/default";
    private static final String DEFAULT_VIEW_ATTRIBUTE_NAME = "view";

    private String defaultLayout = DEFAULT_LAYOUT;
    private String viewAttributeName = DEFAULT_VIEW_ATTRIBUTE_NAME;

    @Autowired
    private Environment environment;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView == null || !modelAndView.hasView()) {
            return;
        }

        if(!isThymeleafView(handler)){
            return;
        }

        String originalViewName = modelAndView.getViewName();

        if (isRedirectOrForward(originalViewName)) {
            return;
        }

        String layoutName = getLayoutName(handler);

        if (Layout.NONE.equals(layoutName)) {
            return;
        }

        modelAndView.setViewName(layoutName);
        modelAndView.addObject(this.viewAttributeName, originalViewName);

        Map<String, Object> model = modelAndView.getModel();

        if(model != null && !model.containsKey("_isLocal")){
            model.put("_isLocal", environment.getActiveProfiles().length == 0);
        }
    }

    private boolean isThymeleafView(Object handler){
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;

            if (method.getBeanType().getPackage().getName().equals(this.getClass().getPackage().getName().replace(".interceptor", ".controller"))) {
                return true;
            }
        }

        if(handler instanceof ParameterizableViewController){
            return true;
        }

        return false;
    }

    private boolean isRedirectOrForward(String viewName) {
        return viewName.startsWith("redirect:") || viewName.startsWith("forward:");
    }

    private String getLayoutName(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Layout layout = getMethodOrTypeAnnotation(handlerMethod);

            if (layout != null) {
                return layout.value();
            }
        }

        return this.defaultLayout;
    }

    private Layout getMethodOrTypeAnnotation(HandlerMethod handlerMethod) {
        Layout layout = handlerMethod.getMethodAnnotation(Layout.class);

        if (layout == null) {
            return handlerMethod.getBeanType().getAnnotation(Layout.class);
        }

        return layout;
    }
}
