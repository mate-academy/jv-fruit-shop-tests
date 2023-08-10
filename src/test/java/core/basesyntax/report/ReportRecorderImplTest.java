package core.basesyntax.report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReportRecorderImplTest {
    private static ReportRecorder reportRecorder = new ReportRecorderImpl();
    private static final Map<String, Integer> VALID_MAP = Map.of("apple", 80, "banana", 20);
    private static final Map<String, Integer> EMPTY_MAP = new HashMap<>();
    private static final List<String> VALID_OUTPUT = List.of("banana=20",
            System.lineSeparator(), "apple=80", System.lineSeparator());
    private static final List<String> EMPTY_OUTPUT = new ArrayList<>();

    @Test
    void getStorageData_validInput_isOk() {
        assertEquals(VALID_OUTPUT,
                reportRecorder.getStorageData(VALID_MAP));
    }

    @Test
    void getStorageData_emptyInput_isOk() {
        assertEquals(EMPTY_OUTPUT,
                reportRecorder.getStorageData(EMPTY_MAP));
    }
}
