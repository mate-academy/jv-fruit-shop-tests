package core.basesyntax;

import core.basesyntax.service.ReaderService;
import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceTest {
    private static ReaderService readerService;

    @BeforeClass
    public static void initialSetup() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFile_validPath_ok() {
        String activitiesInStore = "src/test/test-resources/test-activities.csv";
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
        List<String> result = readerService.readFile(activitiesInStore);
        Assert.assertEquals(expected, result);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_wrongPath_notOk() {
        String wrongPath = "src/test/test-resources/report.csv";
        readerService.readFile(wrongPath);
    }
}
