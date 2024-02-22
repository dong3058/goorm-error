package hello.exception.api;


import hello.exception.exception2.BadRequestException;
import hello.exception.exception2.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;


@Slf4j
@Controller
public class ApiExcpetion {

    @GetMapping("/api/members/{id}")
    public MemberDto getMember(@PathVariable String id){
        if(id.equals("ex")){
            throw new RuntimeException("잘못된 사용자");
            //오류가 발생하면 html페이지가반환됨. 이런건 우리가 원하는게아님.
            //즉 요청의 accpet과 같은 형태로 돌려줘여됨.
        }
        if(id.equals("bad")){

            throw new IllegalArgumentException("잘못된 입력값");

        }
        if(id.equals("user-ex")){
            throw new UserException("사용자오류");
        }

        return new MemberDto(id,"hello"+id);
    }

    @GetMapping("/api/response-status-ex1")
    public String reponseStatusex1(){
        throw new BadRequestException();
    }
    @GetMapping("/api/response-status-ex2")
    public String reponseStatusex2(){
        //error.bad는 앞에서 햇던 메시지를 찾는기능이다.
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"error.bad",new IllegalArgumentException());
        //responsestatusexpcetion은 어노테이셔늘 달수가없는 시스템상 제공하는 오류라던가
        //그리고 어노테이션은 동적인 조건 변경이 안된다.
        //이에대한 문제점은 이responsestatusexpcetion을 적용하는걸로 해결이된다. resolver는 badrequestexpcetion에 나와있는그거다.
        //스프링 내부에 내장된 에러인 금지된 변수 오류의 상태코드를 400으로바꾼것이다.
    }

    @GetMapping("/api/default-handler-ex")
    public String defaultException(@RequestParam Integer data){
        //타입 미스매치를 유도해서 defaultresolver의 기능을 확인하는것.
        return "ok";
    }


    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String memberId;
        private String name;
    }
}
