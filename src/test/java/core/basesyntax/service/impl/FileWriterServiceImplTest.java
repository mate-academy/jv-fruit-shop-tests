package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileWriterServiceImplTest {
    private static final String TEST_FILE = "src/main/resources/data.csv";
    File file;
    FileWriterService fileWriterService;
    List<String> testLines = List.of("b,banana,100", "b,banana,20", "p,banana,100");

    @Before
    public void setUp() throws Exception {
        file = new File(TEST_FILE);
        fileWriterService = new FileWriterServiceImpl();
    }

    @After
    public void tearDown() throws Exception {
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
        List<String> lines = null;
        try {
            lines = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException();
        }

        assertEquals(testLines, lines);
    }

    @Test
    public void createFile_notOk() {
        boolean thrown = false;
        try {
            fileWriterService.writeToFile("b:/notExistingFile.txt", testLines);
        } catch (RuntimeException e) {
            thrown = true;
        }

        assertTrue(thrown);
    }
}