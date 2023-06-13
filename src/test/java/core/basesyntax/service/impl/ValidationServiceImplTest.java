package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ValidationServiceImplTest {
    private static int HEADER_INDEX;
    private static ValidationServiceImpl validationService;
    private List<String> validInput;
    private List<String> testInput;

    @BeforeAll
    static void beforeAll() {
        HEADER_INDEX = 0;
        validationService = new ValidationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        testInput = null;
        validInput = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
    }

    @Test
    void validationService_validData_ok() {
        assertDoesNotThrow(() -> validationService.validateInputData(validInput));
    }

    @Test
    void validationService_emptyEntriesData_notOk() {
        testInput = List.of("type,fruit,quantity", "", "");
        assertThrows(RuntimeException.class, () -> validationService.validateInputData(testInput));
    }

    @Test
    void validationService_moreEntryColumnsData_notOk() {
        testInput = List.of(
                "type,fruit,quantity",
                "b,banana,20,58",
                "b,apple,100,89",
                "s,banana,100,11");
        assertThrows(RuntimeException.class, () -> validationService.validateInputData(testInput));
    }

    @Test
    void validationService_fewEntryColumnsData_notOk() {
        testInput = List.of(
                "type,fruit,quantity",
                "b,banana",
                "b,apple",
                "s,banana");
        assertThrows(RuntimeException.class, () -> validationService.validateInputData(testInput));
    }

    @Test
    void validationService_emptyList_notOk() {
        List<String> emptyList = List.of();
        assertThrows(RuntimeException.class, () -> validationService.validateInputData(emptyList));
    }

    @Test
    void validationService_wrongHeader_notOk() {
        testInput = new ArrayList<>(validInput);
        testInput.remove(HEADER_INDEX);
        assertThrows(RuntimeException.class, () -> validationService.validateInputData(testInput));

        testInput = new ArrayList<>(validInput);
        testInput.add(HEADER_INDEX, "");
        assertThrows(RuntimeException.class, () -> validationService.validateInputData(testInput));
    }

    @Test
    void validationService_invalidOperationCode_notOk() {
        testInput = new ArrayList<>(validInput);
        testInput.add(",banana,50");
        assertThrows(RuntimeException.class, () -> validationService.validateInputData(testInput));

        testInput = new ArrayList<>(validInput);
        testInput.add("u,banana,50");
        assertThrows(RuntimeException.class, () -> validationService.validateInputData(testInput));

        testInput = new ArrayList<>(validInput);
        testInput.add("ss,banana,50");
        assertThrows(RuntimeException.class, () -> validationService.validateInputData(testInput));

        testInput = new ArrayList<>(validInput);
        testInput.add("s s,banana,50");
        assertThrows(RuntimeException.class, () -> validationService.validateInputData(testInput));
    }

    @Test
    void validationService_invalidQuantity_notOk() {
        testInput = new ArrayList<>(validInput);
        testInput.add("s,banana,2 0");
        assertThrows(RuntimeException.class, () -> validationService.validateInputData(testInput));
    }

    @Test
    void validationService_emptyQuantity_notOk() {
        testInput = new ArrayList<>(validInput);
        testInput.add("s,banana,");
        assertThrows(RuntimeException.class, () -> validationService.validateInputData(testInput));
    }

    @Test
    void validationService_lettersQuantity_notOk() {
        testInput = new ArrayList<>(validInput);
        testInput.add("b,banana,af");
        assertThrows(RuntimeException.class, () -> validationService.validateInputData(testInput));
    }

    @Test
    void validationService_negativeQuantity_notOk() {
        testInput = new ArrayList<>(validInput);
        testInput.add("b,banana,-8");
        assertThrows(RuntimeException.class, () -> validationService.validateInputData(testInput));
    }

    @Test
    void validationService_emptyFruit_notOk() {
        testInput = new ArrayList<>(validInput);
        testInput.add("s,,20");
        assertThrows(RuntimeException.class, () -> validationService.validateInputData(testInput));
    }
}

