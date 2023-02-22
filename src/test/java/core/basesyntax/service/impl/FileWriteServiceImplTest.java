package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriteService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileWriteServiceImplTest {
    public static final String TEST_PATH = "src/test/java/resources/output.csv";
    public static final String WRONG_PATH = "scr/list/var/lib";
    public static final String TEST_STRING = "type,fruit,quantity";
    private FileWriteService fileWriteService;

    @Before
    public void setUp() {
        fileWriteService = new FileWriteServiceImpl();
    }

    @Test
    public void writeToFile_Work_Ok() {
        fileWriteService.writeToFile(TEST_STRING, TEST_PATH);
        List<String> actual;
        List<String> expected = List.of("type,fruit,quantity");
        try {
            actual = Files.readAllLines(Path.of(TEST_PATH));
        } catch (IOException e) {
            throw new RuntimeException(String.format("Can't write data to file " + TEST_PATH, e));
        }
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_wrongPath_NotOk() {
        fileWriteService.writeToFile(TEST_STRING, WRONG_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_nullPath_NotOk() {
        fileWriteService.writeToFile(TEST_STRING, null);
    }
}
