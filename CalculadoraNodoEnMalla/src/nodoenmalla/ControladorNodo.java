/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodoenmalla;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hp
 */
public class ControladorNodo implements Runnable{

     public ServerSocket SocketServidor;
     int puerto_I;
     int Otro_Nodo;
    
    public ControladorNodo(int Num_Nodo, int original, int Otro) {
        System.out.println(original);
        try {
             this.SocketServidor = new ServerSocket(Num_Nodo);
         } catch (IOException ex) {
             Logger.getLogger(ControladorNodo.class.getName()).log(Level.SEVERE, null, ex);
         }
        this.puerto_I =original;
        this.Otro_Nodo = Otro;
    }

    
    @Override
    public void run() {
        System.out.println("Me crearon para escuchar a " + Otro_Nodo);
        while(true)
        {
            Socket elsocket;
            String Mensaje="";
            try{
               System.out.println("Se acaba de conectar "+SocketServidor.getLocalPort());
               elsocket = SocketServidor.accept();
               DataInputStream in = new DataInputStream(elsocket.getInputStream());
               DataOutputStream out = new DataOutputStream(elsocket.getOutputStream());
               Mensaje = in.readUTF();
               
               System.out.println(Mensaje);
               elsocket.close();
               
           } catch (IOException ex) {
            System.out.println("Fallo la conexion");
           }
           if(Mensaje.indexOf("Res")== -1)
           {
               try {
                    Socket Pedir_nodos = new Socket("127.0.0.1",puerto_I);
                    DataOutputStream out = new DataOutputStream(Pedir_nodos.getOutputStream());

                    out.writeUTF("Operacion-"+Mensaje);
                    Pedir_nodos.close();

               } catch (IOException ex) {
                    System.out.println("Fallo, error en la conexcion");
               }
           }
           else{
               try {
                Socket Pedir_nodos = new Socket("127.0.0.1",puerto_I);
                DataOutputStream out = new DataOutputStream(Pedir_nodos.getOutputStream());

                out.writeUTF(Mensaje);
                Pedir_nodos.close();

           } catch (IOException ex) {
                System.out.println("Fallo, error en la conexcion");
           }
           }
           
        }
    }

    
}
