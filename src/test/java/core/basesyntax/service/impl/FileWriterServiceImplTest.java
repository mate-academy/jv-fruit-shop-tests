package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.FileWriterService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String VALID_REPORT_FILE_PATH
            = "src/test/java/core/basesyntax/resource/testReport.csv";
    private static final int TEST_FRUIT_AMOUNT_ONE = 10;
    private static final int TEST_FRUIT_AMOUNT_TWO = 20;
    private static final String TEST_FRUIT_TYPE_ONE = "avocado";
    private static final String TEST_FRUIT_TYPE_TWO = "papaya";
    private static final boolean WAS_WRITE = true;
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
        fileWriterService.write(VALID_REPORT_FILE_PATH, null);
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
        boolean actualStatus = fileWriterService.write(VALID_REPORT_FILE_PATH,
                testListWithFruits);
        assertEquals(WAS_WRITE, actualStatus);
    }
}
