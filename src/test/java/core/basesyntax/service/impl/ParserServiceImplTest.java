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
    void parse_csvData_ok() {
        List<String> dataFromFile = new ArrayList<>();
        dataFromFile.add("type,fruit,quantity");
        dataFromFile.add("b,banana,20");
        dataFromFile.add("b,apple,100");
        dataFromFile.add("s,banana,100");

        FruitTransaction firstTransaction = new FruitTransaction();
        firstTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        firstTransaction.setFruit("banana");
        firstTransaction.setQuantity(20);

        FruitTransaction secondTransaction = new FruitTransaction();
        secondTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        secondTransaction.setFruit("apple");
        secondTransaction.setQuantity(100);

        FruitTransaction thirdTransaction = new FruitTransaction();
        thirdTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        thirdTransaction.setFruit("banana");
        thirdTransaction.setQuantity(100);

        List<FruitTransaction> anticipatedResult = List
                .of(firstTransaction, secondTransaction, thirdTransaction);
        List<FruitTransaction> actualResult = parserService.parse(dataFromFile);
        assertArrayEquals(anticipatedResult.toArray(), actualResult.toArray());
    }
}
