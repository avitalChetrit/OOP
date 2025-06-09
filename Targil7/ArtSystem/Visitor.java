/**
 * Visitor interface represents an implementation of the Visitor design pattern.
 */
public interface Visitor {
    void visit(Island island);
    void visit(Lake lake);
    void visit(Tree tree);
    void visit(Boat boat);
    void visit(Flag flag);
    void visit(Kid kid);
    void visit(Kite kite);
    void visit(Painting painting);
}