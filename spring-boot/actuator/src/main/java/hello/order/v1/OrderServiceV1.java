package hello.order.v1;

import hello.order.OrderService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class OrderServiceV1 implements OrderService {

    private final MeterRegistry registry;

    private AtomicInteger stock = new AtomicInteger(100);

    public OrderServiceV1(MeterRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void order() {
        log.info("주문");
        stock.decrementAndGet();

        Counter counter = Counter.builder("my.order")
                .tag("class", this.getClass().getName())
                .tag("method", "order")
                .description("order")
                .register(registry);

        counter.increment(); // 메트릭 값이 1 증가됨
    }

    @Override
    public void cancel() {
        log.info("취소");
        stock.incrementAndGet();

        Counter.builder("my.order") // 메트릭 name
                .tag("class", this.getClass().getName())
                .tag("method", "cancel") // 위의 2개는 tag인데, 메트릭 이름은 같고 tag로 구분짓기
                .description("order") // 카운터를 MeterRegistry에 등록
                .register(registry).increment(); // 카운터의 값을 하나 증가시킴
    }

    @Override
    public AtomicInteger getStock() {
        return stock;
    }
}
