package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CsvFileWriteServiceImplTest {
    private static final FileWriterService fileWriterService = new CsvFileWriteServiceImpl();
    private static final List<Fruit> fruits = List.of(
            new Fruit("apple", 90), new Fruit("banana", 152),
            new Fruit("melon", 20), new Fruit("orange", 100));

    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp() {
        Storage.fruits.addAll(fruits);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void writeBalanceToFile_nullFruitInStorage_NotOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Fruit cannot be null");
        Storage.fruits.add(null);
        fileWriterService.writeBalanceToFile("src/test/resources/output.csv");
    }

    @Test
    public void writeBalanceToFile_nullFruitNameInStorage_NotOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Fruit name cannot be null");
        Storage.fruits.add(new Fruit(null, 10));
        fileWriterService.writeBalanceToFile("src/test/resources/output.csv");
    }

    @Test
    public void writeBalanceToFile_validFruitsInStorage_Ok() {
        String expectedResult = "fruit,quantity" + System.lineSeparator()
                + "apple,90" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "melon,20" + System.lineSeparator()
                + "orange,100";
        String filePath = "src/test/resources/outputValidBalance.csv";
        fileWriterService.writeBalanceToFile(filePath);
        String actualResult = readFromFile(filePath);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void writeBalanceToFile_emptyFruitStorage_Ok() {
        Storage.fruits.clear();
        String expectedResult = "fruit,quantity";
        String filePath = "src/test/resources/outputEmptyBalance.csv";
        fileWriterService.writeBalanceToFile(filePath);
        String actualResult = readFromFile(filePath);
        assertEquals(expectedResult, actualResult);
    }

    private String readFromFile(String filePath) {
        try {
            return Files.readString(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Cannot read data from file: " + filePath, e);
        }
    }
}
