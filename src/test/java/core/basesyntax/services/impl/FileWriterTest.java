package core.basesyntax.services.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.services.FileWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileWriterTest {
    private static final String PATH_TO_TEST_FILE = "src/resources/testReport.txt";
    private static final String EXPECTED_REPORT = "fruit,quantity, banana,152, apple,90";
    private static final File TEST_FILE = new File(PATH_TO_TEST_FILE);
    private FileWriter fileWriter;

    @Before
    public void createFiles() throws Exception {
        fileWriter = new FileWriterImpl();
        TEST_FILE.createNewFile();

    }

    @After
    public void clearFiles() throws Exception {
        Files.deleteIfExists(Path.of(PATH_TO_TEST_FILE));
    }

    @Test
    public void writeToFile_OK() {
        Path pathToFile = Path.of(PATH_TO_TEST_FILE);
        fileWriter.writeToFile(PATH_TO_TEST_FILE, EXPECTED_REPORT);
        String actualResult = readFile(pathToFile);
        assertEquals(EXPECTED_REPORT, actualResult);
    }

    @Test
    public void writeToFile_invalidFilePath() {
        Exception exception = new Exception();
        String invalidPath = "";
        try {
            fileWriter.writeToFile(invalidPath, EXPECTED_REPORT);
        } catch (Exception e) {
            exception = e;
        }
        assertEquals(String.format("For an invalid file path %s should be thrown, "
                                + "but was %s\n",
                        RuntimeException.class, exception.getClass()),
                RuntimeException.class, exception.getClass());
    }

    private String readFile(Path pathToTestFile) {
        try {
            return String.join("", Files.readAllLines(pathToTestFile));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
    }
}
