package yogesh.canon.excelreader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Dpreview1 {
	
	private static String file_path = "C:\\academic\\umd\\yogesh\\dpreview1.csv";
	private static String file_path1 = "C:\\academic\\umd\\yogesh\\press release clean round1.csv";
	private static BufferedWriter out;
	private static StringBuilder stb;
	
	public Dpreview1(String filepath) {
		
		try {
			Launchcsv lcsv = new Launchcsv(file_path1);
			ArrayList<Launch> data = lcsv.getData();
			data.remove(data.size()-1);
			data.remove(data.size()-1);
			BufferedReader in = new BufferedReader(new FileReader(filepath));
			String sb = in.readLine(); // header for dpreview and excluding model no.
			sb = sb.substring(sb.indexOf(',', 0)+1);
			out = new BufferedWriter(new FileWriter(file_path));
			String hd = lcsv.getHeader();
			out.write(hd + sb + "\n");
			
			System.out.println(data.size());
			int m = 0, b = 0;
			for( Launch launch: data) {
				int n = Integer.parseInt(launch.getPdnumber()); 
				
				if( n > m) {
					System.out.println(n);
					while(n > m) {
						sb = in.readLine();
						
						b = sb.indexOf(",", 0); 
						m = Integer.parseInt(sb.substring(0, b));
						 
					}					
				}
				
				if (n == m) {
					StringBuilder stb = new StringBuilder(launch.getPdnumber()+","); 
					stb.append(launch.getBrand()+",");
					stb.append(launch.getModel()+",");
					stb.append(launch.getPressReleaseDate()+",");
					stb.append(launch.getLaunchDate()+",");
					stb.append(launch.getLaunchPrice()+",");
					stb.append(launch.getPreSale()+",");
					int a = b + 1;
					b = sb.indexOf(",", a);  
					stb.append(sb.substring(a));
					out.write(stb+"\n");
				}

			
			}				
			out.flush();
			out.close();
			
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}

}
