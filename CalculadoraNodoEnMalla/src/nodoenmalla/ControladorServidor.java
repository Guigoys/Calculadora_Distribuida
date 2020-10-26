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
             System.out.println("Fallo en la conexion");
         }
        this.puerto_I = puerto_I;
        this.Servidor = Servidor;
    }

    
    
    @Override
    public void run() {
        System.out.println("Yo escucho al servidor de " +  Servidor);
        String Mensaje="";
        while(true)
        {
            Socket elsocket;

            String Resultado ="";
           try{
                System.out.println("Se acaba de conectar "+SocketServidor.getLocalPort());
                elsocket = SocketServidor.accept();
                DataInputStream in = new DataInputStream(elsocket.getInputStream());
                Mensaje = in.readUTF();
                System.out.println("Me llego un mensaje para el servidor de "+ Mensaje);
                elsocket.close();
           } catch (IOException ex) {
            System.out.println("Fallo la conexion");
            continue;
           }
            System.out.println(Mensaje);
            if(Integer.parseInt(Mensaje.split(" ")[0]) < 5 && !Mensaje.split(" ")[0].equals("400"))
            {
                try {
                Socket Operar = new Socket("127.0.0.1",Servidor);
                DataOutputStream outO = new DataOutputStream(Operar.getOutputStream());
                DataInputStream inO = new DataInputStream(Operar.getInputStream());
                outO.writeUTF(Mensaje);
                Resultado = inO.readUTF();
                System.out.println(Resultado);
                Operar.close();

                } catch (IOException ex) {
                    System.out.println("Fallo, error en la conexcion");
                    continue;
                }
                String[] Res=Resultado.split(" ");
                if(Integer.parseInt(Res[0])!= 0)
                {
                    String Nodos ="";
                    try {
                        Socket Pedir_nodos = new Socket("127.0.0.1",puerto_I);
                        DataInputStream in = new DataInputStream(Pedir_nodos.getInputStream());
                        DataOutputStream out = new DataOutputStream(Pedir_nodos.getOutputStream());

                        out.writeUTF(Resultado);
                        Nodos = in.readUTF();
                        Pedir_nodos.close();

                    } catch (IOException ex) {
                        System.out.println("Fallo, error en la conexcion");
                        continue;
                    }
                    String[] Nodo = Nodos.split(" ");
                    for (int  i = 0; i < Nodo.length;i++)
                   {
                        try {
                            Socket Enviar = new Socket("127.0.0.1",Integer.parseInt(Nodo[i]));
                            DataOutputStream outE = new DataOutputStream(Enviar.getOutputStream());
                            System.out.println(Nodo[i]);
                            outE.writeUTF(Resultado);
                            Enviar.close();

                        } catch (IOException ex) {
                            System.out.println("Fallo, error en la conexcion");
                            continue;
                        }
                   }
                }
            }
            else
            {
                if(!Mensaje.split(" ")[0].equals("400"))
                {
                String Nodos ="";
                System.out.println(Mensaje);
                try {
                    Socket Pedir_nodos = new Socket("127.0.0.1",puerto_I);
                    DataInputStream in = new DataInputStream(Pedir_nodos.getInputStream());
                    DataOutputStream out = new DataOutputStream(Pedir_nodos.getOutputStream());

                    out.writeUTF(Mensaje);
                    Nodos = in.readUTF();
                    Pedir_nodos.close();

                } catch (IOException ex) {
                    System.out.println("Fallo, error en la conexcion");
                    continue;
                }
                String[] Nodo = Nodos.split(" ");
                for (int  i = 0; i < Nodo.length;i++)
               {
                    try {
                        Socket Enviar = new Socket("127.0.0.1",Integer.parseInt(Nodo[i]));
                        DataOutputStream outE = new DataOutputStream(Enviar.getOutputStream());
                        System.out.println(Nodo[i]);
                        outE.writeUTF(Mensaje);
                        Enviar.close();

                    } catch (IOException ex) {
                        System.out.println("Fallo, error en la conexcion");
                        continue;
                    }
               }
                }
            }
            
        }
    }
    
}
