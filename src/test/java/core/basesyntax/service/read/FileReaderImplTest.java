package core.basesyntax.service.read;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String CORRECT_PATH = "src/test/resources/input_test.csv";
    private static final String INCORRECT_PATH = "";
    private static final String FIRST_LINE = "b,orange,20";
    private static final String SECOND_LINE = "p,apple,10";
    private static FileReader fileReader;

    @BeforeClass
    public static void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void readInputFile_correctPath_ok() {
        List<String> expected = new ArrayList<>();
        expected.add(FIRST_LINE);
        expected.add(SECOND_LINE);
        List<String> actual = fileReader.read(CORRECT_PATH);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void readInputFile_incorrectPath_notOk() {
        Assert.assertThrows(RuntimeException.class, () -> fileReader.read(INCORRECT_PATH));
    }
}
