package core.basesyntax.service.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String INVALID_FILE_PATH = "src/test/java/resources/inputFileTest.txt";
    private static final String VALID_FILE_PATH = "src/test/java/resources/InputFileTest.csv";
    private static List<String> expectedList;
    private static FileReader fileReader;

    @BeforeClass
    public static void setUp() {
        fileReader = new FileReaderImpl();
        expectedList = List.of("type,fruit,quantity", "b,banana,50",
                        "b,apple,100", "s,banana,25", "p,banana,20", "r,apple,75");
    }

    @Test(expected = RuntimeException.class)
    public void readeFromFile_invalidFilePath_RuntimeException() {
        fileReader.readFromFile(INVALID_FILE_PATH);
    }

    @Test
    public void readeFromFile_validFilePath_isOk() {
        List<String> actual = fileReader.readFromFile(VALID_FILE_PATH);
        assertEquals(expectedList, actual);
    }
}
