package core.basesyntax.service.fileservice;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static FileReader fileReader;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void read_WithValidFileName_Ok() {
        List<String> actual = fileReader.read(
                "src/test/resources/ValidInputDataForTest.csv");
        List<String> expected = new ArrayList<>();
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        assertEquals(expected, actual);
    }
}
