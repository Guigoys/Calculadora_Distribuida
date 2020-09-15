package calculadoraservidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CalculadoraServidor {

    public static void main(String[] args) throws IOException {
        int puertos[] = new int[1000];
        for(int i=0;i<puertos.length; i++)
        {
            puertos[i]=i+7000;
        }
        ServerSocket Servidor = create(puertos);
        
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

                out.writeUTF("Servidor-"+Servidor.getLocalPort());

                mensaje = in.readUTF();
                System.out.println(mensaje);
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
            PUERTO=PUERTO+200;
            if (PUERTO>60000)
            {
                Buscando = false;
            }
        }
        
        RecibirDatos Serv= new RecibirDatos(Servidor);
        int mipuerto = Serv.SocketServidor.getLocalPort();
        Thread llegada = new Thread(Serv);
        
        llegada.start(); 
        
        
    }
    static public ServerSocket create(int[] ports) throws IOException {
        for (int port : ports) {
            try {
                return new ServerSocket(port);
            } catch (IOException ex) {
                continue; // try next port
            }
        }

        // if the program gets here, no port in the range was found
        throw new IOException("no free port found");
    }
}
