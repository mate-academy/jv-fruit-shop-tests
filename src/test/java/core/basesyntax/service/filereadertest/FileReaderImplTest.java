package core.basesyntax.service.filereadertest;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.filereader.FileReader;
import core.basesyntax.service.filereader.FileReaderImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static FileReader fileReader;
    private static List<String> stringDataFromFile;
    private static final String INPUT_TEST_FILE_OK = "src/test/resources/inputTest.csv";

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderImpl();
        stringDataFromFile = new ArrayList<>();
    }

    @Test
    public void readFromFile_Ok() {
        stringDataFromFile.add("b,banana,20");
        stringDataFromFile.add("b,apple,100");
        stringDataFromFile.add("s,banana,100");
        stringDataFromFile.add("p,banana,13");
        stringDataFromFile.add("r,banana,2");
        List<String> dataFromFile = fileReader.dataFromFile(INPUT_TEST_FILE_OK);
        assertEquals(FileReaderImplTest.stringDataFromFile, dataFromFile);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_NotOk() {
        String fileNotExist = "src/test/resources/inputTest1234.csv";
        fileReader.dataFromFile(fileNotExist);
    }
}
