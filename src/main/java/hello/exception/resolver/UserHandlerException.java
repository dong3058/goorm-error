package hello.exception.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.exception.exception2.UserException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserHandlerException implements HandlerExceptionResolver {
    private final ObjectMapper mapper=new ObjectMapper();
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
       try{
           log.info("usehandler resolver 호출됨");
           if(ex instanceof UserException){
               log.info("usexception resolver to 400");
               String accpetheader=request.getHeader("accept");
               response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
               if("application/json".equals(accpetheader)){
                   Map<String,Object> errresult=new HashMap<>();
                   errresult.put("ex",ex.getClass());
                   errresult.put("message",ex.getMessage());
                   String result=mapper.writeValueAsString(errresult);

                   response.setContentType("application/json");
                   response.setContentType("utf-8");
                   response.getWriter().write(result);

                   return new ModelAndView();
                   //was에 정상흐름으로 리턴되므로 오류페이지를 찾는 그런 번거로운 과정이 사라짐.

                   //직접 url에다가 api/members/users를 입력하면 500에러페이지 화면이 나오긴한다.
                   //이는 url에서 직접 요청이 text/html을 accpet으로받기떄문인듯.
               }else{
                   return new ModelAndView("error/500");
               }
           }

       }
       catch(IOException e){
           log.info("resolve{}",e);
       }
       return null;
    }
}
