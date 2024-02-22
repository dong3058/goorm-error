package hello.exception.servelt;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class Errorpagecontroller {

    //RequestDispatcher에잇는거
    public static final String ERROR_EXCEPTION = "jakarta.servlet.error.exception";
    public static final String ERROR_EXCEPTION_TYPE = "jakarta.servlet.error.exception_type";
    public static final String ERROR_MESSAGE = "jakarta.servlet.error.message";
    public static final String ERROR_REQUEST_URI = "jakarta.servlet.error.request_uri";
    public static final String ERROR_SERVLET_NAME = "jakarta.servlet.error.servlet_name";
    public static final String ERROR_STATUS_CODE = "jakarta.servlet.error.status_code";

    //was가 저값을 key로 오류 내용들을 담아놓는다.

    @RequestMapping("/error-page/404")
    public String errorpage404(HttpServletRequest req, HttpServletResponse resp){
        log.info("errorpage 404");
        printerrorinfo(req);
        return "error-page/404";
    }
    @RequestMapping("/error-page/500")
    public String errorpage500(HttpServletRequest req, HttpServletResponse resp){
        log.info("errorpage 500");
        printerrorinfo(req);
        return "error-page/500";
    }


    //베이직 에러 컨트롤러가 처리하는 방식이 애랑 비슷하다.
    //베이직 에러컨트롤러의 error의 메서드가 이런방식이랑 비슷.
    @RequestMapping(value="/error-page/500",produces="application/json")
    //req의 accept이 application/json의 경우 우선권을 가짐.
    public ResponseEntity<Map<String ,Object>> errorpage500api(HttpServletRequest req, HttpServletResponse resp){
            log.info("api errorpage500");


            Map<String,Object> result=new HashMap<>();
            Exception ex=(Exception) req.getAttribute(ERROR_EXCEPTION);
            result.put("status",req.getAttribute(ERROR_STATUS_CODE));
            result.put("message",ex.getMessage());
            Integer statusCode=(Integer)req.getAttribute(ERROR_STATUS_CODE);
            return new ResponseEntity<>(result, HttpStatus.valueOf(statusCode));
            //그냥 특이하게 integer로 값을 가져온다. httpstatsus의 부모 클래스의
        //메서드로 valueof가 작성되잇ㄴㄴ대 그걸 쓰는거다.
        }

    private void printerrorinfo(HttpServletRequest req){

        log.info("ERROR_EXCEPTION:{}",req.getAttribute(ERROR_EXCEPTION));
        //error_exception은 자바 자체적인 에러종류들을 의미한느득함. 500,400에러는 안뜸이거.
        log.info("ERROR_EXCEPTION_TYPE:{}",req.getAttribute(ERROR_EXCEPTION_TYPE));
        log.info("ERROR_MESSAGE:{}",req.getAttribute(ERROR_MESSAGE));
        //senderror에담은 메시지를 의미한다..
        log.info("ERROR_REQUEST_URI:{}",req.getAttribute(ERROR_REQUEST_URI));
        log.info("ERROR_SERVLET_NAME:{}",req.getAttribute(ERROR_SERVLET_NAME));
        log.info("ERROR_STATUS_CODE:{}",req.getAttribute(ERROR_STATUS_CODE));
        //상태코드 자바오류는 500에 포함됨.

        //was 페이지는 오류페이지를 단순히 요청만 하는게아니라
        //오류 정보를 req의 attribute에 넘겨준다.
        //이러한 오류정보를 오류페이지에서 사용하는것이가능하다.

    }
}
