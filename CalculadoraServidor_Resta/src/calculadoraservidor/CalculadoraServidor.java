package calculadoraservidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CalculadoraServidor {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println(args.length);
        String Resul = "";
        double num1 = Integer.parseInt(args[3]);
        double num2 = Integer.parseInt(args[4]);
        double resultado = num1 - num2;
        Resul = "6 " + args[1] + " " + args[2] + " " + args[3] + " " + args[4] + " " + resultado;
        System.out.println(Resul);
        final String HOST ="127.0.0.1";
        DataOutputStream out;
        try {
            Socket elsocket = new Socket(HOST,Integer.parseInt(args[6]));
            out = new DataOutputStream(elsocket.getOutputStream());
            
            out.writeUTF(Resul);
            

            elsocket.close();

        } catch (IOException ex) {
            System.out.println("Fallo, error en la conexcion");
        }

    }
}
