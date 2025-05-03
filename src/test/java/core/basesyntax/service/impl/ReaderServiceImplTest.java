package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String FILE_PATH = "src/test/resources/inputTest.csv";
    private static ReaderService readerService;

    @BeforeClass
    public static void beforeClass() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFrom_validFile_ok() {
        List<String> testList = List.of("type,fruit,quantity", "b,banana,20", "b,apple,100");
        List<String> list = readerService.readFrom(FILE_PATH);
        Assert.assertEquals(
                "Reading doesn't work correctly! Received data must be identical.",
                testList, list);
    }

    @Test(expected = RuntimeException.class)
    public void readFrom_notValidFilePath_ok() {
        readerService.readFrom("file/path");
    }
}
