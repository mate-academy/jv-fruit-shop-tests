package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileReaderServiceTest {

    private static ReaderService reader;

    @BeforeClass
    public static void beforeClass() {
        reader = new CsvFileReaderService();
    }

    @Test
    public void readFromFile_correctFile_ok() {
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );
        assertEquals(expected, reader.readFromFile("src/test/resources/read-test.csv"));
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_incorrectFile_notOk() {
        reader.readFromFile("src/test/resources/read-test1.csv");
    }
}
