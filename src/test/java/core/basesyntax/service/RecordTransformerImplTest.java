package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Record;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecordTransformerImplTest {
    private static RecordTransformer recordTransformer;
    private final Record rec1 = new Record("b","banana",20);
    private final Record rec2 = new Record("b","apple",100);
    private final Record rec3 = new Record("s","banana",100);
    private final Record rec4 = new Record("r","apple",10);
    private final List<Record> listRecords = List.of(rec1,rec2,rec3,rec4);
    private final List<String> inputList =
            List.of("b,banana,20", "b,apple,100","s,banana,100","r,apple,10");

    @BeforeAll
    static void beforeAll() {
        recordTransformer = new RecordTransformerImpl();
    }

    @BeforeEach
    void setUp() {
        recordTransformer.transform(inputList);
    }

    @Test
    void transformStringToRecordOne() {
        assertEquals(Storage.records.get(0).getFruitAmount(),listRecords.get(0).getFruitAmount());
    }

    @Test
    void transformStringToRecordTwo() {
        assertEquals(Storage.records.get(1).getFruitName(),listRecords.get(1).getFruitName());
    }

    @AfterEach
    void tearDown() {
        Storage.records.clear();
    }
}
