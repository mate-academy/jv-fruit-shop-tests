package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String FILE_NAME_FROM =
            "src/main/java/core/basesyntax/resources/inputData.csv";

    @Test
    void readFirstRecord() {
        assertEquals(new FileReaderImpl().read(FILE_NAME_FROM).get(0), "type,fruit,quantity");
    }

    @Test
    void readLastRecord() {
        assertEquals(new FileReaderImpl().read(FILE_NAME_FROM).get(8), "s,banana,50");
    }
}
