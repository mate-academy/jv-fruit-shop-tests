package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWorker;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileWorkerImplTest {
    private static final String PATH_TO_REPORT_FILE = "src/test/resources/report.csv";
    private static final String INCORRECT_PATH = "src/test/resources/test_data.cs";
    private static final String HEADER_FOR_REPORT_FILE = "fruit,amount";
    private static final String COLUMN_SEPARATOR = ",";
    private static final FileWorker fileWorker = new FileWorkerImpl();

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void readFromFile_incorrectPath_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Something went wrong "
                + "when reading data from " + INCORRECT_PATH);
        fileWorker.readFromFile(INCORRECT_PATH);
    }

    @Test
    public void writeToFile_correctOperation_ok() {
        fileWorker.writeToFile(PATH_TO_REPORT_FILE,
                HEADER_FOR_REPORT_FILE, COLUMN_SEPARATOR,
                Map.of("banana", 12));
        List<String> expected = List.of(HEADER_FOR_REPORT_FILE, "banana,12");
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(PATH_TO_REPORT_FILE));
        } catch (IOException ignored) {
            throw new RuntimeException();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void writeToFile_mapIsNull_notOk() {
        exceptionRule.expect(RuntimeException.class);
        fileWorker.writeToFile(PATH_TO_REPORT_FILE, HEADER_FOR_REPORT_FILE, COLUMN_SEPARATOR, null);
    }
}
