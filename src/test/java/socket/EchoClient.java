package socket;

import java.io.*;
import java.net.Socket;

/**
 * Created by scott on 2017/3/9.
 */
public class EchoClient {
    private String host = "localhost";
    private int port = 8000;
    private Socket socket;

    public EchoClient() throws IOException{
        socket = new Socket(host,port);
    }
    public BufferedReader getReader(Socket socket) throws IOException{
        InputStream in = socket.getInputStream();
        return new BufferedReader(new InputStreamReader(in));
    }

    public PrintWriter getWriter(Socket socket) throws IOException{
        OutputStream out = socket.getOutputStream();
        return new PrintWriter(out, true);
    }
    public void talk() throws IOException{
        try{
            BufferedReader br = getReader(socket);
            PrintWriter pw = getWriter(socket);
            BufferedReader local = new BufferedReader(new InputStreamReader(System.in));
            String msg = null;
            while((msg = local.readLine()) != null){
                pw.println(msg);
                System.out.println(br.readLine());

                if(msg.equals("bye")){
                    break;
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        } finally {
            try{
                if(socket != null){
                    socket.close();
                }
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException{
        new EchoClient().talk();
    }
}
