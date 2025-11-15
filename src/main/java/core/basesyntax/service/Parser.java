package core.basesyntax.service;

public interface Parser<T> {
    T parse(String transaction);
}
