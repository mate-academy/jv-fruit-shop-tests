package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.enums.Operation;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.TransactionParseServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParseServiceImplTest {
    private static TransactionParseService transactionParseService;
    private static List<FruitTransaction> testFruitTransactionList;

    @BeforeClass
    public static void setUp() {
        transactionParseService = new TransactionParseServiceImpl();
        testFruitTransactionList = new ArrayList<>();

        testFruitTransactionList.add(new FruitTransaction(Operation.SUPPLY, "apple", 100));
        testFruitTransactionList.add(new FruitTransaction(Operation.PURCHASE, "apple", 50));
    }

    @Test
    public void parseList_checkIfCorrect_isOk() {
        String data = "type,fruit,quantity" + System.lineSeparator() + "s,apple,100"
                + System.lineSeparator() + "p,apple,50";

        List<FruitTransaction> fruitTransactionList = transactionParseService.parseList(data);

        boolean trueStatement = fruitTransactionList.get(0)
                .equals(testFruitTransactionList.get(0))
                && fruitTransactionList.get(1)
                .equals(testFruitTransactionList.get(1));

        assertTrue(trueStatement);
    }
}
