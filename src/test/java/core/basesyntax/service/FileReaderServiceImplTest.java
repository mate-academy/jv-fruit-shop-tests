package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.FileReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String VALID_PATH = "src/test/resources/input.csv";
    private static final String EMPTY_FILE = "src/test/resources/empty.csv";
    private static FileReaderService fileReader;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderServiceImpl();
    }

    @Test
    public void readFile_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,100");
        expected.add("b,apple,100");
        expected.add("p,banana,25");
        expected.add("s,banana,25");
        expected.add("r,banana,200");
        expected.add("p,banana,25");
        List<String> actual = fileReader.read(VALID_PATH);
        assertEquals(expected, actual);
    }

    @Test
    public void readFile_emptyFile() {
        List<String> expected = new ArrayList<>();
        List<String> actual = fileReader.read(EMPTY_FILE);
        assertEquals(expected,actual);
    }
}
