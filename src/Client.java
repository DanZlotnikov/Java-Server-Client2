import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.*;
import javax.swing.*;


public class Client {

    private BufferedReader dataIn;
    private ObjectOutputStream dataOut;
    private ObjectInputStream serverUsersIn;
    private ArrayList<String[]> userList;
    private JTextField[] ids =new JTextField[5];  
    private JTextField[] hebrewNames =new JTextField[5];  
    private JTextField[] englishNames =new JTextField[5];  
    private JTextField[] cities =new JTextField[5];  
    private JTextField[] streets =new JTextField[5];  
    private JTextField[] stNumbers =new JTextField[5];  
    private JTextField[] phones =new JTextField[5];  
    private JTextField[] codes =new JTextField[5];  
    private JTextField[] dates =new JTextField[5];  
    
    private int lineCount = 0;
    Socket socket;


    public Client() { 	
    	final JFrame frame=new JFrame("Mr. Java"); 
    	
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setExtendedState(frame.getExtendedState() | JFrame.NORMAL);
    	
    	
        CreateUserList(frame);
        
    	// Add User Button
        JButton addUserButton=new JButton("Add User");  
        addUserButton.setBounds(400,250,120,30);  
        addUserButton.addActionListener(new ActionListener(){  
    public void actionPerformed(ActionEvent e){  
                AddUserScreen(frame);
            }  
        });  
        
        // Update User Button
        JButton updateUserButton=new JButton("Update User");  
        updateUserButton.setBounds(530,250,120,30);  
        updateUserButton.addActionListener(new ActionListener(){  
    public void actionPerformed(ActionEvent e){  
    	 		UpdateUserScreen(frame);
            }  
        });  
        
        // Delete User Button
        JButton deleteUserButton=new JButton("Delete User");  
        deleteUserButton.setBounds(660,250,120,30);  
        deleteUserButton.addActionListener(new ActionListener(){  
    public void actionPerformed(ActionEvent e){  
    			DeleteUserScreen(frame); 
            }  
        });  
        
      
        // Search User Button
        JButton searchUserButton=new JButton("Search User");  
        searchUserButton.setBounds(660,210,120,30);  
        searchUserButton.addActionListener(new ActionListener(){  
    public void actionPerformed(ActionEvent e){  
    			SearchUserScreen(frame); 
            }  
        });  

        // Get Users Button
        JButton getUsersButton=new JButton("Jump to Top");  
        getUsersButton.setBounds(400,210,120,30);  
        getUsersButton.addActionListener(new ActionListener(){  
    public void actionPerformed(ActionEvent e){  
    		PrintUsersList(0);
    		lineCount = 0;
            }  
        });  
        
        
        
        frame.add(addUserButton);
        frame.add(updateUserButton);
        frame.add(getUsersButton);
        frame.add(deleteUserButton);
        frame.add(searchUserButton);
        frame.setSize(1200,350);  
        frame.setLayout(null); 
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);   
    }

    @SuppressWarnings("unchecked")
	public void GetUserList()
    {
    	HashMap<String, String> formData = new HashMap<String, String>();
    	formData.put("dbAction", "get");
    	try {
    		dataOut.writeObject(formData);
			userList = (ArrayList<String[]>)serverUsersIn.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void CreateUserList(JFrame frame)
    {
    	
    	// ID
    	final JLabel idLabel = new JLabel("ID");
    	idLabel.setBounds(10,20, 100,20);
    	frame.add(idLabel);
    	
        ids =new JTextField[5];  
        
        for (int i = 0; i < 5; i++)
        {
        	ids[i] = new JTextField();  
        	ids[i].setBounds(10, 50 + (30 * i), 100, 20);
        	frame.add(ids[i]);
        }
        
        
        // Hebrew name
    	final JLabel hebrewNameLabel = new JLabel("Name (Hebrew)");
    	hebrewNameLabel.setBounds(130,20, 100,20);
    	frame.add(hebrewNameLabel);
    	
        hebrewNames =new JTextField[5];  
        
        for (int i = 0; i < 5; i++)
        {
        	hebrewNames[i] = new JTextField();  
        	hebrewNames[i].setBounds(130, 50 + (30 * i), 100, 20);
        	frame.add(hebrewNames[i]);
        }
        
        
        // English name
    	final JLabel englishNameLabel = new JLabel("Name (Hebrew)");
    	englishNameLabel.setBounds(250,20, 100,20);
    	frame.add(englishNameLabel);
    	
        englishNames =new JTextField[5];  
        
        for (int i = 0; i < 5; i++)
        {
        	englishNames[i] = new JTextField();  
        	englishNames[i].setBounds(250, 50 + (30 * i), 100, 20);
        	frame.add(englishNames[i]);
        }
        
        // English name
    	final JLabel city = new JLabel("City");
    	city.setBounds(370,20, 100,20);
    	frame.add(city);
    	
        cities =new JTextField[5];  
        
        for (int i = 0; i < 5; i++)
        {
        	cities[i] = new JTextField();  
        	cities[i].setBounds(370, 50 + (30 * i), 100, 20);
        	frame.add(cities[i]);
        }
        
        // Street
    	final JLabel street = new JLabel("Street");
    	street.setBounds(490,20, 100,20);
    	frame.add(street);
    	
        streets =new JTextField[5];  
        
        for (int i = 0; i < 5; i++)
        {
        	streets[i] = new JTextField();  
        	streets[i].setBounds(490, 50 + (30 * i), 100, 20);
        	frame.add(streets[i]);
        }
        
        // House no.
    	final JLabel stNumber = new JLabel("House No.");
    	stNumber.setBounds(610,20, 100,20);
    	frame.add(stNumber);
    	
        stNumbers =new JTextField[5];  
        
        for (int i = 0; i < 5; i++)
        {
        	stNumbers[i] = new JTextField();  
        	stNumbers[i].setBounds(610, 50 + (30 * i), 100, 20);
        	frame.add(stNumbers[i]);
        }
        
        // Phone
    	final JLabel phone = new JLabel("Phone");
    	phone.setBounds(730,20, 100,20);
    	frame.add(phone);
    	
        phones =new JTextField[5];  
        
        for (int i = 0; i < 5; i++)
        {
        	phones[i] = new JTextField();  
        	phones[i].setBounds(730, 50 + (30 * i), 100, 20);
        	frame.add(phones[i]);
        }
        
        // Active code
    	final JLabel code = new JLabel("Active Code");
    	code.setBounds(850,20, 100,20);
    	frame.add(code);
    	
        codes =new JTextField[5];  
        
        for (int i = 0; i < 5; i++)
        {
        	codes[i] = new JTextField();  
        	codes[i].setBounds(850, 50 + (30 * i), 100, 20);
        	frame.add(codes[i]);
        }
        
        // Modification date
    	final JLabel date = new JLabel("Modification Date");
    	date.setBounds(970,20, 100,20);
    	frame.add(date);
    	
        dates =new JTextField[5];  
        
        for (int i = 0; i < 5; i++)
        {
        	dates[i] = new JTextField();  
        	dates[i].setBounds(970, 50 + (30 * i), 100, 20);
        	frame.add(dates[i]);
        }
        
        // Previous Button
        JButton previousButton=new JButton("▲");  
        previousButton.setBounds(1090,60,50,50);  
        previousButton.addActionListener(new ActionListener(){  
    public void actionPerformed(ActionEvent e){  
    	if (lineCount > 4)
    		lineCount -= 5;
    	PrintUsersList(lineCount);
        }  
        });  
        frame.add(previousButton);
        
        // Next Button
        JButton nextButton=new JButton("▼");  
        nextButton.setBounds(1090,120,50,50);  
        nextButton.addActionListener(new ActionListener(){  
    public void actionPerformed(ActionEvent e){  
    	if (lineCount < userList.size() - 5) 
    		lineCount += 5;      
    	PrintUsersList(lineCount);
    	}
        });
        frame.add(nextButton);
    }
    
    public void PrintUsersList(int lineCount)
    {
    	GetUserList();
    	for (int i = 0; i < 5; i++)
    	{
    		ids[i].setText("");
    		hebrewNames[i].setText("");
    		englishNames[i].setText("");
    		cities[i].setText("");
    		streets[i].setText("");
    		stNumbers[i].setText("");
    		phones[i].setText("");
    		codes[i].setText("");
    		dates[i].setText("");
    	}

    	String[] currentUser;
    	for (int i = 0; i < 5; i++)
    	{
    		try {
    		currentUser = userList.get(lineCount + i);
    		ids[i].setText(currentUser[0]);
    		hebrewNames[i].setText(currentUser[1]);
    		englishNames[i].setText(currentUser[2]);
    		cities[i].setText(currentUser[3]);
    		streets[i].setText(currentUser[4]);
    		stNumbers[i].setText(currentUser[5]);
    		phones[i].setText(currentUser[6]);
    		codes[i].setText(currentUser[7]);
    		dates[i].setText(currentUser[8]);
    		}
    		catch (Exception e) {break;}
    	}
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
        		String response;
        		
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
					response = dataIn.readLine();
					
				} catch (IOException e1) {
					e1.printStackTrace();
					response = "Error";
				}
                parentFrame.setEnabled(true); 
                frame.dispose();
                
                JOptionPane.showMessageDialog(parentFrame, response);
                
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
        		String response;
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
					response = dataIn.readLine();
				} catch (IOException e1) {
					e1.printStackTrace();
					response = "Error";
				}
                
                parentFrame.setEnabled(true); 
                frame.dispose();  
                JOptionPane.showMessageDialog(parentFrame, response);
                
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
    
    public void SearchUserScreen(JFrame parentFrame)
    {
    	JFrame frame=new JFrame("Search User");
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
        usernameHebrew.setEditable(false);
        usernameHebrew.setBounds(150, 50, 150,20);  
        
        final JLabel usernameEnglishLabel = new JLabel("Username (English): ");
        usernameEnglishLabel.setBounds(10,80, 150,20);
        final JTextField usernameEnglish=new JTextField();  
        usernameEnglish.setEditable(false);
        usernameEnglish.setBounds(150, 80, 150,20);
        
        final JLabel cityLabel = new JLabel("City: ");
        cityLabel.setBounds(10,110, 150,20);
        final JTextField city=new JTextField();  
        city.setEditable(false);
        city.setBounds(150,110, 150,20);
        
        final JLabel streetLabel = new JLabel("Street: ");
        streetLabel.setBounds(10,140, 150,20);
        final JTextField street=new JTextField();
        street.setEditable(false);
        street.setBounds(150,140, 150,20);
        
        final JLabel numberLabel = new JLabel("House no.: ");
        numberLabel.setBounds(10,170, 150,20);
        final JTextField number=new JTextField();  
        number.setEditable(false);
        number.setBounds(150,170, 150,20);
        
        final JLabel phoneLabel = new JLabel("Phone: ");
        phoneLabel.setBounds(10,200, 150,20);
        final JTextField phone=new JTextField();  
        phone.setEditable(false);
        phone.setBounds(150,200, 150,20);
        
        
        JButton addUserButton=new JButton("Search User");  
        addUserButton.setBounds(10,280,150,30);  
        addUserButton.addActionListener(new ActionListener(){  
        	public void actionPerformed(ActionEvent e){  
        		String[] response = new String[9];
        		formData.put("userCode", userCode.getText());
        		formData.put("dbAction", "search");
                
                try {

					dataOut.writeObject(formData);
					dataOut.flush();
					response = (String[])serverUsersIn.readObject();
					
					userCode.setText(response[0]);
					usernameHebrew.setText(response[1]);
					usernameEnglish.setText(response[2]);
					city.setText(response[3]);
					street.setText(response[4]);
					number.setText(response[5]);
					phone.setText(response[6]);
					
				} catch (IOException | ClassNotFoundException e1) {
					e1.printStackTrace();
				}
           	}  
        }); 
        
        JButton cancelButton=new JButton("Back");  
        cancelButton.setBounds(170,280,150,30); 
        cancelButton.addActionListener(new ActionListener(){  
        	public void actionPerformed(ActionEvent e){  
        		parentFrame.setEnabled(true); 
                frame.dispose();
           	}  
        }); 
        
        frame.add(cancelButton);frame.add(addUserButton);frame.add(usernameHebrew);frame.add(usernameEnglish);frame.add(city);frame.add(street);frame.add(number);frame.add(phone);frame.add(userCode);
        frame.add(usernameHebrewLabel);frame.add(usernameEnglishLabel);frame.add(cityLabel);frame.add(streetLabel);frame.add(numberLabel);frame.add(phoneLabel);frame.add(userCodeLabel);    
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
    	String response;
    	formData.put("userCode", userCode.getText());
    	formData.put("dbAction", "delete");
    	
    	try {
			dataOut.writeObject(formData);
			dataOut.flush();
			response = dataIn.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
			response = "Error";
		}
    	parentFrame.setEnabled(true); 
        frame.dispose();
        JOptionPane.showMessageDialog(parentFrame, response);
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
        
        serverUsersIn = new ObjectInputStream(socket.getInputStream());
        PrintUsersList(0);
        
    }


    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.connectToServer();
    }
}