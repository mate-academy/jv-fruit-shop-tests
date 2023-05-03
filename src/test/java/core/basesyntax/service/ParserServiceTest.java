package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParserServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParserServiceTest {
    private static ParserService service;

    @BeforeEach
    void setUp() {
        service = new ParserServiceImpl();
    }

    @Test
    void parse_null_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> service.parse(null));
    }

    @Test
    void parse_dataFromFile_ok() {
        List<String[]> dataFromFile = new ArrayList<>();
        dataFromFile.add(new String[]{"type", "fruit", "quantity"});
        dataFromFile.add(new String[]{"b", "banana", "20"});
        dataFromFile.add(new String[]{"b", "apple", "100"});
        dataFromFile.add(new String[]{"s", "banana", "100"});

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
        List<FruitTransaction> actual = service.parse(dataFromFile);
        Assertions.assertEquals(expected, actual);
    }
}
