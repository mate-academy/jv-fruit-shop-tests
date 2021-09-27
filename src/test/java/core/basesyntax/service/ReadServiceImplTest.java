package core.basesyntax.service;

import core.basesyntax.service.inter.ReadService;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReadServiceImplTest {
    private static ReadService readService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        readService = new ReadServiceImpl();
    }

    @Test
    public void correctPath_Ok() throws RuntimeException {
        readService.readFromFile("src/test/java/resources/input.csv");
    }

    @Test(expected = RuntimeException.class)
    public void incorrectPath_NotOk() throws RuntimeException {
        readService.readFromFile("src/test/resources/input.csv");
    }
}
