package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParserServiceImplTest {
    private static ParserService parserService;

    @BeforeAll
    static void setup() {
        parserService = new ParserServiceImpl();
    }

    @Test
    void parse_validInput() {
        List<String> validList = Arrays.asList(
                "Operation,Fruit,Quantity",
                "s,Apple,10",
                "r,Orange,5"
        );
        List<FruitTransaction> result = parserService.parse(validList);
        assertEquals(2, result.size());
        FruitTransaction transaction1 = result.get(0);
        assertEquals(FruitTransaction.Operation.SUPPLY.getCode(),
                transaction1.getOperation().getCode());
        assertEquals("Apple", transaction1.getFruit());
        assertEquals(10, transaction1.getQuantity());
        FruitTransaction transaction2 = result.get(1);
        assertEquals(FruitTransaction.Operation.RETURN.getCode(),
                transaction2.getOperation().getCode());
        assertEquals("Orange", transaction2.getFruit());
        assertEquals(5, transaction2.getQuantity());
    }

    @Test
    void parse_emptyInput_throwsException() {
        List<String> emptyList = Arrays.asList();
        assertThrows(RuntimeException.class, () -> {
            parserService.parse(emptyList);
        });
    }

    @Test
    void parse_invalidInput_throwsException() {
        List<String> invalidList = Arrays.asList(
                "Operation,Fruit,Quantity",
                "SUPPLY,Apple"
        );
        assertThrows(RuntimeException.class, () -> {
            parserService.parse(invalidList);
        });
    }
}
