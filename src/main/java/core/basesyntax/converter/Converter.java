package core.basesyntax.converter;

import java.util.List;

public interface Converter<T, R> {

    R convert(T t);

    List<R> convertList(List<T> list);

}
