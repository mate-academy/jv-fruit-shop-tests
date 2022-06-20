package core.basesyntax.service.impl;

import core.basesyntax.service.CsvFileReaderService;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileReaderServiceImplTest {
    private static final String PATHNAME = "src/main/resources/operationsTest.csv";
    private static final String NON_EXISTING_PATHNAME = "src/main/resources/operationsNonExist.csv";
    private static CsvFileReaderService fileReaderService;

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new CsvFileReaderServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void read_pathnameIsNull_notOk() {
        fileReaderService.read(null);
    }

    @Test(expected = RuntimeException.class)
    public void read_fileIsNonExist_notOk() {
        fileReaderService.read(NON_EXISTING_PATHNAME);
    }

    @Test
    public void read_fileIsExists_ok() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,200");
        List<String> actual = fileReaderService.read(PATHNAME);
        Assert.assertEquals(expected, actual);
    }
}
