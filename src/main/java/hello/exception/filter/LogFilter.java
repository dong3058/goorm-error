package hello.exception.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("serveltfilter");
    }

    @Override
    public void destroy() {
        log.info("destroy");
    }

    @Override
    //http 요청이오면 dofilter가 실행. 사실 serveltresquest는 http뿐만아니라
    //다른것에도 반응이 된다.
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("do");

        HttpServletRequest req=(HttpServletRequest) servletRequest;
        //servletrequest가 httpservelt의 부모임.
        String requri=req.getRequestURI();

        String uuid= UUID.randomUUID().toString();//http요청을 각각 구분하기위해서.

        try{
            log.info("Request [{}] [{}] [{}]",uuid,req.getDispatcherType(),requri);
            filterChain.doFilter(servletRequest,servletResponse);//다음필터잇으면 개부르고
            //없으면 서브릿 호추.
            //이게 제일중요하다. 다음 필터를 부르던가 없으면 서블릿을 부르는등
            //다음단계가 진행될려면 이게잇어야시작됨.

        }
        catch(Exception e){
            throw e;
        }
        finally{
            log.info("Response [{}][{}][{}]",uuid,req.getDispatcherType(),requri);
        }



    }
}
