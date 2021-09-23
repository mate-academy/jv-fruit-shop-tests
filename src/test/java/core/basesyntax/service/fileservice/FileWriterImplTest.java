package core.basesyntax.service.fileservice;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static FileWriter fileWriter;
    private static FileReader fileReader;

    @BeforeClass
    public static void beforeClass() {
        fileWriter = new FileWriterImpl();
        fileReader = new FileReaderImpl();
    }

    @Test
    public void write_WithValidFileName_Ok() {
        fileWriter.write("fruit, quantity"
                        + System.lineSeparator() + "b,apple,40",
                "src/test/resources/ValidOutputDataForTest.csv");
        List<String> expected = new ArrayList<>();
        expected.add("b,apple,40");
        List<String> actual = fileReader.read(
                "src/test/resources/ValidOutputDataForTest.csv");
        assertEquals(expected, actual);
    }
}
