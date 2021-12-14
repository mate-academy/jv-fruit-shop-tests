package core.basesyntax.service.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import java.io.File;
import org.junit.After;
import org.junit.Test;

public class CsvFileWriterServiceTest {

    @After
    public void setUp() {
        Storage.fruits.clear();
    }

    @Test
    public void fileWriteAbsolutePath() {
        String absolutePath = new File("src/main/resources/FruitResult.csv").getAbsolutePath();
        CsvFileWriterService csvFileWriterService
                = new CsvFileWriterService("src/main/resources/FruitResult.csv",new String());
        assertTrue(new File(absolutePath).delete());
    }
}
