/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadoraservidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;



 public class RecibirDatos implements Runnable{
        
    public ServerSocket SocketServidor;
    public String huella;
    int puerto;
    String op1;
    String op2;
    String op3;
    String op4;

    public RecibirDatos(ServerSocket ports,String huella, String puerto, String op1, String op2, String op3, String op4) throws IOException {
        this.SocketServidor = ports;
        this.huella=huella;
        this.puerto = Integer.parseInt(puerto);
        this.op1=op1;
        this.op2=op2;
        this.op3=op3;
        this.op4=op4;
    }
    
    @Override
    public void run() {
         while(true)
        {
            Socket elsocket;
            String Mensaje="";
             try {
                System.out.println("Se acaba de conectar "+SocketServidor.getLocalPort());
                elsocket = SocketServidor.accept();
                DataInputStream in = new DataInputStream(elsocket.getInputStream());
                DataOutputStream out = new DataOutputStream(elsocket.getOutputStream());
                Mensaje = in.readUTF();
                System.out.println(Mensaje);
                String[] arrSplit = Mensaje.split(" ");
                String j="";
                switch(arrSplit[0])
                {
                    case "1":
                        j=op1;
                        break;
                    case "2":
                        j=op2;
                        break;
                    case "3":
                        j=op3;
                        break;
                    case "4":
                        j=op4;
                        break;
                }
                out.writeUTF("9 "+huella+ " " + arrSplit[0]+ " " +arrSplit[2] + " " +j);
                elsocket.close();
             } catch (IOException ex) {
                 System.out.println("Fallo la conexion");
             }
             
            //Process process = Runtime.getRuntime().exec("cmd /c start java -jar \"D:\\Home\\Documentos\\7mosemestre\\Computodistribuido\\ClasesJava\\Pruebas\\dist\\Pruebas.jar\" " +num1 +" "+num2 + " "+ SocketServidor.getLocalPort());
             
            Operacion A = new Operacion(Mensaje,puerto,SocketServidor.getLocalPort(),op1,op2,op3,op4);
            Thread ta = new Thread(A);
            ta.start();
                    
        }


    }
    public class Operacion implements Runnable
    {
        String Mensaje;
        int puerto;
        int mipuerto;
        String ope1;
        String ope2;
        String ope3;
        String ope4;
        
        Operacion(String Mensaje, int puerto,int mipuerto, String op1, String op2, String op3, String op4){
            this.Mensaje = Mensaje;
            this.puerto = puerto;
            this.mipuerto = mipuerto;
            this.ope1=op1;
            this.ope2=op2;
            this.ope3=op3;
            this.ope4=op4;
            System.out.println(puerto);
        }
        
        @Override
        public void run() {

            try {
                Process process = null;
                String[] Separa = Mensaje.split(" ");
                switch (Separa[0])
                {
                    case "0":

                        if(Separa[2].equals(huella))
                        {
                            System.out.println("Debo levantar a "+ Separa[1]);
                            System.out.println("cmd /c java -jar \"D:\\Home\\Documentos\\7mosemestre\\Computodistribuido\\Calculadora\\CalculadoraServidor\\dist\\CalculadoraServidor.jar\" \"C:\\Users\\Hp\\Desktop\\"+Separa[1]+"\"");
                            process = Runtime.getRuntime().exec("cmd /c java -jar \"D:\\Home\\Documentos\\7mosemestre\\Computodistribuido\\Calculadora\\CalculadoraServidor\\dist\\CalculadoraServidor.jar\" \"C:\\Users\\Hp\\Desktop\\"+Separa[1]+"\"");
                        }
                        break;
                    case "1":
                    {
                        if (ope1.equals("1"))
                        {
                        try {
                            process = Runtime.getRuntime().exec("cmd /c start java -jar \"D:\\Home\\Documentos\\7mosemestre\\Computodistribuido\\Calculadora\\CalculadoraServidor_Suma\\dist\\CalculadoraServidor_Suma.jar\" "+ Mensaje+" "+ mipuerto + " " + puerto);
                        } catch (IOException ex) {
                            Logger.getLogger(RecibirDatos.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        }
                    }
                    break;
                    
                    case "2":
                    {
                        if (ope2.equals("1")){
                        try {
                            process = Runtime.getRuntime().exec("cmd /c start java -jar \"D:\\Home\\Documentos\\7mosemestre\\Computodistribuido\\Calculadora\\CalculadoraServidor_Resta\\dist\\CalculadoraServidor_Resta.jar\" "+ Mensaje+" "+ mipuerto + " " + puerto);
                        } catch (IOException ex) {
                            Logger.getLogger(RecibirDatos.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        }
                    }
                    break;
                    
                    case "3":
                    {
                        if (ope3.equals("1")){
                        try {
                            process = Runtime.getRuntime().exec("cmd /c start java -jar \"D:\\Home\\Documentos\\7mosemestre\\Computodistribuido\\Calculadora\\CalculadoraServidor_Multi\\dist\\CalculadoraServidor_Multi.jar\" "+ Mensaje+" "+ mipuerto + " " + puerto);
                        } catch (IOException ex) {
                            Logger.getLogger(RecibirDatos.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        }
                    }
                    break;
                    
                    case "4":
                        if (ope4.equals("1"))
                        {
                        process = Runtime.getRuntime().exec("cmd /c start java -jar \"D:\\Home\\Documentos\\7mosemestre\\Computodistribuido\\Calculadora\\CalculadoraServidor_Division\\dist\\CalculadoraServidor_Division.jar\" "+ Mensaje+" "+ mipuerto + " " + puerto);
                        }
                        break;
                }
            } catch (IOException ex) {
                Logger.getLogger(RecibirDatos.class.getName()).log(Level.SEVERE, null, ex);
            }
                    
            
        }
        
    }
 }
