package map.jsonClasses;
public class MarkerInfoAddress
{
	//the structure of the json file
	private String street;
	private String city;
	private String number;
	
	public MarkerInfoAddress() {
		// TODO Auto-generated constructor stub
	}
	
	public MarkerInfoAddress(MarkerInfoAddress m){
		this.street = m.getStreet();
		this.city = m.getCity();
		this.number = m.getAdressNumber();
	}

	public String getStreet(){
		return street;
	}

	public void setStreet(String street){
		this.street = street;
	}

	public String getCity() {
		return city;
	}
	
	public void setCity(String city){
		this.city = city;
	}

	public String getAdressNumber(){
		return number;
	}

	public void setAdressNumber(String number){
		this.number = number;
	}

	@Override
	public String toString(){
		return getStreet() + ", "+getCity()+", "+getAdressNumber();
	}
}