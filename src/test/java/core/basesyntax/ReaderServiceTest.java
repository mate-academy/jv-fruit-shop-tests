package core.basesyntax;

import core.basesyntax.service.ReaderService;
import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceTest {
    private static final String VALID_FILE_NAME = "src/test/resources/testfile.csv";
    private static final String INVALID_FILE_NAME = "src/test/resources/ababagalamaga.geometrydash";
    private static final String EMPTY_FILE_NAME = "src/test/resources/emptyfile.csv";
    private static ReaderService readerService;

    @BeforeClass
    public static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFile_ok() {
        List<String> actual = readerService.readFile(VALID_FILE_NAME);
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,apple,10",
                "b,banana,1",
                "b,orange,102",
                "b,grapefruit,102",
                "s,apple,100",
                "s,grapefruit,1001",
                "s,banana,50",
                "s,orange,3",
                "p,grapefruit,20",
                "p,apple,50",
                "p,banana,1",
                "p,orange,100",
                "r,apple,5",
                "r,banana,12",
                "r,orange,1",
                "r,grapefruit,2");
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readNotExistingFile_shouldThrowRuntimeException_notOk() {
        readerService.readFile(INVALID_FILE_NAME);
    }

    @Test
    public void readEmptyFile_ok() {
        List<String> actual = readerService.readFile(EMPTY_FILE_NAME);
        List<String> expected = List.of();
        Assert.assertEquals(expected, actual);
    }
}
