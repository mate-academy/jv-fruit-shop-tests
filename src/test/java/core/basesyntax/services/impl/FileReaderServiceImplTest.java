package core.basesyntax.services.impl;

import core.basesyntax.services.FileReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String CORRECT_FILE_PATH = "src/test/resources/input.csv";
    private static final String INCORRECT_FILE_PATH = "src/test/resources/wrongInput.csv";
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void read_TestCorrectInput() {
        List<String> expected;
        try {
            expected = Files.readAllLines(Path.of(CORRECT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read from file " + CORRECT_FILE_PATH, e);
        }
        List<String> actual = fileReaderService.read(CORRECT_FILE_PATH);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_TestIncorrectInput() {
        fileReaderService.read(INCORRECT_FILE_PATH);
    }
}
