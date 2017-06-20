package es.uv.etse.bdweb.hotel.common;

public enum RoomState {
	OCUPADA("1"),
	DISPONIBLE("0");
	
	private String estado;
	
	RoomState(String estado) {
		this.estado = estado;
	}
	
	public String estado(){
		return estado;
	}
	
	
}
