package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StorageServiceTest {
    private static final String DEFAULT_FRUIT = "apple";
    private static final int DEFAULT_QUANTITY = 66;
    private static final int QUANTITY_TO_SET = 34;
    private static final int MORE_THEN_HAVE_QUANTITY = 69;
    private static final int NEGATIVE_QUANTITY = -22;
    private final StorageService storageService = new StorageServiceImpl();

    @BeforeEach
    public void beforeEach() {
        Storage.setFruitStorage(new TreeMap<>(Map.of(DEFAULT_FRUIT, DEFAULT_QUANTITY)));
    }

    @Test
    public void storageService_SetValidData_Ok() {
        storageService.setBalance(DEFAULT_FRUIT, QUANTITY_TO_SET);
        assertEquals(Storage.getFruitStorage().get(DEFAULT_FRUIT), QUANTITY_TO_SET);
    }

    @Test
    public void storageService_SetNegativeBalance_NotOk() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            storageService.setBalance(DEFAULT_FRUIT,NEGATIVE_QUANTITY);
        });
    }

    @Test
    public void storageService_RemoveValidData_Ok() {
        storageService.removeFruit(DEFAULT_FRUIT, DEFAULT_QUANTITY);
        assertEquals(0, Storage.getFruitStorage().get(DEFAULT_FRUIT));
    }

    @Test
    public void storageService_NegativeRemoveData_NotOk() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            storageService.removeFruit(DEFAULT_FRUIT,NEGATIVE_QUANTITY);
        });
    }

    @Test
    public void storageService_NotEnoughFruit_NotOk() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            storageService.removeFruit(DEFAULT_FRUIT,MORE_THEN_HAVE_QUANTITY);
        });
    }

    @Test
    public void storageService_AddValidData_Ok() {
        storageService.addFruit(DEFAULT_FRUIT, DEFAULT_QUANTITY);
        assertEquals(DEFAULT_QUANTITY, Storage.getFruitStorage().get(DEFAULT_FRUIT));
    }

    @Test
    public void storageService_AddNegativeData_NotOk() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            storageService.addFruit(DEFAULT_FRUIT, NEGATIVE_QUANTITY);
        });
    }
}
