package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
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
    public void csvReader_parse_Ok() {
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
        List<String> actual = fileReader.parse(testFileOk.toString());
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void csvReader_parse_NullParameter_notOk() {
        fileReader.parse(null);
    }

    @Test(expected = RuntimeException.class)
    public void csvReader_parse_fileDoesNotExist_notOk() {
        fileReader.parse("non existent file");
    }

    @Test(expected = RuntimeException.class)
    public void csvReader_parse_emptyFile_notOk() {
        fileReader.parse(testFileEmpty.toString());
    }
}
