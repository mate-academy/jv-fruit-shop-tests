package core.basesyntax.service.impl;

import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.storage.DataBase;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseFruitHandlerImplTest {
    private static final Map<String, Integer> db = new HashMap<>();
    private static final AddHandlerImpl addHandler = new AddHandlerImpl();
    private static final PurchaseFruitHandlerImpl purchaseHandler
            = new PurchaseFruitHandlerImpl();
    private static OperationType operationTypeSupply;
    private static OperationType operationTypeBalance;
    private static FruitRecordDto fruitRecordDtoBalance;
    private static FruitRecordDto fruitRecordDto;
    private static int balanceAmount;
    private static int purchaseAmount;

    @BeforeEach
    void setUp() {
        operationTypeSupply = OperationType.SUPPLY;
        operationTypeBalance = OperationType.BALANCE;
        purchaseAmount = 25;
        balanceAmount = 50;
        fruitRecordDto
                = new FruitRecordDto(operationTypeSupply, "banana", purchaseAmount);
        fruitRecordDtoBalance =
                new FruitRecordDto(operationTypeBalance, "banana", balanceAmount);
    }

    @AfterEach
    void tearDown() {
        DataBase.getDataBase().remove(fruitRecordDto.getName());
    }

    @Test
    void getAmountAfterPurchase_Ok() {
        addHandler.applyFruitToStorage(fruitRecordDtoBalance);
        int actual = purchaseHandler.applyFruitToStorage(fruitRecordDto);
        db.put(fruitRecordDto.getName(), fruitRecordDto.getAmount());
        Assertions.assertEquals(Optional.ofNullable(db.get(fruitRecordDto.getName())),
                Optional.of(actual));
    }
}
