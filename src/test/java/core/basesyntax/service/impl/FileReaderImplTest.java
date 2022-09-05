package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static FileReader reader;
    private static final String TEST_FILE = "src/test/resources/test_input.csv";
    private static final String EMPTY_FILE = "src/test/resources/empty_test_file.csv";

    @BeforeClass
    public static void beforeClass() {
        reader = new FileReaderImpl();
    }

    @Test
    public void readExistingFile_Ok() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20", "b,apple,100",
                "s,banana,100", "p,banana,13", "r,apple,10", "p,apple,20", "p,banana,5",
                "s,banana,50");
        List<String> actual = reader.readFromFile(TEST_FILE);
        assertEquals(expected, actual);
    }

    @Test
    public void readEmptyFile_Ok() {
        List<String> expected = List.of();
        List<String> actual = reader.readFromFile(EMPTY_FILE);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void readNonexistenceFile_NotOk() {
        reader.readFromFile("");
    }
}
