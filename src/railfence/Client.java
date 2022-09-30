/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package railfence;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author FPTSHOP
 */
public class Client {
    
    
    
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
        
        DatagramSocket client = new DatagramSocket();
        // nhap du lieu
        Scanner sc = new Scanner(System.in);
        String bangMa="";
        int _key=0;
        boolean _number = false;
        while(bangMa==""|| bangMa==null){
            System.out.println("Moi ban nhap bang ma:");
            bangMa= sc.nextLine();
        }
        while (!_number|| _key<2) {            
            try
            {
                System.out.println("Moi ban nhap key:");
                _key = sc.nextInt();
                _number = true;
            }
            catch(Exception ex)
            {
                sc.nextLine();
            }
        }
        
        // gui bang ma hoa
        byte[] data = new byte[256];
        data = RailFenceCipher(bangMa, _key).getBytes();
        InetAddress ip = InetAddress.getByName("localhost");
        DatagramPacket RailFenceOut = new DatagramPacket(data, data.length, ip, 9999);
        client.send(RailFenceOut);
        
        // gui khoa
        byte[] key = new byte[256];
        key = String.valueOf(_key).getBytes();
        DatagramPacket KeyOut = new DatagramPacket(key, key.length, ip, 9999);
        client.send(KeyOut);
        // nhan ket qua cua server
        byte[] nhanketqua = new byte[256];
        DatagramPacket soKyTu = new DatagramPacket(nhanketqua, nhanketqua.length);
        client.receive(soKyTu);
        System.out.println("Chu cai co so lan xuat hien nhieu thu 2 la: " + String.valueOf(new String(soKyTu.getData(),0,soKyTu.getLength())));
        
    }
    public static String RailFenceCipher(String mess, int key){
        String result = "";
        // check duong di cua phan tu
        boolean check = false;
        int row = key;
        int i =0;
        int col = mess.length();
        // klhoi tao mang 
        char arr[][] = new char[row][col];
        for(int j=0;j<col;j++){
            if(i==0 || i==key-1){
                check=!check;
            }
            arr[i][j] = mess.charAt(j);
            if(check)
                i++;
            else
                i--;
        }
        
        for(int a=0;a<row;a++){
            for(int j = 0 ; j<col;j++){
                //System.out.print(arr[a][j] + "[]");
                if(arr[a][j] != 0)
                    result +=arr[a][j];
            }
            //System.out.println();
        }
        //System.out.println(result);
     return result;
    }
}
