package core.basesyntax.strategy.filestrategy;

import core.basesyntax.exception.InvalidFileTypeException;
import java.util.Map;
import java.util.Optional;

public class ReportBuilderStrategy {
    private final Map<String, ReportBuilder> reportBuildersMap;

    public ReportBuilderStrategy(Map<String, ReportBuilder> reportBuildersMap) {
        this.reportBuildersMap = reportBuildersMap;
    }

    public ReportBuilder getReportBuilder(String type) {
        Optional<ReportBuilder> reportBuilderOptional
                = Optional.ofNullable(reportBuildersMap.get(type));
        return reportBuilderOptional.orElseThrow(
                () -> new InvalidFileTypeException("Invalid file type " + type));
    }
}
