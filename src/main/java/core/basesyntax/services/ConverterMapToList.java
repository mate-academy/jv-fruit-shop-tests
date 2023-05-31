package core.basesyntax.services;

import core.basesyntax.model.Fruit;
import java.util.List;
import java.util.Map;

public interface ConverterMapToList {
    List<String> convert(Map<Fruit, Integer> storage);
}
