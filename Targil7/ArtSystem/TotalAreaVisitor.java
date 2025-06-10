// Total Area Visitor
public class TotalAreaVisitor implements Visitor {
    private double totalArea = 0;

    public int getTotalArea() {
        return (int) Math.round(totalArea);
    }

    public void clearTotalArea() { totalArea = 0; }

    @Override
    public void visit(Island island) {
        totalArea += island.calculateArea();
        for (Element child : island.getChildren()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(Lake lake) {
        totalArea += lake.calculateArea();
        for (Element child : lake.getChildren()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(Tree tree) {
        totalArea += tree.calculateArea();
    }

    @Override
    public void visit(Boat boat) {
        totalArea += boat.calculateArea();
    }

    @Override
    public void visit(Flag flag) {
        totalArea += flag.calculateArea();
    }

    @Override
    public void visit(Kid kid) {
        totalArea += kid.calculateArea();
    }

    @Override
    public void visit(Kite kite) {
        totalArea += kite.calculateArea();
    }

    @Override
    public void visit(Painting painting) {
        for (Element element : painting.getElements()) {
            element.accept(this);
        }
    }
}
