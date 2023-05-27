package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Storage;
import core.basesyntax.process.FruitReportMaker;
import core.basesyntax.process.FruitReportMakerImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitReportMakerTests {
    private static final String EXPECTED_REPORT =
            "fruits, quantity" + System.lineSeparator()
            + "banana,100" + System.lineSeparator()
            + "apple,24";
    private static FruitReportMaker fruitReportMaker;
    private Storage storage;

    @BeforeAll
    public static void beforeAll() {
        fruitReportMaker = new FruitReportMakerImpl();
    }

    @BeforeEach
    public void setUp() {
        storage = new Storage();
    }

    @Test
    public void makeFruitReport_twoFruitsInStorage_ok() {
        Storage.fruits.put("banana", 100);
        Storage.fruits.put("apple", 24);
        String report = fruitReportMaker.makeFruitReport();
        assertEquals(EXPECTED_REPORT, report);
    }

    @Test
    public void makeFruitReport_emptyStorage_notOk() {
        assertThrows(RuntimeException.class, () -> {
            fruitReportMaker.makeFruitReport();
        });
    }
}
