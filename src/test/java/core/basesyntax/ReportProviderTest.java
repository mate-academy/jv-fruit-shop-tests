package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.NullDataException;
import core.basesyntax.service.ReportProvider;
import core.basesyntax.service.impl.ReportProviderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportProviderTest {
    private static final String FRUIT_NAME = "apple";
    private static final int QUANTITY = 10;
    private static final String SEPARATOR = ",";
    private static ReportProvider reportProvider;

    @BeforeEach
    void init() {
        reportProvider = new ReportProviderImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.fruitStorage.clear();
    }

    @Test
    void provide_casualData_Ok() {
        Storage.fruitStorage.put(FRUIT_NAME, QUANTITY);
        String exceptedResult = "fruit,quantity"
                + System.lineSeparator()
                + FRUIT_NAME
                + SEPARATOR
                + QUANTITY;
        String result = reportProvider.provide();
        assertEquals(exceptedResult, result,
                "The results must be the same");
    }

    @Test
    void provide_nullKey_notOk() {
        Storage.fruitStorage.put(null, QUANTITY);
        assertThrows(NullDataException.class,
                () -> reportProvider.provide(),
                "The data fruit name can't be null");
    }
}
