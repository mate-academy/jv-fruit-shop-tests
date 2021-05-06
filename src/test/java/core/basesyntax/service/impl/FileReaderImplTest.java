package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static FileReaderService fileReaderService;
    private static final String FILE_PATH = "src/test/java/resources/shop_operation.csv";
    private static final String WRONG_PATH = "/java!/resources/shop_operation.csv";

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderImpl();
    }

    @Test
    public void readFromFileWithCorrectInputPath_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        List<String> actual = fileReaderService.readFromFile(FILE_PATH);
        assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFileTestWithWrongInputPath_NotOk() {
        fileReaderService.readFromFile(WRONG_PATH);
    }
}
