/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package railfence;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 *
 * @author FPTSHOP
 */
public class RailFence {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SocketException, IOException {
        // TODO code application logic here
        // khoi tao socket
        System.out.println("-----------------------SERVER-----------------------");
        DatagramSocket server = new DatagramSocket(9999);
        System.out.println("Server dang hoat dong");
        // nhan mang ma hoa
        byte[] railFenceInput = new byte[256];
        DatagramPacket railFenceIn = new DatagramPacket(railFenceInput, railFenceInput.length);
        server.receive(railFenceIn);
        String railFence = new String(railFenceIn.getData(),0,railFenceIn.getLength());
        // nhan khoa 
        //System.out.println(railFence);
        byte[] keyInput = new byte[256];
        DatagramPacket keyIn = new DatagramPacket(keyInput, keyInput.length);
        server.receive(keyIn);
        int key = Integer.parseInt(new String(keyIn.getData(),0,keyIn.getLength()));
        System.out.println("Bang ma da nhan: " + railFence);
        // end code
        String banro=DeRailFenceCipher(railFence,key);
        System.out.println("Bang ro: " + banro);
        byte[] soKyTu = new byte[256];
        soKyTu=DemKyTu(banro).getBytes();
        DatagramPacket guiSoKyTu = new DatagramPacket(soKyTu, soKyTu.length,keyIn.getAddress(),keyIn.getPort());
        server.send(guiSoKyTu);
    }
    
    
    // code giai ma
    public static String DeRailFenceCipher(String mess, int key){
        String result = "";
        // check duong di cua phan tu
        boolean check = false;
        int row = key;
        int i =0;
        int col = mess.length();
        // klhoi tao mang danh dau ky tu 
        char arr[][] = new char[row][col];
        for(int j=0;j<col;j++){
            if(i==0 || i==key-1){
                check=!check;
            }
            arr[i][j] = '*';
            if(check)
                i++;
            else
                i--;
        }
        // ghi nhung ky tu trong bang ma
        int index =0;
        for(int a=0;a<row;a++){
            for(int j = 0 ; j<col;j++){
                
                if(arr[a][j] == '*' && index<col)
                    arr[a][j] = mess.charAt(index++);
                //System.out.print(arr[a][j] + "[]");
            }
            //System.out.println();
        }
        i=0;
        check =false;
        for(int j=0;j<col;j++){
            if(i==0 || i==key-1){
                check=!check;
            }
            result+=arr[i][j];
            if(check)
                i++;
            else
                i--;
        }
        //System.out.println(result);
     return result;
    }
    
    // tim ky tu nhieu thu 2 trong mang
    static String DemKyTu(String str)
    {
        // tao list chua arrr
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<Integer> number  = new ArrayList<Integer>();
        // lay nhung ky tu co trong chuoi khong lap lai
        for(int i =0;i<str.length();i++){
        if(!list.contains(String.valueOf(str.charAt(i)).toLowerCase()))
        {
            list.add(String.valueOf(str.charAt(i)).toLowerCase());
            number.add(1);
        }
        }
        // dem so lan xuat hien trong chuoi
        for(int i =0 ; i< list.size();i++){
            int sum=0;
            for(int j = 0 ; j<str.length();j++){
                if(list.get(i).equalsIgnoreCase(String.valueOf(str.charAt(j))))
                    sum+=1;
            }
            number.set(i, sum);
        }
        

         //sap xep chuoi
       for(int i=0;i<number.size()-1;i++){
        for (int j =i+1; j<number.size();j++){
            if(number.get(i)<number.get(j)){
                int temp = number.get(i);
                String tam= list.get(i);
                // hoan doi vi tri cua mang so lan xuat hien
                number.set(i,number.get(j));
                number.set(j,temp);
                // hoan doi gia tri xuat hien
                list.set(i, list.get(j));
                list.set(j, tam);
            }
        }
       }
       //System.out.println(list);
        //System.out.println(number);
        // lay phan tu xuat hien nhieu thu 2 trong mang
        int solanXuatHien = number.get(0);
        for(int i=0;i<number.size();i++){
            if(number.get(i)<solanXuatHien){
                solanXuatHien = number.get(i);
                break;
            }
        }
        String result="";
        for (int i=0;i<number.size();i++){
            if(number.get(i)== solanXuatHien){
                result+=","+list.get(i);
            }
        }
        result+="\n So lan xuat hien la: "+ solanXuatHien;
        //System.out.println(result.substring(1));
        return  result.substring(1);
    }
}
