package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.impl.CsvReaderImpl;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderTest {
    public static final String OK_TEST_PATH = "./src/test/java/resources/test_Ok.csv";
    private static FileReader fileReader;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new CsvReaderImpl();
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
        List<String> actual = fileReader.parse(OK_TEST_PATH);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void csvReader_parse_fileDoesNotExist_notOk() {
        fileReader.parse("non existent file");
    }

    @Test(expected = RuntimeException.class)
    public void csvReader_parse_emptyFile_notOk() {
        File testInput = new File("./src/test/java/resources/test_empty.csv");
        fileReader.parse("./src/test/java/resources/test_empty.csv");
    }
}
