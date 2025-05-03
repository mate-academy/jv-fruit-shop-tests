package core.basesyntax.db.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.BalanceStorageDao;
import core.basesyntax.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryBalanceStorageDaoTest {
    private final BalanceStorageDao balanceStorage = InMemoryBalanceStorageDao.getInstance();
    private final Product product = new Product("product");
    private final Product product2 = new Product("product2");

    @BeforeEach
    void setUp() {
        balanceStorage.clear();
    }

    @Test
    void getInstance() {
        assertTrue(
                InMemoryBalanceStorageDao.getInstance() == InMemoryBalanceStorageDao.getInstance()
        );
    }

    @Test
    void save_Ok() {
        balanceStorage.save(product, 5);
        assertEquals(1, balanceStorage.getAll().size());
        assertEquals(5, balanceStorage.getQuantity(product));

        balanceStorage.save(product, 8);
        assertEquals(1, balanceStorage.getAll().size());
        assertEquals(8, balanceStorage.getQuantity(product));

        balanceStorage.save(product2, 45);
        assertEquals(2, balanceStorage.getAll().size());
        assertEquals(45, balanceStorage.getQuantity(product2));
    }

    @Test
    void getQuantity_Ok() {
        balanceStorage.save(product, 5);
        assertEquals(5, balanceStorage.getQuantity(product));
    }

    @Test
    void getQuantity_notExist_zero() {
        assertEquals(0, balanceStorage.getQuantity(product));
    }

    @Test
    void getAll_Ok() {
        balanceStorage.save(product, 5);
        balanceStorage.save(product2, 15);
        assertEquals(2, balanceStorage.getAll().size());
    }

    @Test
    void getAll_empty_Ok() {
        assertEquals(0, balanceStorage.getAll().size());
    }

    @Test
    void remove_Ok() {
        balanceStorage.save(product, 5);
        assertEquals(5, balanceStorage.remove(product));
        assertEquals(0, balanceStorage.getAll().size());
    }

    @Test
    void remove_notExist_Ok() {
        assertEquals(0, balanceStorage.remove(product));
        assertEquals(0, balanceStorage.getAll().size());
    }

    @Test
    void clear_Ok() {
        balanceStorage.save(product, 5);
        balanceStorage.clear();
        assertEquals(0, balanceStorage.getAll().size());
    }
}
