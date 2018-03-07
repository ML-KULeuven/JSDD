package sdd;

public enum VtreeType {

	Right("right"),
	Left("left"),
	Vertical("vertical"),
	Balanced("balanced"),
	Random("random");
	
	
	
	private String str;
	
	VtreeType(String str){
		this.str = str;
	}
	
	@Override
	public String toString() {
		return str;
	}
	
}
