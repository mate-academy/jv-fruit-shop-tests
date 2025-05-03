package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.Operation;
import core.basesyntax.service.ParserService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionMapperTest {
    private static final List<String> WRONG_READER_RESULT = List.of(
            " type,fruit,quantity",
            "    x,banana,100");
    private static final List<String> GOOD_READER_RESULT = List.of(
            " type,fruit,quantity",
            "    b,banana,100");
    private static final List<FruitTransaction> EXPECTED = List.of(
            new FruitTransaction(Operation.BALANCE, "banana", 100));
    private ParserService<String> parserService;

    @BeforeEach
    void setUp() {
        parserService = new FruitTransactionMapper();
    }

    @Test
    void parserService_WrongInput_ThrowException() {
        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
                () -> parserService.parse(WRONG_READER_RESULT));
        assertEquals("Wrong operation code: x", exception.getMessage());
    }

    @Test
    void parserService_InputOk_Equals() {
        List<FruitTransaction> actual = parserService.parse(GOOD_READER_RESULT);
        assertEquals(1, actual.size());
        assertEquals(EXPECTED, actual);
    }
}
