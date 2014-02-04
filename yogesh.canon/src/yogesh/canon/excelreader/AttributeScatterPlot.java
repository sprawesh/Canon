package yogesh.canon.excelreader;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.util.ShapeUtilities;

public class AttributeScatterPlot extends ApplicationFrame {

	public AttributeScatterPlot(String title, ArrayList<ArrayList<Double>> datapoints) {
		super(title);
		JPanel jpanel = createDemoPanel(datapoints);
		jpanel.setPreferredSize(new Dimension(1000, 800));
		setContentPane(jpanel);
	}
	
	public static JPanel createDemoPanel(ArrayList<ArrayList<Double>> datapoints) {
		
		JFreeChart jfreechart = ChartFactory.createScatterPlot(
				"Scatter Plot", "time", "fps", samplexydataset3(datapoints),
				PlotOrientation.VERTICAL, true, true, false);
		Shape cross = ShapeUtilities.createDiagonalCross(2, 1);
		XYPlot xyplot = (XYPlot) jfreechart.getPlot();
		xyplot.setDomainCrosshairVisible(true);
		xyplot.setRangeCrosshairVisible(true);
		xyplot.setBackgroundPaint(Color.white);
		xyplot.setDomainGridlinePaint(Color.white);
		xyplot.setRangeGridlinePaint(Color.white);
		XYItemRenderer renderer = xyplot.getRenderer();
		renderer.setSeriesShape(0, cross);
		renderer.setSeriesPaint(0, Color.red);
		return new ChartPanel(jfreechart);
		
	}	
	public static XYDataset samplexydataset3(ArrayList<ArrayList<Double>> datapoints) {
		String datasize = Integer.toString(datapoints.size());
		XYSeries series = new XYSeries("data size: " + datasize);
		int j = 0;
		for(int i = 0; i < datapoints.size(); i++) {
			double x = datapoints.get(i).get(0);
			double y = datapoints.get(i).get(1);
			series.add(x, y);
			j = i;
		}
		System.out.println(j); 
		XYSeriesCollection xySeriesCollection = new XYSeriesCollection(series);
		return xySeriesCollection;		
	}
	

}
