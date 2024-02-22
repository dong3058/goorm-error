package hello.exception.exception2;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.BAD_REQUEST,reason="잘못된 요청 오류")//reasone은 메시지를 찾는
//기능도있다. 앞서 공부한 그 메시지맞다.
public class BadRequestException extends RuntimeException{
    //badrequestexception이 터지면 exceptionresolver를찾아본다 그럼이게 responsestatusexpectionhnaer
    //에걸리고 처리가된다. -->response status 어노테이션 이있는지 찾고
    //그안의값 2개를 senderror에다가  넣어서 보내준다. 그리고 modelandview()를돌려준다.
    //즉 우리가 만든걸아 비슷한 원리로 돌아감.
    //단 reponse.senderror를 해주었기에 에러페이지를 불러오게된다.




    //언제나 그러햇듯이 스프링은 자체적으로 excpetion handler를 지니고있는대
    //1.expectionhandlerexpectioresolver
    //2.responsestatusExcpetionresolver
    //3.defaulthandlerexpectionresolber 3가지가 존재하며
    //각각 마지막에 오류를처리못햇을경우 return null;로 다음리졸버를 호출한다.  앞서우리가
    //구현한 애들과같다.
    //2번 핸들러의경우 http응답코드를 변경 하는특징
    //2번 의경우 @responsestatus혹은 responsestatusexception을 처리한다.
    //3번의경우 스프링 내부에서 발생한 예외를 처리하는ㅎ대 효과적이다.특히 앞서했던 typedistmatch의경우
    //이러한 오류가 was까지 올라가면 500처리를 하나 클라이언트의 실수가 대부분인 이런오류는
    //400번대 처리가 옳다. 이러한 400번대ㅜ변환을 3번 리졸버가 실행한다.
    //예를들어서 typemismatch의 경우에도 3번 리졸버로인해서 reponse.senderror(Httpservletresponse.sc_badrequest)
    //로 끝나게된다. return은 당연히 모델앤뷰이고.
    //타입 미스매치뿐만아니라 다양한 스프링 내부오류에대한 것이 3번 리졸버에 들ㅇ어있다.



    //즉 예외가 발생하면 exception handler가 작동을하고
    //1,2,3우선순위에 다라서어던 애를 적용할지를찾는다.
}
