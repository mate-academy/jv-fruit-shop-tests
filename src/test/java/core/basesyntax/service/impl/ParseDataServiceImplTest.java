package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseDataService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParseDataServiceImplTest {
    private static ParseDataService parseDataService;
    private static final String TITLE = "type,fruit,quantity";
    private static final String BALANCE = "b,banana,20";
    private static final String RETURN = "r,apple,10";
    private static final String SUPPLY = "s,banana,100";
    private static final String PURCHASE = "p,apple,20";
    private static final String INCORRECT_OPERATION = "incorrect operation";
    private static List<FruitTransaction> fruitTransactionsList;
    private static List<String> dataFromFile;

    @BeforeAll
    static void beforeAll() {
        parseDataService = new ParseDataServiceImpl();
    }

    @BeforeEach
    void setUp() {
        dataFromFile = List.of(TITLE,BALANCE,RETURN,SUPPLY,PURCHASE);
        FruitTransaction fruitTransactionBalance
                = new FruitTransaction(FruitTransaction.Operation.BALANCE,"banana",20);
        FruitTransaction fruitTransactionReturn
                = new FruitTransaction(FruitTransaction.Operation.RETURN,"apple",10);
        FruitTransaction fruitTransactionSupply
                = new FruitTransaction(FruitTransaction.Operation.SUPPLY,"banana",100);
        FruitTransaction fruitTransactionPurchase
                = new FruitTransaction(FruitTransaction.Operation.PURCHASE,"apple",20);
        fruitTransactionsList = List.of(fruitTransactionBalance,fruitTransactionReturn,
                fruitTransactionSupply,fruitTransactionPurchase);
    }

    @Test
    void parseData_dataFromFileIsEmpty_NotOk() {
        assertThrows(RuntimeException.class,
                () -> parseDataService.parseTransactions(new ArrayList<>()));
    }

    @Test
    void parseData_listIsNull_NotOk() {
        assertThrows(RuntimeException.class,
                () -> parseDataService.parseTransactions(null));
    }

    @Test
    void parseData_dataFromFileHasData_Ok() {
        assertEquals(fruitTransactionsList,
                parseDataService.parseTransactions(new ArrayList<>(dataFromFile)));
    }

    @Test
    void parseData_incorrectOperation_NotOk() {
        assertThrows(RuntimeException.class,
                () -> parseDataService.parseTransactions(List.of(INCORRECT_OPERATION)));
    }
}
