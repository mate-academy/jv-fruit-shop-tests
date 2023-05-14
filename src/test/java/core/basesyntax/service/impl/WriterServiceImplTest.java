package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    public static final String WRITE_TO_FILE_PATH = "src/test/resources/data2.txt";
    public static final String WRONG_FILE_PATH = "src/test/resources/data0.txt";
    public static final String REPORT = "fruit,quantity"
            + System.lineSeparator() + "banana,100"
            + System.lineSeparator() + "apple,15";
    public static final WriterService writerService = new WriterServiceImpl();

    @BeforeAll
    static void beforeAll() {
        writerService.createReportFile(REPORT, WRITE_TO_FILE_PATH);
    }

    @Test
    void createReportFile_Ok() {
        try {
            final List<String> actual = Files.readAllLines(Path.of(WRITE_TO_FILE_PATH));
            List<String> expected = new ArrayList<>();
            expected.add("fruit,quantity");
            expected.add("banana,100");
            expected.add("apple,15");
            assertEquals(expected, actual);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void createReportFile_nonExistingPath_throwsException() {
        assertThrows(NoSuchFileException.class, () -> Files.readAllLines(
                Path.of(WRONG_FILE_PATH)));
    }

    @Test
    void createReportFile_nullPath_throwsException() {
        assertThrows(RuntimeException.class,
                () -> writerService.createReportFile(REPORT,null));
    }
}
