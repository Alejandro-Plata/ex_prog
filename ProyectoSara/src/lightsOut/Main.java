package lightsOut;

import java.util.Scanner;

public class Main {
	
	public static void menuConfiguracion() {
		System.out.println("Selecciona si deseas jugar utilizando la configuración del fichero o ingresando los valores manualmente por consola.");
		System.out.println("1. Configuración por fichero");
		System.out.println("2. Configuración por consola");
	}
	
	public static Juego configuracionConsola() {
		
		int filas = 0;
		int duracion = 0;
		int casillasActivas = 0;
		
		Scanner escaner = new Scanner(System.in);
		
		System.out.println("Número de filas: ");
		filas = escaner.nextInt();
		
		if(filas > 9 || filas < 4) {
			throw new IllegalArgumentException("Número de filas inválido");
		}
		
		System.out.println("Duración: ");
		duracion = escaner.nextInt();
		
		if(duracion < 0) {
			throw new IllegalArgumentException("La duración no puede ser negativa");
		}
		
		System.out.println("Número de casillas activas: ");
		casillasActivas = escaner.nextInt();
		
		if(casillasActivas < 0 || casillasActivas > filas*filas) {
			throw new IllegalArgumentException("Número incorrecto de casillas activas");
		}
		
		Tablero tableroConsola = new Tablero(filas, casillasActivas);
		
		Juego partidaConsola = new Juego(tableroConsola, duracion, casillasActivas);
		
		return partidaConsola;
		
		}

	public static void main(String[] args) {
		
		/* Líneas afectadas: 
		 * 
		 * Main
		 * 
		 * 66, 112 - 116
		 * 
		 * Tablero
		 * 
		 * 157 - 170
		 * 
		 * */
		
		Scanner escaner = new Scanner(System.in);
		Juego partida = null;
		int contadorJugadas = 0;
		
		String opcion = ""; //Es una string porque para salir de la partida hay que indicarlo con 00
		int fila = 0;
		int columna = 0;
		
		//Elegir configuración
		do {
			menuConfiguracion(); //Permite elegir al jugador el tipo de configuración que desea
			opcion = escaner.nextLine();
			
			switch(opcion) {
				case "1": partida = new Juego();
					break;
				case "2": partida = configuracionConsola();
					break;
				default: System.out.println("Valor equivocado, inténtalo de nuevo.");
					break;
			}
			
		} while(!opcion.equals("1") && !opcion.equals("2"));
		
		long tiempoInicial = System.currentTimeMillis() / 1000; //Entre 1000 para pasar de milisegundos a segundos
		
		//Movimientos durante la partida
		do {
			
			partida.getTableroJuego().pintarTablero(); //Pinta el tablero de juego
			System.out.println();
			System.out.println("Tiempo restante: " + partida.temporizador(tiempoInicial));
			System.out.println();	
			System.out.println("1. Hacer jugada");
			System.out.println("00. Salir");
			
			opcion = escaner.nextLine();
			
			if(!opcion.equals("00")) {
				
				System.out.println("Elige la fila: ");
				fila = Integer.parseInt(escaner.nextLine());
				System.out.println("Elige la columna: ");
				columna = escaner.nextInt();
				escaner.nextLine(); //Arregla errores en las lecturas posteriores
				
				partida.getTableroJuego().cambiarLuces(fila, columna);
				
				contadorJugadas++;
				
				if (contadorJugadas % 3 == 0) {
					partida.getTableroJuego().invertirTablero();					
				}
				
				if(partida.comprobarVictoria()) {
					System.out.println("¡Enhorabuena! Has ganado.");
					partida.getTableroJuego().pintarTablero();
					
					escaner.close();
					return;
				}
			}
			
		} while (!opcion.equals("00") && partida.temporizador(tiempoInicial) > 0);
		
		if (opcion == "00") {
			System.out.println("Has salido de la partida");
		} else {
			System.out.println("¡Tiempo acabado!");
		} 
		
		escaner.close();
		
	}
	
}
