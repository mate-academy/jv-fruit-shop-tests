package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.CsvFileReader;
import core.basesyntax.CsvFileWriter;
import core.basesyntax.FileDataReader;
import core.basesyntax.FileDataWriter;
import org.junit.jupiter.api.Test;

public class FileServiceImplTest {
    private final FileDataReader fileDataReader = new CsvFileReader();
    private final FileDataWriter fileDataWriter = new CsvFileWriter();

    @Test
    public void read_pathIsNull_notOk() {
        assertThrows(
                RuntimeException.class,
                () -> this.fileDataReader.read(null)
        );
    }

    @Test
    public void read_pathIsEmpty_notOk() {
        assertThrows(
                RuntimeException.class,
                () -> this.fileDataReader.read("")
        );
    }

    @Test
    public void read_fileIsNotFound_notOk() {
        assertThrows(
                RuntimeException.class,
                () -> this.fileDataReader.read("")
        );
    }

    @Test
    public void write_pathIsNull_notOk() {
        assertThrows(
                RuntimeException.class,
                () -> this.fileDataReader.read(null)
        );
    }

    @Test
    public void write_pathIsEmpty_notOk() {
        assertThrows(
                RuntimeException.class,
                () -> this.fileDataWriter.write("", null)
        );
    }

    @Test
    public void write_validData_ok() {
        assertDoesNotThrow(
                () -> this.fileDataWriter.write("temp/test.txt", "")
        );
    }
}
