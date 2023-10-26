package hello.aop.exam.aop;

import hello.aop.exam.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class RetryAspect {

    @Around("@annotation(retry)")
    public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable { // 파라미터에 있는 Retry가 타입이라 @Around 안에가 대체 됨

        log.info("[retry] {} retry={}", joinPoint.getSignature(), retry);

        int maxRetry = retry.value(); // 횟수 제한은 무조건 있어야함
        Exception exceptionHolder = null; // 예외 담아두기

        for (int retryCount = 1; retryCount <= maxRetry; retryCount++) {
            try {
                log.info("[retry] try count={}/{}", retryCount, maxRetry);
                return joinPoint.proceed();
            } catch (Exception e) {
                exceptionHolder = e;
            }
        }
        throw exceptionHolder;
        // 중간에 성공하지않으면 예외를 던진다.
        // 예외가 터져서 다시 for 루프를 돌아서 성공하면 return joinPoint.proceed()를 반환한다.
    }
}