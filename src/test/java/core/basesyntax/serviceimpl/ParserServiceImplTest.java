package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.ParserService;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParserServiceImplTest {
    @BeforeAll
    static void clear() {
        Storage.storage.clear();
    }

    @AfterEach
    void clearAfter() {
        Storage.storage.clear();
    }

    @Test
    void when_NegativeAmount_NotOk() {
        List<String> list = List.of("b,apple,-700");
        ParserService parserService = new ParserServiceImpl();
        assertThrows(IllegalArgumentException.class, () -> parserService.parseOperations(list));
    }

    @Test
    void when_ValidData_Ok() {
        List<String> list = List.of("b,apple,700");
        List<FruitTransaction> expected = List.of(new FruitTransaction(
                Operation.getByCode("b"),
                "apple",
                700));
        ParserService parserService = new ParserServiceImpl();
        List<FruitTransaction> actual = parserService.parseOperations(list);
        assertEquals(expected, actual);
    }

    @Test
    void when_NonExistingCode_NotOk() {
        List<String> list = List.of("o,apple,700");
        ParserService parserService = new ParserServiceImpl();
        assertThrows(RuntimeException.class, () -> parserService.parseOperations(list));
    }
}
