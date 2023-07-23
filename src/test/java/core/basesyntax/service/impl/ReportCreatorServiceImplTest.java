package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReportCreatorService;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReportCreatorServiceImplTest {
    private static final List<FruitTransaction> VALID_LIST_OF_TRANSACTIONS =
            List.of(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                    new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                    new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                    new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
                    new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
                    new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20));
    private final ReportCreatorService<FruitTransaction> reportCreatorService =
            new ReportCreatorServiceImpl();

    @Test
    void create_validListOfTransactions_noExceptions() {
        String actual = reportCreatorService.create(VALID_LIST_OF_TRANSACTIONS);
        String expected = "banana,107" + System.lineSeparator()
                        + "apple,90" + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @Test
    void create_checkForNullInputList_throwRuntimeException() {
        var runtimeException = assertThrows(RuntimeException.class,
                () -> reportCreatorService.create(null));
        assertEquals("The input list of fruit transactions is null!",
                runtimeException.getMessage());
    }
}
