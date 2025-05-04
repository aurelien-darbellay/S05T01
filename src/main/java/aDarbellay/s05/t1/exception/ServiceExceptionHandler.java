package aDarbellay.s05.t1.exception;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ServiceExceptionHandler {
    public <T, U, V, R> Mono<R> exceptionPropagator(T t, U u, V v, ThrowingFunction<T, U, V, R> throwingFunction) {
        try {
            R r = throwingFunction.apply(t, u, v);
            return Mono.just(r);
        } catch (Exception e) {
            return Mono.error(e);
        }
    }
}
