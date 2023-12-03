package hello.order;

import java.util.concurrent.atomic.AtomicInteger;

public interface OrderService {
    void order();
    void cancel();
    AtomicInteger getStock(); // 멀티 쓰레드에서 값을 안전하게 증가시키는 타입
}
