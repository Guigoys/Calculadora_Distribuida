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
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import javafx.application.Platform;
import javafx.scene.control.Alert.AlertType;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    int puerto;
    Thread T1;
    ServerSocket Llegada;
    String huella;
    @FXML
    private Button igual;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            huella =   sha1(String.valueOf(System.currentTimeMillis()));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CalculadoraController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.print(huella);
        Llegada = create(6000);
        puerto = Llegada.getLocalPort();
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
        T1 = new Thread(Recibir);
        T1.start();
    }

    public ServerSocket create(int ports){
        for (int port= ports;port < 7000; port+= 5) {
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
    
    List<Evento> Sumas = new ArrayList<Evento>(); 
    List<Evento> Restas = new ArrayList<Evento>();
    List<Evento> Multiplicaciones= new ArrayList<Evento>();
    List<Evento> Divisiones = new ArrayList<Evento>();
    List<Servi> Servidores_s = new ArrayList<Servi>();
    List<Servi> Servidores_r = new ArrayList<Servi>();
    List<Servi> Servidores_m = new ArrayList<Servi>();
    List<Servi> Servidores_d = new ArrayList<Servi>();
    
    public class Servi {
        String Servidor;
        boolean Aparecio;
    }     
            
    @FXML
    private void botonigua(ActionEvent event) {
        String[] arrSplit = acum.split(" ");
        Thread Time = null;
        String total=arrSplit[0] + " "+ arrSplit[2];
        switch(operacion)
        {
            
            case '1':
                Sumas.add(new Evento(Sumas.size(),total));
                conexion(("1 "+(Sumas.size()-1)+" "+huella+" "+total),'+');
                Time_max_s Cont_t = new Time_max_s();
                Time = new Thread(Cont_t);
                break;
            case '2':
                Restas.add(new Evento(Restas.size(),total));
                conexion(("2 "+(Restas.size()-1)+" "+huella+" "+total),'-');
                Time_max_r Cont_u = new Time_max_r();
                Time = new Thread(Cont_u);
                break;
            case '3':
                Multiplicaciones.add(new Evento(Multiplicaciones.size(),total));
                conexion(("3 "+(Multiplicaciones.size()-1)+" "+huella+" "+total),'*');
                Time_max_m Cont_v = new Time_max_m();
                Time = new Thread(Cont_v);
                break;
            case '4':
                Divisiones.add(new Evento(Divisiones.size(),total));
                conexion(("4 "+(Divisiones.size()-1)+" "+huella+" "+total),'/');
                Time_dax_d Cont_w = new Time_dax_d();
                Time = new Thread(Cont_w);
                break;
        }

        Time.start();
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
                int ajuste = Math.max(0, huella.length() - 4);
                String huella1 = huella.substring(0, ajuste);
                if(Integer.parseInt(arrSplit[0]) >= 5 && (arrSplit[2].equals(huella1)|| arrSplit[2].equals(huella)))
                {
                    String Total ="";
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(CalculadoraController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    switch (arrSplit[0])
                    {
                        case "5":
                            Total = arrSplit[3] +" + "+  arrSplit[4]+ " = " + arrSplit[5];
                            Evento S = Sumas.get(Integer.parseInt(arrSplit[1]));
                            if(S.resultado == -123)
                            {
                                S.resultado = Double.parseDouble(arrSplit[5]);
                                Sumas.set(Integer.parseInt(arrSplit[1]),S);
                                if(recibos_s>=recibos_nesesarios)
                                {
                                    ImpSuma.appendText(Total + "\n"); 
                                }
                                  
                            }
                            break;
                        case "6":
                            Total = arrSplit[3] +" - "+  arrSplit[4]+ " = " + arrSplit[5];
                            Evento B = Restas.get(Integer.parseInt(arrSplit[1]));
                            if(B.resultado==-123){
                                B.resultado = Double.parseDouble(arrSplit[5]);
                                Restas.set(Integer.parseInt(arrSplit[1]),B);
                                if(recibos_r>=recibos_nesesarios)
                                {
                                   ImpRest.appendText(Total+ "\n");
                                }
                            }
                            break;
                        case "7":
                            Total = arrSplit[3] +" * "+  arrSplit[4]+ " = " + arrSplit[5];
                            Evento C = Multiplicaciones.get(Integer.parseInt(arrSplit[1]));
                            if(C.resultado==-123){
                            C.resultado = Double.parseDouble(arrSplit[5]);
                            Multiplicaciones.set(Integer.parseInt(arrSplit[1]),C);
                            if(recibos_m>=recibos_nesesarios)
                                {
                                   ImpMult.appendText(Total+ "\n");
                                }
                            
                            }
                            break;
                        case "8":
                            Total = arrSplit[3] +" / "+  arrSplit[4]+ " = " + arrSplit[5];
                            Evento D = Divisiones.get(Integer.parseInt(arrSplit[1]));
                            if(D.resultado==-123){
                            D.resultado = Double.parseDouble(arrSplit[5]);
                            Divisiones.set(Integer.parseInt(arrSplit[1]),D);
                            if(recibos_d>=recibos_nesesarios)
                                {
                                    ImpDivi.appendText(Total+ "\n");
                                }
                           
                            }
                            break;
                    }
                }
                
                
            }
            
        }

    }
    public class Evento
    {
        int numEvento;
        String operacion;
        public double resultado = -123;
        
        public Evento(int Num, String op)
        {
            numEvento = Num;
            operacion  = op;
        }
    }
    
    int recibos_s=0;
    int recibos_r=0;
    int recibos_m=0;
    int recibos_d=0;
    int recibos_nesesarios = 3;
    
    
    public  class Contador_Tickets_s implements Runnable{

        boolean exit = true;
        ServerSocket Tickets;
        
        Contador_Tickets_s(ServerSocket Tickets){
        this.Tickets = Tickets;
        }
        
        @Override
        public void run() {
            for(int i = 0; i < Servidores_s.size();i++)
                    {
                        Servi A = new Servi();
                        A.Aparecio = false ;
                        A.Servidor = Servidores_s.get(i).Servidor;
                        Servidores_s.set(i,A);
                    }
            while(exit){
                Socket elsocket;
                String Mensaje="";
                
                try{
                    System.out.println("Se acaba de conectar "+Tickets.getLocalPort());
                    elsocket = Tickets.accept();
                    DataInputStream in = new DataInputStream(elsocket.getInputStream());
                    Mensaje = in.readUTF();
                    if(Mensaje.split(" ")[4].equals("1"))
                    {
                       recibos_s++;
                    boolean A = false;
                    System.out.println(Mensaje.split(" ")[1]);
                    for(int i = 0; i < Servidores_s.size();i++)
                    {
                        if(Servidores_s.get(i).Servidor.equals(Mensaje.split(" ")[1]))
                        {
                            Servidores_s.get(i).Aparecio=true;
                            A=true;
                            break;
                        }
                    }
                    if(!A)
                    {
                        Servi aux = new Servi();
                        aux.Aparecio=true;
                        aux.Servidor= Mensaje.split(" ")[1];
                        Servidores_s.add(aux);
                    } 
                    }
                    System.out.println(Mensaje);
                    elsocket.close();
                } catch (IOException ex) {
                    break;
                }  
            }
        }
        
    }
    
    public class Time_max_s implements Runnable
    {

        @Override
        public void run() {
            recibos_s = 0;
            try {
                long start = System.nanoTime();
                ServerSocket Juas = new ServerSocket(puerto+1);
                Contador_Tickets_s contar = new Contador_Tickets_s(Juas);
                Thread Vamoaver = new Thread(contar);
                Vamoaver.start();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CalculadoraController.class.getName()).log(Level.SEVERE, null, ex);
                }
                Juas.close();
                Juas=null;
                System.out.println("Se encontraron "+recibos_s + " de suma");
                if(recibos_s<recibos_nesesarios){
                    Platform.runLater(() -> {
                        Platform.runLater(() -> {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("No hay suficientes servidores, intentelo mas tarde");
                        alert.showAndWait();
                    });
                    });
                     for(int i = 0; i < Servidores_s.size();i++)
                    {
                        System.out.println(Servidores_s.get(i).Servidor +" este esta "+ Servidores_s.get(i).Aparecio);
                        if (!Servidores_s.get(i).Aparecio)
                        {
                            //Mandar a levantar
                            int j=0;
                            String ServUp ="";
                            while(j<Servidores_s.size())
                            {
                                if (Servidores_s.get(j).Aparecio)
                                {
                                    ServUp = Servidores_s.get(j).Servidor;
                                    j=Servidores_s.size()+20;
                                }
                                j++;
                            }
                            Levanta Fall = new Levanta(Servidores_s.get(i).Servidor,ServUp);
                            Thread Lev = new Thread(Fall);
                            Lev.start();
                        }
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(CalculadoraController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    public  class Contador_Tickets_r implements Runnable{

        boolean exit = true;
        ServerSocket Tickets;
        
        Contador_Tickets_r(ServerSocket Tickets){
        this.Tickets = Tickets;
        }
        
        @Override
        public void run() {
            for(int i = 0; i < Servidores_r.size();i++)
                    {
                        Servi A = new Servi();
                        A.Aparecio = false ;
                        A.Servidor = Servidores_r.get(i).Servidor;
                        Servidores_r.set(i,A);
                    }
            while(exit){
                Socket elsocket;
                String Mensaje="";
                
                try{
                    System.out.println("Se acaba de conectar "+Tickets.getLocalPort());
                    elsocket = Tickets.accept();
                    DataInputStream in = new DataInputStream(elsocket.getInputStream());
                    Mensaje = in.readUTF();
                    if(Mensaje.split(" ")[4].equals("1"))
                    {
                       recibos_r++;
                    boolean A = false;
                    System.out.println(Mensaje.split(" ")[1]);
                    for(int i = 0; i < Servidores_r.size();i++)
                    {
                        if(Servidores_r.get(i).Servidor.equals(Mensaje.split(" ")[1]))
                        {
                            Servidores_r.get(i).Aparecio=true;
                            A=true;
                            break;
                        }
                    }
                    if(!A)
                    {
                        Servi aux = new Servi();
                        aux.Aparecio=true;
                        aux.Servidor= Mensaje.split(" ")[1];
                        Servidores_r.add(aux);
                    } 
                    }
                    System.out.println(Mensaje);
                    elsocket.close();
                } catch (IOException ex) {
                    break;
                }  
            }
        }
        
    }
    
    public class Time_max_r implements Runnable
    {

        @Override
        public void run() {
            recibos_r = 0;
            try {
                long start = System.nanoTime();
                ServerSocket Juas1 = new ServerSocket(puerto+2);
                Contador_Tickets_r contar1 = new Contador_Tickets_r(Juas1);
                Thread Vamoaver = new Thread(contar1);
                Vamoaver.start();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CalculadoraController.class.getName()).log(Level.SEVERE, null, ex);
                }
                Juas1.close();
                Juas1=null;
                System.out.println("Se encontraron "+recibos_r + " de resta");
                if(recibos_r<recibos_nesesarios){
                Platform.runLater(() -> {
                            Platform.runLater(() -> {
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Error");
                            alert.setHeaderText(null);
                            alert.setContentText("No hay suficientes servidores, intentelo mas tarde");
                            alert.showAndWait();
                        });
                        });}
                for(int i = 0; i < Servidores_r.size();i++)
                    {
                        System.out.println(Servidores_r.get(i).Servidor +" este esta "+ Servidores_r.get(i).Aparecio);
                        if (!Servidores_r.get(i).Aparecio)
                        {
                            //Mandar a levantar
                            int j=0;
                            String ServUp ="";
                            while(j<Servidores_r.size())
                            {
                                if (Servidores_r.get(j).Aparecio)
                                {
                                    ServUp = Servidores_r.get(j).Servidor;
                                    j=Servidores_r.size()+20;
                                }
                                j++;
                            }
                            Levanta Fall = new Levanta(Servidores_r.get(i).Servidor,ServUp);
                            Thread Lev = new Thread(Fall);
                            Lev.start();
                        }
                    }
            } catch (IOException ex) {
                Logger.getLogger(CalculadoraController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    public  class Contador_Tickets_m implements Runnable{

        boolean exit = true;
        ServerSocket Tickets;
        
        Contador_Tickets_m(ServerSocket Tickets){
        this.Tickets = Tickets;
        }
        
        @Override
        public void run() {
            for(int i = 0; i < Servidores_m.size();i++)
                    {
                        Servi A = new Servi();
                        A.Aparecio = false ;
                        A.Servidor = Servidores_m.get(i).Servidor;
                        Servidores_m.set(i,A);
                    }
            while(exit){
                Socket elsocket;
                String Mensaje="";
                
                try{
                    System.out.println("Se acaba de conectar "+Tickets.getLocalPort());
                    elsocket = Tickets.accept();
                    DataInputStream in = new DataInputStream(elsocket.getInputStream());
                    Mensaje = in.readUTF();
                    if(Mensaje.split(" ")[4].equals("1"))
                    {
                       recibos_m++;
                    boolean A = false;
                    System.out.println(Mensaje.split(" ")[1]);
                    for(int i = 0; i < Servidores_m.size();i++)
                    {
                        if(Servidores_m.get(i).Servidor.equals(Mensaje.split(" ")[1]))
                        {
                            Servidores_m.get(i).Aparecio=true;
                            A=true;
                            break;
                        }
                    }
                    if(!A)
                    {
                        Servi aux = new Servi();
                        aux.Aparecio=true;
                        aux.Servidor= Mensaje.split(" ")[1];
                        Servidores_m.add(aux);
                    } 
                    }
                    System.out.println(Mensaje);
                    elsocket.close();
                } catch (IOException ex) {
                    break;
                }  
            }
        }
        
    }
    
    public class Time_max_m implements Runnable
    {

        @Override
        public void run() {
            recibos_m = 0;
            try {
                long start = System.nanoTime();
                ServerSocket Juas = new ServerSocket(puerto+3);
                Contador_Tickets_m contar = new Contador_Tickets_m(Juas);
                Thread Vamoaver = new Thread(contar);
                Vamoaver.start();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CalculadoraController.class.getName()).log(Level.SEVERE, null, ex);
                }
                Juas.close();
                Juas=null;
                System.out.println("Se encontraron "+recibos_m + " de multiplicacion");
                if(recibos_m<recibos_nesesarios){
                Platform.runLater(() -> {
                            Platform.runLater(() -> {
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Error");
                            alert.setHeaderText(null);
                            alert.setContentText("No hay suficientes servidores, intentelo mas tarde");
                            alert.showAndWait();
                        });
                        });}
                for(int i = 0; i < Servidores_m.size();i++)
                    {
                        System.out.println(Servidores_m.get(i).Servidor +" este esta "+ Servidores_m.get(i).Aparecio);
                        if (!Servidores_m.get(i).Aparecio)
                        {
                            //Mandar a levantar
                            int j=0;
                            String ServUp ="";
                            while(j<Servidores_m.size())
                            {
                                if (Servidores_m.get(j).Aparecio)
                                {
                                    ServUp = Servidores_m.get(j).Servidor;
                                    j=Servidores_m.size()+20;
                                }
                                j++;
                            }
                            Levanta Fall = new Levanta(Servidores_m.get(i).Servidor,ServUp);
                            Thread Lev = new Thread(Fall);
                            Lev.start();
                        }
                    }
            } catch (IOException ex) {
                Logger.getLogger(CalculadoraController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    public  class Contador_Tickets_d implements Runnable{

        boolean exit = true;
        ServerSocket Tickets;
        
        Contador_Tickets_d(ServerSocket Tickets){
        this.Tickets = Tickets;
        }
        
        @Override
        public void run() {
            for(int i = 0; i < Servidores_d.size();i++)
                    {
                        Servi A = new Servi();
                        A.Aparecio = false ;
                        A.Servidor = Servidores_d.get(i).Servidor;
                        Servidores_d.set(i,A);
                    }
            while(exit){
                Socket elsocket;
                String Mensaje="";
                
                try{
                    System.out.println("Se acaba de conectar "+Tickets.getLocalPort());
                    elsocket = Tickets.accept();
                    DataInputStream in = new DataInputStream(elsocket.getInputStream());
                    Mensaje = in.readUTF();
                    if(Mensaje.split(" ")[4].equals("1"))
                    {
                       recibos_d++;
                    boolean A = false;
                    System.out.println(Mensaje.split(" ")[1]);
                    for(int i = 0; i < Servidores_d.size();i++)
                    {
                        if(Servidores_d.get(i).Servidor.equals(Mensaje.split(" ")[1]))
                        {
                            Servidores_d.get(i).Aparecio=true;
                            A=true;
                            break;
                        }
                    }
                    if(!A)
                    {
                        Servi aux = new Servi();
                        aux.Aparecio=true;
                        aux.Servidor= Mensaje.split(" ")[1];
                        Servidores_d.add(aux);
                    } 
                    }
                    System.out.println(Mensaje);
                    elsocket.close();
                } catch (IOException ex) {
                    break;
                }  
            }
        }
        
    }
    
    public class Time_dax_d implements Runnable
    {

        @Override
        public void run() {
            recibos_d = 0;
            try {
                long start = System.nanoTime();
                ServerSocket Juas = new ServerSocket(puerto+4);
                Contador_Tickets_d contar = new Contador_Tickets_d(Juas);
                Thread Vamoaver = new Thread(contar);
                Vamoaver.start();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CalculadoraController.class.getName()).log(Level.SEVERE, null, ex);
                }
                Juas.close();
                Juas=null;
                System.out.println("Se encontraron "+recibos_d + " de division");
                if(recibos_s<recibos_nesesarios){
                Platform.runLater(() -> {
                            Platform.runLater(() -> {
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Error");
                            alert.setHeaderText(null);
                            alert.setContentText("No hay suficientes servidores, intentelo mas tarde");
                            alert.showAndWait();
                        });
                        });}
                for(int i = 0; i < Servidores_d.size();i++)
                    {
                        System.out.println(Servidores_d.get(i).Servidor +" este esta "+ Servidores_d.get(i).Aparecio);
                        if (!Servidores_d.get(i).Aparecio)
                        {
                            //Mandar a levantar
                            int j=0;
                            String ServUp ="";
                            while(j<Servidores_d.size())
                            {
                                if (Servidores_d.get(j).Aparecio)
                                {
                                    ServUp = Servidores_d.get(j).Servidor;
                                    j=Servidores_d.size()+20;
                                }
                                j++;
                            }
                            Levanta Fall = new Levanta(Servidores_d.get(i).Servidor,ServUp);
                            Thread Lev = new Thread(Fall);
                            Lev.start();
                        }
                    }
            } catch (IOException ex) {
                Logger.getLogger(CalculadoraController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    static String sha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
         
        return sb.toString();
    }
    
    public class Levanta implements Runnable
    {
        String huella;
        String Activo;
        
        Levanta(String huella, String Activo){
            this.huella=huella;
            this.Activo = Activo;
        }
        
        @Override
        public void run() {
            String mensaje="";
            final String HOST ="127.0.0.1";
            DataOutputStream out;



            try {
                Socket elsocket = new Socket(HOST,NodoAConectar);
                out = new DataOutputStream(elsocket.getOutputStream());

                out.writeUTF("0 "+huella+" "+Activo);


                elsocket.close();

            } catch (IOException ex) {
                System.out.println("Fallo, error en la conexcion");
            }
        }
        
    }
}
