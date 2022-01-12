package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String FILE_NAME_TO =
            "src/main/java/core/basesyntax/resources/reportData.csv";
    private static FileWriter fileWriter;

    @BeforeAll
    static void beforeAll() {
        fileWriter = new FileWriterImpl();
    }

    @BeforeEach
    void setUp() {
        List<String> report = List.of("banana,152", "apple,90");
        fileWriter.write(report, FILE_NAME_TO);
    }

    @Test
    void readFirstRecordFromWrittenFile() {
        assertEquals(new FileReaderImpl().read(FILE_NAME_TO).get(0), "fruit,amount");
    }

    @Test
    void readLastRecordFromWrittenFile() {
        assertEquals(new FileReaderImpl().read(FILE_NAME_TO).get(2), "apple,90");
    }

    @AfterEach
    void tearDown() {
        Storage.records.clear();
    }
}
