package core.basesyntax.service.shop;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductServiceImplTest {
    private static final List<FruitTransaction> FRUIT_TRANSACTIONS = List.of(
            new FruitTransaction(FruitTransaction.Operation.BALANCE,
                    "banana", 20),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                    "apple", 10),
            new FruitTransaction(FruitTransaction.Operation.RETURN,
                    "banana", 5)
    );
    private static final Map<String, Integer> EXPECTED_FRUITS_RESULT = Map.of(
            "banana", 0, "apple", 0);
    private static ProductService productService;
    private Storage storage;

    @BeforeAll
    static void beforeAll() {
        productService = new ProductServiceImpl();
    }

    @BeforeEach
    public void setUp() {
        storage = new Storage();
    }

    @Test
    public void fillProducts_validTransactions_ok() {
        productService.fillProducts(FRUIT_TRANSACTIONS, storage);

        Map<String, Integer> actualFruits = storage.getFruits();

        Assertions.assertEquals(EXPECTED_FRUITS_RESULT, actualFruits,
                "The fruits in storage do not match the expected fruits. " +
                        "Verify that the fillProducts method initializes the storage correctly.");
    }

    @Test
    public void fillProducts_emptyTransactions_ok() {
        List<FruitTransaction> emptyTransactions = List.of();

        productService.fillProducts(emptyTransactions, storage);
        Map<String, Integer> actualFruits = storage.getFruits();

        Assertions.assertEquals(new HashMap<>(), actualFruits);
    }

    @Test
    public void fillProducts_nullTransactions_notOk() {
        Assertions.assertThrows(NullPointerException.class, () ->
                productService.fillProducts(null, storage));
    }

    @Test
    public void fillProducts_nullStorage_notOk() {
        Assertions.assertThrows(NullPointerException.class, () ->
                        productService.fillProducts(FRUIT_TRANSACTIONS, null),
                "Expected a NullPointerException when the storage is null.");
    }
}
