package hello.exception.exception2.advice;


import hello.exception.exception2.ErrorResult;
import hello.exception.exception2.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {
    //여러 컨트롤러에서 발생한 오류들을 여기서 다처리함.
    //controlleradvice 는 대상 미지정시 모든 곳에 적용.
    //annotations=RestController.class 처럼 restcontroller가붙은 애들애만 관여하던가
    //패키치 경로를 직접 적어서 적용할수도있다.
    //assignabletypes={class,class}이렇게 클래스를 지정하는것도가능.




    //@ResponseStatus(HttpStatus.BAD_REQUEST) 이렇게하면 상태코드도 조절이가능.
    @ExceptionHandler(IllegalArgumentException.class)
    //컨트롤러 내부에서 illegalarugmentexception터지면 애가 잡아냄. 그리고 아래 코드를 실행.
    //에러터질떄 실행 순서는 ㅇ전과 다를게 없는대 단 이미 스프링 자체적으로 보유한 exceptionhandelrresolver
    //가호출되서 이 어노테이션이 있다면 이걸 실행한다.
    //또한 이거에 responsebody도 적용이됨. 또한 정상 흐름으로바꿔서 정상적으로 return 을 해준다.
    //즉 추가적으로 modelandview를 보낼필요도없고 상태코드도 200이된다.
    public ErrorResult illegalexhandler(IllegalArgumentException e){
        log.error("[exceptionhandler] ex",e);
        return new ErrorResult("BAD",e.getMessage());
    }//음... responsebody가 그냥 본문에다가 내용만적느느줄아랐는대 자바객체르 자동으로
    //json으로 적어주는 기능도있내.


    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExhandler(UserException e){
        log.error("[exceptionhandler] ex",e);
        ErrorResult errorresult= new ErrorResult("USER-EX",e.getMessage());
        return new ResponseEntity<>(errorresult, HttpStatus.BAD_REQUEST);
        //http entity는 동적인 상태코드 변경이가능.
    }
    //위의 2개 해결안된애들이 일로옴.
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult userExhandler2(Exception e){
        log.error("[exceptionhandler] ex",e);
        return new ErrorResult("ex","내부오류");
    }
    //예외 처리에서 부모에 속하는 자식 예외가 터지면 예외처리중 더자세한 즉 자식에대한 예외처리가
    //먼저 호출되고 그게아니면 부모의 처리가 호출된다.
    //@ExceptionHandler(a.class,b.class)로 하면 두개에대해서 반응.
    //생략하고 쓰면 저기 안의 parameter(걍 우리가 이제까지 작성한대로)를 기준으로 받는다.
    //또한 반환을 string 으로 하고 controller어노테이션을 달아주면 view이름을 반환하는
    //것도 가능하다.즉 api뿐만아니라 다양하게 처리가 가능하다는말. 일반적인 mvc도 처리가가능.
}
