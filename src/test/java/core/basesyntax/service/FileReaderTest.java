package core.basesyntax.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderTest {
    private static final String CORRECT_PATH = "src/test/resources/testInput.csv";
    private static final String EMPTY_PATH = "src/test/resources/emptyInput.csv";
    private static final String INCORRECT_PATH = "some input";
    private static FileReader fileReader;
    private static List<String> correctInput;

    @BeforeClass
    public static void beforeAll() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void read_readFileCorrectPath_Ok() {
        correctInput = new ArrayList<>();
        correctInput.add("b,banana,20");
        correctInput.add("b,apple,100");
        List<String> actual = fileReader.read(CORRECT_PATH);
        List<String> expected = correctInput;
        Assert.assertEquals("Actual and expected lists differ from each other: ", expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void read_readFileIncorrectPath_Ok() {
        fileReader.read(INCORRECT_PATH);
    }

    @Test
    public void read_readEmptyFile_Ok() {
        correctInput = Collections.emptyList();
        List<String> expected = correctInput;
        List<String> actual = fileReader.read(EMPTY_PATH);
        Assert.assertEquals("List should be empty if file is empty: ", expected, actual);
    }
}
