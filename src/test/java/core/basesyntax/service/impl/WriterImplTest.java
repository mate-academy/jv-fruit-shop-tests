package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterImplTest {
    private final WriterImpl writer = new WriterImpl();
    private final ReaderImpl reader = new ReaderImpl();

    @BeforeAll
    static void setup() {
        Storage.clearStorage();
    }

    @Test
    void writeToFile_nullFilepath_notOk() {
        assertThrows(RuntimeException.class, () -> writer.writeToFile(null));
    }

    @Test
    void writeToFile_emptyFilepath_notOk() {
        assertThrows(RuntimeException.class, () -> writer.writeToFile(""));
    }

    @Test
    void writeToFile_ok() {
        Storage.addFruits("banana", 10);
        writer.writeToFile("src/main/resources/output.txt");
        List<String> writerCheck = reader.readFromFile("src/main/resources/output.txt");
        assertEquals("[banana,10]", writerCheck.toString());
    }
}
