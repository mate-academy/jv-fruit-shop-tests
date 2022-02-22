package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String VALID_SOURCE_FILE_PATH
            = "src/test/java/core/basesyntax/resource/testFileOne.csv";
    private static final String INVALID_SOURCE_FILE_PATH
            = "src/test/java";
    private static final String EXPECTED_FILE_PATH
            = "src/test/java/core/basesyntax/resource/testFileTwo.csv";
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void read_sourceFilePathNull_notOk() {
        fileReaderService.read(null);
    }

    @Test(expected = RuntimeException.class)
    public void read_sourceFileInvalidPath_notOk() {
        fileReaderService.read(INVALID_SOURCE_FILE_PATH);
    }

    @Test
    public void read_validPath_ok() {
        List<String> actualList = fileReaderService.read(VALID_SOURCE_FILE_PATH);
        List<String> expectedList = null;
        try {
            expectedList = Files.readAllLines(Paths.get(EXPECTED_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can`t read file by the path " + EXPECTED_FILE_PATH, e);
        }
        assertEquals(expectedList, actualList);
    }
}
