import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.Range;
import com.orsoncharts.axis.ValueAxis3D;
import com.orsoncharts.data.function.Function3D;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.renderer.GradientColorScale;
import com.orsoncharts.renderer.xyz.SurfaceRenderer;

import java.awt.*;

/**
 * Graph surface plot of substance
 */
public class Grapher {

	//Test graphing y = cosx * sinx first
	public Grapher() {
		System.out.println("Starting Grapher.");
	}

	public static Chart3D createChart() {
		Function3D function = new Function3D() {
			@Override
			public double getValue(double x, double z) {
				return Math.cos(x) * Math.sin(z);
			}
		};
		Chart3D chart = Chart3DFactory.createSurfaceChart("SurfaceRendererTest", "y = cos(x) * sin(z)", function, "X", "Y", "Z");

		XYZPlot plot = (XYZPlot) chart.getPlot();
		plot.setDimensions(new Dimension3D(10, 5, 10));
		ValueAxis3D xAxis = plot.getXAxis();
		xAxis.setRange(-Math.PI, Math.PI);
		ValueAxis3D zAxis = plot.getZAxis();
		zAxis.setRange(-Math.PI, Math.PI);
		SurfaceRenderer renderer = (SurfaceRenderer) plot.getRenderer();
		renderer.setDrawFaceOutlines(false);
		renderer.setColorScale(new GradientColorScale(new Range(-1.0, 1.0),
				Color.RED, Color.YELLOW));
		return chart;
	}
}
