package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.FileReaderImpl;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderTest {
    private static final String INPUT_CORRECT_FILE = "src/main/resources/StoreInformation.csv";
    private static final String INPUT_INCORRECT_FILE = "src/main/resources/wrongName.csv";
    private static FileReader fileReader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void readDataFromFile_Ok() {
        List<String> expect = List.of("type,fruit,quantity", "b,banana,20",
                "b,apple,100", "s,banana,100", "p,banana,13", "r,apple,10",
                "p,apple,20", "p,banana,5", "s,banana,50");
        List<String> actual = fileReader.readDataFromFile(INPUT_CORRECT_FILE);
        assertEquals(expect, actual);
    }

    @Test(expected = RuntimeException.class)
    public void isFileNull_NotOk() {
        List<String> actual = fileReader.readDataFromFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void nameFileWrong_NotOk() {
        List<String> actual = fileReader.readDataFromFile(INPUT_INCORRECT_FILE);
    }

    @Test(expected = RuntimeException.class)
    public void nameFileIsEmpty_NotOk() {
        List<String> actual = fileReader.readDataFromFile("");
    }
}
