package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Test;

public class CsvFileReaderServiceTest {

    @After
    public void setUp() {
        Storage.fruits.clear();
    }

    @Test
    public void fileReadAbsolutePath() {
        CsvFileReaderService csvFileReaderService = new CsvFileReaderService();
        List<String> operations = new ArrayList<>();
        csvFileReaderService.readData("src/main/resources/FruitOperateDay.csv");
        assertEquals(new ArrayList<>(),operations);
    }
}
