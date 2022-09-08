package core.basesyntax.services.impl;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String PATH_EMPTY_FILE =
            "src/test/java/core/basesyntax/resources/input_transactions_empty.csv";
    private static final String PATH_TRANSACTIONS =
            "src/test/java/core/basesyntax/resources/input_transactions.csv";
    private static FileReaderServiceImpl fileReaderService;

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test (expected = RuntimeException.class)
    public void fileReader_incorrectFile_notOk() {
        fileReaderService.read("incorrect/file/path");
    }

    @Test (expected = RuntimeException.class)
    public void fileReader_null_notOk() {
        fileReaderService.read(null);
    }

    @Test
    public void fileReader_emptyFile_Ok() {
        List<String> expected = new ArrayList<>();
        List<String> actual = fileReaderService.read(PATH_EMPTY_FILE);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void fileReader_equalsData_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("b,strawberry,800");
        expected.add("b,blueberry,200");
        expected.add("s,banana,100");
        Assert.assertEquals(fileReaderService.read(PATH_TRANSACTIONS), expected);
    }
}
