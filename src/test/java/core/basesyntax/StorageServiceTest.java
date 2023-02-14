package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.db.FruitTransaction;
import core.db.StorageService;
import core.db.StorageServiceImpl;
import core.service.TransactionService;
import core.service.impl.TransactionServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class StorageServiceTest {
    private static StorageService testStorageService;

    @BeforeAll
    static void setUp() {
        TransactionService testTransactionService = new TransactionServiceImpl();
        List<String> lines = new ArrayList<>();
        lines.add("type,fruit,quantity");
        lines.add("b,banana,20");
        lines.add("b,apple,30");
        lines.add("p,banana,5");
        lines.add("s,banana,7");
        List<FruitTransaction> transactions = testTransactionService.createFromList(lines);
        testStorageService = new StorageServiceImpl();
        transactions.forEach(testStorageService::addFruit);
    }

    @Test
    void getLeftovers() {
        assertEquals(22, testStorageService.getLeftovers().get("banana"),
                "Error in leftovers banana");
        assertEquals(30, testStorageService.getLeftovers().get("apple"),
                "Error in leftovers apple");
    }

    @Test
    void getFruitQuantity() {
        assertEquals(22, testStorageService.getFruitQuantity("banana"),
                "Error in quantity banana");
        assertEquals(30, testStorageService.getFruitQuantity("apple"),
                "Error in quantity apple");
    }

}
