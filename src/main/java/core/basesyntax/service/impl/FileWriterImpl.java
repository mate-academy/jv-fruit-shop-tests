package core.basesyntax.service.impl;

import core.basesyntax.service.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriterImpl implements Writer {
    @Override
    public void writeToFile(String resultingReport, String path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(resultingReport);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file by path " + path, e);
        } catch (NullPointerException e) {
            throw new RuntimeException(" Cannot write resultingReport because it is null "
                    + path, e);
        }
    }
}
