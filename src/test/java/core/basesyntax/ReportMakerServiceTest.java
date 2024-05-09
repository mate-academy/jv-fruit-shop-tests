package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.DaoStorage;
import core.basesyntax.exception.ReportMakerServiceException;
import core.basesyntax.servise.ReportMakerService;
import core.basesyntax.servise.impl.ReportMakerServiceImpl;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportMakerServiceTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String FRUIT = "banana";
    private static final int QUANTITY = 100;
    private static final String TITLE = "fruit,quantity" + LINE_SEPARATOR;
    private static final String SEPARATOR = ",";
    private static Map<String, Integer> storageForTest;
    private static ReportMakerService reportMakerService;

    @BeforeAll
    public static void setUp() {
        storageForTest = new HashMap<>();

        DaoStorage daoStorageForTest = new DaoStorage() {
            @Override
            public void setNewValue(String fruit, Integer quantity) {
                storageForTest.put(fruit, quantity);
            }

            @Override
            public void concatenateValue(String fruit, Integer quantity) {
                storageForTest.merge(fruit, quantity, Integer::sum);
            }

            @Override
            public int getValue(String fruit) {
                return storageForTest.get(fruit);
            }

            @Override
            public Set<Map.Entry<String, Integer>> getStatistic() {
                return storageForTest.entrySet();
            }

            @Override
            public void clear() {
                storageForTest.clear();
            }
        };

        reportMakerService = new ReportMakerServiceImpl(daoStorageForTest);
    }

    @BeforeEach
    public void beforeTest() {
        storageForTest.clear();
    }

    @Test
    public void reportMakerService_daoStorageNull_notOk() {
        assertThrows(ReportMakerServiceException.class,
                () -> new ReportMakerServiceImpl(null));
    }

    @Test
    public void reportMakerService_generateReport_Ok() {
        storageForTest.put(FRUIT, QUANTITY);
        String actual = reportMakerService.generateReport();
        assertAll("Test failed! Report format isn`t correct",
                () -> assertTrue(actual.startsWith(TITLE)),
                () -> assertTrue(actual.contains(FRUIT + SEPARATOR + QUANTITY))
        );
    }

    @Test
    public void reportMakerService_storageNull_notOk() {
        assertThrows(ReportMakerServiceException.class,
                () -> reportMakerService.generateReport());
    }
}
