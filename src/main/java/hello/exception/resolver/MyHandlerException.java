package hello.exception.resolver;


import hello.exception.exception2.UserException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

//exception resolver?
//기존의 컨트롤러(핸들러)에서 오류발생시 posthandle이 스킵되고
//was로 다이렉트로 에러가전달된다.(당연히 model,view콜도 없어짐)
//그러나 exception resolver는 posthanle 대신에 오류에대해서
// 오류를 해결하려고 시도를하고 시도가되면 정상적인 처리흐름이 실행되게 만드는애다.
//참고로 반환 타입은 modeland view이다.
@Slf4j
public class MyHandlerException implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserException user=new UserException();
        RuntimeException run= new RuntimeException();
        try{
            log.info("리졸버 excpetion 호출됨");
            log.info("ex={}",ex);
            log.info("{}",run instanceof  IllegalArgumentException);
        if(ex instanceof IllegalArgumentException){

            log.info("IllegalargumentException resolver to 400");
            //SEND ERROR는 정수를 받는대 HttpSerbletresp는 정수 변수를 가지고있고
            //그게 아래의것이다. httpstatus는 enumㅇ다.
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,ex.getMessage());
            //정상 return을 햇으므로 에러를 먹어버림.
            //그러면 정상흐름으로 리턴되고 was는 400에러인걸확인한다.
            return new ModelAndView();//파라미터를 안넣었으므로 정상적 흐름으로 리턴이된다.
            // 즉 was로 흐름이 흘러감. 정상 흐름으로 리턴되므로 다시 기존의 오류페이지를 호출하는 과정을 거치지않음.
            //빈 모델 앤뷰이므로 뷰랜더링은 하지않음(model and view에다가 값을넣어서
            // 뷰가 랜더링되기할수도있다. 이럴경우 에러페이지가 뜨지않고 커스텀한 페이지가뜨는듯.)


            //불편한점. api응답에는 view가 필요치안흐며
            //또한 json을 response에 직접 넣어주고있다.

        }}
        //페이지 처리말고도 응답의 바디에다가 직접 값을 넣는(json)같은것도된ㄴ다.
        catch(IOException e){
            log.error("ex resolver {}",e);

        }
        return null;
        //null반환시 다음 리졸버(여러개를 등록이가능하다)를 찾고 그래도 해결이안ㄷ면
        //그냥 에러를던지고 코드 500으로 처리하게만듬.

        //이 리졸버는 모든 오류에대해서 호출이되는대 즉 senderror말고 진짜 오류들에 대해서 반응이하는대
        //runtimeexception은 해당되는게 없으므로 null을 돌려주고 위에 써진것처럼
        //was로 가서 코드500으로 처리하게만든다.
     }
}
