package service.impl;

import static model.FruitTransaction.Operation;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import model.FruitTransaction;
import org.junit.jupiter.api.Test;
import service.ParserService;

class ParserServiceImplTest {
    private static final ParserService parserService = new ParserServiceImpl();

    @Test
    void parseLines_validList_Ok() {
        final List<String> data = Arrays.asList("type,fruit,quantity", "b,banana,20", "s,apple,100",
                "p,apple,50");
        final List<FruitTransaction> actual = parserService.parseLines(data);
        final FruitTransaction fruitTransaction1 = new FruitTransaction();
        fruitTransaction1.setOperation(Operation.BALANCE);
        fruitTransaction1.setFruit("banana");
        fruitTransaction1.setQuantity(20);
        final FruitTransaction fruitTransaction2 = new FruitTransaction();
        fruitTransaction2.setOperation(Operation.SUPPLY);
        fruitTransaction2.setFruit("apple");
        fruitTransaction2.setQuantity(100);
        final FruitTransaction fruitTransaction3 = new FruitTransaction();
        fruitTransaction3.setOperation(Operation.PURCHASE);
        fruitTransaction3.setFruit("apple");
        fruitTransaction3.setQuantity(50);
        final List<FruitTransaction> expected = List.of(fruitTransaction1,
                fruitTransaction2, fruitTransaction3);
        assertEquals(3, actual.size());
        assertEquals(expected.get(0).getOperation() + expected.get(0).getFruit()
                        + expected.get(0).getQuantity(), actual.get(0).getOperation()
                + actual.get(0).getFruit() + actual.get(0).getQuantity());
        assertEquals(expected.get(1).getOperation() + expected.get(1).getFruit()
                        + expected.get(1).getQuantity(), actual.get(1).getOperation()
                + actual.get(1).getFruit() + actual.get(1).getQuantity());
        assertEquals(expected.get(2).getOperation() + expected.get(2).getFruit()
                + expected.get(2).getQuantity(), actual.get(2).getOperation()
                + actual.get(2).getFruit() + actual.get(2).getQuantity());
    }

    @Test
    void parseLines_listNull_notOk() {
        assertThrows(NullPointerException.class, () -> parserService.parseLines(null));
    }
}
