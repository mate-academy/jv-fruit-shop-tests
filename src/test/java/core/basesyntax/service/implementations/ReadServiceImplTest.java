package core.basesyntax.service.implementations;

import core.basesyntax.service.inerfaces.ReadService;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReadServiceImplTest {
    private static ReadService read;
    private static final String RIGHT_DATA_ROUTE
            = "src/test/resources/RightInput.csv";
    private static final String EMPTY_FILE_ROUTE
            = "src/test/resources/EmptyFile.csv";
    private static final String WRONG_ROUTE
            = "src/test/resources/EmptyFile2222.csv";

    @Before
    public void beforeEach() {
        read = new ReadServiceImpl();
    }

    @Test
    public void readFile_rightData_Ok() {
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "s,banana,50"
        );
        List<String> actual = read.readFile(RIGHT_DATA_ROUTE);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void readFile_EmptyFile_NotOk() {
        List<String> expected = List.of();
        List<String> actual = read.readFile(EMPTY_FILE_ROUTE);
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void readFile_IncorrectRoute_NotOk() {
        read.readFile(WRONG_ROUTE);
    }
}
