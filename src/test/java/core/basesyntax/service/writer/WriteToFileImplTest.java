package core.basesyntax.service.writer;

import core.basesyntax.db.Storage;
import core.basesyntax.service.transaction.ReportListFruit;
import core.basesyntax.service.transaction.ReportListFruitImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriteToFileImplTest {
    private static final String OUTPUT_FILE = "src/test/resources/output.csv";
    private WriteToFile write;
    private ReportListFruit reportListFruit;

    @BeforeEach
    void setUp() {
        Storage.fruitsDB.clear();
        write = new WriteToFileImpl();
        reportListFruit = new ReportListFruitImpl();
    }

    @Test
    void fileWriteIsExist_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            String content = "Where is my money Lebovski";
            String fileName = "notFile.csv";
            write.writeToFile(fileName, content);
        });
    }

    @Test
    void contentIsEmpty_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            String content = "";
            String fileName = "output.csv";
            write.writeToFile(fileName, content);
        });
    }

    @Test
    void contentIsNull_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> {
           String content = null;
           String fileName = "output.csv";
           write.writeToFile(fileName, content);
        });
    }

    @Test
    void writeContentToFile() {
        Storage.fruitsDB.put("apple", 100);
        Storage.fruitsDB.put("banana", 1000);
        Assertions.assertDoesNotThrow(() -> {
            String content = reportListFruit.createReport();
            write.writeToFile(OUTPUT_FILE, content);
            String string = "fruit,quantity\n"
                        + "banana,1000\n"
                        + "apple,100";
            string.equals(OUTPUT_FILE);
        });
    }
}
