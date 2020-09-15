package nodoenmalla;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class NodoEnMalla {
    
    static Vector<ControladorNodo> ar = new Vector<>();
    static Vector<ControladorCliente> clie = new Vector<>();
    static List<Integer> Nodos = new ArrayList<>();
    static List<Integer> Servidores = new ArrayList<>();
    
    
    public static void main(String[] args) throws IOException {
        int cliente = -5;
        int servidor= -5;
        
        ServerSocket Serv = create(8000);
        int mipuerto = Serv.getLocalPort();
        String HOST ="127.0.0.1";
        DataInputStream in;
        DataOutputStream out;
        int puertos = 8000;
        while(mipuerto > puertos)
        {
            try {
                Socket elsocket = new Socket(HOST,puertos);
                in = new DataInputStream(elsocket.getInputStream());
                out = new DataOutputStream(elsocket.getOutputStream());
                out.writeUTF("Nodo-"+mipuerto);
                elsocket.close();
                puertos = puertos +200;
            }catch (IOException ex) {
                System.out.println("Fallo, error en la conexcion para los demas");
            }
        }
        
        for(int i = 0 ;i< Nodos.size();i++)
        {
            ControladorNodo NuevoHilo = new ControladorNodo(mipuerto+i+1,mipuerto,Nodos.get(i)+Nodos.size());
            Thread t = new Thread(NuevoHilo);
            ar.add(NuevoHilo);
            t.start();
            
        }
         while(true)
        {
            Socket elsocket;
             try {
                System.out.println("Se acaba de conectar "+mipuerto);
                elsocket = Serv.accept();
                DataInputStream inn = new DataInputStream(elsocket.getInputStream());
                DataOutputStream outn = new DataOutputStream(elsocket.getOutputStream());
                String Mensaje = inn.readUTF();
                System.out.println(Mensaje);
                String[] arrSplit = Mensaje.split("-");
                switch(arrSplit[0])
                {
                    case "Nodo":
                        Nodos.add(Integer.parseInt(arrSplit[1]));
                        ControladorNodo NuevoHilo1 = new ControladorNodo(mipuerto+Nodos.size(),mipuerto,Integer.parseInt(arrSplit[1]));
                        Thread t1 = new Thread(NuevoHilo1);
                        ar.add(NuevoHilo1);
                        t1.start();
                        break;
                    case "¿Que eres?":
                        outn.writeUTF("Nodo");
                        break;
                    case "Cliente":
                        if (cliente !=-5)
                        {
                           outn.writeUTF("0");
                        }else
                        {
                            cliente = Integer.parseInt(arrSplit[1]);
                            outn.writeUTF(""+(mipuerto+198));
                            ControladorCliente Cliente = new ControladorCliente(mipuerto+198,mipuerto,Integer.parseInt(arrSplit[1]));
                            Thread tCl = new Thread(Cliente);
                            clie.add(Cliente);
                            tCl.start();
                        }
                        break;
                    case "Servidor":
                        if (servidor !=-5)
                        {
                           outn.writeUTF("0");
                        }else
                        {
                            servidor = Integer.parseInt(arrSplit[1]);
                            outn.writeUTF(""+(mipuerto+199));
                        }
                        break;
                    case "MioCliente":
                            for(int j=0;j<Nodos.size();j++)
                            {
                                
                                try{
                                    Socket Avisar = new Socket("127.0.0.1",(Nodos.get(j)+j+1));
                                    DataOutputStream OutA = new DataOutputStream(Avisar.getOutputStream());

                                    OutA.writeUTF(arrSplit[1]);
                                    Avisar.close();
                                }
                                catch (IOException ex){
                                    System.out.println("No se pudo conectar con "+mipuerto+j+1);
                                }
                            }
                            String Res="";
                            if(servidor !=-5)
                            {
                                try {
                                    Socket Pedir_nodos = new Socket("127.0.0.1",servidor);
                                    DataOutputStream outForS = new DataOutputStream(Pedir_nodos.getOutputStream());
                                    DataInputStream inFromS = new DataInputStream(Pedir_nodos.getInputStream());
                                    outForS.writeUTF(arrSplit[1]);
                                    Res = "Res-"+inFromS.readUTF();
                                    System.out.println(Res);
                                    Pedir_nodos.close();

                                } catch (IOException ex) {
                                    System.out.println("Fallo, error en la conexcion");
                                }
                                for(int j=0;j<Nodos.size();j++)
                                {

                                    try{
                                        Socket Avisar = new Socket("127.0.0.1",(Nodos.get(j)+j+1));
                                        DataOutputStream OutA = new DataOutputStream(Avisar.getOutputStream());

                                        OutA.writeUTF(Res);
                                        Avisar.close();
                                    }
                                    catch (IOException ex){
                                        System.out.println("No se pudo conectar con "+mipuerto+j+1);
                                    }
                                }
                            }
                            if (cliente != -5)
                            {
                                try {
                                    System.out.println("Queremos mandar info al cliente");
                                    Socket Pedir_nodos = new Socket("127.0.0.1",cliente);
                                    DataOutputStream outForC = new DataOutputStream(Pedir_nodos.getOutputStream());
                                    outForC.writeUTF(Res);
                                    Pedir_nodos.close();

                                } catch (IOException ex) {
                                    System.out.println("Fallo, error en la conexcion");
                                }
                            }
                            break;
                    case "Operacion":
                        String Res1="";
                        if(servidor !=-5)
                        {
                            try {
                                Socket Pedir_nodos = new Socket("127.0.0.1",servidor);
                                DataOutputStream outForS = new DataOutputStream(Pedir_nodos.getOutputStream());
                                DataInputStream inFromS = new DataInputStream(Pedir_nodos.getInputStream());
                                outForS.writeUTF(arrSplit[1]);
                                Res1= "Res-"+inFromS.readUTF();
                                System.out.println(Res1);
                                Pedir_nodos.close();

                            } catch (IOException ex) {
                                System.out.println("Fallo, error en la conexcion");
                            }
                            for(int j=0;j<Nodos.size();j++)
                            {

                                try{
                                    Socket Avisar = new Socket("127.0.0.1",(Nodos.get(j)+j+1));
                                    DataOutputStream OutA = new DataOutputStream(Avisar.getOutputStream());

                                    OutA.writeUTF(Res1);
                                    Avisar.close();
                                }
                                catch (IOException ex){
                                    System.out.println("No se pudo conectar con "+mipuerto+j+1);
                                }
                            }
                             if (cliente != -5)
                            {
                                try {
                                    System.out.println("Queremos mandar info al cliente");
                                    Socket Pedir_nodos = new Socket("127.0.0.1",cliente);
                                    DataOutputStream outForC = new DataOutputStream(Pedir_nodos.getOutputStream());
                                    outForC.writeUTF(Res1);
                                    Pedir_nodos.close();

                                } catch (IOException ex) {
                                    System.out.println("Fallo, error en la conexcion");
                                }
                            }
                            
                        }
                        break;
                    case "Res":
                        if (cliente != -5)
                        {
                            try {
                                System.out.println("Queremos mandar info al cliente");
                                Socket Pedir_nodos = new Socket("127.0.0.1",cliente);
                                DataOutputStream outForC = new DataOutputStream(Pedir_nodos.getOutputStream());
                                outForC.writeUTF(Mensaje);
                                Pedir_nodos.close();

                            } catch (IOException ex) {
                                System.out.println("Fallo, error en la conexcion");
                            }
                        }
                        break;
                    default:
                        System.out.println("No se identifico");
                        break;
                }

             } catch (IOException ex) {
                 System.out.println("Fallo la conexion");
             }
             System.out.println(Nodos);
        }
        
    }
    static public ServerSocket create(int portInicial) throws IOException {
        var flag = true;
        while(flag){
            if(portInicial < 65000){
                try {
                    return new ServerSocket(portInicial);
                } catch (IOException ex) {
                    Socket elsocket = new Socket("127.0.0.1",portInicial);
                    DataInputStream in = new DataInputStream(elsocket.getInputStream());
                    DataOutputStream out = new DataOutputStream(elsocket.getOutputStream());
                    out.writeUTF("¿Que eres?");
                    String mensaje = in.readUTF();
                    switch(mensaje)
                    { 
                        case "Nodo":
                            Nodos.add(portInicial);
                            break;
                        default:
                            System.out.println("Este puerto es basura");
                    }    
                    elsocket.close();
                    portInicial=portInicial+200;
                    continue; // try next port
                }
            }else
            {
                flag=false;
            }
        }
        return null;
    }
   
    
}

   

