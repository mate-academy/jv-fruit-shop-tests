package core.basesyntax.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderTest {
    private static final String CORRECT_PATH = "src/test/resources/testInput.csv";
    private static final String EMPTY_FILE = "src/test/resources/emptyInput.csv";
    private static final String INCORRECT_PATH = "some input";
    private static FileReader fileReader;

    @BeforeClass
    public static void beforeAll() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void read_FileWithCorrectPath_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        List<String> actual = fileReader.read(CORRECT_PATH);
        Assert.assertEquals("Actual and expected lists differ from each other: ", expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void read_FileWithIncorrectPath_Ok() {
        fileReader.read(INCORRECT_PATH);
    }

    @Test
    public void read_EmptyFile_Ok() {
        List<String> expected = Collections.emptyList();
        List<String> actual = fileReader.read(EMPTY_FILE);
        Assert.assertEquals("List should be empty if file is empty: ", expected, actual);
    }
}
