package core.basesyntax.service.strategy.handlers;

import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ValidationServiceTest {
    private static ValidationService validationService;

    @BeforeAll
    static void beforeAll() {
        validationService = new ValidationService();
    }

    @AfterEach
    void tearDown() {
        Storage.fruitsMap.clear();
    }

    @Test
    void validateIncreasing_fruitNotExist_notOk() {
        assertThrows(RuntimeException.class,
                () -> validationService.validateIncreasing("paradapple"));
    }

    @Test
    void validateAddingNew_fruitAlreadyExist_notOk() {
        Storage.fruitsMap.put("cherry", 1);
        assertThrows(RuntimeException.class,
                () -> validationService.validateAddingNew("cherry"));
    }

    @Test
    void validateDecreasing_negativeAmount_notOk() {
        Storage.fruitsMap.put("banana", 1);
        assertThrows(RuntimeException.class,
                () -> validationService.validateDecreasing("banana", 101));
    }
}
