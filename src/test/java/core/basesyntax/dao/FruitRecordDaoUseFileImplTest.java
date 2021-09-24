package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.bd.Storage;
import core.basesyntax.model.FruitRecordDto;
import java.io.IOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitRecordDaoUseFileImplTest {
    private static FruitRecordDao fruitRecordDao;
    private static FruitRecordDto modelFruitRecordDto;
    private static final String CORRECT_PATH_FROM = "src/test/resources/filesFruitShop.csv";
    private static final String CORRECT_PATH_TO = "src/test/resources/report_fruit_shop.csv";
    private static final String EXPECTED = "src/test/resources/expected.csv";
    private static final String INCORRECT_EXPECTED = "src/test/resources/incorrect_expected.csv";

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

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.records.clear();
    }
}
