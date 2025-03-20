# UDP-Client-Server
Sure! Let’s go through your **UDP Server code** **line by line** to understand its functionality.

---

## **Code Explanation**

### **1. Importing Required Libraries**
```java
import java.io.*;
import java.net.*;
```
- **`java.io.*`**: This package provides classes for input and output operations (though it's not explicitly used here).
- **`java.net.*`**: This package provides classes for network communication, including UDP (`DatagramSocket`, `DatagramPacket`).

---

### **2. Declaring Class and Variables**
```java
public class UDPServer {
    private static final int PORT = 6060;
    private static DatagramSocket dgramSocket;
    private static DatagramPacket inPacket, outPacket;
    private static byte[] buffer = new byte[1024];
}
```
- **`UDPServer`**: The class that defines the UDP server.
- **`PORT`**: A constant integer (6060) that specifies the port number on which the server listens for incoming data.
- **`dgramSocket`**: A `DatagramSocket` used to send and receive UDP packets.
- **`inPacket` and `outPacket`**: These represent UDP packets for receiving and sending data.
- **`buffer`**: A byte array of size 1024 (1 KB) to temporarily store received data.

---

### **3. Server Initialization**
```java
public static void main(String[] args) {
    System.out.println("Opening Port...");
```
- The `main` method is the entry point of the program.
- **`System.out.println("Opening Port...");`**: Prints a message indicating that the server is about to start.

---

### **4. Creating the Datagram Socket**
```java
try {
    dgramSocket = new DatagramSocket(PORT);
    System.out.println("UDP Server is running on port " + PORT);
```
- **`new DatagramSocket(PORT);`**: Binds the server to port **6060** so it can listen for incoming UDP packets.
- **`System.out.println(...)`**: Confirms that the server is up and running.

---

### **5. Entering an Infinite Loop to Handle Requests**
```java
while (true) {
```
- The `while (true)` loop makes the server **run indefinitely**, continuously waiting for new messages from clients.

---

### **6. Receiving Data from Client**
```java
    inPacket = new DatagramPacket(buffer, buffer.length);
    dgramSocket.receive(inPacket);
```
- **`new DatagramPacket(buffer, buffer.length);`**: Creates a `DatagramPacket` to receive incoming data. The data will be stored in the `buffer` array.
- **`dgramSocket.receive(inPacket);`**: Blocks execution until a packet is received. The received data is stored in `inPacket`.

---

### **7. Extracting the Received Message**
```java
    String received = new String(inPacket.getData(), 0, inPacket.getLength());
    System.out.println("Client: " + received);
```
- **`inPacket.getData()`**: Retrieves the raw byte data from the received packet.
- **`new String(..., 0, inPacket.getLength())`**: Converts the received byte data into a readable `String`, trimming it to the actual message length.
- **`System.out.println("Client: " + received);`**: Prints the message received from the client.

---

### **8. Sending a Response to the Client**
```java
    String response = "Message received: " + received;
    byte[] responseData = response.getBytes();
```
- **`response`**: Creates a reply message to confirm receipt of the client's message.
- **`responseData = response.getBytes();`**: Converts the response string into a byte array for transmission.

---

### **9. Sending the Response Packet**
```java
    InetAddress clientAddress = inPacket.getAddress();
    int clientPort = inPacket.getPort();
```
- **`inPacket.getAddress();`**: Retrieves the client's **IP address** from the received packet.
- **`inPacket.getPort();`**: Retrieves the **port number** from which the client sent the packet.

```java
    outPacket = new DatagramPacket(responseData, responseData.length, clientAddress, clientPort);
    dgramSocket.send(outPacket);
```
- **`new DatagramPacket(responseData, responseData.length, clientAddress, clientPort);`**  
  - Creates a new packet (`outPacket`) containing:
    - The response data (`responseData`).
    - The **client's IP address and port** (so the message is sent back to the right client).
- **`dgramSocket.send(outPacket);`**  
  - Sends the response back to the client.

---

### **10. Handling Exceptions**
```java
} catch (IOException e) {
    System.out.println("Error: " + e.getMessage());
}
```
- **Catches any `IOException`** that might occur during socket operations (e.g., network issues).
- **Prints an error message** if something goes wrong.

---

### **11. Closing the Socket When Server Shuts Down**
```java
finally {
    if (dgramSocket != null && !dgramSocket.isClosed()) {
        dgramSocket.close();
    }
}
```
- The `finally` block ensures that **even if an error occurs, the socket is properly closed** to free up the port.
- **Checks if `dgramSocket` is not null and is open before closing it**.

---

## **How It Works in Simple Terms**
1. The server **starts** and listens for incoming UDP packets on **port 6060**.
2. It **waits** until it **receives a message** from a client.
3. Once a packet arrives, it **extracts the message** and **prints it**.
4. The server **prepares a response** message (`Message received: <client message>`).
5. The response is **sent back** to the same client.
6. The server **loops back** to wait for another message, repeating the process indefinitely.

---

## **Example Run**
**Server Console Output:**
```
Opening Port...
UDP Server is running on port 6060
Client: Hello, Server!
Client: How are you?
Client: Goodbye!
```

If the client sends `"Hello, Server!"`, the server will send back `"Message received: Hello, Server!"`.

---

Let's go through your **UDP Client code** **line by line** to understand its functionality.

---

## **Code Explanation**

### **1. Import Required Libraries**
```java
import java.io.*;
import java.net.*;
```
- **`java.io.*`** → Provides classes for input and output operations (`BufferedReader`, `InputStreamReader`).
- **`java.net.*`** → Provides classes for networking (`DatagramSocket`, `DatagramPacket`, `InetAddress`).

---

### **2. Defining the Class and Constants**
```java
public class UDPClient {
    private static final String SERVER_ADDRESS = "localhost"; // Change this to server IP if needed
    private static final int SERVER_PORT = 6060;
}
```
- **`UDPClient`** → Defines the main class for the UDP client.
- **`SERVER_ADDRESS = "localhost"`** → Specifies the **server's IP address** (currently `localhost`, meaning the same machine).
- **`SERVER_PORT = 6060`** → Specifies the **server's port number**, which should match the port the **UDP server** is listening on.

---

### **3. Main Method - Entry Point**
```java
public static void main(String[] args) {
    DatagramSocket socket = null;
```
- **`main`** → Entry point of the program.
- **`DatagramSocket socket = null;`** → Initializes a **UDP socket**, which will be used to send and receive messages.

---

### **4. Creating the Datagram Socket**
```java
try {
    socket = new DatagramSocket();
    BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
```
- **`new DatagramSocket();`** → Creates a **UDP socket** (automatically binds to an available port).
- **`BufferedReader userInput`** → Allows the user to input messages via the command line.

---

### **5. Infinite Loop for Sending Messages**
```java
while (true) {
    System.out.print("Enter message (or type 'exit' to quit): ");
    String message = userInput.readLine();
```
- **Infinite `while (true)` loop** → Keeps running until the user **exits**.
- **`System.out.print(...)`** → Prompts the user to enter a message.
- **`userInput.readLine();`** → Reads the message typed by the user.

---

### **6. Checking for Exit Condition**
```java
if (message.equalsIgnoreCase("exit")) {
    break;
}
```
- **`message.equalsIgnoreCase("exit")`** → If the user types `"exit"`, the client **breaks** out of the loop and stops running.

---

### **7. Sending Data to the UDP Server**
```java
// Convert message to bytes
byte[] sendData = message.getBytes();
InetAddress serverAddress = InetAddress.getByName(SERVER_ADDRESS);
```
- **`message.getBytes();`** → Converts the message into a **byte array** (since UDP sends raw bytes).
- **`InetAddress.getByName(SERVER_ADDRESS);`** → Converts the server address (`localhost`) into an **IP address object**.

```java
DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, SERVER_PORT);
socket.send(sendPacket);
```
- **`new DatagramPacket(sendData, sendData.length, serverAddress, SERVER_PORT);`**  
  - Creates a **DatagramPacket** containing:
    - The **message bytes**.
    - The **server’s IP address**.
    - The **server’s port number**.
- **`socket.send(sendPacket);`** → Sends the packet to the **UDP server**.

---

### **8. Receiving Response from Server**
```java
// Prepare buffer for response
byte[] buffer = new byte[1024];
DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
socket.receive(receivePacket);
```
- **Creates a buffer (`byte[1024]`)** to store the incoming response.
- **`new DatagramPacket(buffer, buffer.length);`** → Creates a `DatagramPacket` to receive the server's response.
- **`socket.receive(receivePacket);`** → **Blocks** execution **until** a response is received from the server.

---

### **9. Processing the Server’s Response**
```java
String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
System.out.println("Server response: " + response);
```
- **`receivePacket.getData()`** → Retrieves the **byte data** from the response packet.
- **`new String(..., 0, receivePacket.getLength())`** → Converts the byte data into a **String**.
- **`System.out.println("Server response: " + response);`** → Displays the server's response.

---

### **10. Handling Exceptions**
```java
} catch (IOException e) {
    System.out.println("Error: " + e.getMessage());
}
```
- **`catch (IOException e)`** → Catches any **input/output errors** (e.g., network failure).
- **Prints an error message** if something goes wrong.

---

### **11. Closing the Socket (Finally Block)**
```java
finally {
    if (socket != null && !socket.isClosed()) {
        socket.close();
    }
}
```
- The **`finally` block** ensures that the **socket is always closed** when the program exits.
- **`socket.close();`** → Releases the **UDP port** and **cleans up resources**.

---

## **How the UDP Client Works**
1. The client **starts** and asks the user for input.
2. The user **enters a message**, and the client **sends it** as a **UDP packet** to the server.
3. The client **waits for a response** from the server.
4. Once the **server replies**, the client **displays the response**.
5. The process **repeats** until the user **types "exit"**.
6. When `"exit"` is entered, the client **closes the connection and exits**.

---

## **Example Run**
### **Client Console Output**
```
Enter message (or type 'exit' to quit): Hello Server!
Server response: Message received: Hello Server!

Enter message (or type 'exit' to quit): How are you?
Server response: Message received: How are you?

Enter message (or type 'exit' to quit): exit
```
- The client sends **"Hello Server!"** and gets back **"Message received: Hello Server!"**.
- The client sends **"How are you?"** and gets back **"Message received: How are you?"**.
- When the user types **"exit"**, the program **terminates**.

---

## **Summary**
| Step | What Happens? |
|------|--------------|
| 1️⃣ | Client asks user for a message |
| 2️⃣ | User enters a message |
| 3️⃣ | Client sends message to server using UDP |
| 4️⃣ | Client waits for response |
| 5️⃣ | Server responds |
| 6️⃣ | Client displays response |
| 7️⃣ | If user types `"exit"`, client closes socket and exits |

This **UDP Client** is simple, fast, and **does not require a persistent connection** like TCP. 🚀
