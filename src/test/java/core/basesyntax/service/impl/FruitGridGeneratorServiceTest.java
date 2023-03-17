package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.GridGeneratorException;
import core.basesyntax.model.Grid;
import core.basesyntax.service.GridGeneratorService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class FruitGridGeneratorServiceTest {
    private static final String[] EXPECTED_TITLES = {"fruit", "quantity"};
    private static final String[][] EXPECTED_ROWS = {{"banana", "90"}, {"blueberry", "40"}};
    private static final Map<String, Integer> REGULAR_VALUES =
            Map.of("banana", 90, "blueberry", 40);
    private static Grid expectedGrid;
    private static GridGeneratorService gridGeneratorService;

    @Before
    public void before() {
        gridGeneratorService = new FruitGridGeneratorService();
        List<String[]> rows = new ArrayList<>();
        for (String[] s : EXPECTED_ROWS) {
            rows.add(s);
        }
        expectedGrid = new Grid(EXPECTED_TITLES, rows);
    }

    @Test
    public void grid_regularValue_ok() {
        Grid actual = gridGeneratorService.grid(REGULAR_VALUES);
        assertEquals(actual + " expected equals to " + expectedGrid + "!",
                expectedGrid, actual);
    }

    @Test
    public void grid_nullValue_ok() {
        Grid actual = gridGeneratorService.grid(null);
        assertEquals(actual + " expected be null!",
                null, actual);
    }

    @Test(expected = GridGeneratorException.class)
    public void grid_fruitNameNull_notOk() {
        Map<String, Integer> wrongFruitName = new HashMap<>();
        wrongFruitName.put(null, 40);
        gridGeneratorService.grid(wrongFruitName);
    }

    @Test(expected = GridGeneratorException.class)
    public void grid_quantityNull_notOk() {
        Map<String, Integer> wrongFruitCount = new HashMap<>();
        wrongFruitCount.put("apple", null);
        gridGeneratorService.grid(wrongFruitCount);
    }
}
