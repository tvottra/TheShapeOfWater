/*
 *
 * Copyright (c) 2013-2016, Object Refinery Limited.
 * All rights reserved.
 *
 * http://www.object-refinery.com/orsoncharts/index.html
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *   - Neither the name of the Object Refinery Limited nor the
 *     names of its contributors may be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL OBJECT REFINERY LIMITED BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Note that the above terms apply to the demo source only, and not the
 * Orson Charts library.
 *
 */


import static javafx.application.Application.launch;
import java.awt.Color;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.Range;
import com.orsoncharts.axis.ValueAxis3D;
import com.orsoncharts.data.function.Function3D;
import com.orsoncharts.fx.Chart3DViewer;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.renderer.GradientColorScale;
import com.orsoncharts.renderer.xyz.SurfaceRenderer;

/**
 * Displays a surface plot of the gas, based on its van der Waals' constants
 */
public class Grapher extends Application {

	public static Node createDemoNode() {
		Chart3D chart = createChart();
		Chart3DViewer viewer = new Chart3DViewer(chart);
		return viewer;
	}

	/**
	 * Creates a surface chart for the demo.
	 *
	 * @return A surface chart.
	 */
	private static Chart3D createChart() {
		Function3D function = new Function3D() {
			@Override
			public double getValue(double V, double T) {
				double a = TheShapeOfWater.getCurrentA();
				double b = TheShapeOfWater.getCurrentB();
				double output = VanDerWaalsCalculator.calculateP(V, 1, T, a, b);
				if(output > 0) {
					return VanDerWaalsCalculator.calculateP(V, 1, T, a, b);
				} else {
					return 0;
				}
			}
		};

		Chart3D chart = Chart3DFactory.createSurfaceChart(
				"Grapher",
				"van der Waals equation",
				function, "V", "P", "T");

		XYZPlot plot = (XYZPlot) chart.getPlot();
		plot.setDimensions(new Dimension3D(20, 10, 20));
		ValueAxis3D xAxis = plot.getXAxis();
		xAxis.setRange(0, 2000);
		ValueAxis3D zAxis = plot.getZAxis();
		zAxis.setRange(0, 2000);
		SurfaceRenderer renderer = (SurfaceRenderer) plot.getRenderer();
		renderer.setDrawFaceOutlines(false);
		renderer.setColorScale(new GradientColorScale(new Range(-1.0, 1.0),
				Color.BLUE, Color.GREEN));
		return chart;
	}

	@Override
	public void start(Stage stage) throws Exception {
		StackPane sp = new StackPane();
		sp.getChildren().add(createDemoNode());
		Scene scene = new Scene(sp, 1024, 768);
		stage.setScene(scene);
		stage.setTitle("PVT surface for " + TheShapeOfWater.getCurrentGas());
		stage.show();

		//close the Grapher
		stage.setOnCloseRequest(e -> {
			Platform.exit();
		});
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}