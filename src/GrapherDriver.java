import com.orsoncharts.Chart3DPanel;
import com.orsoncharts.fx.Chart3DViewer;

public class GrapherDriver {
	public static void main(String[] args) {
		Grapher myGraph = new Grapher();

		Chart3DViewer chartPanel = new Chart3DViewer(myGraph.createChart());
		chartPanel.getChildrenUnmodifiable().addAll(chartPanel);
	}
}
