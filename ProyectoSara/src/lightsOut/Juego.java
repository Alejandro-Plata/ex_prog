package lightsOut;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
// Añadidos para guardar y cargar tablero
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class Juego {

    private Tablero tableroJuego; 
    private int casillasActivas;
    private int duracion;

    // Constructor modificado: primero intenta cargar tablero_inicial.txt
    public Juego() {
        if (!cargarTableroInicialSiExiste()) {
            configurarFichero();
        }
    }

    public Juego(Tablero tableroJuego, int duracion, int casillasActivas) {
        this.tableroJuego = tableroJuego;
        this.casillasActivas = casillasActivas;
        this.duracion = duracion;
    }

    public Tablero getTableroJuego() {
        return tableroJuego;
    }

    public void setTableroJuego(Tablero tableroJuego) {
        this.tableroJuego = tableroJuego;
    }

    public int getCasillasActivas() {
        return casillasActivas;
    }

    public void setCasillasActivas(int casillasActivas) {
        this.casillasActivas = casillasActivas;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    // Métodos

    public void configurarFichero() {
        String linea;
        try (BufferedReader lector = new BufferedReader(new FileReader("recursos/configuracion.txt"))) {
            //La primera línea son las filas del tablero
            int filas = Integer.parseInt(lector.readLine().split("/")[0].replace(" ", "")); 
            if(filas > 9 || filas < 4) { 
                throw new IllegalArgumentException("Número de filas inválido");
            }
            //La segunda línea es la duración de la partida
            this.duracion = Integer.parseInt(lector.readLine().split("/")[0].replace(" ", "")); 
            if(duracion < 0) {
                throw new IllegalArgumentException("La duración no puede ser negativa");
            }
            //La tercera línea es el número de casillas activas
            this.casillasActivas = Integer.parseInt(lector.readLine().split("/")[0].replace(" ", "")); 
            if(casillasActivas < 0 || casillasActivas > filas*filas) {
                throw new IllegalArgumentException("Número incorrecto de casillas activas");
            }
            if (casillasActivas != 0) {
                this.tableroJuego = new Tablero(filas, casillasActivas);
                return; //Salimos del método, no hay nada más que configurar
            }
            Casilla [][] tableroPredeterminado = new Casilla [filas][filas];
            ArrayList <String> casillasPorFila = new ArrayList <String>(); 
            while ((linea = lector.readLine()) != null) {
                //La línea no debe empezar por un comentario, por lo que descartamos todas las líneas que empiecen por "/"
                if(!linea.startsWith("/")) {
                    //Elegimos como elementos de la lista la primera parte de dividir la línea cuando se encuentre una /,
                    //para separar los comentarios de los datos realmente de interés. Además, eliminamos los espacios en 
                    casillasPorFila.add(linea.split("/")[0].replace(" ", ""));
                }
            }
            /*
             * Rellenamos el tablero predeterminado aprovechando el ArrayList.
             * Cada elemento del ArrayList corresponde a una fila. Más concretamente, cada elemento es una cadena de caracteres
             * con cada caracter representando el estado de una casilla.
             * Es por esto que indicamos que cada columna se corresponde con el caracter en la misma posición que esa columna.
             * Por último, una vez seleccionado cada caracter lo evaluamos como un booleano igualandolo a 1. Si el caracter es 1,
             * el resultado es true y por tanto la casilla se inicia como encendida. Si el resultado es false, la casilla se 
             */
            for (int i = 0; i < tableroPredeterminado.length; i++) {
                for(int j = 0; j < tableroPredeterminado[0].length; j++) {
                    tableroPredeterminado[i][j] = new Casilla (casillasPorFila.get(i).charAt(j) == '1');
                }
            }
            this.tableroJuego = new Tablero(tableroPredeterminado); //Crea el tablero predeterminado
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Guarda el estado actual del tablero en tablero_guardado.txt
     */
    public void guardarTableroFinal() {
        if (tableroJuego == null) return;
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter("tablero_guardado.txt"))) {
            Casilla[][] matriz = tableroJuego.getTableroVirtual();
            for (int i = 0; i < matriz.length; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < matriz[i].length; j++) {
                    sb.append(matriz[i][j].isEncendida() ? '1' : '0');
                }
                escritor.write(sb.toString());
                escritor.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Intenta cargar el tablero desde tablero_inicial.txt.
     * @return true si se ha cargado, false si no existe el fichero.
     */
    public boolean cargarTableroInicialSiExiste() {
        File fichero = new File("tablero_inicial.txt");
        if (!fichero.exists()) {
            return false;
        }
        try (BufferedReader lector = new BufferedReader(new FileReader(fichero))) {
            String linea;
            ArrayList<String> filas = new ArrayList<>();
            while ((linea = lector.readLine()) != null) {
                if (!linea.trim().isEmpty() && !linea.startsWith("/")) {
                    filas.add(linea.trim());
                }
            }
            int n = filas.size();
            Casilla[][] tablero = new Casilla[n][n];
            for (int i = 0; i < n; i++) {
                String fila = filas.get(i);
                for (int j = 0; j < n; j++) {
                    tablero[i][j] = new Casilla(fila.charAt(j) == '1');
                }
            }
            this.tableroJuego = new Tablero(tablero);
            this.duracion = 60; // Valor por defecto o puedes leerlo de otro sitio
            this.casillasActivas = 0;
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public long temporizador(long tiempoInicial) {
        return duracion - (System.currentTimeMillis() / 1000 - tiempoInicial);
    }

    public boolean comprobarVictoria() {
        for (int i = 0; i < tableroJuego.getTableroVirtual().length; i++) {
            for (int j = 0; j < tableroJuego.getTableroVirtual()[0].length; j++) {
                //Con que haya una casilla encendida, no has ganado.
                if(tableroJuego.getTableroVirtual()[i][j].isEncendida()) {
                    return false;
                }
            }
        }
        return true;
    }

    

    }

    //
    // 


