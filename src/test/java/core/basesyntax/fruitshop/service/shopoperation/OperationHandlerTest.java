package core.basesyntax.fruitshop.service.shopoperation;

import core.basesyntax.fruitshop.dao.FruitStorageDaoImpl;
import core.basesyntax.fruitshop.exception.FruitQuantityException;
import core.basesyntax.fruitshop.model.dto.FruitOperationDto;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationHandlerTest {
    private static FruitOperationDto fruitOperationDto;
    private static OperationHandler storageDecrease;

    @BeforeClass
    public static void beforeClass() {
        storageDecrease = new StorageDecreaseHandler(new FruitStorageDaoImpl());
    }

    @Test
    public void getVariationValue_OperationWithPositiveFruitQuantity_isOk() {
        fruitOperationDto = new FruitOperationDto(OperationType.PURCHASE,
                "banana", new BigDecimal(20));
        BigDecimal expected = BigDecimal.valueOf(20);
        BigDecimal actual = storageDecrease.getVariationValue(fruitOperationDto);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = FruitQuantityException.class)
    public void getVariationValue_OperationWithNegativeFruitQuantity_IsNotOk() {
        fruitOperationDto = new FruitOperationDto(OperationType.PURCHASE,
                "banana", new BigDecimal(-20));
        storageDecrease.getVariationValue(fruitOperationDto);
    }

    @Test(expected = NullPointerException.class)
    public void getVariationValue_FruitOperationDtoWithNullValues() {
        FruitOperationDto nullableDto = new FruitOperationDto(null, null, null);
        storageDecrease.getVariationValue(nullableDto);
    }
}
