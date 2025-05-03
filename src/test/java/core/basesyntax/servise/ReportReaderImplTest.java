package core.basesyntax.servise;

import core.basesyntax.servise.inrterfase.ReportReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportReaderImplTest {
    private static final String DIRECT_TO_EXIST_FILE
            = "src/test/resources/inputReaderTest.csv";
    private static final String DIRECT_TO_NOT_EXIST_FILE
            = "src/test/resources/inputReade.csv";
    private ReportReader reader;

    @Before
    public void setUp() throws Exception {
        reader = new ReportReaderImpl();
    }

    @Test
    public void readFile_ExistFile_Ok() {
        List<String> expectedResult = new ArrayList<>();
        expectedResult.add("type,fruit,quantity");
        expectedResult.add("b,banana,20");
        expectedResult.add("b,apple,100");
        expectedResult.add("s,banana,100");
        expectedResult.add("p,banana,13");
        expectedResult.add("r,apple,10");
        expectedResult.add("p,apple,20");
        expectedResult.add("p,banana,5");
        expectedResult.add("s,banana,50");

        reader.readFile(DIRECT_TO_EXIST_FILE);
        List<String> actualResult = reader.readFile(DIRECT_TO_EXIST_FILE);
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test (expected = RuntimeException.class)
    public void readFile_NotExistFile_NotOk() {
        reader.readFile(DIRECT_TO_NOT_EXIST_FILE);
    }
}
