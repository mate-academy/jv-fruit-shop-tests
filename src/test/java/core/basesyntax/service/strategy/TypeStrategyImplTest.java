package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitRecord;
import core.basesyntax.service.strategy.strategyimpl.BalanceStrategy;
import core.basesyntax.service.strategy.strategyimpl.PurchaseStrategy;
import core.basesyntax.service.strategy.strategyimpl.ReturnStrategy;
import core.basesyntax.service.strategy.strategyimpl.SupplyStrategy;
import core.basesyntax.service.strategy.strategyimpl.TypeService;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TypeStrategyImplTest {
    private TypeStrategy typeStrategy;
    private TypeService typeService;

    @BeforeEach
    void setUp() {
        HashMap<FruitRecord.Operation, TypeService> typeServiceHashMap = new HashMap<>();
        typeServiceHashMap.put(FruitRecord.Operation.SUPPLY, new SupplyStrategy());
        typeServiceHashMap.put(FruitRecord.Operation.BALANCE, new BalanceStrategy());
        typeServiceHashMap.put(FruitRecord.Operation.RETURN, new ReturnStrategy());
        typeServiceHashMap.put(FruitRecord.Operation.PURCHASE, new PurchaseStrategy());
        typeStrategy = new TypeStrategyImpl(typeServiceHashMap);
    }

    @Test
    void typeStrategy_inputCorrect_isOk() {
        assertEquals(typeStrategy.getType(FruitRecord.Operation.SUPPLY).getClass(),
                SupplyStrategy.class);
        assertEquals(typeStrategy.getType(FruitRecord.Operation.PURCHASE).getClass(),
                PurchaseStrategy.class);
        assertEquals(typeStrategy.getType(FruitRecord.Operation.RETURN).getClass(),
                ReturnStrategy.class);
        assertEquals(typeStrategy.getType(FruitRecord.Operation.BALANCE).getClass(),
                BalanceStrategy.class);
    }
}
