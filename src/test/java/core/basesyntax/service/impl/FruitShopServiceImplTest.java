package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.exception.FruitShopException;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private static final String BALANCE = "b";
    private static final String SUPPLY = "s";
    private static final String PURCHASE = "p";
    private static final String RETURN = "r";
    private static final String INPUT_FILE = "src/main/resources/input.txt";
    private static final String OUTPUT_FILE = "src/main/resources/output.txt";
    private static final String CONTENT_OUTPUT_FILE
            = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90";
    private FruitShopService fruitShopService;

    @Before
    public void setUp() {
        Map<String, OperationHandler> operationHandlerMap = new HashMap<>();

        operationHandlerMap.put(BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(PURCHASE, new PurchaseOperationHandler());
        operationHandlerMap.put(RETURN, new ReturnOperationHandler());

        fruitShopService = new FruitShopServiceImpl(
                new ParserServiceImpl(),
                new FruitsHolderServiceImpl(),
                new OperationStrategyImpl(operationHandlerMap),
                new ReportMakerServiceImpl()
        );
    }

    @Test
    public void report_defaultCase_ok() {
        fruitShopService.report(INPUT_FILE, OUTPUT_FILE);
        StringBuilder result = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(OUTPUT_FILE))) {
            String value = br.readLine();
            while (value != null) {
                result.append(value).append(System.lineSeparator());
                value = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals("Report method not working correctly",
                CONTENT_OUTPUT_FILE, result.toString().trim());
    }

    @Test
    public void report_argumentsNull_notOk() {
        try {
            fruitShopService.report(null, null);
        } catch (FruitShopException e) {
            return;
        }
        fail("report must throw FruitShopException if one of the arguments is null");
    }

    @Test
    public void report_firstArgumentIsNull_notOk() {
        try {
            fruitShopService.report(null, OUTPUT_FILE);
        } catch (FruitShopException e) {
            return;
        }
        fail("report must throw FruitShopException if one of the arguments is null");
    }

    @Test
    public void report_secondArgumentIsNull_notOk() {
        try {
            fruitShopService.report(INPUT_FILE, null);
        } catch (FruitShopException e) {
            return;
        }
        fail("report must throw FruitShopException if one of the arguments is null");
    }
}
