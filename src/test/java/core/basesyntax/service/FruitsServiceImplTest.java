package core.basesyntax.service;

import core.basesyntax.db.FruitsStorage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class FruitsServiceImplTest {
    private FruitsService fruitsService = new FruitsServiceImpl();

    @After
    public void afterEachTest() {
        FruitsStorage.fruits.clear();
    }

    @Test
    public void generateFruitsReportWithDifferentTypesOfFruits_Ok() {
        FruitsStorage.fruits.put("banana", 100);
        FruitsStorage.fruits.put("orange", 70);
        FruitsStorage.fruits.put("apple", 50);
        String actualResult = fruitsService.generateFruitsReport(FruitsStorage.fruits);
        String expectedResult = "fruit,quantity" + System.lineSeparator()
                + "banana,100" + System.lineSeparator()
                + "orange,70" + System.lineSeparator()
                + "apple,50";
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void generateFruitsReportWithoutFruits_Ok() {
        String actualResult = fruitsService.generateFruitsReport(FruitsStorage.fruits);
        String expectedResult = null;
        Assert.assertEquals(expectedResult, actualResult);
    }
}
