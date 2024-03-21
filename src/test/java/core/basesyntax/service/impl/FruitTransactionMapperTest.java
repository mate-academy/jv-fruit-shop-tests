package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.Operation;
import core.basesyntax.service.ParserService;
import java.util.List;
import org.junit.jupiter.api.Test;

class FruitTransactionMapperTest {
    private final List<String> wrongReaderResult = List.of(
            " type,fruit,quantity",
            "    x,banana,100");
    private final List<String> goodReaderResult = List.of(
            " type,fruit,quantity",
            "    b,banana,100");
    private final List<FruitTransaction> expected = List.of(
            new FruitTransaction(Operation.BALANCE, "banana", 100));
    private final ParserService<String> parserService = new FruitTransactionMapper();

    @Test
    void parserService_WrongInput_ThrowException() {
        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
                () -> parserService.parse(wrongReaderResult));
        assertEquals("Wrong operation code: x", exception.getMessage());
    }

    @Test
    void parserService_InputOk_Equals() {
        List<FruitTransaction> actual = parserService.parse(goodReaderResult);
        assertEquals(1, actual.size());
        assertEquals(expected, actual);
    }
}
