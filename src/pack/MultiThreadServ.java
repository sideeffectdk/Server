package pack;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;


/**
 * Created by sorin on 12/3/2015.
 *
 */

public class MultiThreadServ {



    ServerSocket serv;
    public MultiThreadServ(){
        try{
            ServerSocket serv= new ServerSocket(7777);
            System.out.println("Listening");
            while (true) {
                Socket so;
                so=serv.accept();
                Client cl=new Client(so);

            }
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }
    public static void main(String args[]) throws Exception{
        new MultiThreadServ();
    }
    public class Client extends Thread implements Runnable{
        Socket so;
        public Client(Socket so){
            this.so=so;
            this.start();
        }
        @Override
        public void run(){
            try {
                OutputStream out = so.getOutputStream();
                InputStream in = so.getInputStream();
                int kernelChoice=3;
                byte[] imdim = new byte[4];
                in.read(imdim);
                int size = ByteBuffer.wrap(imdim).asIntBuffer().get();

                //Getting image from the client
                System.out.println("geting image");
                byte[] imageAr = new byte[size];
               int sizerecv = 0;
                int sizerecv2 = 0;
                byte[] imageAr2 = new byte[size];
                int place = 0;

                while (sizerecv < size) {
                    sizerecv2 = in.read(imageAr);
                    for (int i = 0; i < sizerecv2; i++) {
                        imageAr2[i + place] = imageAr[i];
                    }
                    place = place + sizerecv2;
                    System.out.println(imageAr[1]);
                    System.out.println(imageAr2[1]);
                    sizerecv = sizerecv + sizerecv2;
                }
                System.out.println("converting bytes to image");
                //Bytes-image
                BufferedImage bufimg = null;
                try {
                    System.out.println("init new byte");
                    bufimg = ImageIO.read(new ByteArrayInputStream(imageAr2));

                } catch (EOFException ex) {
                    System.out.println("Image read");
                }
                //converting program
                System.out.println("start to convert");
            //BufferedImage conv= Converter.convert(bufimg,kernelChoice);
            //sending to client
                System.out.println("send to the client");
            ByteArrayOutputStream byteO=new ByteArrayOutputStream();

            byte[] cSize=ByteBuffer.allocate(4).putInt(byteO.size()).array();
            out.write(cSize);
            out.write(byteO.toByteArray());
            out.flush();
            in.close();
            out.close();
            so.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

    }
}
