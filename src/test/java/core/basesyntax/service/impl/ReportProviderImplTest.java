package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.StorageDao;
import core.basesyntax.service.ReportProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportProviderImplTest {
    private ReportProvider provider;

    @BeforeEach
    public void setUp() {
        provider = new ReportProviderImpl();
        StorageDao.storage.clear();
    }

    @Test
    public void provideWithValidData_ok() {
        StorageDao.storage.put("apple", 100);
        assertEquals(provider.provide(), "fruit,quantity"
                + System.lineSeparator() + "apple,100");
    }

    @Test
    public void provideWithIncorrectData_notOk() {
        StorageDao.storage.put("apple", -100);
        Throwable exception = assertThrows(RuntimeException.class,
                provider::provide);
        assertEquals("Operation value cannot be negative", exception.getMessage());
    }
}
