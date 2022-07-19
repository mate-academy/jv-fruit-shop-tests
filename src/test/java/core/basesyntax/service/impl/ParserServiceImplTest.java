package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserServiceImplTest {
    private static ParserService parserService;

    @BeforeClass
    public static void beforeClass() {
        parserService = new ParserServiceImpl();
    }

    @Test
    public void parseData_FruitTransactionsCorrespondParsedData_Ok() {
        List<String[]> fileInfo = new ArrayList<>();
        fileInfo.add(new String[] {"type;fruit;quantity;"});
        fileInfo.add(new String[] {"b;peach;10"});
        fileInfo.add(new String[] {"b;lemon;14"});

        List<FruitTransaction> expectedFruitTransactions = new ArrayList<>();
        expectedFruitTransactions.add(new FruitTransaction("peach",
                FruitTransaction.Operation.BALANCE, 10));
        expectedFruitTransactions.add(new FruitTransaction("lemon",
                FruitTransaction.Operation.BALANCE, 14));

        List<FruitTransaction> actualFruitTransactions = parserService.parseData(fileInfo);

        for (int i = 0; i < actualFruitTransactions.size(); i++) {
            FruitTransaction actualTransaction = actualFruitTransactions.get(i);
            FruitTransaction expectedTransaction = expectedFruitTransactions.get(i);
            Assert.assertEquals("Test failed! Expected: fruit transaction's type "
                            + expectedTransaction.getOperation() + " but was: "
                            + actualTransaction.getOperation(),
                    expectedTransaction.getOperation(), actualTransaction.getOperation());
            Assert.assertEquals("Test failed! Expected: fruit transaction's fruitName "
                            + expectedTransaction.getFruitName() + " but was "
                            + actualTransaction.getFruitName(),
                    expectedTransaction.getFruitName(), actualTransaction.getFruitName());
            Assert.assertEquals("Test failed! Expected: fruit transaction's quantity "
                            + expectedTransaction.getQuantity() + " but was "
                            + actualTransaction.getQuantity(), expectedTransaction.getQuantity(),
                    actualTransaction.getQuantity());
        }
    }

    @Test(expected = RuntimeException.class)
    public void parseData_WrongOperationType_NotOk() {
        List<String[]> fileInfo = new ArrayList<>();
        fileInfo.add(new String[] {"type;fruit;quantity;"});
        fileInfo.add(new String[] {"E;peach;10"});
        fileInfo.add(new String[] {"b;lemon;14"});
        parserService.parseData(fileInfo);
    }
}
