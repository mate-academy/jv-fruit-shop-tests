package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.StorageToStringService;
import org.junit.jupiter.api.Test;

class StorageToStringServiceImplTest {
    private final StorageToStringService storageToStringService = new StorageToStringServiceImpl();

    @Test
    void convert_validStorage_noException() {
        Storage.storage.put("banana", 120);
        Storage.storage.put("apple", 46);
        String actual = storageToStringService.convert();
        String expected = "fruits,quantity" + System.lineSeparator()
                        + "banana,120" + System.lineSeparator()
                        + "apple,46" + System.lineSeparator();
        assertEquals(expected, actual);;
    }

}
