package core.basesyntax.service.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import core.basesyntax.service.Reader;
import org.junit.jupiter.api.Test;

class FileReaderTest {
    private static final Reader fileReader = new FileReaderImpl();

    @Test
    public void read_nullFileName_notOk() {
        assertThrows(IllegalArgumentException.class, () -> fileReader.read(null));
    }

    @Test
    public void read_emptyFileName_notOk() {
        assertThrows(IllegalArgumentException.class, () -> fileReader.read(""));
    }

    @Test
    public void read_nonExistingFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileReader.read("nonExistingFile.txt"));
    }

    @Test
    public void read_emptyLinesFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileReader.read("empty_line_file.txt"));
    }

    @Test
    public void read_nonTxtFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileReader.read("weird_file.png"));
    }

    @Test
    public void read_normalFile_Ok() {
        assertDoesNotThrow(() -> fileReader.read(
                "src/test/resources/normal_source_file.txt"));
    }
}
