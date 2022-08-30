package core.basesyntax.service.io;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String PATH_TO_TEST_INPUT_FILE = "src/resourcesTest/operationsTest.csv";

    @Test
    public void fileReader_Ok() {
        FileReader fileReader = new FileReaderImpl();
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        List<String> actual = fileReader.readeFromFile(PATH_TO_TEST_INPUT_FILE);
        assertEquals(expected,actual);
    }
}
