package hello2.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;


@Configuration
@ComponentScan(
        // basePackages = "hello.core.member", (member 패키지 이하의 폴더만 탐색)
        // 생략하면 첫 번째 줄인 hello2.core 이하의 파일을 모두 탐색하기에 이렇게 하는것이 관례이다.
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
        // classes = Configuration.class는 AppConfig를 빼주는 코드
        // 테스트 코드의 configuration도 포함될 수 있기에 빼주는 것
        // 보통 설정 정보를 컴포넌트 스캔 대상에서 제외하지 않지만 기존 예제 코드를 유지하기 위한 것
)
public class AutoAppConfig {
}
