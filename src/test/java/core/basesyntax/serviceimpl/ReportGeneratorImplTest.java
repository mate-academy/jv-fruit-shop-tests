package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator<List<String>> reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        Storage.fruitStorage.put("banana", 125);
        Storage.fruitStorage.put("apple", 90);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test
    void getReport_correct_ok() {
        List<String> expected = List.of("banana,125", "apple,90");
        List<String> actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }

    @Test
    void getReport_emptyStorage_notOk() {
        Storage.fruitStorage.clear();
        assertThrows(IllegalStateException.class, () -> {
            reportGenerator.getReport();
        });
    }
}
