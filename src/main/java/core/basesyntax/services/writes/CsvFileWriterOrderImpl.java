package core.basesyntax.services.writes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CsvFileWriterOrderImpl implements CsvFileWriterOrder {
    private static final String EXCEPTION_TEXT = "An error occurred during recording";
    private static final String NOT_EXIST = ("File does not exist: ");
    private static final String BE_NULL = "File path cannot be null: ";

    @Override
    public boolean writerOrder(String content, String filePath) {
        checkFileAndPath(content,filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(content);
            writer.newLine();
            return true;
        } catch (IOException e) {
            throw new RuntimeException(EXCEPTION_TEXT, e);
        }
    }

    private void checkFileAndPath(String content,String filePath) {
        if (filePath == null) {
            throw new IllegalArgumentException(BE_NULL + filePath);
        }

        if (content.length() == 0) {
            throw new IllegalArgumentException(NOT_EXIST + content);
        }
    }
}
