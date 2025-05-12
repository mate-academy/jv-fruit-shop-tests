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
        assertThrows(IllegalArgumentException.class,
                () -> parserService.parse(null));
    }

    @Test
    void parse_emptyData_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> parserService.parse(new ArrayList<>()));
    }

    @Test
    void parse_dataFromFile_ok() {
        List<String> dataFromFile = new ArrayList<>();
        dataFromFile.add("type,fruit,quantity");
        dataFromFile.add("b,banana,20");
        dataFromFile.add("b,apple,100");
        dataFromFile.add("s,banana,100");

        FruitTransaction bananaBalanceTransaction = new FruitTransaction();
        bananaBalanceTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        bananaBalanceTransaction.setFruit("banana");
        bananaBalanceTransaction.setQuantity(20);

        FruitTransaction appleBalanceTransaction = new FruitTransaction();
        appleBalanceTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        appleBalanceTransaction.setFruit("apple");
        appleBalanceTransaction.setQuantity(100);

        FruitTransaction bananaSupplyTransaction = new FruitTransaction();
        bananaSupplyTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        bananaSupplyTransaction.setFruit("banana");
        bananaSupplyTransaction.setQuantity(100);

        List<FruitTransaction> expected = List
                .of(bananaBalanceTransaction, appleBalanceTransaction, bananaSupplyTransaction);
        List<String> validDataFromFile = new ArrayList<>(dataFromFile
                .subList(1, dataFromFile.size()));
        List<FruitTransaction> actual = parserService.parse(validDataFromFile);
        assertArrayEquals(expected.toArray(), actual.toArray());
    }
}
