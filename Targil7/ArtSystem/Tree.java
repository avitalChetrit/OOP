/**
 * Represents a Tree element in a terrestrial habitat.
 * A tree has a defined number of leaves and is represented as a geometric shape
 * comprised of a rectangular base and a triangular top.
 */
public class Tree extends Element {
    int leavesAmount;

    public Tree(double width, double height, int leavesAmount, String path) {
        super(width, height, path);
        this.leavesAmount = leavesAmount;
    }

    @Override
    public String getName() {
        return "tree";
    }

    @Override
    public Habitat getHabitat() {
        return Habitat.TERRESTRIAL;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public double calculateArea() {
        // Rectangle + Triangle: (width/2) * length
        return (width/2) * length;
    }

    public int getLeavesAmount() {
        return leavesAmount;
    }
}
