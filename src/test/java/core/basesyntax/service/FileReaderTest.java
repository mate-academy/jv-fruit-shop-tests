package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.impl.FileReaderImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderTest {
    private static final String FILE_WITH_DATA = "src/test/java/resources/FileWithData.csv";
    private static final String EMPTY_FILE = "src/test/java/resources/EmptyFile.csv";
    private static final String INVALID_PATH = "";
    private static FileReader fileReader;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void reade_fileWithData_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,40");
        expected.add("b,apple,50");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,60");
        expected.add("p,apple,20");
        expected.add("p,banana,15");
        expected.add("s,banana,50");
        List<String> actual = fileReader.read(FILE_WITH_DATA);
        assertEquals(expected, actual);
    }

    @Test
    public void reade_fileWithInvalidData_NotOk() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,150");
        expected.add("b,apple,50");
        expected.add("s,banana,100");
        expected.add("p,banana,130");
        expected.add("r,apple,60");
        expected.add("p,apple,20");
        expected.add("p,banana,15");
        expected.add("s,banana,50");
        List<String> actual = fileReader.read(FILE_WITH_DATA);
        assertNotEquals(expected, actual);
    }

    @Test
    public void reade_emptyFile_Ok() {
        List<String> expected = new ArrayList<>();
        List<String> actual = fileReader.read(EMPTY_FILE);
        assertEquals(expected, actual);
    }

    @Test
    public void emptyFileTest_NotOk() {
        List<String> expected = new ArrayList<>();
        List<String> actual = fileReader.read(FILE_WITH_DATA);
        assertNotEquals(expected, actual);
    }

    @Test
    public void readInvalidPath_NotOk() {
        try {
            fileReader.read(INVALID_PATH);
        } catch (Exception e) {
            return;
        }
        fail("Invalid path file: " + INVALID_PATH);
    }
}
