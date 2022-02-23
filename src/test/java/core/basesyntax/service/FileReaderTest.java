package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.CsvReaderImpl;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderTest {
    private static FileReaderService fileReader;
    private static File testFileOk;
    private static File testFileEmpty;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new CsvReaderImpl();
        testFileOk = new File("./src/resources/test_Ok.csv");
        testFileEmpty = new File("./src/resources/test_empty.csv");
    }

    @Test
    public void read_Ok() {
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
        List<String> actual = fileReader.read(testFileOk.toString());
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_NullParameter_notOk() {
        fileReader.read(null);
    }

    @Test(expected = RuntimeException.class)
    public void read_fileDoesNotExist_notOk() {
        fileReader.read("non existent file");
    }

    @Test(expected = RuntimeException.class)
    public void read_emptyFile_notOk() {
        fileReader.read(testFileEmpty.toString());
    }
}
