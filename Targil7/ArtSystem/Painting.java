 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a Painting, which is a collection of various elements in a structured hierarchy.
 * Elements are added to the Painting and organized based on their paths. If an element has
 * a defined path, it is nested within its corresponding parent element. If no path is
 * provided, the element is treated as a top-level entity in the Painting.
 * <p>
 * The Painting also supports the Visitor design pattern, allowing different operations
 * to be applied across its elements.
 * <p>
 * Key features:
 * - Maintains a mapping between element paths and elements for efficient organization.
 * - Supports a hierarchical structure for elements by nesting child elements
 *   within composite parent elements.
 * - Offers visitors the ability to traverse and process the Painting as an object.
 */
public class Painting {
    Map<String, Element> pathToElementMap;
    List<Element> elementList;

    Painting() {
        elementList = new ArrayList<>();
        pathToElementMap = new HashMap<>();
    }

    public void addElement(Element element) {
        pathToElementMap.put(element.getFullName(), element);
        if (element.getPath().isEmpty()) {
            elementList.add(element);
        } else {
            // Get containing element (father) by path
            Element containingElement = pathToElementMap.get(element.getPath());
            if (containingElement instanceof CompositeElement) {
                ((CompositeElement) containingElement).addChild(element);
            }else {
                System.out.println("Element " + element.getName() + " cannot be added to " + containingElement.getName());
            }
        }
    }

    public String getName() {
        return "painting";
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public List<Element> getElements() {
        return elementList;
    }
}