package core.basesyntax.service.impl;

import core.basesyntax.service.FileReaderService;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String PATH_VALID_DATA = "src/test/resources/test1.csv";
    private static final String PATH_INVALID_DATA = "src/test/resources";
    private static final String EMPTY_DATA = "src/test/resources/test2.csv";
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void readToList_validData_Ok() {
        List<String> expected = List
                .of("b,banana,20",
                        "b,apple,100",
                        "s,banana,100",
                        "p,banana,13",
                        "r,apple,10",
                        "p,apple,20",
                        "p,banana,5",
                        "s,banana,50"
                );
        List<String> actual = fileReaderService.readToList(PATH_VALID_DATA);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void readToList_emptyFile_Ok() {
        List<String> expected = List.of();
        List<String> actual = fileReaderService.readToList(EMPTY_DATA);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void readToList_nullPath_notOK() {
        fileReaderService.readToList(null);
    }

    @Test(expected = RuntimeException.class)
    public void readToList_invalidPath_notOK() {
        fileReaderService.readToList(PATH_INVALID_DATA);
    }
}
