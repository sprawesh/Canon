package yogesh.canon.excelreader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Launchcsv {
	
	private static String file_path = "C:\\academic\\umd\\yogesh\\round1.csv";
	private static BufferedWriter out;
	private static StringBuilder stb;
	private ArrayList<Launch> data = new ArrayList<Launch>();
	private ArrayList<String> header = new ArrayList<String>();
	private String hd;
	
	public Launchcsv(String filepath) {
		
		try {
			out = new BufferedWriter(new FileWriter(file_path));
			prepareData(filepath);
			out.flush();
			out.close();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
	}

	private void prepareData(String filepath) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(filepath));
			 hd = in.readLine();
			
			writefile(hd);
			System.out.println(hd);			
			
			String sb = in.readLine(); // first line of data
			while( sb != null) {
				if( sb != null) {
					
					int a = 0;
					int b = sb.indexOf(",", a);
					String pdnumber = sb.substring(a, b);
					
					a = b+1;
					b = sb.indexOf(",", a);
					String brand = sb.substring(a, b);
					
					a = b+1;
					b = sb.indexOf(",", a);
					String Model = sb.substring(a, b);
					
					a = b+1;
					b = sb.indexOf(",", a);
					String releaseDate = sb.substring(a, b);
					
					a = b+1;
					b = sb.indexOf(",", a);
					String launchDate = sb.substring(a, b);
					
					a = b+1;
					b = sb.indexOf(",", a);
					String launchPrice = sb.substring(a, b);
					
					a = b+1;
					b = sb.indexOf(",", a);
					String preSale = sb.substring(a, b);
					
					a = b+1;
					b = sb.indexOf(",", a);
					String comments = sb.substring(a, b);
					
					a = b+1;
					b = sb.indexOf(",", a);
					String pressId = sb.substring(a, b);
					
					a = b+1;
					b = sb.indexOf(",", a);
					String fileid = sb.substring(a, b);
					
					a = b+1;
					b = sb.indexOf(",", a);
					String press = sb.substring(a, b);
					
					a = b+1;
					b = sb.indexOf(",", a);
					String pressDate = sb.substring(a, b);
					
					Launch launch = new Launch(pdnumber);
					launch.setBrand(brand);
					launch.setModel(Model);
					launch.setPressReleaseDate(releaseDate);
					launch.setLaunchDate(launchDate);
					launch.setLaunchPrice(launchPrice);
					launch.setPreSale(preSale); 
					data.add(launch);
					
					stb = new StringBuilder(pdnumber +",");
					stb.append(brand + ",");
					stb.append(Model + ",");
					stb.append(releaseDate + ",");
					stb.append(launchDate + ",");
					stb.append(launchPrice + ",");
					stb.append(preSale + "\n");
					out.write(stb.toString());
					sb = in.readLine();
				}
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}

	private void writefile(String sb) {
		try {
			int a = sb.indexOf("Comments");
			String relevantSb = sb.substring(0, a);
			out.write(relevantSb + "\n"); 
		} catch (IOException e) {
			
			e.printStackTrace(); 
		}
		
	}
	
	public ArrayList<Launch> getData() {
		return data; 
	}
	
	public String getHeader() {
		int a = hd.indexOf("Comments");
		return hd.substring(0, a);		
	}

}
