package aDarbellay.s05.t1.exception;

public interface ThrowingFunction<T, U, V, R> {
    R apply(T t, U u, V v) throws Exception;
}
