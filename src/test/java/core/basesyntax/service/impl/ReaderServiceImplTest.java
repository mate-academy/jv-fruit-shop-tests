package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private final ReaderService readerService = new ReaderServiceImpl();

    @Test
    void readFromFile_toNotExistFileName_notOk() {
        assertThrows(RuntimeException.class, () -> readerService.readFromFile(null));
    }

    @Test
    void readFromFile_wrongFileDirectory_notOk() {
        String path = "system/test/file/folder/dfg.csv";
        assertThrows(RuntimeException.class, () -> readerService.readFromFile(path));
    }

    @Test
    void readFromFile_toExistFile_Ok() {
        String path = "src/test/resources/fruitsTest.csv";
        List<String> dataList = readerService.readFromFile(path);
        assertFalse(dataList.isEmpty(), "file is empty");
    }
}
