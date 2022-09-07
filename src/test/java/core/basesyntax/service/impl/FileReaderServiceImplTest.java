package core.basesyntax.service.impl;

import core.basesyntax.service.FileReaderService;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileReaderServiceImplTest {
    public static final String INPUT_FILE = "src/test/java/resources/input_FileReader.csv";
    public static final String INPUT_NOT_EXISTING_FILE = "";
    private FileReaderService readerService;

    @Before
    public void setUp() {
        readerService = new FileReaderServiceImpl();
    }

    @Test
    public void readFromFile_ReadFromExistingFile_Ok() {
        List<String> expectedList = List.of("type,fruit,quantity",
                "s,pie,50",
                "r,pineapple,37",
                "b,plum,111",
                "p,orange,14");
        List<String> actualList = readerService.readFromFile(INPUT_FILE);
        Assert.assertEquals(expectedList, actualList);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_ReadFromNotExistingFile_NotOk() {
        readerService.readFromFile(INPUT_NOT_EXISTING_FILE);
    }
}
