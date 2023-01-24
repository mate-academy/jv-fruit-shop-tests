package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String INPUT_FILE_PATH =
            "src/test/java/core/basesyntax/resources/data.csv";
    private static final String INCORRECT_FILE_PATH =
            "src/test/java/core/basesyntax/resources/fileAbsent.csv";
    private FileReaderService fileReaderService;

    @Before
    public void setUp() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void read_fileExists_ok() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20", "b,apple,100",
                "s,banana,100", "p,banana,13", "r,apple,10", "p,apple,20",
                "p,banana,5", "s,banana,50");
        List<String> actual = fileReaderService.read(INPUT_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_fileDoesNotExist_notOk() {
        fileReaderService.read(INCORRECT_FILE_PATH);
    }
}
