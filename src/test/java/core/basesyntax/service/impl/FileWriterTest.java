package core.basesyntax.service.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import core.basesyntax.service.Writer;
import org.junit.jupiter.api.Test;

class FileWriterTest {
    private static final Writer fileWriter = new FileWriterImpl();

    @Test
    public void read_nullReport_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> fileWriter.write(null, "test.txt"));
    }

    @Test
    public void read_emptyReport_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> fileWriter.write("", "test.txt"));
    }

    @Test
    public void read_nullFileName_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> fileWriter.write("test", null));
    }

    @Test
    public void read_emptyFileName_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> fileWriter.write("test", ""));
    }

    @Test
    public void read_normalFile_Ok() {
        assertDoesNotThrow(() -> fileWriter.write(
                "test", "test.txt"));
    }
}
