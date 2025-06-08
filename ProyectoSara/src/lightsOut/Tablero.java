package lightsOut;

public class Tablero {

	private int filas; //Los tableros son cuadrados, por lo que podemos reutilizar el valor de las filas para las columnas
	private Casilla [][] tableroVirtual; //Objeto tablero que utilizaremos para el juego
	
	public Tablero(int filas, int casillasActivas) {
		this.filas = filas;
		this.tableroVirtual = new Casilla [filas][filas];
		
		inicializarTablero(casillasActivas);
	}

	//Constructor específico para el tablero predeterminado en el fichero de configuración
	public Tablero(Casilla [][] tableroJuego) {
		this.tableroVirtual = tableroJuego;  
	}

	//Getters y setters

	public int getFilas() {
		return filas;
	}

	public void setFilas(int filas) {
		this.filas = filas;
	}

	public Casilla[][] getTableroVirtual() {
		return tableroVirtual;
	}

	public void setTableroVirtual(Casilla[][] tableroVirtual) {
		this.tableroVirtual = tableroVirtual;
	}
	
	//Métodos
	
	public void pintarTablero() {
		
		for (int i = 0; i < tableroVirtual.length; i++) { //Recorrer las filas
			for (int j = 0; j < tableroVirtual[0].length; j++) {
				System.out.print(tableroVirtual[i][j]); 
			}
			System.out.println();
		}
			
	}
	
	public void inicializarTablero(int casillasActivas) {
		
		// Crear un nuevo tablero con todas las casillas apagadas
				Casilla [][] nuevoTablero = new Casilla[filas][filas];
				for (int i = 0; i < filas; i++) {
					for (int j = 0; j < filas; j++) {
						nuevoTablero[i][j] = new Casilla(false);
					}
				}
				
				// Activar aleatoriamente 'casillasActivas' casillas
				int activadas = 0;
				while (activadas < casillasActivas) {
					// Generar posiciones aleatorias dentro del tablero
					int fila = (int) (Math.random() * filas); //3
					int columna = (int) (Math.random() * filas); //5
					
					// Solo activar la casilla si no está ya encendida
					if (!nuevoTablero[fila][columna].isEncendida()) {
						nuevoTablero[fila][columna].setEncendida(true); //Enciende la casilla
						activadas++;
					}
				}
				
				// Establecer el nuevo tablero
				this.tableroVirtual = nuevoTablero;
			}
		
	
	public void cambiarLuces(int fila, int columna) {
		
		
		//Primero, cambiamos el estado de la casilla seleccionada:
		if (tableroVirtual[fila][columna].isEncendida()) {
			tableroVirtual[fila][columna].setEncendida(false);
		} else {
			tableroVirtual[fila][columna].setEncendida(true);
		}
		
		//Vamos a cambiar las de alrededor. Para ello, debemos tener en cuenta:
		
		//Si NO está en la parte superior del tablero, cambiamos la luz inmediatamente arriba
		
		if (fila > 0) {
			
			if (tableroVirtual[fila - 1][columna].isEncendida()) {
				
				tableroVirtual[fila - 1][columna].setEncendida(false);
				
			} else {
				
				tableroVirtual[fila - 1][columna].setEncendida(true);
				
			}
			
		}
		
		//Si NO está en la parte inferior del tablero, cambiamos la luz inmediatamente abajo
		
		if (fila < tableroVirtual.length - 1) {
			
			if (tableroVirtual[fila + 1][columna].isEncendida()) {
				
				tableroVirtual[fila + 1][columna].setEncendida(false);
				
			} else {
				
				tableroVirtual[fila + 1][columna].setEncendida(true);
				
			}
			
		}
		
		//Si NO está en el lateral izquierdo del tablero, cambiamos la luz inmediatamente a la izquierda
		
		if (columna > 0) {
			
			if (tableroVirtual[fila][columna - 1].isEncendida()) {
				
				tableroVirtual[fila][columna - 1].setEncendida(false);
				
			} else {
				
				tableroVirtual[fila][columna - 1].setEncendida(true);
				
			}
			
		}
		
		//Si NO está en el lateral derecho del tablero, cambiamos la luz inmediatamente a la derecha
		
		if (columna < tableroVirtual[0].length - 1) {
		
			if (tableroVirtual[fila][columna + 1].isEncendida()) {
				
				tableroVirtual[fila][columna + 1].setEncendida(false);
				
			} else {
				
				tableroVirtual[fila][columna + 1].setEncendida(true);
				
			}
			
		}
	}

	public void invertirTablero() {

        for (int i = 0; i < tableroVirtual.length; i++) {
            for (int j = 0; j < tableroVirtual[0].length; j++) {
                
                if(tableroVirtual[i][j].isEncendida()) {
                    tableroVirtual[i][j].setEncendida(false);
                } else {
                	tableroVirtual[i][j].setEncendida(true);
                }
            }
        }

	}
}


