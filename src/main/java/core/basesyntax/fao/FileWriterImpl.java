package core.basesyntax.fao;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileWriterImpl implements CustomFileWriter {
    private static final String FILE_TO_WRITE = "finalReport.csv";

    @Override
    public void write(String info) {
        String filePath = "output/" + FILE_TO_WRITE;

        try {
            Files.createDirectories(Paths.get("output"));
        } catch (IOException e) {
            throw new RuntimeException("Error creating output directory", e);
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            bufferedWriter.write(info);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to the file " + FILE_TO_WRITE, e);
        }
    }
}
