public class MovementReport implements Report {
    private final String content;
    public MovementReport(String content) { this.content = content; }

    @Override
    public String getContent() {
        return getType() + ": " + content;
    }
    @Override
    public String getType() { return "MovementReport"; }

}
