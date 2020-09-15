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
public class ControladorServidor implements Runnable{
    
    public ServerSocket SocketServidor;
    int puerto_I;
    int Servidor;

    public ControladorServidor(int Num_Nodo, int puerto_I, int Servidor) {
        try {
             this.SocketServidor = new ServerSocket(Num_Nodo);
         } catch (IOException ex) {
             Logger.getLogger(ControladorNodo.class.getName()).log(Level.SEVERE, null, ex);
         }
        this.puerto_I = puerto_I;
        this.Servidor = Servidor;
    }

    
    
    @Override
    public void run() {
        System.out.println("Yo escucho al servidor de " +  Servidor);
        while(true)
        {
             Socket elsocket;
             String Mensaje="";
             String Resultado ="";
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
            try {
                Socket Operar = new Socket("127.0.0.1",Servidor);
                DataOutputStream outO = new DataOutputStream(Operar.getOutputStream());
                DataInputStream inO = new DataInputStream(Operar.getInputStream());
                outO.writeUTF(Mensaje);
                Resultado = inO.readUTF();
                Operar.close();

            } catch (IOException ex) {
                System.out.println("Fallo, error en la conexcion");
            }
            String Nodos ="";
            try {
                Socket Pedir_nodos = new Socket("127.0.0.1",puerto_I);
                DataInputStream in = new DataInputStream(Pedir_nodos.getInputStream());
                DataOutputStream out = new DataOutputStream(Pedir_nodos.getOutputStream());

                out.writeUTF(Resultado);
                Nodos = in.readUTF();
                System.out.println(Nodos);
                Pedir_nodos.close();

            } catch (IOException ex) {
                System.out.println("Fallo, error en la conexcion");
            }
            System.out.println(Nodos);
            String[] Nodo = Nodos.split(" ");
            for (int  i = 0; i < Nodo.length;i++)
           {
                try {
                    Socket Enviar = new Socket("127.0.0.1",Integer.parseInt(Nodo[i]));
                    DataOutputStream outE = new DataOutputStream(Enviar.getOutputStream());

                    outE.writeUTF(Resultado);
                    Enviar.close();

                } catch (IOException ex) {
                    System.out.println("Fallo, error en la conexcion");
                }
           }
        }
    }
    
}
