package core.basesyntax.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileReaderImpl implements FileReader {

    private static final String LINE_PATTERN = "^[bprs],\\w+,\\d+$";

    @Override
    public List<String> reader(String fileName) {

        try {
            List<String> lines = Files.readAllLines(Path.of(fileName));

            for (String line : lines) {
                if (!isValidLine(line)) {
                    throw new RuntimeException("Invalid data in line: " + line);
                }
            }

            return lines;

        } catch (IOException e) {
            throw new RuntimeException("Can't read files", e);
        }
    }

    private boolean isValidLine(String line) {
        Pattern pattern = Pattern.compile(LINE_PATTERN);
        Matcher matcher = pattern.matcher(line);
        return matcher.matches();
    }
}
