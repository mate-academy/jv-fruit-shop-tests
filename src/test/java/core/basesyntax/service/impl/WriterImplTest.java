package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WriterImplTest {
    WriterImpl writer = new WriterImpl();
    ReaderImpl reader = new ReaderImpl();
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