package core.basesyntax.impl;

import core.basesyntax.service.FileReaderService;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String INPUT_FILE_PATH = "src/test/resources/fruits_info.csv";
    private static final String NO_FILE = "";
    private static FileReaderService readerService;

    @BeforeClass
    public static void beforeClass() {
        readerService = new FileReaderServiceImpl();
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_ReadFromNotExistingFile_notOk() {
        readerService.readFromFile(NO_FILE);
    }

    @Test
    public void readFromFile_ReadFromExistingFile_Ok() {
        List<String> expectedListOfLines = List.of("type,fruit,quantity",
                "b,banana,50", "b,raspberry,100",
                "p,raspberry,20", "b,lemon,80",
                "s,lemon,20", "p,lemon,30",
                "s,kiwi,90");
        List<String> actualListOfLines = readerService.readFromFile(INPUT_FILE_PATH);
        Assert.assertEquals(expectedListOfLines, actualListOfLines);
    }
}
