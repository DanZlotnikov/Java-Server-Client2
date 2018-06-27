import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;
import javax.swing.*;


public class Client {

    private BufferedReader dataIn;
    private ObjectOutputStream dataOut;
    private JTextField dataField = new JTextField(40);
    private JTextArea messageArea = new JTextArea(8, 60);
    Socket socket;

    /**
     * Constructs the client by laying out the GUI and registering a
     * listener with the textfield so that pressing Enter in the
     * listener sends the textfield contents to the server.
     */
    public Client() { 	
    	final JFrame frame=new JFrame("Mr. Java"); 
    	
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setExtendedState(frame.getExtendedState() | JFrame.NORMAL);
        final JTextField textfield=new JTextField();  
        textfield.setBounds(50,50, 150,20); 
        
        JButton addUserButton=new JButton("Add User");  
        addUserButton.setBounds(20,50,120,30);  
        addUserButton.addActionListener(new ActionListener(){  
    public void actionPerformed(ActionEvent e){  
                AddUserScreen(frame);
            }  
        });  
        
        JButton updateUserButton=new JButton("Update User");  
        updateUserButton.setBounds(160,50,120,30);  
        updateUserButton.addActionListener(new ActionListener(){  
    public void actionPerformed(ActionEvent e){  
    	 		UpdateUserScreen(frame);
            }  
        });  
        
        JButton deleteUserButton=new JButton("Delete User");  
        deleteUserButton.setBounds(300,50,120,30);  
        deleteUserButton.addActionListener(new ActionListener(){  
    public void actionPerformed(ActionEvent e){  
    			DeleteUserScreen(frame); 
            }  
        });  
        
        frame.add(addUserButton);  
        frame.add(updateUserButton);
        frame.add(deleteUserButton);
        frame.setSize(460,200);  
        frame.setLayout(null); 
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);   
    }

    public void AddUserScreen(JFrame parentFrame)
    {
    	JFrame frame=new JFrame("Add User");  
    	frame.setDefaultCloseOperation(0);
    	
    	parentFrame.setEnabled(false);
        HashMap<String, String> formData = new HashMap<String, String>();
        
    	
    	final JLabel usernameHebrewLabel = new JLabel("Username (Hebrew): ");
    	usernameHebrewLabel.setBounds(10,20, 150,20);
        final JTextField usernameHebrew=new JTextField();  
        usernameHebrew.setBounds(150, 20, 150,20);  
        
        final JLabel usernameEnglishLabel = new JLabel("Username (English): ");
        usernameEnglishLabel.setBounds(10,50, 150,20);
        final JTextField usernameEnglish=new JTextField();  
        usernameEnglish.setBounds(150, 50, 150,20);
        
        final JLabel cityLabel = new JLabel("City: ");
        cityLabel.setBounds(10,80, 150,20);
        final JTextField city=new JTextField();  
        city.setBounds(150,80, 150,20);
        
        final JLabel streetLabel = new JLabel("Street: ");
        streetLabel.setBounds(10,110, 150,20);
        final JTextField street=new JTextField();  
        street.setBounds(150,110, 150,20);
        
        final JLabel numberLabel = new JLabel("House no.: ");
        numberLabel.setBounds(10,140, 150,20);
        final JTextField number=new JTextField();  
        number.setBounds(150,140, 150,20);
        
        final JLabel phoneLabel = new JLabel("Phone: ");
        phoneLabel.setBounds(10,170, 150,20);
        final JTextField phone=new JTextField();  
        phone.setBounds(150,170, 150,20);
        
        
        String[] choices = { "Active","Inactive"};
        final JLabel activeCodesLabel = new JLabel("Status Code: ");
        activeCodesLabel.setBounds(10,200, 150,20);
        final JComboBox<String> activeCodes = new JComboBox<String>(choices);
        activeCodes.setBounds(150,200, 150,20);
        
        JButton addUserButton=new JButton("Add User");  
        addUserButton.setBounds(10,250,150,30);  
        addUserButton.addActionListener(new ActionListener(){  
        	public void actionPerformed(ActionEvent e){  
    			formData.put("usernameHebrew", usernameHebrew.getText());
                formData.put("usernameEnglish", usernameEnglish.getText());
                formData.put("city", city.getText());
                formData.put("street", street.getText());
                formData.put("stNumber", number.getText());
                formData.put("phone", phone.getText());
                formData.put("activeCode", "1");
                formData.put("dbAction", "add");
                
                try {
					dataOut.writeObject(formData);
					dataOut.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
                parentFrame.setEnabled(true); 
                frame.dispose();
           	}  
        }); 
        
        JButton cancelButton=new JButton("Cancel");  
        cancelButton.setBounds(170,250,150,30); 
        cancelButton.addActionListener(new ActionListener(){  
        	public void actionPerformed(ActionEvent e){  
        		parentFrame.setEnabled(true); 
                frame.dispose();
           	}  
        }); 
        
        frame.add(addUserButton);frame.add(cancelButton);frame.add(usernameHebrew);frame.add(usernameEnglish);frame.add(city);frame.add(street);frame.add(number);frame.add(phone);frame.add(activeCodes);
        frame.add(usernameHebrewLabel);frame.add(usernameEnglishLabel);frame.add(cityLabel);frame.add(streetLabel);frame.add(numberLabel);frame.add(phoneLabel);frame.add(activeCodesLabel);   
        frame.setSize(400,400);  
        frame.setLayout(null); 
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);  
        
    }
    
    public void UpdateUserScreen(JFrame parentFrame)
    {
    	JFrame frame=new JFrame("Update User");
    	frame.setDefaultCloseOperation(0);
    	
    	parentFrame.setEnabled(false);
        HashMap<String, String> formData = new HashMap<String, String>();
    	
        final JLabel userCodeLabel = new JLabel("User Code: ");
    	userCodeLabel.setBounds(10,20, 150,20);
        final JTextField userCode=new JTextField();  
        userCode.setBounds(150, 20, 150,20);  
        
    	final JLabel usernameHebrewLabel = new JLabel("Username (Hebrew): ");
    	usernameHebrewLabel.setBounds(10,50, 150,20);
        final JTextField usernameHebrew=new JTextField();  
        usernameHebrew.setBounds(150, 50, 150,20);  
        
        final JLabel usernameEnglishLabel = new JLabel("Username (English): ");
        usernameEnglishLabel.setBounds(10,80, 150,20);
        final JTextField usernameEnglish=new JTextField();  
        usernameEnglish.setBounds(150, 80, 150,20);
        
        final JLabel cityLabel = new JLabel("City: ");
        cityLabel.setBounds(10,110, 150,20);
        final JTextField city=new JTextField();  
        city.setBounds(150,110, 150,20);
        
        final JLabel streetLabel = new JLabel("Street: ");
        streetLabel.setBounds(10,140, 150,20);
        final JTextField street=new JTextField();  
        street.setBounds(150,140, 150,20);
        
        final JLabel numberLabel = new JLabel("House no.: ");
        numberLabel.setBounds(10,170, 150,20);
        final JTextField number=new JTextField();  
        number.setBounds(150,170, 150,20);
        
        final JLabel phoneLabel = new JLabel("Phone: ");
        phoneLabel.setBounds(10,200, 150,20);
        final JTextField phone=new JTextField();  
        phone.setBounds(150,200, 150,20);
        
        
        String[] choices = { "Active","Inactive"};
        final JLabel activeCodesLabel = new JLabel("Status Code: ");
        activeCodesLabel.setBounds(10,230, 150,20);
        final JComboBox<String> activeCodes = new JComboBox<String>(choices);
        activeCodes.setBounds(150,230, 150,20);
        
        JButton addUserButton=new JButton("Update User Data");  
        addUserButton.setBounds(10,280,150,30);  
        addUserButton.addActionListener(new ActionListener(){  
        	public void actionPerformed(ActionEvent e){  
        		formData.put("userCode", userCode.getText());
    			formData.put("usernameHebrew", usernameHebrew.getText());
                formData.put("usernameEnglish", usernameEnglish.getText());
                formData.put("city", city.getText());
                formData.put("street", street.getText());
                formData.put("stNumber", number.getText());
                formData.put("phone", phone.getText());
                formData.put("activeCode", "1");
                formData.put("dbAction", "update");
                
                try {
					dataOut.writeObject(formData);
					dataOut.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
                
                parentFrame.setEnabled(true); 
                frame.dispose();  
                
           	}  
        }); 
        
        JButton cancelButton=new JButton("Cancel");  
        cancelButton.setBounds(170,280,150,30); 
        cancelButton.addActionListener(new ActionListener(){  
        	public void actionPerformed(ActionEvent e){  
        		parentFrame.setEnabled(true); 
                frame.dispose();
           	}  
        }); 
        
        frame.add(cancelButton);frame.add(addUserButton);frame.add(usernameHebrew);frame.add(usernameEnglish);frame.add(city);frame.add(street);frame.add(number);frame.add(phone);frame.add(activeCodes);frame.add(userCode);
        frame.add(usernameHebrewLabel);frame.add(usernameEnglishLabel);frame.add(cityLabel);frame.add(streetLabel);frame.add(numberLabel);frame.add(phoneLabel);frame.add(activeCodesLabel);frame.add(userCodeLabel);    
        frame.setSize(400,400);  
        frame.setLayout(null); 
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);  
        
    }
    
    public void DeleteUserScreen(JFrame parentFrame)
    {	
    	JFrame frame=new JFrame("Delete User");  
    	frame.setDefaultCloseOperation(0);
    	
    	parentFrame.setEnabled(false); 
    	HashMap<String, String> formData = new HashMap<String, String>();
    	
    	final JLabel userCodeLabel = new JLabel("User Code: ");
    	userCodeLabel.setBounds(10,20, 150,20);
        final JTextField userCode=new JTextField();  
        userCode.setBounds(100, 20, 150,20);  
        
    	
        JButton b=new JButton("Delete User");  
        b.setBounds(10,70,150,30);  
        b.addActionListener(new ActionListener(){  
    public void actionPerformed(ActionEvent e){  
    	formData.put("userCode", userCode.getText());
    	formData.put("dbAction", "delete");
    	
    	try {
			dataOut.writeObject(formData);
			dataOut.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    	parentFrame.setEnabled(true); 
        frame.dispose();
            }  
        });  
        
        JButton cancelButton=new JButton("Cancel");  
        cancelButton.setBounds(170,70,150,30); 
        cancelButton.addActionListener(new ActionListener(){  
        	public void actionPerformed(ActionEvent e){  
        		parentFrame.setEnabled(true); 
                frame.dispose();
           	}  
        }); 
        
        frame.add(cancelButton);frame.add(b);frame.add(userCode);frame.add(userCodeLabel); 
        frame.setSize(400, 150);  
        frame.setLayout(null); 
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);  
    }
    
    public void connectToServer() throws IOException {

        String serverAddress = "127.0.0.1";

        // Make connection and initialize streams
        this.socket = new Socket(serverAddress, 5050);
        dataIn = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        dataOut =  new ObjectOutputStream(socket.getOutputStream());
        
        System.out.println(dataIn.readLine());
    }


    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.connectToServer();
    }
}