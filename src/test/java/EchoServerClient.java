import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by scott on 2017/3/9.
 */
public class EchoServerClient {
    private int port = 8000;
    private ServerSocket serverSocket;

    public EchoServerClient() throws IOException{
        serverSocket = new ServerSocket(port);
        System.out.println("server is started!");
    }
    public String echo(String msg){
        return "echo: "+msg;
    }

    private PrintWriter getPrintWriter(Socket socket) throws IOException{
        OutputStream out = socket.getOutputStream();
        return new PrintWriter(out, true);
    }

    private BufferedReader getBufferedReader(Socket socket) throws IOException{
        InputStream in = socket.getInputStream();
        return new BufferedReader(new InputStreamReader(in));
    }

    public void service(){
        while(true){
            Socket socket = null;
            try{
                socket = serverSocket.accept();
                System.out.println("New connection accepted :" + socket.getInetAddress() + "," + socket.getPort());
                BufferedReader br = getBufferedReader(socket);
                PrintWriter pw = getPrintWriter(socket);

                String msg = null;
                while((msg = br.readLine()) != null){
                    System.out.println(msg);
                    pw.println(echo(msg));
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
    }

    public static void main(String[] args) throws IOException{
        new EchoServerClient().service();
    }
}
