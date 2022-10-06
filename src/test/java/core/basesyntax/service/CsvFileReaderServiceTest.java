package core.basesyntax.service;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import java.util.List;

public class CsvFileReaderServiceTest {

    private static ReaderService reader;

    @BeforeClass
    public static void beforeClass() {
        reader = new CsvFileReaderService();
    }

    @Test
    public void read_Data_From_Correct_File_ok() {
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
    public void read_Data_From_Incorrect_File_notOk() {
        reader.readFromFile("src/test/resources/read-test1.csv");
    }
}