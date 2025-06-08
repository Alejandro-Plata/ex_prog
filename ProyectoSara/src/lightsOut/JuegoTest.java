package lightsOut;

public class JuegoTest {

	public static void main(String[] args) {
	
		// Prueba del constructor del juego
        System.out.println("1. Crear un juego con tablero de 4x4, 4 casillas activas y duración de 120 segundos");
        
        Tablero tableroJuegoPrueba = new Tablero(4, 4);
        
        Juego juego = new Juego(tableroJuegoPrueba, 120, 4);
        
        if (juego.getTableroJuego() != null && juego.getDuracion() == 120) {
        
        	System.out.println("Juego creado correctamente");
        
        } else {
        
        	System.out.println("Error al crear el juego");
        
        }
        
        System.out.println();
        
        // Pruebas para comprobar victoria
        
        System.out.println("2. Comprobar victoria cuando todas están apagadas");
        
        Tablero tablero = new Tablero(4, 0); // todas apagadas
        
        juego.setTableroJuego(tablero);
        
        if (juego.comprobarVictoria()) {
        
        	System.out.println("No hay casillas encendidas, victoria correcta.");
        
        } else {
        	
            System.out.println("Error, debería ser victoria");
            
        }
        
        System.out.println();
        
        System.out.println("3. Comprobar victoria cuando hay una casilla encendida");
        
        
        tablero.getTableroVirtual()[0][0].setEncendida(true); // encender una luz
        
        if (!juego.comprobarVictoria()) {
            
        	System.out.println("Como hay una casilla encendida, no hay victoria.");
        
        } else {
            
        	System.out.println("Debería haber fallado (casilla encendida)");
        
        }
        
        System.out.println();

        // Prueba del temporizador
        
        System.out.println("4. Probando temporizador");
        
        long inicio = System.currentTimeMillis() / 1000;
        
        try { 
        	
        	Thread.sleep(2000); //Hace que el programa se detenga 2 segundos
        	
        } catch (Exception e) {
        
        	e.printStackTrace();
        	
        }
        
        long tiempoRestante = juego.temporizador(inicio);
        
        System.out.println("Tiempo restante (debería ser menor a 120): " + tiempoRestante);

        System.out.println();
        
        // Prueba del método cambiarLuces
        System.out.println("5. Prueba de cambiarLuces en (1,1)");
        
        Tablero tablero2 = new Tablero(3, 0); // todas apagadas
        
        tablero2.cambiarLuces(1, 1);
        
        if (tablero2.getTableroVirtual()[1][1].isEncendida() && //La casilla indicada
        	tablero2.getTableroVirtual()[0][1].isEncendida() && //La casilla de arriba
        	tablero2.getTableroVirtual()[2][1].isEncendida() && //La casilla de abajo
        	tablero2.getTableroVirtual()[1][0].isEncendida() && //La casilla de la izquierda
        	tablero2.getTableroVirtual()[1][2].isEncendida())   //La casilla de la derecha
        { 
        
        	System.out.println("Prueba exitosa para el método cambiarLuces");
        
        } else {
        
        	System.out.println("Error en cambiarLuces");
        
        }
		
	}
	
}
