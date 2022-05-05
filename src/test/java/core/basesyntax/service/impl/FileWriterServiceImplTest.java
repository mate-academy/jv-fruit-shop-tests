package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import core.basesyntax.service.FileWriterService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String TEST_FILE = "src/main/resources/data.csv";
    private File file;
    private FileWriterService fileWriterService;
    private List<String> testLines;

    @Before
    public void setUp() {
        file = new File(TEST_FILE);
        fileWriterService = new FileWriterServiceImpl();
        testLines = List.of("b,banana,100", "b,banana,20", "p,banana,100");
    }

    @After
    public void tearDown() {
        file.delete();
    }

    @Test
    public void createFile_Ok() {
        fileWriterService.writeToFile(TEST_FILE, testLines);
        assertTrue(file.exists());
    }

    @Test
    public void writeToFile_Ok() {
        fileWriterService.writeToFile(TEST_FILE, testLines);
        List<String> actual = null;
        try {
            actual = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException("We should get an exception "
                    + "when we try to write data to a file.");
        }
        assertEquals(testLines, actual);
    }

    @Test
    public void createFile_notOk() {
        try {
            fileWriterService.writeToFile("b:/notExistingFile.txt", testLines);
        } catch (RuntimeException e) {
            return;
        }
        fail("We should get an exception while trying to read data from not existent file.");
    }
}
