package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    private static final FileWriterServiceImpl fileWriter = new FileWriterServiceImpl();
    private static final String DATA = "fruit,quantity" + System.lineSeparator()
            + "banana,100" + System.lineSeparator() + "apple,100";
    private static final String DIR_RESOURCES = "src" + File.separator + "test" + File.separator
            + "resources" + File.separator;

    @Test
    void writeToFile_NonExistingFile_Ok() {
        fileWriter.writeToFile(DIR_RESOURCES + "Report.csv", DATA);
    }

    @Test
    void writeToFile_NullValues_notOk() {
        assertThrows(RuntimeException.class, () -> fileWriter.writeToFile(null, null));
        assertThrows(RuntimeException.class, () -> fileWriter.writeToFile(null, DATA));
        assertThrows(RuntimeException.class, ()
                -> fileWriter.writeToFile(DIR_RESOURCES + "FruitReport.csv", null));
    }

    @Test
    void writeToFile_ExistingFile_Ok() {
        fileWriter.writeToFile(DIR_RESOURCES + "StoreReport.csv", DATA);
    }
}
