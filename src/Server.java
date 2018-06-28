import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Server {

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

    private static class Capitalizer extends Thread {
        private Socket socket;
        private int clientNumber;

        public Capitalizer(Socket socket, int clientNumber) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            log("New connection with client# " + clientNumber + " at " + socket);
        }

      
        @SuppressWarnings("unchecked")
		public void run() {
            try {
            	ObjectInputStream dataIn = new ObjectInputStream(socket.getInputStream());
            	PrintWriter dataOut = new PrintWriter(socket.getOutputStream());
            	ObjectOutputStream userListOut = new ObjectOutputStream(socket.getOutputStream());
            	
            	ArrayList<String[]> usersArr = new ArrayList(); 
            	
                // Get objects from client
                while (true) {
                    HashMap<String, String> formData = new HashMap<String, String>();
                    formData = (HashMap<String, String>)dataIn.readObject();
                    
                    if (formData.get("dbAction").equals("add"))
                    {
                    	String response = dbActions.dbInsert(formData);
                    	dataOut.println(response);
                    	dataOut.flush();
                    }
                    
                    if (formData.get("dbAction").equals("update")) 
                    {
                    	String response = dbActions.dbUpdate(formData.get("userCode"), formData);
                    	dataOut.println(response);
                    	dataOut.flush();
                    }
                    if (formData.get("dbAction").equals("delete"))
                    {
                    	String response = dbActions.dbDelete(formData.get("userCode"));
                    	dataOut.println(response);
                    	dataOut.flush();
                    }
                    if (formData.get("dbAction").equals("get"))
                    {
                    	usersArr = dbActions.dbSelect();
                    	userListOut.writeObject(usersArr);
                    }
                    
                    if (formData.get("dbAction").equals("search"))
                    {
                    	String[] user = new String[9];
                    	user = dbActions.dbSearchUser(formData.get("userCode").toString());
                    	userListOut.writeObject(user);
                    }
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