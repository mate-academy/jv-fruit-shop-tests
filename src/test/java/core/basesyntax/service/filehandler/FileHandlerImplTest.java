package core.basesyntax.service.filehandler;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileHandlerImplTest {
    private static FileHandler fileHandler;
    private static final String TEST_INPUT_DATA = "src/test/resources/testFile.txt";
    private static final String TEST_OUTPUT_DATA = "src/test/resources/testOutFile.txt";

    @BeforeClass
    public static void beforeClass() {
        fileHandler = new FileHandlerImpl();
    }

    @Test
    public void readFromFile_Ok() {
        String actual = String.join("\n", fileHandler.readFromFile(TEST_INPUT_DATA));
        try {
            String expected = String.join("\n", Files.readAllLines(Path.of(TEST_INPUT_DATA)));
            assertEquals(expected, actual);
        } catch (IOException e) {
            throw new RuntimeException("File read error in readFromFile_ok");
        }
    }

    @Test(expected = RuntimeException.class)
    public void readWithInvalidSours_NotOk() {
        fileHandler.readFromFile("");
    }

    @Test
    public void writeToFile_Ok() {
        try {
            String expected = String.join("\n", Files.readAllLines(Path.of(TEST_INPUT_DATA)));
            fileHandler.writeToFile(TEST_OUTPUT_DATA, expected);
            String actual = String.join("\n", Files.readAllLines(Path.of(TEST_OUTPUT_DATA),
                    Charset.defaultCharset()));
            assertEquals(expected, actual);
        } catch (IOException e) {
            throw new RuntimeException("File write error");
        }
    }

    @Test(expected = RuntimeException.class)
    public void writeWithInvalidSours_NotOk() {
        fileHandler.writeToFile("", "anyData");
    }
}
