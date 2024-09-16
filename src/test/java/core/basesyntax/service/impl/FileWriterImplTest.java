package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileWriterImplTest {
    public static final String WRONG_FILEPATH = "/var/lib";
    public static final String CREATED_PATH = "src/test/resources/inputFile";
    public static final String TEST_STRING = "type,fruit,quantity\nb,banana,20";
    private static final String SUCCESSFUL_PROCESS = "This process was successful";
    private FileWriter writerService;

    @Before
    public void setUp() {
        writerService = new FileWriterImpl();
    }

    @Test
    public void writeToFile_validPath_ok() {
        writerService.writeData(SUCCESSFUL_PROCESS, CREATED_PATH);
        List<String> expected = new ArrayList<>();
        expected.add(SUCCESSFUL_PROCESS);
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(CREATED_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_wrongPath_NotOk() {
        writerService.writeData(TEST_STRING, WRONG_FILEPATH);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_nullPath_NotOk() {
        writerService.writeData(TEST_STRING, null);
    }
}
