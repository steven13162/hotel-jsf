package es.uv.etse.bdweb.hotel.common;

public enum RoomType {
	INDIVIDUAL("INDIVIDUAL"),
	DOBLE("DOBLE"),
	MATRIMONIAL("MATRIMONIAL");
	
	private String tipo;
	
	RoomType(String tipo){
		this.tipo = tipo;
	}
	
	public String getTipo(){
		return tipo;
	}
}
