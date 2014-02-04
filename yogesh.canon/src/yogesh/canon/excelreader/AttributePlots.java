package yogesh.canon.excelreader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

import org.jfree.data.time.TimeSeries;
import org.jfree.ui.RefineryUtilities;

public class AttributePlots {
	
	private ArrayList<ArrayList<Double>> cumAtributes = new ArrayList<ArrayList<Double>>();
	ArrayList<Double> datapoint;
	private String hd;
	String[] heading;
	public AttributePlots(String filepath, String brand) {
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(filepath));
			hd = in.readLine();	
			heading = hd.split(",");
			
			String[] all = null;
			String datas = in.readLine();
			while( datas != null) {
				if(datas != null) {
					all = datas.split(",");
					if(all[1].equalsIgnoreCase(brand)){
						//datapoint = new ArrayList<Double>();
						double time = Double.parseDouble(all[5])/365+Double.parseDouble(all[6])/12+Double.parseDouble(all[7]);
						double resolution = Double.parseDouble(all[8])*Double.parseDouble(all[9])/1000000;
						// other resolutions
						String oresolutions[] = all[10].split(",");
						double sensor = Double.parseDouble(all[11])*Double.parseDouble(all[12]);						
						//ISO
						double iso = Double.parseDouble(all[15]);
						//focus range
						String dumval = all[25];		// next is screen size - 18				
						if(!dumval.equalsIgnoreCase("NA")) {
							datapoint = new ArrayList<Double>();
							datapoint.add(time);
							//double vol = Double.parseDouble(all[26])*Double.parseDouble(all[27])*Double.parseDouble(all[28]);
							//double density = Double.parseDouble(dumval)/vol;
							double fps = Double.parseDouble(all[29]);
							datapoint.add(fps);
							//datapoint.add(Double.parseDouble(all[25]));
							System.out.println("vol,"+fps); 
							//System.out.println("weight,"+Double.parseDouble(all[25])); 
							cumAtributes.add(datapoint);
						}
						//System.out.println(rfocus); 
						//datapoint.add(time);
						//datapoint.add(resolution);
						//datapoint.add(sensor);
						//datapoint.add(iso);						
						
					}
				}
				datas = in.readLine();
			}
			in.close();
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		AttributeScatterPlot demo = new AttributeScatterPlot("scatter plot", cumAtributes);
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);
	}
	
	public AttributePlots() {
		// TODO Auto-generated constructor stub
	}
	ArrayList<ArrayList<Double>> collections1= new ArrayList<ArrayList<Double>>();
	ArrayList<ArrayList<Double>> collections2= new ArrayList<ArrayList<Double>>();
	ArrayList<ArrayList<Double>> collections3= new ArrayList<ArrayList<Double>>();
	ArrayList<Integer> dummies = new ArrayList<Integer>();
	public void extraProperties(String filepath, String brand) {
		StringBuilder b;
		ArrayList<ArrayList<Double>> collections= new ArrayList<ArrayList<Double>>();		
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(filepath+"BookModified.csv")); 
			out.write("rmin,rmax,rsiz,ismin,ismax,issiz,cmin,cmax,csize,resmin,remax,resize,storage_dummy");
			out.newLine();
			BufferedReader in = new BufferedReader(new FileReader(filepath+"sub2dprNoReviewDate.txt"));
			hd = in.readLine();	
			heading = hd.split(",");
			//out.write("brand,month,day,year,resolution,iso_old,continuous_drive,cmin,cmax,csize,iso,imin,imax,isize,resolution,rmin,rmax,rsize"); // ---write heading
			String[] all = null;
			String iso;
			String datas = in.readLine();
			while( datas != null) {
				if(datas != null) {
					all = datas.split("\t");			
					if(all[0].equalsIgnoreCase(brand)){							
						String resolutions = all[1];
						String[] values = resolutions.split(";");
						StringBuilder sb = new StringBuilder();
						double mpix = 0;
						ArrayList<Double> collection = new ArrayList<Double>();
						for(int i = 0; i < values.length; i++) {
							if(values[i].contains("x")){
								if(!(values[i].contains("&amp"))){
									String[] dim = values[i].split("x");
									mpix = Double.parseDouble(dim[0])*Double.parseDouble(dim[1])/1000000;
								}
							}
							collection.add(mpix);
						}
						collections.add(collection);
						//System.out.println(sb.toString()); 
						appendISO(all[2]);
						appendDrive(all[3]);
						appendResol(all[4]);
						dummies.add(appendTypes(all[5])); 
					}
					
				}
				datas = in.readLine();
			}
			
			for(int i = 0; i < collections.size(); i++) {
				b = new StringBuilder();
				ArrayList<Double> dd = collections.get(i);
				ArrayList<Double> is = collections1.get(i);
				ArrayList<Double> ix = collections2.get(i);
				ArrayList<Double> ir = collections3.get(i);
				// get max, min and different values
				/*
				StringBuilder sb = new StringBuilder();
				for(int j = 0; j < dd.size(); j++) {
					sb.append(dd.get(j)).append(" ");
				}*/
				b.append(Collections.min(dd)).append(",").append(Collections.max(dd)).append(",").append(dd.size()).append(",")
				.append(Collections.min(is)).append(",").append(Collections.max(is)).append(",").append(is.size()).append(",")
				.append(Collections.min(ix)).append(",").append(Collections.max(ix)).append(",").append(ix.size()).append(",")
				.append(Collections.min(ir)).append(",").append(Collections.max(ir)).append(",").append(ir.size()).append(",")
				.append(dummies.get(i)); 
				out.write(b.toString());
				out.newLine();
				//System.out.println(b.toString());					
			}
			ArrayList<String> ids1 = new ArrayList<String>(ids);
			for(int k=0; k<ids.size();k++){
				System.out.println(ids1.get(k)); 
			}
			in.close();
			out.flush();out.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}

	TreeSet<String> ids = new TreeSet<String>();
	private int appendTypes(String string) {
		int dummy = 0;
		String[] models = string.split("[\\;,\\/,\\(,\\)]");
		for(int i = 0; i < models.length; i++) {
			if(models[i].equalsIgnoreCase("CompactFlash")){
				dummy = 1;
			}
			else if(models[i].equalsIgnoreCase("SD")){
				for(int j = i; j < models.length; j++) {
					if(models[j].equalsIgnoreCase("MMCard")){
						dummy = 1;
						for(int k = j; k < models.length; k++) {
							if(models[k].equalsIgnoreCase("SDHC")){
								dummy = 3;
							}
						}
					}
				}
				
				for(int j = i; j < models.length; j++) {
					if(models[j].equalsIgnoreCase("SDHCard")){
						dummy = 2;
					}
				}
				
				for(int j = i; j < models.length; j++) {
					if(models[j].equalsIgnoreCase("SDHC")){
						for(int k = i; k < models.length; k++) {
							if(models[k].equalsIgnoreCase("MMC")){
								for(int ik = k; ik < models.length; ik++) {
									if(models[ik].equalsIgnoreCase("MMcplus")){
										dummy = 4;
										for(int lk = k; lk < models.length; lk++) {
											if(models[lk].equalsIgnoreCase("SDXC")){
												dummy = 5;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		return dummy;
	}

	private void appendResol(String string) {
		ArrayList<Double> collection = new ArrayList<Double>();		
		String[] values = string.split("[\\;,\\/,\\(,\\)]");
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < values.length; i++) {
			
			 if(values[i].contains("fps")){
				 int a = values[i].indexOf("fps");
				 sb.append(values[i].substring(0, a) + "\t");
				 
				 if(isNumeric(values[i].substring(0, a))){
				 collection.add(Double.parseDouble(values[i].substring(0, a)));
				 }
			 }
			if(values[i].contains("Auto")||values[i].contains("NA")) {
				continue; 
			}
			if(isNumeric(values[i])){
			collection.add(Double.parseDouble(values[i])); 
			}
		}
		if(collection.size() == 0) {
			collection.add((double) 0);
		}
		//System.out.println(collection);
		//System.out.println(Collections.min(collection)+" " + Collections.max(collection)+" "+collection.size());
		collections3.add(collection);
		
	}

	private void appendDrive(String string) {
		ArrayList<Double> collection = new ArrayList<Double>();
		StringBuilder sb = new StringBuilder();
		 String[] s = string.split("[\\;,\\(,\\),\\/]"); 
		 for(int i = 0; i < s.length; i++) {
			 if(s[i].contains("fps")){
				 int a = s[i].indexOf("fps");
				 sb.append(s[i].substring(0, a) + "\t");
				 collection.add(Double.parseDouble(s[i].substring(0, a))); 
			 }
			 
		 }
		 if(sb.length() == 0) {
			 collection.add(Double.parseDouble("0")); 
		 }
		// System.out.println(collection); 
		 collections2.add(collection);
	}

	private void appendISO(String all) {
		ArrayList<Double> collection = new ArrayList<Double>();		
		String[] values = all.split(";");
		for(int i = 0; i < values.length; i++) {
			if(values[i].contains("Auto")||values[i].contains("NA")) {
				continue; 
			}
			if(isNumeric(values[i])){
			collection.add(Double.parseDouble(values[i])); 
			}
		}
		if(collection.size() == 0) {
			collection.add((double) 0);
		}
		//System.out.println(collection);
		//System.out.println(Collections.min(collection)+" " + Collections.max(collection)+" "+collection.size());
		collections1.add(collection);
	}
	
	public static boolean isNumeric(String number) {
		try {
			double d = Double.parseDouble(number);
		} catch(NumberFormatException nex) {
			return false;
		}
		return true;
	}

}
