package yogesh.canon.excelreader;

public class Launch {
	
	private String pdnumber;
	private String brand;
	private String model;
	private String pressReleaseDate;
	private String launchDate;
	private String launchPrice;
	private String preSale;
	
	
	public Launch(String pdnumber) {
		this.pdnumber = pdnumber;
	}
	
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	public void setPressReleaseDate(String pressReleaseDate) {
		this.pressReleaseDate = pressReleaseDate;
	}
	
	public void setLaunchDate(String launchDate) {
		this.launchDate = launchDate;
	}
	
	public void setLaunchPrice(String launchPrice) {
		this.launchPrice = launchPrice;
	}
	
	public void setPreSale(String preSale) {
		this.preSale = preSale;
	}	
	
	public String getPdnumber() {
		return pdnumber;
	}
	
	public String getBrand() {
		return brand;
	}
	
	public String getModel() {
		return model;
	}
	
	public String getPressReleaseDate() {
		return pressReleaseDate;
	}
	
	public String getLaunchDate() {
		return launchDate;
	}
	
	public String getLaunchPrice() {
		return launchPrice;
	}
	
	public String getPreSale() {
		return preSale;
	}

}
