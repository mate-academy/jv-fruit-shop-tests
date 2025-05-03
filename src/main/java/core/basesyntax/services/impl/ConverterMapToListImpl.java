package core.basesyntax.services.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.services.ConverterMapToList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConverterMapToListImpl implements ConverterMapToList {
    private static final String COMMA = ",";

    @Override
    public List<String> convert(Map<Fruit, Integer> storage) {
        return storage.entrySet()
                .stream()
                .map(m -> m.getKey() + COMMA + m.getValue().toString())
                .sorted()
                .collect(Collectors.toList());
    }
}
