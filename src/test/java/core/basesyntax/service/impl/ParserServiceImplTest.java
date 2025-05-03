package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParserServiceImplTest {
    private static ParserService parserService;

    @BeforeEach
    void setUp() {
        parserService = new ParserServiceImpl();
    }

    @Test
    void parse_null_notOk() {
        assertThrows(NullPointerException.class,
                () -> parserService.parse(null));
    }

    @Test
    void parse_dataFromFile_ok() {
        List<String> dataFromFile = new ArrayList<>();
        dataFromFile.add("type,fruit,quantity");
        dataFromFile.add("b,banana,20");
        dataFromFile.add("b,apple,100");
        dataFromFile.add("s,banana,100");

        FruitTransaction transaction1 = new FruitTransaction();
        transaction1.setOperation(FruitTransaction.Operation.BALANCE);
        transaction1.setFruit("banana");
        transaction1.setQuantity(20);

        FruitTransaction transaction2 = new FruitTransaction();
        transaction2.setOperation(FruitTransaction.Operation.BALANCE);
        transaction2.setFruit("apple");
        transaction2.setQuantity(100);

        FruitTransaction transaction3 = new FruitTransaction();
        transaction3.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction3.setFruit("banana");
        transaction3.setQuantity(100);

        List<FruitTransaction> expected = List.of(transaction1, transaction2, transaction3);
        List<FruitTransaction> actual = parserService.parse(dataFromFile);
        assertArrayEquals(expected.toArray(), actual.toArray());
    }
}
