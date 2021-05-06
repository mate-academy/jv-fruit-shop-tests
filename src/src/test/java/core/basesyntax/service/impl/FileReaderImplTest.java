package core.basesyntax.service.impl;

import core.basesyntax.service.FileReaderService;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static FileReaderService fileReader;
    private static final String PATH_TO_FILE = "src/test/java/core/basesyntax/resources/data.csv";
    private static final String PATH_TO_EMPTY_FILE
            = "src/test/java/core/basesyntax/resources/emptyFile.csv";
    private static final String WRONG_PATH_TO_FILE = "src/test/java/core/resources/wrongFile.csv";

    @BeforeClass
    public static void beforeClass() throws Exception {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void readFromFile_isOk() {
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );
        List<String> actual = fileReader.readFromFile(PATH_TO_FILE);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_wrongPath_NotOk() {
        fileReader.readFromFile(WRONG_PATH_TO_FILE);
    }
}
