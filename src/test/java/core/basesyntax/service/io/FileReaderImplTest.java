package core.basesyntax.service.io;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String PATH_TO_TEST_INPUT_FILE = "src/resourcesTest/operationsTest.csv";
    private static final String PATH_TO_NOT_EXIST_TEST_INPUT_FILE = "";
    private static FileReader fileReader;

    @BeforeClass
    public static void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void fileReader_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        List<String> actual = fileReader.readeFromFile(PATH_TO_TEST_INPUT_FILE);
        assertEquals(expected,actual);
    }

    @Test
    public void fileReader_InvalidPath_NatOk() {
        assertThrows(RuntimeException.class,
                () -> fileReader.readeFromFile(PATH_TO_NOT_EXIST_TEST_INPUT_FILE));
    }
}
