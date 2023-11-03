package core.basesyntax.strategy;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.model.FruitInputData;
import core.basesyntax.strategy.impl.FruitBalanceHandler;
import core.basesyntax.strategy.impl.FruitPurchaseHandler;
import core.basesyntax.strategy.impl.FruitReturnHandler;
import core.basesyntax.strategy.impl.FruitSupplyHandler;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private static final String ASSERTION_FAILURE_MESSAGE = "Expected report is: "
            + System.lineSeparator() + "{%s}"
            + "but actual report is: "
            + System.lineSeparator() + "{%s}";
    private static final String DEFAULT_FRUIT_NAME = "apple";
    private static final String BALANCE_OPERATION_CODE = "b";
    private static final String SUPPLY_OPERATION_CODE = "s";
    private static final String PURCHASE_OPERATION_CODE = "p";
    private static final String RETURN_OPERATION_CODE = "r";
    private static final String INCORRECT1_OPERATION_CODE = "a";
    private static final String INCORRECT2_OPERATION_CODE = "balance";
    private static final String REPORT_COLUMNS = "fruit,quantity";
    private static final String SEPARATE_SYMBOL = ",";
    private static final int DEFAULT_FRUIT_AMOUNT = 10;
    private static final int FRUIT_AMOUNT_CASE1 = 5;
    private static final int FRUIT_AMOUNT_CASE2 = 11;
    private static final int FRUIT_AMOUNT_CASE3 = 3;
    private static OperationStrategy operationStrategy;
    private static FruitInputData fruitInputData;
    private static FruitDao fruitDao;

    @BeforeAll
    public static void setUp() {
        List<StorageUpdateHandler> storageUpdateHandlers = new ArrayList<>();
        storageUpdateHandlers.add(new FruitBalanceHandler());
        storageUpdateHandlers.add(new FruitSupplyHandler());
        storageUpdateHandlers.add(new FruitPurchaseHandler());
        storageUpdateHandlers.add(new FruitReturnHandler());
        operationStrategy = new OperationStrategyImpl(storageUpdateHandlers);
        fruitDao = new FruitDaoImpl();

    }

    @BeforeEach
    public void initializeNewField() {
        fruitInputData = new FruitInputData();
        fruitInputData.setOperationCode(BALANCE_OPERATION_CODE);
        fruitInputData.setFruitName(DEFAULT_FRUIT_NAME);
        fruitInputData.setAmount(DEFAULT_FRUIT_AMOUNT);
        operationStrategy.manageStorageCells(fruitInputData);
    }

    @Test
    public void balance_correctOperationCode_Ok() {
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "apple,10"
                + System.lineSeparator();
        Assertions.assertEquals(expected, getFinalReport(),
                String.format(ASSERTION_FAILURE_MESSAGE, expected, getFinalReport()));
    }

    @Test
    public void supply_correctOperationCode_Ok() {
        fruitInputData.setOperationCode(SUPPLY_OPERATION_CODE);
        fruitInputData.setFruitName(DEFAULT_FRUIT_NAME);
        fruitInputData.setAmount(DEFAULT_FRUIT_AMOUNT);
        operationStrategy.manageStorageCells(fruitInputData);
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "apple,20"
                + System.lineSeparator();
        Assertions.assertEquals(expected, getFinalReport(),
                String.format(ASSERTION_FAILURE_MESSAGE, expected, getFinalReport()));
    }

    @Test
    public void purchase_correctOperationCode_Ok() {
        fruitInputData.setOperationCode(PURCHASE_OPERATION_CODE);
        fruitInputData.setFruitName(DEFAULT_FRUIT_NAME);
        fruitInputData.setAmount(FRUIT_AMOUNT_CASE1);
        operationStrategy.manageStorageCells(fruitInputData);
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "apple,5"
                + System.lineSeparator();
        Assertions.assertEquals(expected, getFinalReport(),
                String.format(ASSERTION_FAILURE_MESSAGE, expected, getFinalReport()));
    }

    @Test
    public void purchase_incorrectFruitsAmount_notOk() {
        fruitInputData.setOperationCode(PURCHASE_OPERATION_CODE);
        fruitInputData.setFruitName(DEFAULT_FRUIT_NAME);
        fruitInputData.setAmount(FRUIT_AMOUNT_CASE2);
        Assertions.assertThrows(RuntimeException.class,
                () -> operationStrategy.manageStorageCells(fruitInputData));

    }

    @Test
    public void return_correctOperationCode_Ok() {
        fruitInputData.setOperationCode(PURCHASE_OPERATION_CODE);
        fruitInputData.setFruitName(DEFAULT_FRUIT_NAME);
        fruitInputData.setAmount(FRUIT_AMOUNT_CASE1);
        operationStrategy.manageStorageCells(fruitInputData);
        fruitInputData.setOperationCode(RETURN_OPERATION_CODE);
        fruitInputData.setFruitName(DEFAULT_FRUIT_NAME);
        fruitInputData.setAmount(FRUIT_AMOUNT_CASE3);
        operationStrategy.manageStorageCells(fruitInputData);
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "apple,8"
                + System.lineSeparator();

        Assertions.assertEquals(expected, getFinalReport(),
                String.format(ASSERTION_FAILURE_MESSAGE, expected, getFinalReport()));
    }

    @Test
    public void manage_incorrectOperationCode_notOk() {
        fruitInputData.setOperationCode(INCORRECT1_OPERATION_CODE);
        Assertions.assertThrows(RuntimeException.class,
                () -> operationStrategy.manageStorageCells(fruitInputData));
        fruitInputData.setOperationCode(INCORRECT2_OPERATION_CODE);
        Assertions.assertThrows(RuntimeException.class,
                () -> operationStrategy.manageStorageCells(fruitInputData));
        fruitInputData.setOperationCode(null);
        Assertions.assertThrows(RuntimeException.class,
                () -> operationStrategy.manageStorageCells(fruitInputData));

    }

    @AfterEach
    public void cleanFruitStorage() {
        fruitDao.removeAll();
    }

    private String getFinalReport() {
        StringBuilder reportBuilder = new StringBuilder(REPORT_COLUMNS);
        fruitDao.getAll().forEach((key, value) ->
                reportBuilder.append(System.lineSeparator())
                        .append(key)
                        .append(SEPARATE_SYMBOL)
                        .append(value));
        return reportBuilder + System.lineSeparator();
    }
}
