public class RoutineReport implements Report {
    private final String content;
    public RoutineReport(String content) { this.content = content; }

    @Override
    public String getContent() {
        return getType() + ": " + content;
    }
    @Override
    public String getType() { return "RoutineReport"; }
}
