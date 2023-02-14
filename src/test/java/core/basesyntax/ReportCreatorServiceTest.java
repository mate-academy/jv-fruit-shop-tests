package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.db.StorageService;
import core.db.StorageServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReportCreatorServiceTest {
    private static StorageService testStorageService;

    @BeforeAll
    static void setUp() {
        testStorageService = new StorageServiceImpl();
        testStorageService.setFruitQuantity("banana", 6);
        testStorageService.setFruitQuantity("apple", 16);
    }

    @Test
    void getLeftovers() {
        assertEquals(22, testStorageService.getLeftovers().get("banana"),
                "Error in leftovers banana");
        assertEquals(30, testStorageService.getLeftovers().get("apple"),
                "Error in leftovers apple");
    }
}
