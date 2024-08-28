package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportServiceImplTest {
    private static final List<FruitTransaction> TRANSACTIONS_TO_ADD = List.of(
            new FruitTransaction(FruitTransaction.Operation.BALANCE,
                    "banana",
                    20),
            new FruitTransaction(FruitTransaction.Operation.BALANCE,
                    "apple",
                    100)
    );
    private static final String FILLED_REPORT = "fruit,quantity"
            + System.lineSeparator()
            + "banana,20"
            + System.lineSeparator()
            + "apple,100";
    private static final String EMPTY_REPORT = "fruit,quantity";
    private static ReportServiceImpl reportService;

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.storage.clear();
    }

    @Test
    void createReport_emptyStorage_ok() {
        String actual = reportService.createReport();
        assertEquals(EMPTY_REPORT, actual);
    }

    @Test
    void createReport_filledStorage_ok() {
        for (FruitTransaction transaction : TRANSACTIONS_TO_ADD) {
            Storage.storage.put(transaction.getFruit(), transaction.getQuantity());
        }
        String actual = reportService.createReport();
        assertEquals(FILLED_REPORT, actual);
    }
}
