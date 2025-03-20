import java.io.*;
import java.net.*;

public class UDPClient {
    private static final String SERVER_ADDRESS = "localhost"; // Change this to server IP if needed
    private static final int SERVER_PORT = 6060;

    public static void main(String[] args) {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.print("Enter message (or type 'exit' to quit): ");
                String message = userInput.readLine();

                if (message.equalsIgnoreCase("exit")) {
                    break;
                }

                // Send data to server
                byte[] sendData = message.getBytes();
                InetAddress serverAddress = InetAddress.getByName(SERVER_ADDRESS);
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, SERVER_PORT);
                socket.send(sendPacket);

                // Receive response from server
                byte[] buffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(receivePacket);

                String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Server response: " + response);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}
