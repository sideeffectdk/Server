package pack;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;


/**
 * Created by MED3-4 - 7/12/2015.
 */
public class EchoClient {
    private String hostName;
    private int portNumber;
    public static void main(String[] args) throws IOException{
        new EchoClient();
    }
    public EchoClient(){
        this.hostName="localhost";
        this.portNumber=7777;
        try{
            client(new Socket(hostName,portNumber));

        }
        catch (IOException e){
            e.fillInStackTrace();
        }
    }
    public void client(Socket so){
        OutputStream out;
        InputStream in=null;
        try {
            out=so.getOutputStream();
            in=so.getInputStream();
            //getting image
            JFileChooser file=new JFileChooser();
            file.setDialogTitle("Select");
            int action=file.showOpenDialog(null);
            if(action!=JFileChooser.APPROVE_OPTION)
                System.exit(0);
            File fi=file.getSelectedFile();
            BufferedImage bi=ImageIO.read(fi);
            ByteArrayOutputStream bo=new ByteArrayOutputStream();
            ImageIO.write(bi,"jpg",bo);
            byte[] size= ByteBuffer.allocate(4).putInt(bo.size()).array();
            System.out.println("Sending images");
            out.write(size);
            out.write(bo.toByteArray());;
            out.flush();

            byte[] sizeAr=new byte[4];
            in.read(sizeAr);
            System.out.println("Getting image");
            int cSize=ByteBuffer.wrap(sizeAr).asIntBuffer().get();
            byte[] imageArray=new byte[cSize];
            int sizerecv=0;
            int sizerecv2=0;

            byte[] imageArray2=new byte[cSize];
            int place=0;
            while (sizerecv<cSize){
                sizerecv2=in.read(imageArray);
                for(int i=0;i<sizerecv2;i++)
                {
                    imageArray2[i+place]=imageArray[i];
                }
                place = place +sizerecv2;
                sizerecv=sizerecv2+sizerecv;
            }
            System.out.println("Got the image");
            BufferedImage  cBufimg=null;
            try {
                cBufimg=ImageIO.read(new ByteArrayInputStream(imageArray2));
            }
            catch (EOFException e){
                System.out.println();
            }
            ImageIO.write(cBufimg,"jpg",new File("converted.jpg"));
            }
            catch (IOException e){
                e.printStackTrace();
            }

        finally {
            try{
                in.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
