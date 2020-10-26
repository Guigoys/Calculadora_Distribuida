package calculadoraservidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CalculadoraServidor {

    public static void main(String[] args) throws IOException {
        
        if(args.length==0){
            String huella="";
            try {
                huella =   sha1(String.valueOf(System.currentTimeMillis()));
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(CalculadoraServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(huella);
            int puertos[] = new int[500];
            int j =0;
            for(int i=0;i<puertos.length; i++)
            {
                puertos[i]=j+7000;
                j+=2;
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

                    out.writeUTF("Servidor "+Servidor.getLocalPort());

                    mensaje = in.readUTF();
                    if (Integer.parseInt(mensaje) !=0)
                    {
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

            RecibirDatos Serv= new RecibirDatos(Servidor,huella,mensaje,"1","1","1","1");
            int mipuerto = Serv.SocketServidor.getLocalPort();
            Thread llegada = new Thread(Serv);

            try {
                File myObj = new File("C:\\Users\\Hp\\Desktop\\"+huella+".txt");
                if (myObj.createNewFile()) {
                    try {
                        FileWriter Escribir = new FileWriter("C:\\Users\\Hp\\Desktop\\"+huella+".txt");
                        Escribir.write(huella+"\n");
                        Escribir.write(mipuerto+"\n");
                        Escribir.write(mensaje+"\n");
                        Escribir.write(1+"\n");
                        Escribir.write(1+"\n");
                        Escribir.write(1+"\n");
                        Escribir.write(1+"\n");
                        Escribir.close();
                        System.out.println("Successfully wrote to the file.");
                      } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                      }

                } else {
                    System.out.println("File already exists.");
                }
            } 
            catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

            llegada.start(); 
        }else
        {
            if(args[0].equals("1") ||args[0].equals("0") )
            {
                String huella="";
            try {
                huella =   sha1(String.valueOf(System.currentTimeMillis()));
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(CalculadoraServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(huella);
            int puertos[] = new int[500];
            int j =0;
            for(int i=0;i<puertos.length; i++)
            {
                puertos[i]=j+7000;
                j+=2;
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

                    out.writeUTF("Servidor "+Servidor.getLocalPort());

                    mensaje = in.readUTF();
                    if (Integer.parseInt(mensaje) !=0)
                    {
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

            RecibirDatos Serv= new RecibirDatos(Servidor,huella,mensaje,args[0],args[1],args[2],args[3]);
            int mipuerto = Serv.SocketServidor.getLocalPort();
            Thread llegada = new Thread(Serv);

            try {
                File myObj = new File("C:\\Users\\Hp\\Desktop\\"+huella+".txt");
                if (myObj.createNewFile()) {
                    try {
                        FileWriter Escribir = new FileWriter("C:\\Users\\Hp\\Desktop\\"+huella+".txt");
                        Escribir.write(huella+"\n");
                        Escribir.write(mipuerto+"\n");
                        Escribir.write(mensaje+"\n");
                        Escribir.write(args[0]+"\n");
                        Escribir.write(args[1]+"\n");
                        Escribir.write(args[2]+"\n");
                        Escribir.write(args[3]+"\n");
                        Escribir.close();
                        System.out.println("Successfully wrote to the file.");
                      } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                      }

                } else {
                    System.out.println("File already exists.");
                }
            } 
            catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

            llegada.start();
            }
            else
            {
                File myObj = new File(args[0]+".txt");
                Scanner myReader = new Scanner(myObj);
                String hash = myReader.nextLine();
                System.out.println("El hash es "+ hash );
                String puerto = myReader.nextLine();
                System.out.println("El puerto es "+ puerto );
                String nodo = myReader.nextLine();
                System.out.println("El nodo es "+ nodo );
                String op1 = myReader.nextLine();
                System.out.println("operacion: "+ op1 );
                String op2 = myReader.nextLine();
                System.out.println("operacion: "+ op2 );
                String op3 = myReader.nextLine();
                System.out.println("operacion: "+ op3 );
                String op4 = myReader.nextLine();
                System.out.println("operacion: "+ op4 );
                myReader.close();
                ServerSocket  Servidor = null;
                try{
                Servidor = new ServerSocket(Integer.parseInt(puerto));
                }catch(IOException ex){
                     System.exit(1);
                }
                RecibirDatos Serv= new RecibirDatos(Servidor,hash,nodo, op1, op2, op3, op4);
                Thread llegada = new Thread(Serv);
                llegada.start();
                }
            }
        
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
    static String sha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
         
        return sb.toString();
    }
}
