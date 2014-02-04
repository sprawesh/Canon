package yogesh.canon.excelreader;



import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.ApplicationFrame;



public class AttributesMatching extends ApplicationFrame {
	
	private ArrayList<TimeSeries> cumAtributes = new ArrayList<TimeSeries>();
	private ArrayList<String> models = new ArrayList<String>();
	private static int rowSize;
	private String hd;
	String[] heading;
	public AttributesMatching(String filepath) {
		super("attribute plot");
		// FROM HERE
		ArrayList<String[]> action = new ArrayList<String[]>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(filepath));
			hd = in.readLine();	
			heading = hd.split(",");
			
			// for data
			String[] all = null, allsub = null;
			String brand;
			String datas = in.readLine();
			while( datas != null) {
				if(datas != null) {
					all = datas.split(",");
					allsub = new String[all.length-58];
					for(int i = 0; i < allsub.length; i++) {
						allsub[i] = all[58+i];
						//System.out.println(allsub[i]);
					}
				}

				brand = all[1];				
				if(brand.contains("canon")){
					action.add(allsub);
					models.add(all[3]);
				}
				datas = in.readLine();
			}		

			TreeSet<String> body = new TreeSet<String>();			

		} catch(Exception ex) {
			ex.printStackTrace();
		}
		plot(convertTimeSeries(action)); 
		//createVariables(action);
	}
	
	public void createVariables(ArrayList<String[]> action){
		
		new File("extendedData").mkdir(); // new folder
		String folderpath = "extendedData" + "/";
		String name = folderpath + "extendedData.csv";		
		TreeSet<String> body = new TreeSet<String>();
		int bodyType = 3;		
		//int dataIndex = 7+9+1+4+1+29+1+1+7; // Flash modes next
		int dataIndex = 7+9+1+4+1+29+1+1+7+6; // all data
		for(int i = 0; i < action.size(); i++) {			
			String[] data = action.get(i);
			body.add(data[bodyType]);
		}
			
		System.out.println("text");
		ArrayList<String> items = new ArrayList<String>(body);
		// ALSO PRINT DATES
		try {
			ArrayList<String[]> cor = otherResolutionTypes(action, 6);
			BufferedWriter out = new BufferedWriter(new FileWriter(name));
			StringBuilder sb = new StringBuilder();
			sb.append("model_tf1"+ ",");
			for(int di = 0; di < dataIndex-1-9-1-4-1-29-1-1-7-6; di++) {
				sb.append(heading[58+di] + ","); 
			}
			// expanding other resolutions
			String subheading = heading[58+dataIndex-1-9-1-4-1-29-1-1-7-6];
			int ressize = rowSize;
			for(int i = 0; i < rowSize; i++) {				
				sb.append(subheading + i + ","); 
			}
			ArrayList<String[]> cor1 = otherResolutionTypes(action, 7);
			subheading = heading[58+dataIndex-9-1-4-1-29-1-1-7-6];
			
			int imsize = rowSize;
			for(int i = 0; i < rowSize; i++) {
				sb.append(subheading + i+ ",");
			}
			
			for(int i = dataIndex-9+1-1-4-1-29-1-1-7-6; i <= dataIndex-1-4-1-29-1-1-7-6; i++) {
				sb.append(heading[58+i] + ",");
			}			
			subheading = heading[58+dataIndex-4-1-29-1-1-7-6]; // ISO details
			ArrayList<String[]> cor2 = otherResolutionTypes(action, 17); // index is dataIndex
			int isosize = rowSize;
			for(int i = 0; i < rowSize; i++) {
				sb.append(subheading + i + ","); 
			}
			
			for(int i = dataIndex-4+1-1-29-1-1-7-6; i <= dataIndex-1-29-1-1-7-6; i++) {
				sb.append(heading[58+i] + ",");
			}
			subheading = heading[58+dataIndex-29-1-1-7-6]; //JPEG details
			ArrayList<String[]> cor3 = otherResolutionTypes(action, 22);
			int jpsize = rowSize;
			for(int i = 0; i < rowSize; i++) {
				sb.append(subheading + i + ","); 
			}
			for(int i = dataIndex-29+1-1-1-7-6; i <= dataIndex-1-1-7-6; i++) {
				sb.append(heading[58+i] + ",");
			}
			subheading = heading[58+dataIndex-1-7-6];
			ArrayList<String[]> cor4 = otherResolutionTypes(action, 52);
			int flashsize = rowSize;
			for(int i = 0; i < rowSize; i++) {
				sb.append(subheading + i + ","); 
			}
			subheading = heading[58+dataIndex-7-6];
			ArrayList<String[]> cor5 = otherResolutionTypes(action, 53);
			int contsize = rowSize;
			for(int i = 0; i < rowSize; i++) {
				sb.append(subheading + i + ","); 
			}
			for(int i = dataIndex-7+1-6; i <= dataIndex; i++) {
				sb.append(heading[58+i] + ",");
			}
			sb.append("\n");
			out.write(sb.toString());  
			System.out.println(heading[58+dataIndex]);
			
			for(int i = 0; i < action.size(); i++) {
				int j = 0;
				sb = new StringBuilder();
				sb.append(models.get(i)+",");
				for(int k = 0; k <= dataIndex; k++) {
					//date
					if(k < 3) {
						sb.append(action.get(i)[k] + ","); 
					}
					// body type
					else if(k == 3) {						
						while(!(action.get(i)[k].equalsIgnoreCase(items.get(j)))) {
							j++;	
					}
						sb.append(Integer.toString(j) + ",");
				}
					// max resolution
					else if (k > 3 & k < 6) {
						sb.append(action.get(i)[k] + ",");
					}
					else if( k >= 6 & k < 7) {
						for(int ki = 0; ki < ressize; ki++) {
							if( ki < cor.get(i).length) {
								sb.append(cor.get(i)[ki]+ ","); 
							} else {
								sb.append(",");
							}							
						}
					}
					else if(k == 7) {
						for(int ki = 0; ki < imsize; ki++) {
							if(ki < cor1.get(i).length) {
								sb.append(cor1.get(i)[ki]+","); 
							} else {
								sb.append(",");
							}
						}
					}
					else if(k > 7 & k < 17) {
						sb.append(action.get(i)[k] + ",");
					}// ISO record
					else if(k == 17) {
						for(int ki = 0; ki < isosize; ki++) {
							if(ki < cor2.get(i).length) {
								sb.append(cor2.get(i)[ki]+","); 
							} else {
								sb.append(",");
							}
						}
					}
					else if(k > 17 & k < 22) {
						sb.append(action.get(i)[k] + ",");
					}
					else if(k == 22) {
						for(int ki = 0; ki < jpsize; ki++) {
							if(ki < cor3.get(i).length) {
								sb.append(cor3.get(i)[ki]+","); 
							} else {
								sb.append(",");
							}
						}
					}
					else if(k > 22 & k < 52) {
						sb.append(action.get(i)[k] + ",");
					}
					else if(k == 52) {
						for(int ki = 0; ki < flashsize; ki++) {
							if(ki < cor4.get(i).length) {
								sb.append(cor4.get(i)[ki]+","); 
							} else {
								sb.append(",");
							}							
						}
					}
					else if(k == 53) {
						for(int ki = 0; ki < contsize; ki++) {
							if(ki < cor5.get(i).length) {
								sb.append(cor5.get(i)[ki]+","); 
							} else {
								sb.append(",");
							}
						}
					}
					else if(k > 53) {
						sb.append(action.get(i)[k] + ",");
					}
					
				}
				sb.append("\n");
				out.write(sb.toString());
			}
			out.flush();
			out.close();
		} catch (IOException e) {			
			e.printStackTrace();
		}		
		
	}

	private ArrayList<String[]> otherResolutionTypes(ArrayList<String[]> action, int columnposition) {
		ArrayList<String[]> splitted = new ArrayList<String[]>();
		ArrayList<Integer> record = new ArrayList<Integer>();		
		for(int i = 0; i < action.size(); i++) {			
			String[] data = action.get(i);			
			String[] ors = data[columnposition].split(";");
			splitted.add(ors);
			record.add(ors.length);			
		}
		Collections.sort(record);
		rowSize = record.get(record.size()-1);		
		System.out.println("the size: " + rowSize); 
		return splitted;			
	}

	private ArrayList<TimeSeries> convertTimeSeries(ArrayList<String[]> action) {
		TimeSeries series = new TimeSeries("attribute trend");
		for(int i = 0; i < action.size(); i++){
			double number;
			if(action.get(i)[19+1+4+3+1+5+1+8].equalsIgnoreCase("NA")){
				continue;
			}
			else {
				number = Double.parseDouble(action.get(i)[19+1+4+3+1+5+1+8]);
			}
			series.addOrUpdate(new Day(Integer.parseInt(action.get(i)[1]), 
					Integer.parseInt(action.get(i)[0]), 
					Integer.parseInt(action.get(i)[2])), 
					number);
		}
		cumAtributes.add(series);
		return cumAtributes;		
	}

	public void plot(ArrayList<TimeSeries> data) {
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		for(TimeSeries ts: cumAtributes) {
			dataset.addSeries(ts); // FROM HERE
		}
		JFreeChart chart = ChartFactory.createTimeSeriesChart("max-aperture", "Launch Date", 
				"Value", dataset, true, true, false);
		chart.setBorderPaint(Color.white);
		XYPlot plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setOutlinePaint(null);
		
		DateAxis axis = (DateAxis)plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("dd-MM-yy"));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1000, 1200));
		setContentPane(chartPanel);
		
	}
}