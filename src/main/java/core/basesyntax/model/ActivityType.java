package core.basesyntax.model;

public enum ActivityType {
    b("balance"),
    p("purchase"),
    r("return"),
    s("supply");

    private String fullName;

    ActivityType(String fullName) {
        this.fullName = fullName;
    }

    String getFullName() {
        return fullName;
    }
}
