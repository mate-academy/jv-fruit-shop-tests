package core.service.impl;

import core.service.ReaderService;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String NON_EXISTING_FILENAME = "RAW_filename.csv";
    private static final String EMPTY_FILENAME = "";
    private static final String CORRECT_INPUT = "src/test/resources/testFile.csv";
    private static final String EMPTY_FILE = "src/test/resources/testEmpty.csv";
    private static final String WRONG_INPUT = "src/test/resources/test_file_wrong_data.csv";
    private static ReaderService fileReader;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void readFromFile_correctInput_Ok() {
        List<String> expected = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<String> actual = fileReader.readFromFile(CORRECT_INPUT);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_wrongData_Ok() {
        List<String> expected = List.of("type,fruit,quantity // Something wrong!!!",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100");
        List<String> actual = fileReader.readFromFile(WRONG_INPUT);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_nonExistingFile_notOk() {
        fileReader.readFromFile(NON_EXISTING_FILENAME);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_emptyFilename_notOk() {
        fileReader.readFromFile(EMPTY_FILENAME);
    }

    @Test
    public void readFromFile_readEmptyFile_Ok() {
        List<String> listWithEmptyFile = fileReader.readFromFile(EMPTY_FILE);
        Assert.assertTrue(listWithEmptyFile.isEmpty());
    }
}
