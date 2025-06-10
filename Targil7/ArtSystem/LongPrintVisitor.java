
public class LongPrintVisitor implements Visitor {
    private StringBuilder result = new StringBuilder();

    public String getResult() {
        return result.toString().trim();
    }

    public void clearResult() { result = new StringBuilder(); }

    @Override
    public void visit(Island island) {
        if (island.getChildren().isEmpty()) {
            result.append("An empty island named ").append(island.getName()).append(". ");
        } else {
            result.append("An island named ").append(island.getName()).append(" containing: ");
            for (int i = 0; i < island.getChildren().size(); i++) {
                island.getChildren().get(i).accept(this);
                if (i < island.getChildren().size() - 1) {
                    result.append(" ");
                }
            }
            result.append(" ");
        }
    }

    @Override
    public void visit(Lake lake) {
        if (lake.getChildren().isEmpty()) {
            result.append("An empty lake named ").append(lake.getName()).append(". ");
        } else {
            result.append("A lake named ").append(lake.getName()).append(" containing: ");
            for (int i = 0; i < lake.getChildren().size(); i++) {
                lake.getChildren().get(i).accept(this);
                if (i < lake.getChildren().size() - 1) {
                    result.append(" ");
                }
            }
            result.append(" ");
        }
    }

    @Override
    public void visit(Tree tree) {
        result.append("A tree with an amount of ").append(tree.getLeavesAmount()).append(" leaves.");
    }

    @Override
    public void visit(Boat boat) {
        result.append("A boat made of ").append(boat.getMaterial().toString().toLowerCase()).append(" material.");
    }

    @Override
    public void visit(Flag flag) {
        result.append("A flag of color: ").append(flag.getColor().toString().toLowerCase()).append(".");
    }

    @Override
    public void visit(Kid kid) {
        result.append("A ").append(kid.getAge()).append(" year old kid with ")
                .append(kid.getHairColor().toString().toLowerCase()).append(" hair.");
    }

    @Override
    public void visit(Kite kite) {
        result.append("A kite of color: ").append(kite.getColor().toString().toLowerCase()).append(".");
    }

    @Override
    public void visit(Painting painting) {
        for (int i = 0; i < painting.getElements().size(); i++) {
            painting.getElements().get(i).accept(this);
            if (i < painting.getElements().size() - 1) {
                result.append(" ");
            }
        }
    }
}