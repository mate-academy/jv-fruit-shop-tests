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
    private static final String CORRECT_FILE_PATH = "src/test/resources/report-test.csv";
    private static final String REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator() + "apple,90";
    private WriterService writerService;

    @Before
    public void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void writeToFile_ok() {
        writerService.writeToFile(REPORT);
        List<String> expected = List.of("fruit,quantity",
                "banana,152",
                "apple,90");
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(CORRECT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file " + CORRECT_FILE_PATH,e);
        }
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void writeToFile_Null_NotOk() {
        writerService.writeToFile(null);
    }
}
