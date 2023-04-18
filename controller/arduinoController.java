package controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortDataListener;
import java.awt.Robot;
import java.awt.event.KeyEvent;

/**
 *
 * @author Dylan Meza
 */

public class arduinoController {

    /**
    * Constructor vacio.
    */
    public arduinoController() {
    }

     /**
     * Puerto serie a utilizar.
     */
    private SerialPort serialPort;

    /**
     * Conecta el controlador al puerto serie especificado.
     * @param portName Nombre del puerto serie.
     */
    public void conectar(String portName) {
        try {
            // Se obtiene el puerto serie y se configura.
            serialPort = SerialPort.getCommPort(portName);
            serialPort.setBaudRate(9600);
            serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 100, 0);
            serialPort.openPort();
            
            // Se añade un listener para recibir datos del puerto serie.
            serialPort.addDataListener(new SerialPortDataListener() {
                @Override
                public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }
                @Override
                public void serialEvent(SerialPortEvent event) {
                    // Si no hay datos disponibles, se retorna.
                    if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
                        return;
            try {
                // Se lee el dato del puerto serie.
                BufferedReader reader = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
                String inputLine = reader.readLine();
                if (inputLine != null) {
                    // Se realiza una acción en función del dato leído.
                    Robot robot = new Robot();
                    switch (inputLine) {
                        case "BTN_LEFT":
                            robot.keyPress(KeyEvent.VK_LEFT);
                            robot.keyRelease(KeyEvent.VK_LEFT);
                            break;
                        case "BTN_DOWN":
                            robot.keyPress(KeyEvent.VK_DOWN);
                            robot.keyRelease(KeyEvent.VK_DOWN);
                            break;
                        case "BTN_RIGHT":
                            robot.keyPress(KeyEvent.VK_RIGHT);
                            robot.keyRelease(KeyEvent.VK_RIGHT);
                            break;
                        case "BTN_UP":
                            robot.keyPress(KeyEvent.VK_UP);
                            robot.keyRelease(KeyEvent.VK_UP);
                            break;
                        case "BTN_CLICK":
                            robot.keyPress(KeyEvent.VK_ENTER);
                            robot.keyRelease(KeyEvent.VK_ENTER);
                            break;
                    }
                }
            } catch (Exception e) {
                System.err.println("Error al leer los datos del puerto serie: " + e);
            }
        }
                });
        } catch (Exception e) {
            System.out.println("Error al abrir el puerto serie: " + e.getMessage());
        }
    }

    /**
     * Envía un comando al puerto serie.
     * @param comando Comando a enviar.
     */
    public void enviar(char comando) {
        try {
            OutputStream output = serialPort.getOutputStream();
            output.write(comando);
        } catch (Exception e) {
            System.err.println("Error al enviar datos al puerto serie: " + e);
        }
    }

    /**
     * Desconecta el controlador del puerto serie.
     */
    public void desconectar() {
        // Se remueve el listener y se cierra el puerto serie.
        serialPort.removeDataListener();
        serialPort.closePort();
        serialPort = null;
        
    }
}