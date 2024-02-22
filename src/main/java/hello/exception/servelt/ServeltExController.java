package hello.exception.servelt;


import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Slf4j
@Controller
public class ServeltExController {

    @GetMapping("/error-ex")
    public void errorEx(){
        throw new RuntimeException("예외발생!");
        //500번 즉 서버내부에서 해결할수없는 에러가 발생함.
        //컨트롤러(예외발생)->인터셉터->서블릿->필터->was--.여기까지 에러가오면 즉
        //try catch를 통해서 잡지못한 에러가 was까지 전파가되고 이 에러를 500으로처리한다.
        //즉 그냥 exception은 무조건 500처리(내부에서 처리하지못한오류)

        //보통 메인 함수는 오류발생이 스레드가 종료되고 오류가 터진다
        //차이점 ㅇㅇ;

        //정확히는 오류가 터지고 was까지 간다면 webservercustomizer로가서
        // 해당되는 에러페이지 객체를 찾고
        //그에 해당되는 경로를 다시호출 ErrorPage errorpageex= new ErrorPage(RuntimeException.class,"/error-page/500");
        //즉 error-500경로.
        //그러면 에러 컨트롤러가 호출되면서 페이지가 불러진다.
    }
    @GetMapping("/error-404")
    public void error404(HttpServletResponse response) throws IOException {
        response.sendError(404,"404오류!");
    }
    @GetMapping("/error-500")
    public void error500(HttpServletResponse response) throws IOException{
        log.info("에러코드{} [{}]",HttpStatus.INTERNAL_SERVER_ERROR,HttpStatus.valueOf(500));
        //value로 값을 가져와도 되고 그냥 쓰면 500 internal_sever_error 이런꼴로 출려고딤.
        //본디ENUM은 VALUOF에다가 STRING만 들어가는대 애는 숫자도되내.,? 그냥 특이케이스인듯?
        //httpstatus의 부모클래스의 valueof 메서드를 가져다쓴ㄴ다.
        response.sendError(500,"500오류!");

        //컨트롤러(resp.senderror)->인터셉터->서블릿->필터->was
        // resp.senderror이 발생시 resp내부에느 오류발생 상태를 저장을 하고
        //서블릿 컨테이너는 고객게에 등답 전에 resp에 senderror 가 호출되엇는지 체크
        // 그리고 호출되었다먄 오류코드에 맞추어서 기본오류페이지를 보여줌.
        //senderror가 진짜로 에러를 터트리는건아니고 에러가 발생했다라고 상태를 저장한것이다.


        //즉 오류가 터져서 직접적으로 was까지가든가 아니면 resp.senderror에
        //기록을 남기면 오류페이지정보를 체크한다/
        //webservercustomizer에서 에러페이지 객체중 해당되는 오류가
        //등록된 애를 찾고 그애의 경로를 호출한다.
        //이 호출된것이 에러 컨트롤러로가서 해당디ㅗ는 페이지를 불러온다.
        //was->/error-page/500 다시요청->필터->서블릿->인터셉터->컨트롤러(/error-page/500)->view
        //서버내부에서 자체적으로 다시요청해서 view를 보여준다.
        //특히 이떄 오류페이지 경로로 모든 필터 서블릿 기타등등이 다시호출된다.

        //즉 client에서 다시 req를 보낸게아니다 서버내부에서처리한것.
        //서버 내부에서는 컨트롤러가 2번 호출된셈.

        //근대 오류발생할떄마다 filter나 인터셉트를 매번 호출하는건 비효율적
        //이문제를 해결하려면 정상적 요청인지,오류로인한것인지 구분을해야됨.
        //필터는 이떄를 위해서 dispatchtype이란옵션을 제공.
        //고객이 처음요청시 request값이고 에러가나면 error값이다.
        //즉 처음에 요청시 request 이고 마지막에 컨트로러에서 error발생시
        //다시 was로이동-->was에서 타입을 error로 바꾸고 다시 컨트롤러까지실행
        //이떄 조건을 설정해둔대로 필터,인터셉터는 error면 실행x.
    }
}
