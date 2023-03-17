package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.exception.GridIoException;
import core.basesyntax.model.Grid;
import core.basesyntax.service.GridReadService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvGridReadServiceTest {
    private static final String[] EXPECTED_TITLES = {"model", "color", "price"};
    private static final String[][] EXPECTED_ROWS = {
            {"Ferrari", "blue", "9000"},
            {"BMW", "gray", "4500"},
            {"Audi", "yellow", "3000"}};
    private static final String REGULAR_VALUES_PATH = "src/test/resources/regular.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/empty.csv";
    private static final String NOT_EXIST_PATH =
            "src/test/resources/random/doubleRandom/tooRandom.csv";
    private static final GridReadService gridReadService = new CsvGridReadService();
    private static Grid expectedGrid;

    @BeforeClass
    public static void beforeClass() {
        List<String[]> rows = new ArrayList<>();
        for (String[] s : EXPECTED_ROWS) {
            rows.add(s);
        }
        expectedGrid = new Grid(EXPECTED_TITLES, rows);
    }

    @Test
    public void regularCsvRead_ok() {
        Grid actual = gridReadService.getGrid(REGULAR_VALUES_PATH);
        assertEquals(expectedGrid + " expected, but was " + actual + "!",
                expectedGrid, actual);
    }

    @Test
    public void notExistFileRead_notOk() {
        boolean thrown = false;
        try {
            gridReadService.getGrid(NOT_EXIST_PATH);
        } catch (GridIoException e) {
            thrown = true;
        }
        assertTrue("GridIOException expected true but was false!", thrown);
    }

    @Test
    public void emptyFileRead_ok() {
        Grid actual = gridReadService.getGrid(EMPTY_FILE_PATH);
        assertEquals("Null expected, but was " + actual + "!",
                null, actual);
    }
}
