package fruitshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fruitshop.model.FruitTransaction;
import fruitshop.service.impl.ParserServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Test;

class ParserServiceTest {
    private final ParserServiceImpl parserService = new ParserServiceImpl();

    @Test
    void parse_validData_ok() {
        List<String> inputData = List.of(
                "operation,fruit,quantity",
                "b,apple,50",
                "s,banana,30"
        );

        List<FruitTransaction> transactions = parserService.parse(inputData);

        assertEquals(2, transactions.size());
        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0).getOperation());
        assertEquals("apple", transactions.get(0).getFruit());
        assertEquals(50, transactions.get(0).getQuantity());
    }

    @Test
    void parse_invalidOperation_notOk() {
        List<String> inputData = List.of(
                "operation,fruit,quantity",
                "x,apple,50"
        );

        assertThrows(IllegalArgumentException.class, () -> parserService.parse(inputData));
    }
}
