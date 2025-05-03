package core.basesyntax.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.servise.FileReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String FROM_FILE = "src/test/resources/database.csv";
    private static final String EMPTY_FILE = "src/test/resources/emptyFile.csv";
    private static FileReaderService fileReaderService;
    private static List<String> dataFromFile;

    @BeforeClass
    public static void setUp() {
        fileReaderService = new FileReaderServiceImpl();
        dataFromFile = new ArrayList<>();
        dataFromFile.add("type,fruit,quantity");
        dataFromFile.add("b,banana,20");
        dataFromFile.add("b,apple,100");
        dataFromFile.add("s,banana,100");
        dataFromFile.add("p,banana,13");
        dataFromFile.add("r,apple,10");
        dataFromFile.add("p,apple,20");
        dataFromFile.add("p,banana,5");
        dataFromFile.add("s,banana,50");
    }

    @Test
    public void readFileCorrectData_isOk() {
        List<String> expected = dataFromFile;
        List<String> actual = fileReaderService.read(FROM_FILE);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void validateNullInputData_notOk() {
        fileReaderService.read("invalid/directory/file.csv");
    }

    @Test
    public void readEmptyFile_ok() {
        List<String> expected = new ArrayList<>();
        List<String> actual = fileReaderService.read(EMPTY_FILE);
        assertEquals(expected, actual);
    }
}
