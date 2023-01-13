package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class FruitParserImplTest {
    private final String dataFromFile = "type,fruit,quantity" + System.lineSeparator()
            + "b,banana,20" + System.lineSeparator()
            + "b,apple,100" + System.lineSeparator()
            + "s,banana,10" + System.lineSeparator()
            + "p,apple,13";

    @Test
    public void parseData_validInput_ok() {
        FruitParser fruitParser = new FruitParserImpl();
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        final List<FruitTransaction> actual = fruitParser.parseData(dataFromFile);
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 20));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 100));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 10));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", 13));
        assertEquals("Wrong output for: " + dataFromFile, fruitTransactionList, actual);
    }
}
