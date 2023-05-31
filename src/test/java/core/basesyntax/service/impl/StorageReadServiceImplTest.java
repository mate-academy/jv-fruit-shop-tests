package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.StorageReadService;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageReadServiceImplTest {
    private static StorageReadService storageReadService;

    @BeforeClass
    public static void beforeClass() {
        storageReadService = new StorageReadServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readData_notExistingFile_notOk() {
        storageReadService.readData("src/main/resources/NotExisting.csv");
    }

    @Test
    public void readData_validFile_ok() {
        List<String> actual = storageReadService.readData("src/main/resources/storage.csv");
        List<String> expected = List.of("type,fruit,quantity",
                                        "b,banana,40",
                                        "b,cherry,25",
                                        "p,banana,20",
                                        "p,cherry,10");
        assertEquals(expected, actual);
    }
}
