package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.DataConvertService;
import core.basesyntax.service.DataProcessService;
import core.basesyntax.service.ReadFromCsvFileService;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConvertServiceImplTest {
    private static final String FOURTH_FILENAME = "fruitsWithException.csv";
    private static final String APPLE_NAME = "apple";
    private static final String BANANA_NAME = "banana";
    private static final Integer APPLE_COUNT = 15;
    private static final Integer BANANA_COUNT = 20;
    private static DataConvertService dataConverter;
    private static DataProcessService dataProcessor;
    private static ReadFromCsvFileService csvReader;

    @BeforeEach
    void setUp() {
        csvReader = new ReadFromCsvFileServiceImpl();
    }

    @BeforeAll
    static void beforeAll() {
        dataConverter = new DataConvertServiceImpl();
        dataProcessor = new DataProcessServiceImpl();
    }

    @Test
    void dataConvert_correctValues_Ok() {
        FruitTransaction first =
                new FruitTransaction(Operation.BALANCE, APPLE_NAME, APPLE_COUNT);
        FruitTransaction second =
                new FruitTransaction(Operation.BALANCE, BANANA_NAME, BANANA_COUNT);
        FruitTransaction third =
                new FruitTransaction(Operation.SUPPLY, APPLE_NAME, 10);
        FruitTransaction fourth =
                new FruitTransaction(Operation.SUPPLY, BANANA_NAME, 5);
        FruitTransaction fifth =
                new FruitTransaction(Operation.PURCHASE, APPLE_NAME, 10);
        FruitTransaction sixth =
                new FruitTransaction(Operation.PURCHASE, BANANA_NAME, 15);
        FruitTransaction seventh =
                new FruitTransaction(Operation.RETURN, APPLE_NAME, 5);
        FruitTransaction eighth =
                new FruitTransaction(Operation.RETURN, BANANA_NAME, 10);

        List<String> args = List.of("b,apple,15", "b,banana,20",
                "s,apple,10", "s,banana,5",
                "p,apple,10", "p,banana,15",
                "r,apple,5", "r,banana,10");

        List<FruitTransaction> expected = List.of(first, second, third,
                fourth, fifth, sixth, seventh, eighth);
        List<FruitTransaction> actual = dataConverter.convert(args);

        assertEquals(expected, actual);
    }

    @Test
    void dataProcessor_lessThenZero_NotOk() {
        assertThrows(RuntimeException.class, () -> dataProcessor.processFruits(
                dataConverter.convert(csvReader.readFile(FOURTH_FILENAME))));
    }

    @AfterEach
    void tearDown() {
        Storage.fruitsCount.clear();
    }
}
