package core.basesyntax;

import core.basesyntax.servises.FileReader;
import core.basesyntax.servises.impl.FileReaderImpl;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String WRONG_PATH = "java/abcd.csv";
    private static final String CORRECT_PATH = "src/test/java/resources/inputTest.csv";
    private static final String EMPTY_FILE = "src/test/java/resources/emptyFile.csv";
    private static final List<String> test = Arrays.asList(
            "b,banana,20", "b,apple,100", "s,banana,100","p,banana,13");

    private static FileReader fileReader;

    @BeforeClass
    public static void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test(expected = RuntimeException.class)
    public void fileReader_wrongPath_notOk() {
        fileReader.readFromFile(WRONG_PATH);
    }

    @Test
    public void fileReader_correctPath_ok() {
        List<String> readFromCorrectFile = fileReader.readFromFile(CORRECT_PATH);
        Assert.assertEquals(readFromCorrectFile, test);
    }

    @Test(expected = RuntimeException.class)
    public void fileReader_emptyFile_notOk() {
        fileReader.readFromFile(EMPTY_FILE);
    }
}
