package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.ParserService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParserServiceImplTest {
    private static ParserService parserService;

    @BeforeAll
    static void init() {
        parserService = new ParserServiceImpl();
    }

    @Test
    void when_NegativeAmount_NotOk() {
        List<String> list = List.of("b,apple,-700");
        assertThrows(IllegalArgumentException.class, () -> parserService.parseOperations(list));
    }

    @Test
    void when_ValidData_Ok() {
        List<String> list = List.of("b,apple,700");
        List<FruitTransaction> expected = List.of(new FruitTransaction(
                Operation.getByCode("b"),
                "apple",
                700));
        List<FruitTransaction> actual = parserService.parseOperations(list);
        assertEquals(expected, actual);
    }

    @Test
    void when_NonExistingCode_NotOk() {
        List<String> list = List.of("o,apple,700");
        assertThrows(RuntimeException.class, () -> parserService.parseOperations(list));
    }
}
