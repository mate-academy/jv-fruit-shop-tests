package core.basesyntax;

import core.basesyntax.db.FileWriter;
import core.basesyntax.db.FileWriterImpl;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileWriterTest {
    @Test
    void write_newReport_ok() {
        String fileName = "src/main/resources/report.csv";
        FileWriter fileWriter = new FileWriterImpl();
        String data = "Some data to test";
        Assertions.assertDoesNotThrow(() -> fileWriter.write(data, fileName));
    }

    @Test
    void write_wrongDir_notOk() {
        String fileName = "src/main/resources/somedir/report.csv";
        FileWriter fileWriter = new FileWriterImpl();
        String data = "Some data to test";
        Assertions.assertThrows(RuntimeException.class, () -> fileWriter.write(data, fileName));
    }

    @Test
    void write_fileAlreadyLocked_notOk() throws IOException {
        String fileName = "src/main/resources/report.csv";
        FileWriter fileWriter = new FileWriterImpl();
        String data = "Some data to test";
        FileLock fileLock = null;
        try {
            RandomAccessFile raf = new RandomAccessFile("src/main/resources/report.csv", "rw");
            FileChannel channel = raf.getChannel();
            fileLock = channel.lock();
            Assertions.assertThrows(RuntimeException.class, () -> fileWriter.write(data, fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (fileLock != null) {
                fileLock.release();
            }
        }
    }
}
