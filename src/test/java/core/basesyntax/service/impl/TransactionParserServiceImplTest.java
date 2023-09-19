package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.service.TransactionParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionParserServiceImplTest {
    private TransactionParserService transactionParserService;

    @BeforeEach
    public void setUp() {
        transactionParserService = new TransactionParserServiceImpl();
    }

    @Test
    public void parse_validData_returnsParsedTransactions() {
        List<String> inputData = new ArrayList<>();
        inputData.add("s,apple,5");
        inputData.add("p,banana,10");
        inputData.add("r,banana,15");

        List<FruitTransaction> transactions = transactionParserService.parse(inputData);

        assertEquals(3, transactions.size());

        FruitTransaction transaction1 = transactions.get(0);
        assertEquals(Operation.SUPPLY, transaction1.getOperation());
        assertEquals(new Fruit("apple"), transaction1.getFruit());
        assertEquals(5, transaction1.getQuantity());

        FruitTransaction transaction2 = transactions.get(1);
        assertEquals(Operation.PURCHASE, transaction2.getOperation());
        assertEquals(new Fruit("banana"), transaction2.getFruit());
        assertEquals(10, transaction2.getQuantity());

        FruitTransaction transaction3 = transactions.get(2);
        assertEquals(Operation.RETURN, transaction3.getOperation());
        assertEquals(new Fruit("banana"), transaction3.getFruit());
        assertEquals(15, transaction3.getQuantity());
    }

    @Test
    public void parse_invalidData_NotOk() {
        List<String> inputData = new ArrayList<>();
        inputData.add("INVALID_DATA");
        Assert.assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> transactionParserService.parse(inputData));
    }
}
