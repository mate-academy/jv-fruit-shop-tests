package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.FileReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceTest {
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test (expected = RuntimeException.class)
    public void readWrongPathFile_notOk() {
        String wrongPath = "THE_WRONG_WAY";
        fileReaderService.read(wrongPath);
    }

    @Test
    public void readFile_ok() {
        List<String> expected = new ArrayList<>();
        String fileTest = "src/test/resources/testInputFile";
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        List<String> actual = fileReaderService.read(fileTest);
        assertEquals(expected, actual);
    }
}
