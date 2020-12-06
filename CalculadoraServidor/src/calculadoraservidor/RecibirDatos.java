/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadoraservidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Base64;
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
    
    int TengoSuma=0;
    int TengoResta=0;
    int TengoMulti=0;
    int TengoDivi=0;

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
            int i=0;
             try {
                System.out.println("Se acaba de conectar "+SocketServidor.getLocalPort());
                elsocket = SocketServidor.accept();
                DataInputStream in = new DataInputStream(elsocket.getInputStream());
                DataOutputStream out = new DataOutputStream(elsocket.getOutputStream());
                Mensaje = in.readUTF();
                System.out.println(Mensaje);
                String[] arrSplit = Mensaje.split(" ");
                i=Integer.parseInt(arrSplit[0]);
                String j="0";
                Process process = null;
                switch(arrSplit[0])
                {
                    case "0":
                    if(arrSplit[2].equals(huella))
                    {
                        if(arrSplit[1].equals("1")||arrSplit[1].equals("2")||arrSplit[1].equals("3")||arrSplit[1].equals("4")){
                            byte[] decode = Base64.getDecoder().decode(arrSplit[3]);
                            try {
                                switch (arrSplit[1]){
                                    case "1":
                                        writeBytesToFile("D:/Home/Documentos/7mosemestre/Computodistribuido/Calculadora/CalculadoraServidor/Sumar.jar", decode);
                                        TengoSuma=1;
                                        break;
                                    case "2":
                                        writeBytesToFile("D:/Home/Documentos/7mosemestre/Computodistribuido/Calculadora/CalculadoraServidor/Restar.jar", decode);
                                        TengoResta=1;
                                        break;
                                    case "3":
                                        writeBytesToFile("D:/Home/Documentos/7mosemestre/Computodistribuido/Calculadora/CalculadoraServidor/Multiplicar.jar", decode);
                                        TengoMulti=1;
                                        break;
                                    case "4":
                                        writeBytesToFile("D:/Home/Documentos/7mosemestre/Computodistribuido/Calculadora/CalculadoraServidor/Dividir.jar", decode);
                                        TengoDivi=1;
                                        break;    
                                }
                            } catch (IOException ex) {
                                System.out.println("Error al crear el archivo");
                            }
                        }
                        else
                        {
                        System.out.println("Debo levantar a "+ arrSplit[1]);
                        System.out.println("cmd /c java -jar \"D:\\Home\\Documentos\\7mosemestre\\Computodistribuido\\Calculadora\\CalculadoraServidor\\dist\\CalculadoraServidor.jar\" \"C:\\Users\\Hp\\Desktop\\"+arrSplit[1]+"\"");
                        process = Runtime.getRuntime().exec("cmd /c java -jar \"D:\\Home\\Documentos\\7mosemestre\\Computodistribuido\\Calculadora\\CalculadoraServidor\\dist\\CalculadoraServidor.jar\" \"C:\\Users\\Hp\\Desktop\\"+arrSplit[1]+"\"");
                        }
                    }
                    break;
                    case "1":
                        if(TengoSuma==1)
                        {
                            j=op1;
                        }
                        else
                        {
                            i=0;
                        }
                        break;
                    case "2":
                        if(TengoResta==1)
                        {
                        j=op2;
                        }
                        else
                        {
                            i=0;
                        }
                        break;
                    case "3":
                        if(TengoMulti==1)
                        {
                        j=op3;
                        }
                        else
                        {
                            i=0;
                        }
                        break;
                    case "4":
                        if(TengoDivi==1)
                        {
                        j=op4;
                        }
                        else
                        {
                            i=0;
                        }
                        break;
                }
                out.writeUTF("9 "+huella+ " " + arrSplit[0]+ " " +arrSplit[2] + " " +j);
                elsocket.close();
             } catch (IOException ex) {
                 System.out.println("Fallo la conexion");
             }
             
            //Process process = Runtime.getRuntime().exec("cmd /c start java -jar \"D:\\Home\\Documentos\\7mosemestre\\Computodistribuido\\ClasesJava\\Pruebas\\dist\\Pruebas.jar\" " +num1 +" "+num2 + " "+ SocketServidor.getLocalPort());
            if(i>0)
            { 
            Operacion A = new Operacion(Mensaje,puerto,SocketServidor.getLocalPort(),op1,op2,op3,op4);
            Thread ta = new Thread(A);
            ta.start();
            }
                    
        }


    }
    private void writeBytesToFile(String dDivisionjar, byte[] bytes) throws IOException{
         try (FileOutputStream fos = new FileOutputStream(dDivisionjar)) {
            fos.write(bytes);
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
            String[] Separa = Mensaje.split(" ");
            URL[] classLoaderUrls;
            URLClassLoader urlClassLoader;
            Class<?> clazz;
            String class_name = "";
            switch (Separa[0]) {
                case "1":
                    class_name = "Sumar";
                    break;
                case "2":
                    class_name = "Restar";
                    break;
                case "3":
                    class_name = "Multiplicar";
                    break;
                case "4":
                    class_name = "Dividir";
                    break;
            }
            
            try {
                System.out.println("CALLING " + class_name + ".jar");
                classLoaderUrls = new URL[]{new URL("file:////D:/Home/Documentos/7mosemestre/Computodistribuido/Calculadora/CalculadoraServidor/" +class_name + ".jar")};
                urlClassLoader = new URLClassLoader(classLoaderUrls);
                clazz = urlClassLoader.loadClass(class_name.toLowerCase()+ "." +class_name);
                Constructor<?> ct = clazz.getConstructor(String.class, int.class);
                ct.newInstance( Mensaje, puerto);
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            
        }
        
        
    }
 }
