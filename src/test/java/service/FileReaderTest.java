package service;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import service.impl.FileReaderImpl;

public class FileReaderTest {
    private static final String VALID_TEST_CSV_INPUT_PATH = "src/test/resources/inputValidTest.csv";
    private static final String NOT_VALID_TEST_CSV_INPUT_PATH =
            "src/test/resources/inputNotValidTest.csv";
    private static final String EMPTY_INPUT_PATH = "src/test/resources/emptyTest.csv";
    private static service.FileReader fileReader;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void readValidInput_Ok() {
        List<String> expected = List.of("type,fruit,quantity",
                "b,banana,30",
                "b,apple,300",
                "s,banana,50",
                "p,banana,44",
                "r,apple,5",
                "p,apple,4",
                "p,banana,7",
                "s,banana,4");
        List<String> actual = fileReader.readFromFile(VALID_TEST_CSV_INPUT_PATH);
        assertEquals(expected, actual);
    }

    @Test
    public void readNotValidInput_Ok() {
        List<String> expected = List.of("type,fruit,quantity",
                "f,20",
                "b,apple,100",
                "n,gas,100",
                "p,banana",
                "g,apple,10",
                "j,apple",
                "g,rain,5",
                "s,John,50");
        List<String> actual = fileReader.readFromFile(NOT_VALID_TEST_CSV_INPUT_PATH);
        assertEquals(expected, actual);
    }

    @Test
    public void readEmptyFile_Ok() {
        List<String> expected = List.of();
        List<String> actual = fileReader.readFromFile(EMPTY_INPUT_PATH);
        assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void readFromEmptyPath_NotOk() {
        fileReader.readFromFile(null);
    }

    @Test (expected = NullPointerException.class)
    public void readFromFile_readFromNullPathFile_NotOk() {
        fileReader.readFromFile(null);
    }
}
