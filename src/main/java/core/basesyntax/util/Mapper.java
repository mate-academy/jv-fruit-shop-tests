package core.basesyntax.util;

public interface Mapper<F, T> {
    T map(F from);
}
