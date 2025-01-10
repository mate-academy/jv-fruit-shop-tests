package core.basesyntax.service.impl;

import core.basesyntax.exceptions.OperationDefinitionException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import core.basesyntax.service.OperationDefStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static DataConverter dataConverter;
    private static List<String> input = new ArrayList<>();

    @BeforeAll
    static void beforeAll() {
        Map<String, FruitTransaction.Operation> operationMap = new HashMap<>();
        operationMap.put("b", FruitTransaction.Operation.BALANCE);
        operationMap.put("s", FruitTransaction.Operation.SUPPLY);
        operationMap.put("p", FruitTransaction.Operation.PURCHASE);
        operationMap.put("r", FruitTransaction.Operation.RETURN);
        OperationDefStrategy operationDefStrategy = new OperationDefStrategyImpl(operationMap);
        dataConverter = new DataConverterImpl(operationDefStrategy);
    }

    @AfterEach
    void tearDown() {
        input.clear();
    }

    @Test
    void dataConvertTest_Ok() {
        input.add("type,fruit,quantity");
        input.add("b,banana,20");
        input.add("b,apple,100");
        input.add("s,banana,100");
        input.add("p,banana,13");
        input.add("r,apple,10");

        List<FruitTransaction> expectedResult = new ArrayList<>();
        expectedResult.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "banana",
                20
        ));
        expectedResult.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "apple",
                100
        ));
        expectedResult.add(new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                "banana",
                100
        ));
        expectedResult.add(new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                "banana",
                13
        ));
        expectedResult.add(new FruitTransaction(
                FruitTransaction.Operation.RETURN,
                "apple",
                10
        ));

        List<FruitTransaction> actualResult = dataConverter.convertToTransaction(input);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void dataConvertTest_NotOk() {
        input.add("type,fruit,quantity");
        input.add("i,banana,20");
        Assertions.assertThrows(OperationDefinitionException.class, () -> {
            dataConverter.convertToTransaction(input);
        });
    }
}
