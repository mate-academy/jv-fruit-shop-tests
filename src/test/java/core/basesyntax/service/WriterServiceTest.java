package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.impl.CsvWriterServiceImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class WriterServiceTest {
    private static final String TITLE = "fruit,quantity";
    private static final String INDENTATION = "\t";
    private static final String SEPARATOR = ",";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String PATH_TO_FILE = "src/test/resources/report.csv;";
    private WriterService writerService = new CsvWriterServiceImpl();

    @Test
    void reportCreatesFileIfNotExist_Ok() {
        writerService.write(PATH_TO_FILE, new String());
        File file = new File(PATH_TO_FILE);
        assertTrue(file.exists());
    }

    @Test
    void createsEmptyReportFromEmptyData_ok() throws IOException {
        writerService.write(PATH_TO_FILE, new String());
        long actual = Files.size(Path.of(PATH_TO_FILE));
        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    void checkReportMatchingToInputData_ok() throws IOException {
        String expected = new StringBuilder()
                .append(INDENTATION).append(TITLE)
                .append(LINE_SEPARATOR).append(INDENTATION)
                .append("banana" + SEPARATOR + 42).toString();
        writerService.write(PATH_TO_FILE, expected);
        List<String> strings = Files.readAllLines(Path.of(PATH_TO_FILE));
        String actual = strings.get(0) + LINE_SEPARATOR + strings.get(1);
        assertEquals(expected, actual);
    }
}
