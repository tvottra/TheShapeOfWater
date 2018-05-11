import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;

public class ProtectedToPublic extends Parent {

	public ObservableList<Node> callGetChildren() {
		return getChildren();
	}
}
