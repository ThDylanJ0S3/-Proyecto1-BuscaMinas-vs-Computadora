package Model;

import controller.gameViewController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 *
 * @author Dylan Meza
 */

public class Cronometro {
    private int secs = 0; // Segundos transcurridos
    private Timeline timeLine; // Línea de tiempo para actualizar el cronómetro
    gameViewController timer; // Controlador de vista del juego
    
    /**
    * Constructor de la clase "Cronometro".
    * 
    * @param timer el controlador de vista del juego.
    */
    public Cronometro(gameViewController timer) {
        this.timer = timer;
    }
    
    /**
    * Inicia el cronómetro del juego.
    */
    public void gameTimerInit(){
        if (timeLine != null) {
            timeLine.stop();
        }
        secs = 0;

        timeLine = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            secs++;
            actualizarCronometro();
        }));

        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
    }
    
    /**
    * Actualiza el cronómetro en la vista del juego con los segundos transcurridos.
    */
    private void actualizarCronometro() {
        int minutos = (secs % 3600) / 60;
        int seconds = secs % 60;
        String textoCronometro = (minutos <= 9?"0":"") + minutos + ":" + (seconds <= 9?"0":"") + seconds;      
        timer.cronometro.setText(textoCronometro);
    }
    
    /**
    * Detiene el cronómetro del juego.
    */
    public void detenerCronometro() {
        if (timeLine != null) {
            timeLine.stop();
        }
    }

}
