

public class CountElementsVisitor implements Visitor {
    private int count = 0;

    public int getCount() {
        return count;
    }

    public void clearCount() { count = 0; }

    @Override
    public void visit(Island island) {
        count++;
        for (Element child : island.getChildren()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(Lake lake) {
        count++;
        for (Element child : lake.getChildren()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(Tree tree) {
        count++;
    }

    @Override
    public void visit(Boat boat) {
        count++;
    }

    @Override
    public void visit(Flag flag) {
        count++;
    }

    @Override
    public void visit(Kid kid) {
        count++;
    }

    @Override
    public void visit(Kite kite) {
        count++;
    }

    @Override
    public void visit(Painting painting) {
        for (Element element : painting.getElements()) {
            element.accept(this);
        }
    }
}
