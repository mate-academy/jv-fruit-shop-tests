package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.FileReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceTest {
    private static FileReaderService fileReaderService;
    private static final String INPUT_FILE = "src/test/resources/testInputFile";

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
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        List<String> actual = fileReaderService.read(INPUT_FILE);
        assertEquals(expected, actual);
    }
}
