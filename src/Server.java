import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {

    /**
     * Application method to run the server runs in an infinite loop
     * listening on port 5050.  When a connection is requested, it
     * spawns a new thread to do the servicing and immediately returns
     * to listening.  The server keeps a unique client number for each
     * client that connects just to show interesting logging
     * messages.  It is certainly not necessary to do this.
     */
    public static void main(String[] args) throws Exception {
        System.out.println("The capitalization server is running.");
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(5050);
        try {
            while (true) {
                new Capitalizer(listener.accept(), clientNumber++).start();
            }
        } finally {
            listener.close();
        }
    }

    /**
     * A private thread to handle capitalization requests on a particular
     * socket.  The client terminates the dialogue by sending a single line
     * containing only a period.
     */
    private static class Capitalizer extends Thread {
        private Socket socket;
        private int clientNumber;

        public Capitalizer(Socket socket, int clientNumber) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            log("New connection with client# " + clientNumber + " at " + socket);
        }

        /**
         * Services this thread's client by first sending the
         * client a welcome message then repeatedly reading strings
         * and sending back the capitalized version of the string.
         */
        @SuppressWarnings("unchecked")
		public void run() {
            try {
            	ObjectInputStream dataIn = new ObjectInputStream(socket.getInputStream());
            	PrintWriter dataOut = new PrintWriter(socket.getOutputStream());
            	
            	dataOut.println("Hello");
                // Get objects from client
                while (true) {
                    HashMap<String, String> formData = new HashMap<String, String>();
                    formData = (HashMap<String, String>)dataIn.readObject();
                    
                    for (HashMap.Entry<String, String> entry : formData.entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();
                        log (key + ": " + value);
                    }
                    
                    if (formData.get("dbAction").equals("add"))
                    	dbActions.dbInsert(formData);
                    
                    if (formData.get("dbAction").equals("update"))
                    		dbActions.dbUpdate(formData.get("userCode"), formData);

                    if (formData.get("dbAction").equals("delete"))
                    	dbActions.dbDelete(formData.get("userCode"));
                    
                }
            } catch (IOException e) {
                log("Error handling client# " + clientNumber + ": " + e);
            } catch (ClassNotFoundException e) {
            	log("Error handling client# " + clientNumber + ": " + e);
			} finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    log("Couldn't close a socket, what's going on?");
                }
                log("Connection with client# " + clientNumber + " closed");
            }
        }

        /**
         * Logs a simple message.  In this case we just write the
         * message to the server applications standard output.
         */
        private void log(String message) {
            System.out.println(message);
        }
    }
}