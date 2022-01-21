package core.basesyntax.service;

import core.basesyntax.service.impl.FileReaderImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FileReaderTest {
    private static final String INPUT_CORRECT_FILE = "src/main/resources/StoreInformation.csv";
    private static final String INPUT_INCORRECT_FILE = "src/main/resources/wrongName.csv";
    private static List<String> expect;
    private static List<String> actual;
    private static FileReader fileReader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void readDataFromFile_Ok() {
        expect = List.of("type,fruit,quantity", "b,banana,20",
                "b,apple,100", "s,banana,100", "p,banana,13", "r,apple,10",
                "p,apple,20", "p,banana,5", "s,banana,50");
        actual = fileReader.readDataFromFile(INPUT_CORRECT_FILE);
        assertEquals(expect, actual);
    }

    @Test
    public void IsEmptyFile_NotOk() {
        expect = new ArrayList<>();
        actual = fileReader.readDataFromFile(INPUT_CORRECT_FILE);
        assertNotEquals(expect, actual);
    }

    @Test
    public void wrongDataFromFile_NotOk() {
        expect = List.of("type,fruit", "r,banana,20",
                "s,apple,100", "s,banana,20");
        actual = fileReader.readDataFromFile(INPUT_CORRECT_FILE);
        assertNotEquals(expect, actual);
    }

    @Test(expected = RuntimeException.class)
    public void isFileNull_NotOk() {
        actual = fileReader.readDataFromFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void nameFileWrong_NotOk() {
        actual = fileReader.readDataFromFile(INPUT_INCORRECT_FILE);
    }

    @Test(expected = RuntimeException.class)
    public void nameFileIsEmpty_NotOk() {
        actual = fileReader.readDataFromFile("");
    }
}
