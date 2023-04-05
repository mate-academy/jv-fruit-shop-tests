package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParseImpl;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParseServiceTest {
    private static ParseService parse;

    @BeforeClass
    public static void beforeClass() {
        parse = new ParseImpl();
    }

    @Test
    public void parse_validData_ok() {
        List<FruitTransaction> transactionsExpected = fillExpectedList();
        List<String> listToParse = dataToParse();
        List<FruitTransaction> transactionsActual = parse.parse(listToParse);

        Assert.assertEquals("Lists have different size",
                transactionsExpected.size(), transactionsActual.size());

        for (int i = 0; i < transactionsExpected.size(); i++) {
            Assert.assertEquals(transactionsExpected.get(i).getType(),
                    transactionsActual.get(i).getType());
            Assert.assertEquals(transactionsExpected.get(i).getFruitName(),
                    transactionsActual.get(i).getFruitName());
            Assert.assertEquals(transactionsExpected.get(i).getQuantity(),
                    transactionsActual.get(i).getQuantity());
        }
    }

    @Test
    public void parse_emptyList_ok() {
        List<FruitTransaction> transactionsExpected = Collections.emptyList();
        List<String> listToParse = Collections.emptyList();
        List<FruitTransaction> transactionsActual = parse.parse(listToParse);

        Assert.assertEquals(transactionsExpected.size(), transactionsActual.size());
    }

    private List<String> dataToParse() {
        List<String> dataFromFileExpected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        return dataFromFileExpected;
    }

    private List<FruitTransaction> fillExpectedList() {
        List<FruitTransaction> transactionsExpected = List.of(
                new FruitTransaction(FruitTransaction.OperationType.BALANCE,
                        "banana", 20),
                new FruitTransaction(FruitTransaction.OperationType.BALANCE,
                        "apple", 100),
                new FruitTransaction(FruitTransaction.OperationType.SUPPLY,
                        "banana", 100),
                new FruitTransaction(FruitTransaction.OperationType.PURCHASE,
                        "banana", 13),
                new FruitTransaction(FruitTransaction.OperationType.RETURN,
                        "apple", 10),
                new FruitTransaction(FruitTransaction.OperationType.PURCHASE,
                        "apple", 20),
                new FruitTransaction(FruitTransaction.OperationType.PURCHASE,
                        "banana", 5),
                new FruitTransaction(FruitTransaction.OperationType.SUPPLY,
                        "banana", 50)
        );
        return transactionsExpected;
    }
}
