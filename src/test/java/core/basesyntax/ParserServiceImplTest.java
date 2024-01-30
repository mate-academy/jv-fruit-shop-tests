package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.fruitshop.model.FruitTransaction;
import core.basesyntax.fruitshop.service.impl.ParserServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class ParserServiceImplTest {
    private final ParserServiceImpl parserService = new ParserServiceImpl();

    @Test
    void parseValidLine() {
        List<String> lines = new ArrayList<>();
        lines.add("b,apple,20");
        List<FruitTransaction> result = parserService.parse(lines);
        assertEquals(1, result.size(), "Should parse one transaction");
        FruitTransaction transaction = result.get(0);
        assertEquals(FruitTransaction.Operation.BALANCE, transaction.getOperation(),
                "Operation should be BALANCE");
        assertEquals("apple", transaction.getFruit(), "Fruit should be apple");
        assertEquals(20, transaction.getQuantity(), "Quantity should be 20");
    }

    @Test
    void ignoreHeader() {
        List<String> lines = new ArrayList<>();
        lines.add("type,fruit,quantity");
        lines.add("s,banana,10");
        List<FruitTransaction> result = parserService.parse(lines);
        assertEquals(1, result.size(), "Should ignore header and parse one transaction");
    }
}
