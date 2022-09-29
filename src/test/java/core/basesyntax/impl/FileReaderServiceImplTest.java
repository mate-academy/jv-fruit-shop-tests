package core.basesyntax.impl;

import core.basesyntax.service.FileReaderService;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String INPUT_FILE_PATH
            = "src/test/java/core/basesyntax/resources/testexample.csv";
    private static final String NO_FILE = "";
    private final FileReaderService readerService = new FileReaderServiceImpl();

    @Test (expected = RuntimeException.class)
    public void readFromFile_NotExistingFile_NotOk() {
        readerService.readFromFile(NO_FILE);
    }

    @Test
    public void readFromFile_ExistingFile_Ok() {
        List<String> expectedListOfLines = List.of("type,fruit,quantity",
                "b,banana,20", "b,apple,100",
                "s,banana,100", "p,banana,13",
                "r,apple,10", "p,apple,20",
                "p,banana,5", "s,banana,50");
        List<String> actualListOfLines = readerService.readFromFile(INPUT_FILE_PATH);
        Assert.assertEquals(expectedListOfLines, actualListOfLines);
    }
}
