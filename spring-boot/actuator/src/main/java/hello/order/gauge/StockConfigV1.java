package hello.order.gauge;

import hello.order.OrderService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StockConfigV1 {
    @Bean
    public MyStockMetric myStockMetric(OrderService orderService, MeterRegistry registry) {
        return new MyStockMetric(orderService, registry);
    }
    @Slf4j
    static class MyStockMetric {
        private OrderService orderService;
        private MeterRegistry registry;

        public MyStockMetric(OrderService orderService, MeterRegistry registry)
        {
            this.orderService = orderService;
            this.registry = registry;
        }

        @PostConstruct // 외부에서 메트릭을 확인할 때마다 호출되며 이 함수의 반환 값이 게이지의 값이다.
        public void init() {
            Gauge.builder("my.stock", orderService, service -> {
                log.info("stock gauge call");
                return service.getStock().get();
            }).register(registry);
        }
    }
}