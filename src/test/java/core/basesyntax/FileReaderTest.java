package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.impl.FileReadServiceImpl;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderTest {
    private static FileReader fileReader;

    @BeforeClass
    public static void before() {
        fileReader = new FileReadServiceImpl();
    }

    @Test (expected = RuntimeException.class)
    public void invalidPath_Not_Ok() {
        String fromFile = "incorrect/path/to/file.csv";
        fileReader.readData(fromFile);
    }

    @Test
    public void correctInputPath_Ok() {
        String fromFile = "src/test/resources/storageTest.csv";
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20", "b,apple,100");
        List<String> actual = fileReader.readData(fromFile);
        assertEquals(expected, actual);
    }
}
