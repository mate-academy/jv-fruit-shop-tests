package core.basesyntax.processing;

import core.basesyntax.enums.Operation;
import core.basesyntax.handler.TransactionHandler;
import core.basesyntax.handler.impl.BalanceTransactionHandler;
import core.basesyntax.handler.impl.PurchaseTransactionHandler;
import core.basesyntax.handler.impl.ReturnTransactionHandler;
import core.basesyntax.handler.impl.SupplyTransactionHandler;
import core.basesyntax.reader.CsvFileReader;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RawDataProcessorTest {
    private static final int QUANTITY_EXAMPLE = 20;
    private static final String FRUIT_BANANA = "banana";
    private static final String FRUIT_APPLE = "apple";
    private static final String FILE_TO_READ =
            "src/test/resources/db/ReadTransactionTest";
    private CsvFileReader fileReader;
    private RawDataProcessor rawDataProcessor;

    @BeforeEach
    void setUp() {
        Map<String, TransactionHandler> map = Map.of(
                Operation.BALANCE.getCode(), new BalanceTransactionHandler(),
                Operation.SUPPLY.getCode(), new SupplyTransactionHandler(),
                Operation.PURCHASE.getCode(), new PurchaseTransactionHandler(),
                Operation.RETURN.getCode(), new ReturnTransactionHandler()
        );
        rawDataProcessor = new RawDataProcessor(map);
    }

    @Test
    void reading_notEmptyFile_Ok() {
        fileReader = new CsvFileReader(FILE_TO_READ);
        List<List<String>> actualLists = fileReader.readTransactions();

        Map<String, Integer> actualProcessMap = rawDataProcessor.process(actualLists);
        Map<String, Integer> expectedMap = Map.of(
                FRUIT_BANANA, QUANTITY_EXAMPLE,
                FRUIT_APPLE, QUANTITY_EXAMPLE
        );

        Assertions.assertEquals(expectedMap, actualProcessMap);
    }
}
