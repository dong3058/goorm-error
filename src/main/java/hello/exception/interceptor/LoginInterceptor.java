package hello.exception.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    public static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requri=request.getRequestURI();
        String uuid= UUID.randomUUID().toString();

        request.setAttribute(LOG_ID,uuid);//인터셉터도 싱글톤이기에 지역변수에담는건위험
        //그래서 request에다가 담음.

        //requestmapping은 handlermethod가 건너온다. 핸들러 정보거 여기에있다.
        if(handler instanceof HandlerMethod){
            HandlerMethod hn=(HandlerMethod) handler;//호룿랗ㄹ 컨트롤러 메서드의 모든정보가담김.대충 잉렇게
            //담긴다 생각하면됨.
        }
        log.info("REQUEST [{}] [{}] [{}] [{}]",uuid, request.getDispatcherType() ,requri,handler);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("posthandler {}", modelAndView);

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Object logId=request.getParameter(LOG_ID);
        String requri= request.getRequestURI();
        String uuid=(String) request.getAttribute(LOG_ID);
        log.info("response [{}] [{}] [{}]",logId, request.getDispatcherType(),requri);
        if(ex!=null){
            log.error("complication error",ex);
        }

    }
}
