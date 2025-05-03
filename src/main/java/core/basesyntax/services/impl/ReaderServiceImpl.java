package core.basesyntax.services.impl;

import core.basesyntax.exceptions.FileReadException;
import core.basesyntax.model.Activity;
import core.basesyntax.parsers.ActivityParser;
import core.basesyntax.parsers.impl.ActivityParserImpl;
import core.basesyntax.services.FileReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReaderServiceImpl implements FileReaderService {
    private ActivityParser activityParser;
    private LineValidatorImpl lineValidator;

    public ReaderServiceImpl() {
        activityParser = new ActivityParserImpl();
        lineValidator = new LineValidatorImpl();
    }

    @Override
    public List<Activity> readFile(Path path) {
        if (path == null || !Files.exists(path)) {
            throw new FileReadException("File path is wrong");
        }
        try (Stream<String> stringStream = Files.lines(path)) {
            List<Activity> activities = stringStream
                    .skip(1)
                    .filter(s -> lineValidator.validate(s))
                    .map(activityParser::parse)
                    .collect(Collectors.toList());
            if (activities.size() < 2) {
                throw new FileReadException("File is empty");
            }
            return activities;
        } catch (IOException e) {
            throw new RuntimeException("Check file with path " + path + " " + e);
        }
    }
}
