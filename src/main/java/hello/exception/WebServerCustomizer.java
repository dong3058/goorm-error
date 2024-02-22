package hello.exception;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


//@Component
//스프링 부트는 이클래스에있는 모든 내용을 자동으로 처리한다.
//errorpage도 자동으로 등록하고 /error라는 경로로 기본 오류 페이지를 설정한다.
//new erroerpage("/error") 이런꼴이다.
//서블릿밖으로 예외가 발생하거나 response.senderror가 호출되면 모든 오류는 /error를 호출한다.
//basicerrorcontroller라는 스프링 컨트롤러가 자동으로 등록한다 그리고 여기엔 기능이 다구현되있음.
//errorpage에서 등록한 /error를 매핑해서 처리하는 컨트롤러이다.
//errormvcautoconfiguration이라는 클래스가 오류페이지를 자동으로 등록하는 역할을함.
//basicerrorcontroller는의 순서에따라서 웹피이지를 만들기만 하면되는대
//templates아래에 error 폴더에 4xx.html 이렇게쓰자
//에러코드 4~~은 전부다 애가 처리한다는 의미이다.
//또한 베이직 에러 컨트롤러는 model에다가 정보를 담아서 뷰에 전달한다. 뷰는 이정보를 출력이가능하다.
//또 베이직 에러컨트롤러는 자동으로 req의 accpet에 맞춰서 오류를 전달해줌.
//걍 자동 처리해준다 이소리다 ㅋㅋ;
//베이직 컨트롤러를 확장해서 json에러처리 응답을 더 확장할수도있음.

//정확히는 베이직 컨트롤러가 req의 accpet이 text/html이면 html을 돌려주는 메서드를 ㄱ실행하고
//그외에 대해선 error()라는 다른 메서드를 적용해서 돌려준다.
//즉 text/html을 처리한느애가 기본설정을 /error로 잡아서 그아래의 html파일에대해서 찾아준다는의미.
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Override
    public void customize(ConfigurableWebServerFactory factory) {

        //해당되능 에러가 발생하면 여기를 부르도록 하는것이다. 정확히는 해당 컨트롤러를 부르는것.
       ErrorPage errorpage404= new ErrorPage(HttpStatus.NOT_FOUND,"/error-page/404");
       //404코드가 오면 뒤의 URL을 호출해라 라는말.앞의 에러코드는 enum인대 자도으로 뒤에서 에러페이지 컨트롤러에서
        //req에다가 이넘 안의 value값즉 404를 담는것같다. string꼴로.
       ErrorPage errorpage500= new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,"/error-page/500");

       ErrorPage errorpageex= new ErrorPage(RuntimeException.class,"/error-page/500");


        //페이지등록
        factory.addErrorPages(errorpage404,errorpage500,errorpageex);
        //resp.senderror(400)이 터지면 404로 넘어가고
        //나머지도 같은 메커니즘.
    }
}
