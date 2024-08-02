package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private static final ReportGeneratorImpl reportCreatorService = new ReportGeneratorImpl();

    @Test
    void create_validData_ok() {
        Storage.fruitStorage.put("banana", 10);
        Storage.fruitStorage.put("apple", 20);
        Set<String> actual = reportCreatorService.getReport();
        Set<String> expected = new HashSet<>();
        expected.add("banana,10");
        expected.add("apple,20");
        assertEquals(expected, actual);
    }

    @Test
    void create_emptyData_ok() {
        Set<String> actual = reportCreatorService.getReport();
        Set<String> expected = new HashSet<>();
        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitStorage.clear();
    }
}
