package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String REPORT_FILE_PATH_ACTUAL
            = "src/test/java/core/basesyntax/resource/testReportOne.csv";
    private static final String REPORT_FILE_PATH_EXPECTED
            = "src/test/java/core/basesyntax/resource/testReportTwo.csv";
    private static final int TEST_FRUIT_AMOUNT_ONE = 10;
    private static final int TEST_FRUIT_AMOUNT_TWO = 20;
    private static final String TEST_FRUIT_TYPE_ONE = "avocado";
    private static final String TEST_FRUIT_TYPE_TWO = "papaya";
    private static FileWriterService fileWriterService;
    private static List<Fruit> testListWithFruits;

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Before
    public void setUp() {
        Storage.fruits.clear();
    }

    @Test(expected = RuntimeException.class)
    public void write_reportFilePathNull_notOk() {
        fileWriterService.write(null, testListWithFruits);
    }

    @Test(expected = RuntimeException.class)
    public void write_ListForReportNull() {
        fileWriterService.write(REPORT_FILE_PATH_ACTUAL, null);
    }

    @Test
    public void write_reportFilePath_ok() {
        testListWithFruits = new ArrayList<>();
        Fruit testFruitOne = new Fruit();
        testFruitOne.setFruitType(TEST_FRUIT_TYPE_ONE);
        testFruitOne.setAmount(TEST_FRUIT_AMOUNT_ONE);
        Fruit testFruitTwo = new Fruit();
        testFruitTwo.setFruitType(TEST_FRUIT_TYPE_TWO);
        testFruitTwo.setAmount(TEST_FRUIT_AMOUNT_TWO);
        testListWithFruits.add(testFruitOne);
        testListWithFruits.add(testFruitTwo);
        fileWriterService.write(REPORT_FILE_PATH_ACTUAL, testListWithFruits);
        List<String> actualList = null;
        List<String> expectedList = null;
        try {
            actualList = Files.readAllLines(Paths.get(REPORT_FILE_PATH_ACTUAL));
            expectedList = Files.readAllLines(Paths.get(REPORT_FILE_PATH_EXPECTED));
        } catch (IOException e) {
            throw new RuntimeException("Can`t read file by the path", e);
        }
        assertEquals(expectedList, actualList);
    }
}
