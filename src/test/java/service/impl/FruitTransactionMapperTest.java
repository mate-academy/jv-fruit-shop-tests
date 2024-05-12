package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import model.FruitTransaction;
import model.Operation;
import org.junit.jupiter.api.Test;

class FruitTransactionMapperTest {
    @Test
    void createFruitTransactionDtoList_Ok() {
        List<String> fruitTransactionsList = List.of("p,banana,13", "r,apple,10");
        FruitTransactionMapperImpl fruitTransactionMapper = new FruitTransactionMapperImpl();

        List<FruitTransaction> actualList = fruitTransactionMapper.map(fruitTransactionsList);

        FruitTransaction fruitTransaction1 = actualList.get(0);

        assertEquals(Operation.PURCHASE, fruitTransaction1.getOperation());
        assertEquals("banana",fruitTransaction1.getFruit());
        assertEquals(13, fruitTransaction1.getQuantity());

        FruitTransaction fruitTransaction2 = actualList.get(1);

        assertEquals(Operation.RETURN, fruitTransaction2.getOperation());
        assertEquals("apple",fruitTransaction2.getFruit());
        assertEquals(10, fruitTransaction2.getQuantity());
    }
}
