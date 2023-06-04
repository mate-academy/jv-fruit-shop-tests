package core.basesyntax.service;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
//
public class ReaderServiceTest {
    private static final String CORRECT_FILE = "src/test/resources/activitiesTest.csv";
    private static final List<String> EXPECTED_DATA = List.of("type,fruit,quantity",
            "b,banana,43", "b,apple,82", "b,orange,30", "b,kiwi,16", "s,banana,60",
            "p,banana,12", "p,orange,10", "r,apple,8", "p,kiwi,4", "p,apple,22",
            "r,orange,6", "p,banana,6", "r,kiwi,4", "p,banana,18", "p,orange,8",
            "p,apple,12", "p,kiwi,8", "r,banana,3", "p,apple,18", "p,orange,2",
            "r,apple,4");

    @Test
    public void read_csvFile_Ok() {
        ReaderService reader = new ReaderServiceImpl();
        List<String> actualData = reader.readFromCsvFile(CORRECT_FILE);
        Assert.assertEquals("Wrong data from file: " + CORRECT_FILE,
                EXPECTED_DATA, actualData);
    }
}
