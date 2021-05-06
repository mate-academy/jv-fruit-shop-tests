package core.basesyntax.services.impl;

import core.basesyntax.services.FileReaderService;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String CORRECT_FILE_PATH = "src/test/resources/input.csv";
    private static final String INCORRECT_FILE_PATH = "src/test/resources/wrongInput.csv";
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void read_TestCorrectInput() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20", "b,apple,100",
                "s,banana,100", "p,banana,13", "r,apple,10", "p,apple,20", "p,banana,5",
                "s,banana,50");
        List<String> actual = fileReaderService.read(CORRECT_FILE_PATH);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_TestIncorrectInput() {
        fileReaderService.read(INCORRECT_FILE_PATH);
    }
}
