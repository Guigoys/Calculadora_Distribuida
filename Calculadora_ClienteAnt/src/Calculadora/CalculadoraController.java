/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calculadora;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * FXML Controller class
 *
 * @author Hp
 */
public class CalculadoraController implements Initializable {

    String acum="";
    int NodoAConectar=0;
    @FXML
    private Button num1;
    @FXML
    private Button num2;
    @FXML
    private Button num3;
    @FXML
    private Button num4;
    @FXML
    private Button num5;
    @FXML
    private Button num6;
    @FXML
    private Button num7;
    @FXML
    private Button num8;
    @FXML
    private Button num9;
    @FXML
    private Button num0;
    @FXML
    private Button punt;
    @FXML
    private Button bora;
    @FXML
    private Button suma;
    @FXML
    private Button rest;
    @FXML
    private Button mult;
    @FXML
    private Button divi;
    @FXML
    private Label Res;
    @FXML
    private TextArea ImpSuma;
    @FXML
    private TextArea ImpRest;
    @FXML
    private TextArea ImpMult;
    @FXML
    private TextArea ImpDivi;
    
    
    double acumulador=0;
    char operacion='0';
    @FXML
    private Button igual;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServerSocket Llegada = create(6000);
        
        String mensaje="";
        final String HOST ="127.0.0.1";
        int PUERTO =8000;
        DataInputStream in;
        DataOutputStream out;
        var Buscando=true;
        while(Buscando){
            try {
                Socket elsocket = new Socket(HOST,PUERTO);
                in = new DataInputStream(elsocket.getInputStream());
                out = new DataOutputStream(elsocket.getOutputStream());

                out.writeUTF("Cliente "+Llegada.getLocalPort());

                mensaje = in.readUTF();
                if (Integer.parseInt(mensaje) !=0)
                {
                    System.out.println(PUERTO);
                    System.out.println(mensaje);
                    Buscando = false;
                    break;
                }
                elsocket.close();

            } catch (IOException ex) {
                System.out.println("Fallo, error en la conexcion");
            }
            PUERTO= PUERTO+200;
            if (PUERTO>60000)
            {
                Buscando = false;
            }
        }
        NodoAConectar = Integer.parseInt(mensaje);
        
        HiloRecibir Recibir = new HiloRecibir(Llegada);
        Thread T1 = new Thread(Recibir);
        T1.start();
    }

    public ServerSocket create(int ports){
        for (int port= ports;port < 7000; port++) {
            try {
                return new ServerSocket(port);
            } catch (IOException ex) {
                continue; // try next port
            }
        }
        try {
            // if the program gets here, no port in the range was found
            throw new IOException("no free port found");
        } catch (IOException ex) {
            Logger.getLogger(Appmain.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }    

    @FXML
    private void button1(ActionEvent event) {
        acum=acum+"1";
        Res.setText(acum);
    }

    @FXML
    private void button2(ActionEvent event) {
        acum=acum+"2";
        Res.setText(acum);
    }

    @FXML
    private void button3(ActionEvent event) {
        acum=acum+"3";
        Res.setText(acum);
    }

    @FXML
    private void button4(ActionEvent event) {
        acum=acum+"4";
        Res.setText(acum);
    }

    @FXML
    private void button5(ActionEvent event) {
        acum=acum+"5";
        Res.setText(acum);
    }

    @FXML
    private void button6(ActionEvent event) {
        acum=acum+"6";
        Res.setText(acum);
    }

    @FXML
    private void Button7(ActionEvent event) {
        acum=acum+"7";
        Res.setText(acum);
    }

    @FXML
    private void button8(ActionEvent event) {
        acum=acum+"8";
        Res.setText(acum);
    }

    @FXML
    private void button9(ActionEvent event) {
        acum=acum+"9";
        Res.setText(acum);
    }

    @FXML
    private void button0(ActionEvent event) {
        acum=acum+"0";
        Res.setText(acum);
    }

    @FXML
    private void buttonpunt(ActionEvent event) {
        acum=acum+".";
        Res.setText(acum);
    }

    @FXML
    private void buttonbor(ActionEvent event) {
        acum = "";
        Res.setText(acum);
    }

    @FXML
    private void buttonsum(ActionEvent event) {
        
        acumulador = Double.parseDouble(acum);
        operacion = '1';
        acum=acum + " + ";
        Res.setText(acum);
        
    }

    @FXML
    private void buttonmen(ActionEvent event) {
        
        acumulador = Double.parseDouble(acum);
        operacion = '2';
        acum=acum + " - ";
        Res.setText(acum);
    }

    @FXML
    private void buttonmult(ActionEvent event) {
       acumulador = Double.parseDouble(acum);
        operacion = '3';
        acum=acum + " * ";
        Res.setText(acum);
    }

    @FXML
    private void buttondiv(ActionEvent event) {
        acumulador = Double.parseDouble(acum);
        operacion = '4';
        acum=acum + " / ";
        Res.setText(acum);
    }
    
    String sumat="";
    String restt="";
    String multt="";
    String divit="";
    List<String> Sumas = new ArrayList<String>(); 
    List<String> Restas = new ArrayList<String>();
    List<String> Multiplicaciones= new ArrayList<String>();
    List<String> Divisiones = new ArrayList<String>();
    
    @FXML
    private void botonigua(ActionEvent event) {
        String[] arrSplit = acum.split(" ");
        
        String total=arrSplit[0] + " "+ arrSplit[2];
        switch(operacion)
        {
            case '1':
                Sumas.add(acum);
                sumat=sumat+"\n"+acum;
                //ImpSuma.appendText(sumat);
                conexion(("1 "+total),'+');
                break;
            case '2':
                Restas.add(acum);
                restt=restt+"\n"+acum;
                conexion(("2 "+total),'-');
                //ImpRest.appendText(restt);
                break;
            case '3':
                Multiplicaciones.add(acum);
                multt=multt+"\n"+acum;
                conexion(("3 "+total),'*');
                //ImpMult.appendText(multt);
                break;
            case '4':
                Divisiones.add(acum);
                divit=divit+"\n"+acum;
                conexion(("4 "+total),'/');
                //ImpDivi.appendText(acum + "\n");
                break;
        }
        acum="";
        Res.setText(acum);
    }
    public void conexion(String Total, char operacion)
    {
        String mensaje="";
        final String HOST ="127.0.0.1";
        DataInputStream in;
        DataOutputStream out;
        
        
        
        try {
            Socket elsocket = new Socket(HOST,NodoAConectar);
            out = new DataOutputStream(elsocket.getOutputStream());
            
            out.writeUTF(Total);
            

            elsocket.close();

        } catch (IOException ex) {
            System.out.println("Fallo, error en la conexcion");
        }
    }
    public class HiloRecibir implements Runnable{

        ServerSocket A;
        
        public HiloRecibir(ServerSocket Creado) {
            this.A= Creado;
        }

        
        @Override
        public void run() {
            while(true)
            {
                Socket elsocket;
                String Mensaje="";
                try{
                    System.out.println("Se acaba de conectar "+A.getLocalPort());
                    elsocket = A.accept();
                    DataInputStream in = new DataInputStream(elsocket.getInputStream());
                    Mensaje = in.readUTF();
                    System.out.println(Mensaje);
                    elsocket.close();
                } catch (IOException ex) {
                    System.out.println("Fallo la conexion");
                }
                String[] arrSplit = Mensaje.split(" ");
                if(Integer.parseInt(arrSplit[0]) >= 5)
                {
                    String Total ="";
                    switch (arrSplit[0])
                    {
                        case "5":
                            Total = arrSplit[1] +" + "+  arrSplit[2]+ " = " + arrSplit[3];
                            ImpSuma.appendText(Total + "\n");
                            break;
                        case "6":
                            Total = arrSplit[1] +" - "+  arrSplit[2]+ " = " + arrSplit[3];
                            ImpRest.appendText(Total+ "\n");
                            break;
                        case "7":
                            Total = arrSplit[1] +" * "+  arrSplit[2]+ " = " + arrSplit[3];
                            ImpMult.appendText(Total+ "\n");
                            break;
                        case "8":
                            Total = arrSplit[1] +" / "+  arrSplit[2]+ " = " + arrSplit[3];
                            ImpDivi.appendText(Total+ "\n");
                            break;
                    }
                }
                
                
            }
            
        }

    }
    
}



