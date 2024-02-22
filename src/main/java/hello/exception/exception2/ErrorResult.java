package hello.exception.exception2;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
public class ErrorResult {

    private String code;
    private String message;
}
