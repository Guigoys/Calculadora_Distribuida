/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administrador;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Scanner;

/**
 *
 * @author Hp
 */
public class Administrador {

    public static void main(String[] args) throws IOException {
        Scanner Input = new Scanner(System.in);
        int NodoAConectar=8000;
        String mensaje="";
        final String HOST ="127.0.0.1";
        DataOutputStream out ;
        var A = true;
        while(A){
            try {
                Socket elsocket = new Socket(HOST,NodoAConectar);
                out = new DataOutputStream(elsocket.getOutputStream());

                out.writeUTF("Soy Amin");

                elsocket.close();
                A=false;

            } catch (IOException ex) {
                System.out.println("Fallo, error en la conexcion");
                NodoAConectar=NodoAConectar+200;
            }
        }
        byte[] bytesSumar = Files.readAllBytes(Paths.get("D:/Home/Documentos/7mosemestre/Computodistribuido/Calculadora/Administrador/Sumar.jar"));
        String op1 = Base64.getEncoder().encodeToString(bytesSumar);
        byte[] bytesRestar = Files.readAllBytes(Paths.get("D:/Home/Documentos/7mosemestre/Computodistribuido/Calculadora/Administrador/Restar.jar"));
        String op2 = Base64.getEncoder().encodeToString(bytesRestar);
        byte[] bytesMultiplicar = Files.readAllBytes(Paths.get("D:/Home/Documentos/7mosemestre/Computodistribuido/Calculadora/Administrador/Multiplicar.jar"));
        String op3 = Base64.getEncoder().encodeToString(bytesMultiplicar);
        byte[] bytesDividir = Files.readAllBytes(Paths.get("D:/Home/Documentos/7mosemestre/Computodistribuido/Calculadora/Administrador/Dividir.jar"));
        String op4 = Base64.getEncoder().encodeToString(bytesDividir);
        System.out.println("Estamos en "+NodoAConectar);
        String MensajeFinal = "";
        while(true){
            System.out.println("Escriba la instruccoion a realizar (1,2,3 o 4) y el identificador del servidor asi:");
            System.out.println("4 abcde1234");
            String Destino = Input.nextLine();
            String[] arrSplit = Destino.split(" ");
            switch(arrSplit[0]){
                case "1":
                    MensajeFinal = "0 " + Destino + " " + op1;
                    break;
                case "2":
                    MensajeFinal = "0 " + Destino + " " + op2;
                    break;
                case "3":
                    MensajeFinal = "0 " + Destino + " " + op3;
                    break;
                case "4":
                    MensajeFinal = "0 " + Destino + " " + op4;
                    break;
            }
            try {
                Socket elsocket = new Socket(HOST,NodoAConectar+198);
                out = new DataOutputStream(elsocket.getOutputStream());

                out.writeUTF(MensajeFinal);

                elsocket.close();

            } catch (IOException ex) {
                System.out.println("Fallo, error en la conexcion");
                NodoAConectar=NodoAConectar+200;
            }
        }
    }
    
}
