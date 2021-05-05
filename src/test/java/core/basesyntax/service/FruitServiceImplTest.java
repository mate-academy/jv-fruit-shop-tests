package core.basesyntax.service;

import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.dao.strategy.FruitBalance;
import core.basesyntax.dao.strategy.FruitOperations;
import core.basesyntax.dao.strategy.FruitPurchase;
import core.basesyntax.dao.strategy.FruitSupplyOrReturn;
import core.basesyntax.dto.FruitDto;
import core.basesyntax.service.validation.Validator;
import core.basesyntax.service.validation.ValidatorImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static final String BALANCE = "b";
    private static final String PURCHASE = "p";
    private static final String RETURN = "r";
    private static final String SUPPLY = "s";
    private static final int VALID_QUANTITY = 50;
    private static final int INVALID_QUANTITY = -20;
    private static final String FRUIT_BANANA = "banana";
    private static FruitDto validBalanceDto;
    private static FruitDto validSupplyDto;
    private static FruitDto validPurchaseDto;
    private static FruitDto validReturnDto;
    private static FruitDao fruitDao;
    private static FruitService fruitService;
    private static List<FruitDto> fruits;

    @BeforeClass
    public static void beforeClass() {
        Map<String, FruitOperations> operationsMap = new HashMap<>();
        operationsMap.put(BALANCE, new FruitBalance());
        operationsMap.put(PURCHASE, new FruitPurchase());
        operationsMap.put(RETURN, new FruitSupplyOrReturn());
        operationsMap.put(SUPPLY, new FruitSupplyOrReturn());
        fruitDao = new FruitDaoImpl();
        Validator validator = new ValidatorImpl(operationsMap, fruitDao);
        fruitService = new FruitServiceImpl(operationsMap, validator, fruitDao);
        validBalanceDto = new FruitDto(FRUIT_BANANA, VALID_QUANTITY, BALANCE);
        validSupplyDto = new FruitDto(FRUIT_BANANA, VALID_QUANTITY, SUPPLY);
        validPurchaseDto = new FruitDto(FRUIT_BANANA, VALID_QUANTITY, PURCHASE);
        validReturnDto = new FruitDto(FRUIT_BANANA, VALID_QUANTITY, RETURN);
        fruits = new ArrayList<>();
    }

    @Test
    public void applyOperationsOnFruitsDto_OnValidFruitsDto_Ok() {
        fruits.add(validBalanceDto);
        fruits.add(validSupplyDto);
        fruits.add(validReturnDto);
        fruits.add(validPurchaseDto);
        fruits.add(validPurchaseDto);
        fruitService.applyOperationsOnFruitsDto(fruits);
        assertTrue(fruitDao.containFruit(FRUIT_BANANA)
                && fruitDao.getQuantity(FRUIT_BANANA) == VALID_QUANTITY);
    }

    @Test(expected = RuntimeException.class)
    public void applyOperationsOnFruitsDto_OnInvalidFruitsDto_Ok() {
        fruits.add(new FruitDto(FRUIT_BANANA, INVALID_QUANTITY, BALANCE));
        fruitService.applyOperationsOnFruitsDto(fruits);
    }

    @After
    public void tearDown() {
        fruits.clear();
        fruitDao.clearStorage();
    }
}
