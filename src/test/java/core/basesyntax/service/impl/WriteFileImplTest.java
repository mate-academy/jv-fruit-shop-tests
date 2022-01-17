package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriteFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriteFileImplTest {
    private static List<String> expected_Ok = new ArrayList<>();
    private static List<String> expected_NotOk = new ArrayList<>();
    private static final String TITLE = "fruit,quantity";
    private static final String REPORT_FILENAME = "src/test/resources/report.csv";
    private static final WriteFile WRITE_FILE = new WriteFileImpl();

    @BeforeClass
    public static void beforeClass() {
        expected_Ok = List.of(TITLE, "banana,60", "apple,100");
        expected_NotOk = List.of(TITLE, "banana,600", "apple,100");
    }

    @Test
    public void writeToFile_Ok() {
        WRITE_FILE.writeToFile(expected_Ok, REPORT_FILENAME);
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(REPORT_FILENAME));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
        assertEquals(expected_Ok, actual);
    }

    @Test (expected = AssertionError.class)
    public void writeToFile_NotOk() {
        WRITE_FILE.writeToFile(expected_Ok, REPORT_FILENAME);
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(REPORT_FILENAME));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
        assertEquals(expected_NotOk, actual);
    }
}
