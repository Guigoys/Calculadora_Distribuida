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

 public class RecibirDatos implements Runnable{
        
    public ServerSocket SocketServidor;

    public RecibirDatos(ServerSocket ports) throws IOException {
        this.SocketServidor = ports;
    }

    @Override
    public void run() {
         while(true)
        {
            Socket elsocket;
             try {
                System.out.println("Se acaba de conectar "+SocketServidor.getLocalPort());
                elsocket = SocketServidor.accept();
                DataInputStream in = new DataInputStream(elsocket.getInputStream());
                DataOutputStream out = new DataOutputStream(elsocket.getOutputStream());
                String Mensaje = in.readUTF();
                System.out.println(Mensaje);
                double resultado = opera(Mensaje);
                out.writeUTF(Mensaje+" = "+resultado);
                elsocket.close();
             } catch (IOException ex) {
                 System.out.println("Fallo la conexion");
             }
        }
    }

     public static double opera(String Total)
    {
        String[] arrSplit = Total.split(" ");   
        double num1 =Double.parseDouble(arrSplit[1]);
        double num2 =Double.parseDouble(arrSplit[2]);

        double res=0;
        switch(arrSplit[0])
        {
            case "1":
                res=num1+num2;
                break;
            case "2":
                res=num1-num2;
                break;
            case "3":
                res=num1*num2;
                break;
            case "4":
                res=num1/num2;
                break;
            default:
                res=num1;
                break;
        }
        return res;  
    }

}
