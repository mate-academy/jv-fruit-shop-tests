package core.basesyntax.service.impl;

import core.basesyntax.dto.FruitTransaction;
import core.basesyntax.service.ParserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ParserServiceImplTest {
    @Test
    public void parseLine_ValidLine_ShouldReturnFruitTransaction() {
        ParserService parserService = new ParserServiceImpl();
        String line = "b,apple,5";
        FruitTransaction fruitTransaction = parserService.parseLine(line);
        Assertions.assertEquals("b", fruitTransaction.getOperation());
        Assertions.assertEquals("apple", fruitTransaction.getFruit().getName());
        Assertions.assertEquals(5, fruitTransaction.getQuantity());
    }

    @Test
    public void parseLine_InvalidLine_ShouldThrowException() {
        ParserService parserService = new ParserServiceImpl();
        String line = "b,apple,-5";
        Assertions.assertThrows(RuntimeException.class, () -> parserService.parseLine(line));
    }
}
