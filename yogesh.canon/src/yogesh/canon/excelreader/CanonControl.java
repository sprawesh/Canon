package yogesh.canon.excelreader;

import java.text.SimpleDateFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.FastScatterPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class CanonControl extends ApplicationFrame {
	
		
	public CanonControl(String title) {
		super(title);
		TimeSeries series = new TimeSeries("attribute trend");
		series.add(new Day(12, 4, 2000), 10);
		series.add(new Day(26, 2, 1999), 11);
		series.add(new Day(29, 6, 2009), 9);
		
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(series);
		
		JFreeChart chart = ChartFactory.createTimeSeriesChart("Plot Values", "Launch Date", 
				"value", dataset, 
				true, true, false);
		XYPlot plot = chart.getXYPlot();
		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("dd-MM-yy"));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1000, 1200));
		setContentPane(chartPanel);		
	}

	private static String file_path2 = "C:\\academic\\umd\\yogesh\\dpreviewcleaned round 1.csv";
	private static String file_path3 = "C:\\academic\\umd\\yogesh\\2-data\\dpr1withalldetails.csv";
	
	public static void main1(String[] args) {
		
		//Launchcsv dailyme = new Launchcsv(file_path1);
		//new Dpreview1(file_path2);
		AttributesMatching demo = new AttributesMatching(file_path3);	
		//CanonControl demo = new CanonControl("Time Series Demo");		
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);
	}
	
public static void main(String[] args) {
	new AttributePlots("C:\\academic\\umd\\yogesh\\2-data\\attribute-plots\\subdprNoReview.csv", "canon");
	AttributePlots arp= new AttributePlots();
	//arp.extraProperties("C:\\academic\\umd\\yogesh\\2-data\\attribute-plots\\", "canon");
	System.out.println("done");
	}

}
