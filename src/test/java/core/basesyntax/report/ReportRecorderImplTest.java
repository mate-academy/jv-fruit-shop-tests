package core.basesyntax.report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReportRecorderImplTest {
    private static final ReportRecorder REPORT_RECORDER = new ReportRecorderImpl();
    private static final Map<String, Integer> VALID_MAP = new HashMap<>();
    private static final Map<String, Integer> EMPTY_MAP = new HashMap<>();
    private static final List<String> VALID_OUTPUT = List.of("banana=20",
            System.lineSeparator(), "apple=80", System.lineSeparator());
    private static final List<String> EMPTY_OUTPUT = new ArrayList<>();

    static {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 20);
        FruitTransaction fruitTransaction2 = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 80);
        VALID_MAP.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
        VALID_MAP.put(fruitTransaction2.getFruit(), fruitTransaction2.getQuantity());
    }

    @Test
    void getStorageData_validInput_isOk() {
        assertEquals(VALID_OUTPUT,
                REPORT_RECORDER.getStorageData(VALID_MAP));
    }

    @Test
    void getStorageData_emptyInput_isOk() {
        assertEquals(EMPTY_OUTPUT,
                REPORT_RECORDER.getStorageData(EMPTY_MAP));
    }
}
