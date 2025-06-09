// Short Print Visitor
public class ShortPrintVisitor implements Visitor {
    private StringBuilder result = new StringBuilder();

    public String getResult() {
        return result.toString().trim();
    }

    @Override
    public void visit(Island island) {
        result.append(island.getFullName()).append("\n");
        for (Element child : island.getChildren()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(Lake lake) {
        result.append(lake.getFullName()).append("\n");
        for (Element child : lake.getChildren()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(Tree tree) {
        result.append(tree.getFullName()).append("\n");
    }

    @Override
    public void visit(Boat boat) {
        result.append(boat.getFullName()).append("\n");
    }

    @Override
    public void visit(Flag flag) {
        result.append(flag.getFullName()).append("\n");
    }

    @Override
    public void visit(Kid kid) {
        result.append(kid.getFullName()).append("\n");
    }

    @Override
    public void visit(Kite kite) {
        result.append(kite.getFullName()).append("\n");
    }

    @Override
    public void visit(Painting painting) {
        for (Element element : painting.getElements()) {
            element.accept(this);
        }
    }
}

