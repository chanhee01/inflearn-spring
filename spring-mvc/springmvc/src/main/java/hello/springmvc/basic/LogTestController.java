package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController  //  그냥 컨트롤러는 view 이름이 반환되지만 RestController는 문자를 반환할 수 있다.
public class LogTestController {
//    private final Logger log = LoggerFactory.getLogger(LogTestController.class);

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "spring";

        System.out.println("name = " + name);

        log.trace("trace log ={}", name);
        log.debug("debug log ={}", name);
        // trace와 debug는 개발관련이라 application.properties에서 별도로 설정해줘야함
        // 개발 서버에서는 debug로 배포, 운영 서버에서는 info로 배포
        // LEVEL : TRACE > DEBUG > INFO > WARN > ERROR
        // 하위 순위까지 지원이라 warn으로 설정하면 warn, error만 보여줌
        // logging.level.hello.springmvc.debug 등으로 사용(기본이 info)
        log.info(" info log={}", name);
        log.warn(" warn log={}", name);
        log.error("error log={}", name);
        // 대괄호는 뒤에 문자가 치환이 됨

        return "ok";
    }
}
