package core.basesyntax.service.impl;

import core.basesyntax.dto.FruitTransaction;
import core.basesyntax.service.ParserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ParserServiceImplImplTest {
    @Test
    public void parseLine_ValidLine_ShouldReturnFruitTransaction() {
        ParserServiceImpl parserServiceImpl = new ParserServiceImplImpl();
        String line = "b,apple,5";
        FruitTransaction fruitTransaction = parserServiceImpl.parseLine(line);
        Assertions.assertEquals("b", fruitTransaction.getOperation());
        Assertions.assertEquals("apple", fruitTransaction.getFruit().getName());
        Assertions.assertEquals(5, fruitTransaction.getQuantity());
    }

    @Test
    public void parseLine_InvalidLine_ShouldThrowException() {
        ParserServiceImpl parserServiceImpl = new ParserServiceImplImpl();
        String line = "b,apple,-5";
        Assertions.assertThrows(RuntimeException.class, () -> parserServiceImpl.parseLine(line));
    }
}
