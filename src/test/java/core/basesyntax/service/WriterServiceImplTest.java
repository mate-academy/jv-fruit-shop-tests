package core.basesyntax.service;

import core.basesyntax.service.inter.WriteService;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static WriteService writeService;
    private static String report;

    @BeforeClass
    public static void beforeClass() throws Exception {
        writeService = new WriterServiceImpl();
    }

    @Test
    public void correctPath_Ok() {
        report = "banana, 47";
        writeService.writeToFile("src/test/java/resources/output.csv",
                report);
    }

    @Test (expected = RuntimeException.class)
    public void incorrectPath_NotOk() {
        report = "apple, 33";
        writeService.writeToFile("src/java/resources/output.csv",
                report);
    }
}
