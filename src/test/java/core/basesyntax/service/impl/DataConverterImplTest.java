package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static List<String> input1;
    private static List<String> input2;
    private static DataConverterImpl dataConverter;

    @BeforeAll
    static void beforeAll() {
        input1 = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );
        input2 = List.of(
                "type,fruit,quantity",
                "b,banana,0",
                "p,apple,0"
        );
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction() {
        List<FruitTransaction> fruitTransactionList =
                dataConverter.convertToTransaction(input1);

        assertEquals(8, fruitTransactionList.size());

    }

    @Test
    void convertToTransactionEdge() {
        List<FruitTransaction> fruitTransactionList =
                dataConverter.convertToTransaction(input2);

        assertEquals(0, fruitTransactionList.get(0).getQuantity());
        assertEquals("banana", fruitTransactionList.get(0).getFruit());
    }
}
