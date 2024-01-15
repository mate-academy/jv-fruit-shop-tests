package core.basesyntax.model.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.DataRecorder;
import core.basesyntax.service.impl.DataRecorderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataRecordeImplTest {
    private static final String EXPECTED_RESULT = "fruit, quantity"
            + System.lineSeparator() + "banana,13"
            + System.lineSeparator() + "apple,34"
            + System.lineSeparator();

    private DataRecorder dataRecorder;

    @BeforeEach
    void setUp() {
        FruitDao fruitDao = new FruitDaoImpl();
        dataRecorder = new DataRecorderImpl();
        fruitDao.addFruits("banana", 13);
        fruitDao.addFruits("apple", 34);
    }

    @Test
    void dataRecord_check_ok() {
        assertEquals(EXPECTED_RESULT, dataRecorder.recordData(Storage.storage));
    }
}
