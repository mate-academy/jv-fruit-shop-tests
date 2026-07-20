package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParserServiceTest {
    private ParserServiceImpl parserService;
    private List<String> validInput;

    @BeforeEach
    void setUp() {
        parserService = new ParserServiceImpl();
        validInput = List.of("b,banana,100");
    }

    @Test
    void parse_nullInput_notOk() {
        assertThrows(RuntimeException.class, () -> parserService.parse(null));
    }

    @Test
    void parse_emptyInput_notOk() {
        assertThrows(RuntimeException.class, () -> parserService.parse(List.of()));
    }

    @Test
    void parse_validInput_ok() {
        List<FruitTransaction> result = parserService.parse(validInput);
        assertEquals(FruitTransaction.Operation.BALANCE, result.get(0).getOperation());
        assertEquals("banana", result.get(0).getFruit());
        assertEquals(100, result.get(0).getQuantity());
    }

    @Test
    void parse_negativeQuantity_notOk() {
        List<String> notValidInput = List.of("b,banana,-5");
        assertThrows(RuntimeException.class, () -> parserService.parse(notValidInput));
    }

    @Test
    void parse_notValidOperationInput_notOk() {
        List<String> notValidOperationInput = List.of("x,banana,50");
        assertThrows(RuntimeException.class, () -> parserService.parse(notValidOperationInput));
    }
}
