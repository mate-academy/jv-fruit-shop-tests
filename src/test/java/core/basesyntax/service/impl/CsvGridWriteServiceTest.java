package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.model.Grid;
import core.basesyntax.service.GridWriteService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvGridWriteServiceTest {
    private static final String[] TITLES_TO_WRITE = new String[]{"model", "color", "price"};
    private static final String[][] ROWS_TO_WRITE = new String[][]{
            {"Ferrari", "blue", "9000"},
            {"BMW", "gray", "4500"},
            {"Audi", "yellow", "3000"}};
    private static final String[] EXPECTED_LINES = new String[] {
            "model,color,price", "Ferrari,blue,9000",
            "BMW,gray,4500", "Audi,yellow,3000"};
    private static final String OUTPUT_PATH = "src/test/resources/regularOutput.csv";
    private static final String WRONG_PATH = "src/test/resources/random/doubleRandom/tooRandom.csv";
    private static Grid gridToWrite;
    private static final GridWriteService gridWriteService = new CsvGridWriteService();

    @BeforeClass
    public static void beforeClass() {
        File file = new File(OUTPUT_PATH);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<String[]> rows = new ArrayList();
        for (String[] s : ROWS_TO_WRITE) {
            rows.add(s);
        }
        gridToWrite = new Grid(TITLES_TO_WRITE, rows);
    }

    @Test
    public void write_regularGrid_ok() {
        gridWriteService.write(OUTPUT_PATH, gridToWrite);
        try (BufferedReader reader = new BufferedReader(new FileReader(OUTPUT_PATH))) {
            for (int i = 0; i < EXPECTED_LINES.length; i++) {
                String line = reader.readLine();
                assertEquals(EXPECTED_LINES[i] + " expected, but was " + line,
                        EXPECTED_LINES[i], line);
            }
        } catch (FileNotFoundException e) {
            fail(e + " founded!");
        } catch (IOException e) {
            fail(e + " founded!");
        }
    }
}
