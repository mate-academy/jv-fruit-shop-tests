package core.basesyntax.service.impl;

import core.basesyntax.exceptions.NegativeTransactionException;
import core.basesyntax.exceptions.NullDataException;
import core.basesyntax.exceptions.TransactionQuantityException;
import core.basesyntax.exceptions.WrongOperationException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataParseService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class DataParseServiceImplTest {
    private static List<String> valid;
    private static List<String> wrongOperation;
    private static List<String> notNumberQuantity;
    private static List<String> onlyHeader;
    private static List<String> negativeQuantity;
    private static List<FruitTransaction> validTransactions;
    private final DataParseService parseService;

    public DataParseServiceImplTest() {
        parseService = new DataParseServiceImpl();

        validTransactions = new ArrayList<>();
        validTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 20));
        validTransactions.add(new FruitTransaction(FruitTransaction.Operation.RETURN,
                "apple", 10));
        validTransactions.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 50));

        valid = new ArrayList<>();
        valid.add("type,fruit,quantity");
        valid.add("b,banana,20");
        valid.add("r,apple,10");
        valid.add("s,banana,50");

        wrongOperation = new ArrayList<>();
        wrongOperation.add("HEADER");
        wrongOperation.add("q,banana,1000");

        notNumberQuantity = new ArrayList<>();
        notNumberQuantity.add("HEADER");
        notNumberQuantity.add("b,banana,twenty");

        onlyHeader = new ArrayList<>();
        onlyHeader.add("HEADER");

        negativeQuantity = new ArrayList<>();
        negativeQuantity.add("HEADER");
        negativeQuantity.add("b,banana,-20");
    }

    @Test
    public void parseData_valid_Ok() {
        Assert.assertEquals(validTransactions, parseService.parseData(valid));
    }

    @Test (expected = WrongOperationException.class)
    public void parseData_WrongOperation_NotOk() {
        parseService.parseData(wrongOperation);
    }

    @Test (expected = TransactionQuantityException.class)
    public void parseData_NotNumberQuantity_NotOk() {
        parseService.parseData(notNumberQuantity);
    }

    @Test (expected = NullDataException.class)
    public void parseData_Null_NotOk() {
        parseService.parseData(null);
    }

    @Test
    public void parseData_OnlyHeaderPresent_Ok() {
        Assert.assertEquals(Collections.emptyList(), parseService.parseData(onlyHeader));
    }

    @Test (expected = NegativeTransactionException.class)
    public void parseData_NegativeQuantity_NotOk() {
        parseService.parseData(negativeQuantity);
    }
}
