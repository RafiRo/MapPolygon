package map.jsonClasses;

public class MarkerInfo {
	//the structure of the json file
	private String type;
	private String name;
	private MarkerInfoAddress address;
	private String phone;

	public MarkerInfo() {
		// TODO Auto-generated constructor stub
	}

	public MarkerInfo(MarkerInfo m)	{
		this.type = m.getType();
		this.name = m.getName();
		this.phone = m.getPhone();
		this.address = new MarkerInfoAddress(m.getAddress());
	}

	public String getType() {
		return type;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public MarkerInfoAddress getAddress(){
		return address;
	}

	public void setAddress(MarkerInfoAddress address){
		this.address = address;
	}

	@Override
	public String toString() {
		return "***** MarkerInfo Details *****\ntype:"+getType() +
				"\nname:"+getName() + 
				"\nAdress:"+getAddress().toString() +
				"\nphone:"+getPhone();
	}
}