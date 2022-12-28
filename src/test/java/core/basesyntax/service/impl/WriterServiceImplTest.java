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
    public static final String TEST_FILEPATH = "src/test/resources/report.csv";
    public static final String WRONG_FILEPATH = "/var/lib";
    public static final String TEST_STRING = "type,fruit,quantity\nb,banana,20";
    private WriterService writerService;

    @Before
    public void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void writeToFile_Work_Ok() {
        writerService.writeToFile(TEST_STRING, TEST_FILEPATH);
        List<String> actual;
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20");
        try {
            actual = Files.readAllLines(Path.of(TEST_FILEPATH));
        } catch (IOException e) {
            throw new RuntimeException(String.format("No such file or "
                    + "directory %s",TEST_FILEPATH), e);
        }
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_wrongPath_NotOk() {
        writerService.writeToFile(TEST_STRING, WRONG_FILEPATH);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_nullPath_NotOk() {
        writerService.writeToFile(TEST_STRING, null);
    }
}
