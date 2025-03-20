import java.io.*; 
import java.net.*; 

//declaring class variables
public class UDPServer{
    private static final int PORT = 6060; 
    private static DatagramSocket dgramSocket; 
    private static DatagramPacket inPacket, outPacket; 
    private static byte[] buffer = new byte[1024]; 
    public static void main(String[] args) {
        System.out.println("Opening Port..."); 
        try {
            dgramSocket = new DatagramSocket(PORT); 
            System.out.println("UDP Server is running on port " + PORT); 

           
            while (true) { 
                // Receive packet
                inPacket = new DatagramPacket(buffer, buffer.length); 
                dgramSocket.receive(inPacket); 

                // Extract message
                String received = new String(inPacket.getData(), 0, inPacket.getLength());
                System.out.println("Client: " + received);

                // Send response
                String response = "Message received: " + received;
                byte[] responseData = response.getBytes();

                InetAddress clientAddress = inPacket.getAddress();
                int clientPort = inPacket.getPort();

                outPacket = new DatagramPacket(responseData, responseData.length, clientAddress, clientPort);
                dgramSocket.send(outPacket);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            if (dgramSocket != null && !dgramSocket.isClosed()) {
                dgramSocket.close();
            }
        }
    }
}
