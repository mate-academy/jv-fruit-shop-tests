package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.bd.Storage;
import core.basesyntax.model.FruitRecordDto;
import java.io.IOException;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitRecordDaoUseFileImplTest {
    private static FruitRecordDao fruitRecordDao;
    private static FruitRecordDto modelFruitRecordDto;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitRecordDao = new FruitRecordDaoUseFileImpl();
        modelFruitRecordDto = new FruitRecordDto();
    }

    @Test
    public void add_checkAdding_Ok() {
        modelFruitRecordDto.setType(FruitRecordDto.Activities.RETURN);
        modelFruitRecordDto.setFruit("banana");
        modelFruitRecordDto.setAmount(20);
        fruitRecordDao.add(modelFruitRecordDto);
        int expected = 1;
        int actual = Storage.records.size();
        assertEquals(expected, actual);
    }

    @Test
    public void add_checkCorrectAdding_Ok() throws IOException {
        modelFruitRecordDto.setType(FruitRecordDto.Activities.RETURN);
        modelFruitRecordDto.setFruit("banana");
        modelFruitRecordDto.setAmount(20);
        fruitRecordDao.add(modelFruitRecordDto);
        FruitRecordDto actual = Storage.records.get(0);
        assertEquals(modelFruitRecordDto, actual);
    }

    @After
    public void tearDown() throws Exception {
        Storage.records.clear();
        Storage.fruitQuantity.clear();
    }
}
