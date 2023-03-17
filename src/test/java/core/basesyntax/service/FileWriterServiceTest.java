package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.impl.FileWriterServiceImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceTest {
    private static final String FILE = "src/test/resources/result.csv";
    private static final String UNEXISTING_FILE = "src/test/resources/unexisting.csv";
    private static final String EMPTY_DATA = "";
    private static final String CORRECT_DATA = "Some data that should be in file";
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Before
    public void createFile() {
        File file = new File(FILE);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Can't create file " + file, e);
        }
    }

    @After
    public void deleteFile() {
        try {
            Files.deleteIfExists(Path.of(FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't delete file " + FILE);
        }
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_nullFileName_NotOk() {
        fileWriterService.writeToFile(null, CORRECT_DATA);
        fail("An error was expected in case of null data"
                + " to write in file");
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_nullData_NotOk() {
        fileWriterService.writeToFile(FILE, null);
        fail("An error was expected in case of null data"
                + " to write in file");
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_emptyData_NotOk() {
        fileWriterService.writeToFile(FILE, EMPTY_DATA);
        fail("An error was expected in case of empty data"
                + " to write in file");
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_unexistingFile_NotOk() {
        fileWriterService.writeToFile(UNEXISTING_FILE, CORRECT_DATA);
        fail("An error was expected in case of writing"
                + " to unexisting file");
    }

    @Test
    public void writeToFile_successWrite_Ok() {
        fileWriterService.writeToFile(FILE, CORRECT_DATA);
        String actualData = readFromFile(FILE).trim();
        assertEquals(CORRECT_DATA, actualData);
    }

    private String readFromFile(String fileName) {
        try {
            return Files.readString(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly read data from file " + fileName, e);
        }
    }
}
