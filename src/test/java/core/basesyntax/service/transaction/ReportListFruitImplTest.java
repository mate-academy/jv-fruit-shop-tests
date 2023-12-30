package core.basesyntax.service.transaction;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ReportListFruitImplTest {
    private static final String DELIMITER = ",";
    private ReportListFruit reportListFruit;

    private StringBuilder builder;

    @BeforeEach
    void setUp() {
        reportListFruit = new ReportListFruitImpl();
        builder = new StringBuilder();
    }

    @Test
    void reportListIsWork_ok() {
        assertDoesNotThrow(() -> {
            builder.append(Storage.fruitsDB.entrySet().stream()
                .map(key -> key.getKey() + DELIMITER + key.getValue())
                .collect(Collectors.joining(System.lineSeparator())));
        });

    }
}
