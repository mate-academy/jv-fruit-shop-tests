package core.basesyntax.handler;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.enums.Operation;
import core.basesyntax.exceptions.NegativeFruitBalanceException;
import core.basesyntax.handler.impl.BalanceTransactionHandler;
import core.basesyntax.handler.impl.PurchaseTransactionHandler;
import core.basesyntax.handler.impl.ReturnTransactionHandler;
import core.basesyntax.handler.impl.SupplyTransactionHandler;
import core.basesyntax.processing.RawDataProcessor;
import core.basesyntax.reader.CsvFileReader;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionHandlerTest {
    private static final int ZERO_INDEX_POSITION = 0;
    private static final int FIRST_INDEX_POSITION = 1;
    private static final int SECOND_INDEX_POSITION = 2;
    private static final int THIRD_INDEX_POSITION = 3;
    private static final int FOURTH_INDEX_POSITION = 4;
    private static final int SEVENTH_INDEX_POSITION = 7;
    private static final String FILE_WITH_DIFFERENT_TYPES =
            "src/test/resources/db/ReadTransactionWithDifferentTypes";
    private static final String FILE_WITH_NEGATIVE_BALANCE =
            "src/test/resources/db/fileWithNegativeBalance";

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
        fileReader = new CsvFileReader(FILE_WITH_DIFFERENT_TYPES);
    }

    @Test
    void checkTypesOperation_Ok() {

        List<List<String>> actualLists = fileReader.readTransactions();

        Assertions.assertEquals(
                Operation.BALANCE.getCode(),
                actualLists.get(ZERO_INDEX_POSITION).get(ZERO_INDEX_POSITION)
        );
        Assertions.assertEquals(
                Operation.BALANCE.getCode(),
                actualLists.get(FIRST_INDEX_POSITION).get(ZERO_INDEX_POSITION)
        );
        Assertions.assertEquals(
                Operation.SUPPLY.getCode(),
                actualLists.get(SECOND_INDEX_POSITION).get(ZERO_INDEX_POSITION)
        );
        Assertions.assertEquals(
                Operation.PURCHASE.getCode(),
                actualLists.get(THIRD_INDEX_POSITION).get(ZERO_INDEX_POSITION)
        );
        Assertions.assertEquals(
                Operation.RETURN.getCode(),
                actualLists.get(FOURTH_INDEX_POSITION).get(ZERO_INDEX_POSITION)
        );
        Assertions.assertEquals(
                Operation.SUPPLY.getCode(),
                actualLists.get(SEVENTH_INDEX_POSITION).get(ZERO_INDEX_POSITION)
        );
    }

    @Test
    void processWithNegativeBalance_NotOk() {
        fileReader = new CsvFileReader(FILE_WITH_NEGATIVE_BALANCE);
        List<List<String>> actualLists = fileReader.readTransactions();

        assertThrows(NegativeFruitBalanceException.class,
                () -> rawDataProcessor.process(actualLists));
    }
}
