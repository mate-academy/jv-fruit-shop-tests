package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.service.FileService;
import org.junit.jupiter.api.Test;

class FileServiceImplTest {
    private static final String READ_FROM = "src/test/java/core/basesyntax/util/read.csv";
    private static final String READ_EMPTY = "src/test/java/core/basesyntax/util/empty.csv";
    private static final String INVALID_FILE = "";
    private static final String WRITE_TO = "src/test/java/core/basesyntax/util/write.csv";
    private static final String READ_EXCEPTION_MESSAGE = "Can't read from file ";
    private static final String WRITE_EXCEPTION_MESSAGE = "Can't write to file ";
    private static final String RAW_DATA = "type,fruit,quantity" + System.lineSeparator()
            + "b,banana,20" + System.lineSeparator()
            + "b,apple,100" + System.lineSeparator()
            + "s,banana,100" + System.lineSeparator()
            + "p,banana,13" + System.lineSeparator()
            + "r,apple,10" + System.lineSeparator()
            + "p,apple,20" + System.lineSeparator()
            + "p,banana,5" + System.lineSeparator()
            + "s,banana,50";

    private FileService fileService = new FileServiceImpl();

    @Test
    void read_fromFile_ok() {
        String lines = fileService.readFromFile(READ_FROM);
        assertEquals(RAW_DATA, lines);
    }

    @Test
    void read_emptyFile_ok() {
        String emptyLine = fileService.readFromFile(READ_EMPTY);
        assertEquals("", emptyLine);
    }

    @Test
    void read_nullFile_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            String lines = fileService.readFromFile(INVALID_FILE);
        });
        assertEquals(READ_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    void write_toFile_ok() {
        fileService.writeToFile(WRITE_TO,RAW_DATA);
        String lines = fileService.readFromFile(WRITE_TO);
        assertEquals(RAW_DATA, lines);
    }

    @Test
    void write_toFile_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fileService.writeToFile(INVALID_FILE, RAW_DATA);
        });
        assertEquals(WRITE_EXCEPTION_MESSAGE, exception.getMessage());
    }
}
