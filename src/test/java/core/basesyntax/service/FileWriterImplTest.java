package core.basesyntax.service;

import core.basesyntax.service.impl.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String TEST_FILE_FOR_WRITER = "src/test/resources/FileForWriterTest.csv";
    private static final String WRONG_FILE_FOR_WRITER = "src/test/resoursec/FileForWriterTest.csv";
    private static FileWriterService fileWriterService;
    private static List<String> dataForTest;
    private static List<String> actual;

    @Before
    public void setUp() {
        fileWriterService = new FileWriterImpl();
        dataForTest = new ArrayList<>();
        actual = new ArrayList<>();
    }

    @Test(expected = RuntimeException.class)
    public void write_nullPath_notOk() {
        fileWriterService.writeToFile(null, dataForTest);
    }

    @Test(expected = RuntimeException.class)
    public void write_wrongPath_notOk() {
        fileWriterService.writeToFile(WRONG_FILE_FOR_WRITER, dataForTest);
    }

    @Test(expected = RuntimeException.class)
    public void write_nullAsData_notOk() {
        fileWriterService.writeToFile(TEST_FILE_FOR_WRITER, null);
    }

    @Test(expected = RuntimeException.class)
    public void write_emptyData_notOk() {
        fileWriterService.writeToFile(TEST_FILE_FOR_WRITER, dataForTest);
    }

    @Test
    public void write_validDataToFile_isOk() {
        dataForTest.add("s,mango,20");
        fileWriterService.writeToFile(TEST_FILE_FOR_WRITER, dataForTest);
        actual = readFileForTest(TEST_FILE_FOR_WRITER);
        List<String> expected = List.of("s,mango,20");
        Assert.assertEquals(expected, actual);
    }

    private List<String> readFileForTest(String pathName) {
        List<String> dataFromFile;
        try {
            dataFromFile = Files.readAllLines(Path.of(pathName));
            return dataFromFile;
        } catch (IOException e) {
            throw new RuntimeException("Wrong path name for file");
        }
    }
}
