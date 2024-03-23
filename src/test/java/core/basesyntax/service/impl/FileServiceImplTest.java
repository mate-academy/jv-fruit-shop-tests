package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.service.FileService;
import core.basesyntax.util.CommonUtils;
import org.junit.jupiter.api.Test;

class FileServiceImplTest {
    private static final String READ_FROM = "src/test/java/core/basesyntax/util/read.csv";
    private static final String READ_EMPTY = "src/test/java/core/basesyntax/util/empty.csv";
    private static final String NON_EXISTING = "";
    private static final String WRITE_TO = "src/test/java/core/basesyntax/util/write.csv";
    private static final String READ_EXCEPTION_MESSAGE = "Can't read from file ";
    private static final String WRITE_EXCEPTION_MESSAGE = "Can't write to file ";

    private FileService fileService = new FileServiceImpl();

    @Test
    void read_fromFile_ok() {
        String lines = fileService.readFromFile(READ_FROM);
        assertEquals(CommonUtils.getRawData(), lines);
    }

    @Test
    void read_emptyFile_ok() {
        String emptyLine = fileService.readFromFile(READ_EMPTY);
        assertEquals("", emptyLine);
    }

    @Test
    void read_nullFile_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            String lines = fileService.readFromFile(NON_EXISTING);
        });
        assertEquals(READ_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    void write_toFile_ok() {
        fileService.writeToFile(WRITE_TO, CommonUtils.getRawData());
        String lines = fileService.readFromFile(WRITE_TO);
        assertEquals(CommonUtils.getRawData(), lines);
    }

    @Test
    void write_toNonExistingFile_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fileService.writeToFile(NON_EXISTING, CommonUtils.getRawData());
        });
        assertEquals(WRITE_EXCEPTION_MESSAGE, exception.getMessage());
    }
}
