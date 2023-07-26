package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dto.FruitTransaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ParserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParserServiceImplTest {
    private ParserService parserService;

    @BeforeEach
    void setUp() {
        parserService = new ParserServiceImpl();
    }

    @Test
    void parseLineCorrectDataOk() {
        String testLine = "b,banana,20";
        FruitTransaction expected = new FruitTransaction("b", new Fruit("banana"), 20);
        FruitTransaction actual = parserService.parseLine(testLine);
        assertEquals(expected, actual);
    }

    @Test
    void parseLineIncorrectDataNotOk() {
        String testLine = "b,banana,-20";
        assertThrows(RuntimeException.class, () -> parserService.parseLine(testLine));
    }

    @Test
    public void parseLineValidLineShouldReturnFruitTransaction() {
        ParserService parserService = new ParserServiceImpl();
        String line = "b,apple,5";
        FruitTransaction fruitTransaction = parserService.parseLine(line);
        assertEquals("b", fruitTransaction.getOperation());
        assertEquals("apple", fruitTransaction.getFruit().getName());
        assertEquals(5, fruitTransaction.getQuantity());
    }

    @Test
    public void parseLineInvalidLineShouldThrowException() {
        ParserService parserService = new ParserServiceImpl();
        String line = "b,apple,-5";
        assertThrows(RuntimeException.class, () -> parserService.parseLine(line));
    }
}
