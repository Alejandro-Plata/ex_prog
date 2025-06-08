package lightsOut;
/**
 * Esta clase se encarga de crear las casillas del juego
 */
public class Casilla {
	
	private boolean estaEncendida;
	private int edad; //5
	
	public Casilla(boolean estaEncendida) {
		this.estaEncendida = estaEncendida;
	}

	public boolean isEncendida() {
		return estaEncendida;
	}
	
	/* Setter que permite modificar el estado de la casilla. 
	 * Este método es el que utilizaremos durante el programa
	 * para cambiar el estado de la casilla, siempre que sea necesario, tras cada jugada.
	 */
	public void setEncendida(boolean estaEncendida) {
		this.estaEncendida = estaEncendida;
	}

	@Override
	public String toString() {
		if (estaEncendida) {
			return "💡";
		} else {
			return "⚫";
		}
	}

	// Getter para la edad de la casilla
	public int getEdad() {
		return edad;
	}

	// Setter para la edad de qu

	//aaaaaa


	
	
}
