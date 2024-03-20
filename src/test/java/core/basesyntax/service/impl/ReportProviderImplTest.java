package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.StorageDao;
import core.basesyntax.service.ReportProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportProviderImplTest {
    private static final ReportProvider PROVIDER = new ReportProviderImpl();

    @BeforeEach
    public void clearStorage() {
        StorageDao.storage.clear();
    }

    @Test
    public void provideWithValidData_ok() {
        StorageDao.storage.put("apple", 100);
        Assertions.assertEquals(PROVIDER.provide(), "fruit,quantity"
                + System.lineSeparator() + "apple,100");
    }

    @Test
    public void provideWithIncorrectData_notOk() {
        StorageDao.storage.put("apple", -100);
        Throwable exception = assertThrows(RuntimeException.class,
                PROVIDER::provide);
        Assertions.assertEquals("Operation value cannot be negative", exception.getMessage());
    }
}
