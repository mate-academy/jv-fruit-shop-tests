package core.basesyntax.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParserServiceImplTest {
    private ParserServiceImpl parserService;
    private FruitTransaction fruitTransaction;
    private List<String> records;
    private List<String> recordsWrong;

    @BeforeEach
    public void setUp() {
        parserService = new ParserServiceImpl();
        fruitTransaction = new FruitTransaction(Operation.BALANCE, "banana", 20);
        records = new ArrayList<>(List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,102",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        ));

        recordsWrong = new ArrayList<>(List.of(
                "type,fruit,quantity",
                "b,banana,yy"
        ));
    }

    @Test
    public void validTransactions_ok() {
        List<FruitTransaction> actualResult = parserService.parse(records);
        Assertions.assertEquals(fruitTransaction, actualResult.get(0));
    }

    @Test
    public void invalidTransactions_notOk() {
        Assertions.assertThrows(RuntimeException.class, () ->
                parserService.parse(recordsWrong));
    }

    @Test
    public void emptyTransactions_notOk() {
        Assertions.assertThrows(RuntimeException.class, () ->
                parserService.parse(new ArrayList<>()));
    }
}
