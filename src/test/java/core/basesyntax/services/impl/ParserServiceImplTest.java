package core.basesyntax.services.impl;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserServiceImplTest {
    private static ParserServiceImpl parserService;

    @BeforeClass
    public static void beforeClass() {
        parserService = new ParserServiceImpl();
    }

    @Test
    public void parse_emptyTransactionList_Ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        List<String> transactions = new ArrayList<>();
        Assert.assertEquals(expected, parserService.parse(transactions));
    }

    @Test
    public void parse_notEmptyTransactionsList_Ok() {
        Assert.assertEquals(createExpectedTransactions(),
                parserService.parse(createDataTransactions()));
    }

    private List<String> createDataTransactions() {
        return List.of("type,fruit,quantity", "b,banana,20", "b,apple,100", "s,banana,100");
    }

    private List<FruitTransaction> createExpectedTransactions() {
        FruitTransaction bananaBalanceTransaction = new FruitTransaction();
        bananaBalanceTransaction.setOperation(FruitTransaction.Operation.getTypeOperation("b"));
        bananaBalanceTransaction.setFruit("banana");
        bananaBalanceTransaction.setQuantity(20);

        FruitTransaction appleBalanceTransaction = new FruitTransaction();
        appleBalanceTransaction.setOperation(FruitTransaction.Operation.getTypeOperation("b"));
        appleBalanceTransaction.setFruit("apple");
        appleBalanceTransaction.setQuantity(100);

        FruitTransaction bananaSupplyTransaction = new FruitTransaction();
        bananaSupplyTransaction.setOperation(FruitTransaction.Operation.getTypeOperation("s"));
        bananaSupplyTransaction.setFruit("banana");
        bananaSupplyTransaction.setQuantity(100);

        return List.of(bananaBalanceTransaction, appleBalanceTransaction, bananaSupplyTransaction);
    }
}
