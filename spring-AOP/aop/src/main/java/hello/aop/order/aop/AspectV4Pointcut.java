package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV4Pointcut {

    // hello.aop.order 패키지와 하위 패키지
    @Pointcut("hello.aop.order.aop.Pointcuts.allOrder()") // 포인트컷
    private void allOrder(){}

    // 클래스 이름 패턴이 *Service
    @Pointcut("hello.aop.order.aop.Pointcuts.orderAndService()")
    private void allService(){}

    @Around("allOrder()") // allOrder() 메서드를 넣어주면 됨
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature()); // joinPoint 시그니처 (메서드 뭐 호출되었는지)
        return joinPoint.proceed();
    }

    // hello.aop.order 패키지와 하위 패키지면서 클래스 이름 패턴이 *Service인 것
    @Around("allOrder() && allService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
            return e;
        } finally {
            log.info("[리소스 릴리스] {}", joinPoint.getSignature());
        }
    }
}
