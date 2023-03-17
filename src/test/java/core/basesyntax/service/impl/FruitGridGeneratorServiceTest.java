package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.exception.GridGeneratorException;
import core.basesyntax.model.Grid;
import core.basesyntax.service.GridGeneratorService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitGridGeneratorServiceTest {
    private static final String[] EXPECTED_TITLES = {"fruit", "quantity"};
    private static final String[][] EXPECTED_ROWS = {{"banana", "90"}, {"blueberry", "40"}};
    private static Grid expectedGrid;
    private static Map<String, Integer> REGULAR_VALUES = new HashMap<>();

    private static GridGeneratorService gridGeneratorService;

    @BeforeClass
    public static void beforeClass() {
        REGULAR_VALUES.put("banana", 90);
        REGULAR_VALUES.put("blueberry", 40);
    }

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
    public void gridRegularValue_ok() {
        Grid expected = expectedGrid;
        Grid actual = gridGeneratorService.grid(REGULAR_VALUES);
        assertEquals(actual + " expected equals to " + expected + "!",
                expected, actual);
    }

    @Test
    public void gridNullValue_ok() {
        Grid actual = gridGeneratorService.grid(null);
        assertEquals(actual + " expected be null!",
                null, actual);
    }

    @Test
    public void gridFruitOrValueNull_notOk() {
        boolean thrownOnName = false;
        boolean thrownOnCount = false;
        Map<String, Integer> wrongFruitName = new HashMap<>();
        wrongFruitName.put(null, 40);
        Map<String, Integer> wrongFruitCount = new HashMap<>();
        wrongFruitCount.put("apple", null);
        try {
            gridGeneratorService.grid(wrongFruitName);
        } catch (GridGeneratorException e) {
            thrownOnName = true;
        }
        assertTrue("GridGeneratorException when fruit name"
                + " are null expected true, but was false", thrownOnName);
        try {
            gridGeneratorService.grid(wrongFruitCount);
        } catch (GridGeneratorException e) {
            thrownOnCount = true;
        }
        assertTrue("GridGeneratorException when fruit count"
                + " are null expected true, but was false", thrownOnCount);
    }
}
