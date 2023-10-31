package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static final String INVALID_OUTPUT_PATH = "src/resources/inputTest.csv";
    private static WriterService writerService;

    @BeforeAll
    public static void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void writeToFile_invalidPath_NotOk() {
        String fruitReport = "fruit, quantity\nbanana,20\norange,12\npineapple,3\n";
        Assert.assertThrows(RuntimeException.class,
                () -> writerService.writeToFile(fruitReport, INVALID_OUTPUT_PATH));
    }
}
