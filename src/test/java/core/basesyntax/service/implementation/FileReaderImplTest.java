package core.basesyntax.service.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String PATH_OF_FULL_FILE = "src/test/resources/directory.csv";
    private static final String PATH_OF_EMPTY_FILE = "src/test/resources/emptyFile.csv";
    private static final String PATH_OF_NOT_EXIST_FILE = "";
    private FileReader fileReader;

    @Before
    public void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void readAllDataOfFile_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
        List<String> actual = fileReader.readAllDataOfFile(PATH_OF_FULL_FILE);
        assertEquals(expected, actual);
    }

    @Test
    public void readAllDataOfEmptyFile_ok() {
        List<String> expected = Collections.emptyList();
        List<String> actual = fileReader.readAllDataOfFile(PATH_OF_EMPTY_FILE);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readAllDataOfFile_NotOk() {
        fileReader.readAllDataOfFile(PATH_OF_NOT_EXIST_FILE);
    }
}
