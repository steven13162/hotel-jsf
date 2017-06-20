package es.uv.etse.bdweb.hotel.common;

public enum ReserveState {
	ACTIVA("ACTIVA"),
	CERRADA("CERRADA"),
	CANCELADA("CANCELADA"),
	PROGRESA("PROGRESA");

	private String estado; 
	
	ReserveState(String estado) {
		this.estado = estado;
	}
	
	public String getEstado(){
		return estado;
	}
}
