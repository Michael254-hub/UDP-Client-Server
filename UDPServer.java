import java.io.*; //package provides classes for input-output operations
import java.net.*; //package provides classes for network communication, including UDP (DatagramSocket, DatagramPacket)

//declaring class variables
public class UDPServer{
    private static final int PORT = 6060; //constant interger that specifies the port number on which the server listens for incoming data.
    private static DatagramSocket dgramSocket; //used to send and receive UDP packets.
    private static DatagramPacket inPacket, outPacket; //representation of UDP packets receiving and sending data
    private static byte[] buffer = new byte[1024]; //temporary storage of data

    public static void main(String[] args) {
        System.out.println("Opening Port..."); //prints out the message indicating the server is about to start
        try {
            dgramSocket = new DatagramSocket(PORT); //binds the server to port 6060 so it can listen for incoming UDP packets 
            System.out.println("UDP Server is running on port " + PORT); //confirms the server is up and running

            //the while (true) loop makes the server run indefinitely, continuously waiting for new messages for clients
            while (true) { 
                // Receive packet
                inPacket = new DatagramPacket(buffer, buffer.length); //creates a DatagramPacket to receive incoming data which is stored in the buffer array.
                dgramSocket.receive(inPacket); //blocks execution until a packet is received and the data stored in inPacket.

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
