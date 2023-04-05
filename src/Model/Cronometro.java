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
    private int secs=0;
    private Timeline timeLine;
    gameViewController timer;

    public Cronometro(gameViewController timer) {
        this.timer = timer;
    }
    
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
    private void actualizarCronometro() {
        int minutos = (secs % 3600) / 60;
        int seconds = secs % 60;
        String textoCronometro = (minutos <= 9?"0":"") + minutos + ":" + (seconds <= 9?"0":"") + seconds;      
        timer.cronometro.setText(textoCronometro);
    }
    
    public void detenerCronometro() {
        if (timeLine != null) {
            timeLine.stop();
        }
    }

}
