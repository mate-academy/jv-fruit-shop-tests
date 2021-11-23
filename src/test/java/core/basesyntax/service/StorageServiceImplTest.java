package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageServiceImplTest {
    private static StorageService storageService;
    private static String inputTestFilePath =
            "src/test/java/core/basesyntax/test_resources/input_test";

    @BeforeClass
    public static void beforeClass() {
        storageService = new StorageServiceImpl();
    }

    @Test
    public void updateProductsAmountInStorage_Ok() {
        storageService.updateProductsAmountInStorage(inputTestFilePath);
        int expected = 200;
        assertEquals(expected, Storage.fruitsCount.get("apple"));
    }
}
