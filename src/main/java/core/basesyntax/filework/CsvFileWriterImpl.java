package core.basesyntax.filework;

import java.io.IOException;
import java.io.Writer;

public class CsvFileWriterImpl implements FileWriter {
    @Override
    public void write(String path, String content) {

        try (Writer writer = new java.io.FileWriter(path)) {
            writer.append(content);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file");
        }
    }
}
