package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitShopDao;
import core.basesyntax.db.FruitShopDaoImpl;
import core.basesyntax.service.FruitStorageWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitStorageWriterImplTest {
    private static final String NULL_STRING = null;
    private FruitStorageWriter fruitStorageWriter;

    @BeforeEach
    void setUp() {
        FruitShopDao fruitShopDao = new FruitShopDaoImpl();
        fruitStorageWriter = new FruitStorageWriterImpl(fruitShopDao);
    }

    @Test
    void writeReportToFile_NullInput_NotOk() {
        assertThrows(NullPointerException.class,
                () -> fruitStorageWriter.writeReportToFile(NULL_STRING),
                "Null String");
    }
}
