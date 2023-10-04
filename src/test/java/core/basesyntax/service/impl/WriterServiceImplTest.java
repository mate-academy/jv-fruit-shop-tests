package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    public static final String INVALID_OUTPUT_PATH = "src/resources/inputTest.csv";

    @Test
    public void writeToFile_invalidPath_NotOk() {
        String fruitReport = "fruit, quantity\nbanana,20\norange,12\npineapple,3\n";
        WriterService writerService = new WriterServiceImpl();
        Assert.assertThrows(RuntimeException.class,
                () -> writerService.writeToFile(fruitReport, INVALID_OUTPUT_PATH));
    }
}
