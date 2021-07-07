package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Test;

public class FileReaderTest {
    private static final String INPUT_DATA = "src/test/resources/input.csv";
    private static final String NO_CORRECT_PATH = "src/test/resources/";
    private static final FileReader fileReader = new FileReaderImpl();

    @Test
    public void test_readingFromFile_OK() {
        String expected = null;
        try {
            expected = String.join("\n", Files.readAllLines(Path.of(INPUT_DATA)));
        } catch (IOException e) {
            throw new RuntimeException("We can't find file " + INPUT_DATA);
        }
        String actual = String.join("\n", fileReader.readFromFile(INPUT_DATA));
        assertEquals(expected, actual.toString());
    }

    @Test(expected = RuntimeException.class)
    public void test_readingFromFile_NotOK() {
        fileReader.readFromFile(NO_CORRECT_PATH);
    }
}

