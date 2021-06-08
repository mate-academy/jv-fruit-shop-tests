package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitDto;
import org.junit.Test;

public class TypeStrategyImplTest {
    private final TypeStrategyImpl typeStrategy = new TypeStrategyImpl();

    @Test
    public void getTypeHandlerForBalanceOperation_Ok() {
        FruitDto fruitDto = new FruitDto("b", "banana", 100);
        TypeHandler expected = new BalanceOperation();
        TypeHandler actual = typeStrategy.getTypeHandler(fruitDto);
        assertEquals("You choose type handler wrong", actual.getClass(), expected.getClass());
    }

    @Test
    public void getTypeHandlerForPurchaseOperation_Ok() {
        FruitDto fruitDto = new FruitDto("p", "banana", 100);
        TypeHandler expected = new PurchaseOperation();
        TypeHandler actual = typeStrategy.getTypeHandler(fruitDto);
        assertEquals("You choose type handler wrong", actual.getClass(), expected.getClass());
    }

    @Test
    public void getTypeHandlerForSupplyOperation_Ok() {
        FruitDto fruitDto = new FruitDto("s", "banana", 100);
        TypeHandler expected = new SupplyOperation();
        TypeHandler actual = typeStrategy.getTypeHandler(fruitDto);
        assertEquals("You choose type handler wrong", actual.getClass(), expected.getClass());
    }

    @Test
    public void getTypeHandlerForReturnOperation_Ok() {
        FruitDto fruitDto = new FruitDto("r", "banana", 100);
        TypeHandler expected = new ReturnOperation();
        TypeHandler actual = typeStrategy.getTypeHandler(fruitDto);
        assertEquals("You choose type handler wrong", actual.getClass(), expected.getClass());
    }
}
