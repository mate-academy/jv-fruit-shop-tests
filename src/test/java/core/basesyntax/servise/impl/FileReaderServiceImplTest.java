package core.basesyntax.servise.impl;

import core.basesyntax.servise.FileReaderService;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String VALID_FILEPATH = "src/resources/daily_records.csv";
    private static final String INVALID_FILEPATH = "/daily_records.csv";
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void init() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void read_validFilePath_Ok() {
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,100",
                "b,apple,100",
                "s,banana,100",
                "p,banana,150",
                "r,banana,50",
                "s,apple,50",
                "p,apple,100",
                "r,apple,50"
        );

        List<String> actual = fileReaderService.read(VALID_FILEPATH);

        Assert.assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void read_invalidFilePath_notOk() {
        fileReaderService.read(INVALID_FILEPATH);
    }

    @Test (expected = RuntimeException.class)
    public void read_nullFilePath_notOk() {
        fileReaderService.read(null);
    }
}
