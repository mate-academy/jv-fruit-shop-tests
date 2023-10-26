package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Operation;
import core.basesyntax.service.DataConvertService;
import core.basesyntax.service.DataProcessService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceOperationHandlerImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandlerImpl;
import core.basesyntax.strategy.impl.ReturnOperationHandlerImpl;
import core.basesyntax.strategy.impl.SupplyOperationHandlerImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DataProcessServiceImplTest {
    private static final String FIRST_FILENAME = "fruits1.csv";
    private static final String SECOND_FILENAME = "fruits2.csv";
    private static final String THIRD_FILENAME = "fruits3.csv";
    private static final String LESS_THEN_ZERO_FILENAME = "fruitsWithLessThenZeroQuantity.csv";
    private static final String ZERO_QUANTITY_FILENAME = "zeroFruits.csv";
    private static final Map<Operation, OperationHandler> operationPicker =
            Map.of(Operation.BALANCE, new BalanceOperationHandlerImpl(),
                    Operation.PURCHASE, new PurchaseOperationHandlerImpl(),
                    Operation.RETURN, new ReturnOperationHandlerImpl(),
                    Operation.SUPPLY, new SupplyOperationHandlerImpl());
    private static ReadFromCsvFileServiceImpl reader;
    private static DataConvertService convertor;
    private static DataProcessService processor;

    @BeforeAll
    static void beforeAll() {
        reader = new ReadFromCsvFileServiceImpl();
        convertor = new DataConvertServiceImpl();
        processor = new DataProcessServiceImpl(operationPicker);
    }

    @Test
    void dataProcessor_twoFruits_Ok() {
        processor.processFruits(convertor.convert(reader.readFile(FIRST_FILENAME)));
        Map<String, Integer> expected = Map.of("banana", 152, "apple", 90);
        Map<String, Integer> actual = Storage.fruitsCount;

        assertEquals(expected, actual);
    }

    @Test
    void dataProcessor_threeFruits_Ok() {
        processor.processFruits(convertor.convert(reader.readFile(SECOND_FILENAME)));
        Map<String, Integer> expected = Map.of("pineapple", 111,
                "apple", 117, "banana", 60);
        Map<String, Integer> actual = Storage.fruitsCount;

        assertEquals(expected, actual);
    }

    @Test
    void dataProcessor_fourFruits_Ok() {
        processor.processFruits(convertor.convert(reader.readFile(THIRD_FILENAME)));
        Map<String, Integer> expected = Map.of("melon", 10,
                "apple", 100, "banana", 10, "grape", 150);
        Map<String, Integer> actual = Storage.fruitsCount;

        assertEquals(expected, actual);
    }

    @Test
    void dataProcessor_lessThenZero_NotOk() {
        assertThrows(RuntimeException.class, () ->
                processor.processFruits(convertor.convert(reader.readFile(LESS_THEN_ZERO_FILENAME))));
    }

    @Test
    void dataProcessor_zeroValues_Ok() {
        processor.processFruits(convertor.convert(reader.readFile(ZERO_QUANTITY_FILENAME)));
        Map<String, Integer> expected = Map.of("apple", 0, "banana", 0);
        Map<String, Integer> actual = Storage.fruitsCount;

        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitsCount.clear();
    }
}
