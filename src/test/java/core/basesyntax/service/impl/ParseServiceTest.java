package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseService;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ParseServiceTest {

    @Test
    void parseTransactions_inputData_ok() {
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange", 120),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "lemon", 50),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "lemon",10),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "lemon",2),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "orange",25),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "orange",55),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,"orange",10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,"lemon",5),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,"lemon",30));
        ParseService parser = new ParseServiceImpl();
        List<FruitTransaction> actual = parser.parseTransactions(List.of("type,fruit,quantity",
                "b,orange,120",
                "b,lemon,50",
                "p,lemon,10",
                "r,lemon,2",
                "p,orange,25",
                "s,orange,55",
                "p,orange,10",
                "p,lemon,5",
                "s,lemon,30"));
        assertEquals(expected, actual, "Test failed! You should returned next list "
                + expected + " but you returned "
                + actual.toString());
    }
}
