package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String VALID_INPUT_FILEPATH =
            "src/test/java/core/basesyntax/resources/test_Input.csv";
    private static final String INVALID_INPUT_FILEPATH = "";
    private static final String EMPTY_INPUT_FILEPATH =
            "src/test/java/core/basesyntax/resources/test_empty.csv";
    private static ReaderService readerService;

    @Before
    public void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void read_validFile_Ok() {
        List<String> expected = List.of("type,fruit,quantity",
                "b,banana,10",
                "b,apple,50",
                "s,banana,50",
                "p,banana,5",
                "r,apple,15",
                "p,apple,35",
                "p,banana,10",
                "s,banana,40");
        List<String> actual = readerService.fileReader(VALID_INPUT_FILEPATH);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_invalidFile_notOk() {
        readerService.fileReader(INVALID_INPUT_FILEPATH);
    }

    @Test(expected = RuntimeException.class)
    public void read_emptyFile_notOk() {
        readerService.fileReader(EMPTY_INPUT_FILEPATH);
    }
}
