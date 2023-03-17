package core.basesyntax.service.impl;

import core.basesyntax.exception.GridGeneratorException;
import core.basesyntax.model.Grid;
import core.basesyntax.service.GridGeneratorService;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FruitGridGeneratorService implements GridGeneratorService<Map<String, Integer>> {
    public static final String[] DEFAULT_TITLES = {"fruit", "quantity"};
    private static final int FRUIT_INDEX = 0;
    private static final int QUANTITY_INDEX = 1;
    private static final int ROW_SIZE = 2;

    @Override
    public Grid grid(Map<String, Integer> value) {
        if (value == null) {
            return null;
        }
        List<String[]> rows = value.entrySet()
                .stream()
                .sorted(new Comparator<Map.Entry<String, Integer>>() {
                    @Override
                    public int compare(Map.Entry<String, Integer> e1,
                                       Map.Entry<String, Integer> e2) {
                        return e1.getValue() - e2.getValue();
                    }
                })
                .map(e -> createRow(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
        return new Grid(DEFAULT_TITLES, rows);
    }

    private String[] createRow(String fruit, Integer amount) {
        if (fruit == null || amount == null) {
            throw new GridGeneratorException("Null value not allowed!");
        }
        String[] row = new String[ROW_SIZE];
        row[FRUIT_INDEX] = fruit;
        row[QUANTITY_INDEX] = Integer.toString(amount);
        return row;
    }
}
