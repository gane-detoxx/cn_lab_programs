import java.io.*;
import java.net.*;

public class FileTransferServer {

    public static void main(String[] args) throws Exception {

        // Initialize Sockets
        ServerSocket sscoket = new ServerSocket(5000);
        Socket socket = sscoket.accept();

        // The InetAddress specification
        InetAddress IA = InetAddress.getByName("localhost");

        // Specify the file
        File file = new File("./data1.txt");
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);

        // Get socket's output stream
        OutputStream os = socket.getOutputStream();

        // Read File Contents into contents array byte[] contents;
        long fileLength = file.length();
        long current = 0;

        long start = System.nanoTime();
        while (current != fileLength) {
            int size = 10000;
            if (fileLength - current >= size) {
                current += size;
            } else {
                size = (int) (fileLength - current);
                current = fileLength;
            }
            byte[] contents = new byte[size];
            bis.read(contents, 0, size);
            os.write(contents);
            System.out.print("Sending file ... " + (current * 100) / fileLength + "% complete!");
        }

        os.flush();

        // File transfer done. Close the socket connection! socket.close();
        sscoket.close();
        System.out.println("File sent succesfully!");
    }
}
