package core.basesyntax.fruitshop.report.generator;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private final FileWriterImpl fileWriter = new FileWriterImpl();

    @Test
    void writeReport_existingPath_ok() {
        fileWriter.writeReport("", "src/test/TestWriter.csv");
    }

    @Test
    void writeReport_nonExistentPath_notOk() {
        assertThrows(RuntimeException.class, () ->
                fileWriter.writeReport("", "srcw/testwf2/TestWriter.csv"));
    }
}
