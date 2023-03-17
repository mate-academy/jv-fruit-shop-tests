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
    private static List<String> data;
    private static List<String> wrongOperationData;
    private static List<String> notNumberQuantityData;
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

        data = new ArrayList<>();
        data.add("type,fruit,quantity");
        data.add("b,banana,20");
        data.add("r,apple,10");
        data.add("s,banana,50");

        wrongOperationData = new ArrayList<>();
        wrongOperationData.add("HEADER");
        wrongOperationData.add("q,banana,1000");

        notNumberQuantityData = new ArrayList<>();
        notNumberQuantityData.add("HEADER");
        notNumberQuantityData.add("b,banana,twenty");

        onlyHeader = new ArrayList<>();
        onlyHeader.add("HEADER");

        negativeQuantity = new ArrayList<>();
        negativeQuantity.add("HEADER");
        negativeQuantity.add("b,banana,-20");
    }

    @Test
    public void parseData_Ok() {
        Assert.assertEquals(validTransactions, parseService.parseData(data));
    }

    @Test (expected = WrongOperationException.class)
    public void parseWrongOperation_NotOk() {
        parseService.parseData(wrongOperationData);
    }

    @Test (expected = TransactionQuantityException.class)
    public void parseNotNumberQuantity_NotOk() {
        parseService.parseData(notNumberQuantityData);
    }

    @Test (expected = NullDataException.class)
    public void parseNullData_NotOk() {
        parseService.parseData(null);
    }

    @Test
    public void parseDataWithOnlyHeaderPresent_Ok() {
        Assert.assertEquals(Collections.emptyList(), parseService.parseData(onlyHeader));
    }

    @Test (expected = NegativeTransactionException.class)
    public void parseDataNegativeQuantity() {
        parseService.parseData(negativeQuantity);
    }
}
