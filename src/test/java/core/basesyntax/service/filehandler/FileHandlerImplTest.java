package core.basesyntax.service.filehandler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileHandlerImplTest {
    private static FileHandler fileHandler;
    private static final String TEST_INPUT_DATA = "src\\test\\resources\\testFile.txt";
    private static final String TEST_OUTPUT_DATA = "src\\test\\resources\\testOutFile.txt";

    @BeforeClass
    public static void beforeClass() {
        fileHandler = new FileHandlerImpl();
    }

    @Test
    public void test_readFromFile_Ok() {
        try {
            String expected = String.join("\n", Files.readAllLines(Path.of(TEST_INPUT_DATA)));
            String actual = String.join("\n", fileHandler.readFromFile(TEST_INPUT_DATA));
            assertEquals(expected, actual);
        } catch (IOException e) {
            throw new RuntimeException("File read error in test_readFromFile_ok");
        }
    }

    @Test
    public void test_readWithInvalidSours_NotOk() {
        try {
            fileHandler.readFromFile("");
        } catch (RuntimeException e) {
            return;
        }
        fail();
    }

    @Test
    public void test_writeToFile_Ok() {
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

    @Test
    public void test_writeWithInvalidSours_NotOk() {
        try {
            fileHandler.writeToFile("", "anyData");
        } catch (RuntimeException e) {
            return;
        }
        fail();
    }
}
