package core.basesyntax;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class BalanceCalculatorServiceTest {

    @AfterEach
    public void afterEachTest() {
        Storage.records.clear();
    }

    @Test
    public void testCalculate() {
    }
}
