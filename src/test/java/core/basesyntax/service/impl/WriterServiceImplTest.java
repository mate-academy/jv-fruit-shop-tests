package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class WriterServiceImplTest {

    private static final String TEST_FILE_PATH = "src/test/resources/test_report.csv";
    private static final String WRONG_FILE_PATH = "src/testABC/resources/test_report.csv";
    private static final String REPORT = "fruit,quantity\n"
            + "banana,152\n"
            + "apple,90";
    private WriterService fileWriter;

    @Before
    public void setUp() {
        fileWriter = new WriterServiceImpl();
    }

    @Test
    public void writeToFile_Ok() {
        fileWriter.writeToFile(TEST_FILE_PATH, REPORT);
        List<String> expected = List.of(
                "fruit,quantity",
                "banana,152",
                "apple,90");
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(TEST_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_NotOk() {
        fileWriter.writeToFile(WRONG_FILE_PATH, REPORT);
    }
}
