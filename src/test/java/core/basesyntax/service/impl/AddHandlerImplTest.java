package core.basesyntax.service.impl;

import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.storage.DataBase;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddHandlerImplTest {
    private static final Map<String, Integer> db = new HashMap<>();
    private static final OperationType operationType = core.basesyntax.service.impl.OperationType
            .getOperationType("s");
    private static FruitRecordDto fruitRecordDto;
    private static final AddHandlerImpl addHandler = new AddHandlerImpl();
    private static int amount;

    @BeforeEach
    void setUp() {
        amount = 25;
        fruitRecordDto
                = new FruitRecordDto(operationType, "banana", amount);
    }

    @Test
    void getAmountOfFruitFromStorage_Ok() {
        db.put(fruitRecordDto.getName(), fruitRecordDto.getAmount());
        int expected = addHandler.applyFruitToStorage(fruitRecordDto);
        int actual = db.get(fruitRecordDto.getName());
        Assert.assertEquals(expected, actual);
        DataBase.getDataBase().remove(fruitRecordDto.getName());
    }

    @Test
    void addFruitToStorage_Ok() {
        db.put(fruitRecordDto.getName(), fruitRecordDto.getAmount());
        addHandler.applyFruitToStorage(fruitRecordDto);
        Assert.assertEquals(db, DataBase.getDataBase());
        DataBase.getDataBase().remove(fruitRecordDto.getName());
    }
}
