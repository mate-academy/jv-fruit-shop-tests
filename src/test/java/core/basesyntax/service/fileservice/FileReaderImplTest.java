package core.basesyntax.service.fileservice;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String FILE_NAME = "src/test/resources/ValidInputDataForTest.csv";
    private static FileReader fileReader;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void read_WithValidFileName_Ok() {
        List<String> actual = fileReader.read(
                FILE_NAME);
        List<String> expected = new ArrayList<>();
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_WithInvalidFileName_NotOk() {
        fileReader.read("InvalidData");
    }
}
