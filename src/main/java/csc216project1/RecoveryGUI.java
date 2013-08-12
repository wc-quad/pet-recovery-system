package csc216project1;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * A graphical user interface for a pet chip registration system.
 * 
 * @author Jo P
 * @version 2.0
 */
public class RecoveryGUI extends JFrame implements ActionListener {

    // Window, button, and scrollpane strings
    private final static String TITLE = "Pet Recovery System";
    private final static String REGISTRATIONS = "Registered Pets";
    private final static String DELETE = "Remove Selected Pet";
    private final static String ADD = "Add New Pet";
    private final static String FIND = "Search";
    private final static String QUIT = "Quit";
    private static String[] PET_KINDS = {"Dog", "Cat", ""};
    private final static JLabel lblName = new JLabel("Owner: ");
    private final static JLabel lblPhone = new JLabel("Phone: ");
    private final static JLabel lblPetKind = new JLabel("Pet Kind: ");
    private final static JLabel lblSearch = new JLabel("Search for: ");

    // Size constants for the window and scroll panes
    private final static int FRAME_WIDTH = 400;
    private final static int FRAME_HEIGHT = 600;
    private static final int RESERVATION_LENGTH = 50;
    private static final int PAD = 10;

    // Buttons
    private JButton btnDelete = new JButton(DELETE);
    private JButton btnAdd = new JButton(ADD);
    private JButton btnFind = new JButton(FIND);
    private JButton btnQuit = new JButton(QUIT);
    private JRadioButton[] rbtnSearch = new JRadioButton[PetData.SEARCH_FIELD.length];
    private ButtonGroup groupSearch = new ButtonGroup();

    // Panels 
    private JPanel pnlSearch = new JPanel();
    private JPanel pnlRadioSearch = new JPanel();
    private JPanel pnlAdd = new JPanel();
    private JPanel pnlTop = new JPanel();
    private JPanel pnlMiddle = new JPanel();
    private JPanel pnlBottom = new JPanel();

    // Fields, combo box for pet data
    private JTextField txtName = new JTextField();
    private JTextField txtPhone = new JTextField();
    private JTextField txtSearch = new JTextField();
    private JComboBox cmbPetKind = new JComboBox(PET_KINDS);
    private JLabel[] lblPet = { lblPhone, lblName, lblPetKind };
    private Component[] cmpPet = { txtPhone, txtName, cmbPetKind };

    // Scrolling list of registrations
    private DefaultListModel dlmRegistrations = new DefaultListModel();
    private JList lstRegistrations = new JList(dlmRegistrations);
    private JScrollPane scrollRegistrations = new JScrollPane(lstRegistrations);
    private Font listFont = new Font("Courier", Font.PLAIN, 12);

    // Backend model -- the database
    private IDataCollection database;

    /**
     * Constructor. Sets up the user interface and initializes the backend model.
     * 
     * @param filename  file that initializes the registration list
     */
    public RecoveryGUI(String filename) throws FileNotFoundException{   	
    	database = new PetData(filename);
        initUI();
    }

    /**
     * Constructor. Sets up the user interface and initializes the backend model with
     * and empty list.
     */
    public RecoveryGUI() {
    	database = new PetData();
        initUI();
    }

    // ------ Controller Methods ---------------------------

    /**
     * Button click reactions.
     * @param e user event (button click)
     */
    public void actionPerformed(ActionEvent e) {
    	// Quit: stop program execution
        if (e.getSource().equals(btnQuit))
            endExecution();
        // Delete: remove the item and refresh the screen
        if (e.getSource().equals(btnDelete)) {
            database.remove(
                    (String) lstRegistrations.getSelectedValue());
            loadReservationList();
            clearFields();
        }
        // Add: add an object with the given attributes to the database
        if (e.getSource().equals(btnAdd)) {
            String phone = txtPhone.getText();
            String name = txtName.getText();
            String kind = (String) cmbPetKind.getSelectedItem();
            database.add(name, phone, kind);
            loadReservationList();
            clearFields();
        }
        // Find: Display all items whose attributes of the given
        // search type begin with the given search string
        if (e.getSource().equals(btnFind)) {
        	String searchString = txtSearch.getText().trim();
        	String searchType = PetData.SEARCH_FIELD[0];
        	for (JRadioButton rbtn : rbtnSearch) {
        		if (rbtn.isSelected())
        			searchType = rbtn.getText();
        	}
        	String result = database.search(searchString, searchType);
        	loadReservationList(result);       	
        }     
    }

    // --------End Controller Methods ---------------------

    // ------ Private Methods -----------------------------

    /**
     * Private method - initializes the user interface.
     */
    private void initUI() {
        // Set up the panels and the list that make the UI
        Container c = getContentPane();
    	setUpPanels();
        setUpLists();

        // Construct the main window and add listeners.
        setTitle(TITLE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        c.add(pnlTop, BorderLayout.NORTH);
        c.add(pnlMiddle, BorderLayout.CENTER);
        c.add(pnlBottom, BorderLayout.SOUTH);
        setVisible(true);
        clearFields();
        addListeners();

        // Make sure the application quits when the window is closed.
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                endExecution();
            }
        });
    }

    /**
     * Private method - Sets up the scrolling list of reservations with data
     * from the database.
     */
    private void setUpLists() {
        loadReservationList();
        lstRegistrations.setFont(listFont);
        lstRegistrations.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * Private method - Determines the geometry of the main window.
     */
    private void setUpPanels() {
    	setUpTop(); 	
    	setUpMiddle();
    	setUpBottom();
    }
    
    /**
     * Private method - Sets up the top panel for adding and searching.
     */
    private void setUpTop() {
    	setUpAdd();
    	setUpSearch();        pnlTop.setLayout(new BoxLayout(pnlTop, BoxLayout.PAGE_AXIS));
        pnlTop.add(pnlAdd);
        pnlTop.add(new JSeparator(JSeparator.HORIZONTAL));
        pnlTop.add(pnlSearch);
    }
    
    private void setUpAdd() { 
    	// Put the components in a grid
    	GridBagConstraints constraint = new GridBagConstraints();
        pnlAdd.setLayout(new GridBagLayout());
        pnlAdd.setBorder((EmptyBorder) BorderFactory.createEmptyBorder( PAD, PAD / 2, PAD, PAD));

        // Components for pet data
        for (int i = 0; i < lblPet.length; i++) {
            constraint.gridx = 0;
            constraint.gridy = i; 
            constraint.fill = GridBagConstraints.NONE;
            constraint.weightx = 0.0;
            pnlAdd.add(lblPet[i], constraint);

            constraint.gridx = 1;
            constraint.fill = GridBagConstraints.HORIZONTAL;
            constraint.weightx = 1.0;
            pnlAdd.add(cmpPet[i], constraint);
        }

        //Put in the add button
        constraint.gridwidth = 2;     
        constraint.gridx = 0;
        constraint.gridy += 1;       
        pnlAdd.add(btnAdd, constraint); 	
    }

    /**
     * Private method - Sets up the search panel.
     */
    private void setUpSearch() {
    	// Set up the radio buttons.
    	for (int k = 0; k < PetData.SEARCH_FIELD.length; k++) {
    		rbtnSearch[k] = new JRadioButton(PetData.SEARCH_FIELD[k]);
    		groupSearch.add(rbtnSearch[k]);
    		pnlRadioSearch.add(rbtnSearch[k]);
    	}
    	rbtnSearch[0].setSelected(true);
    	
    	// Put the panel together
    	GridBagConstraints c = new GridBagConstraints();
    	pnlSearch.setBorder((EmptyBorder) BorderFactory.createEmptyBorder( PAD, PAD / 2, PAD, PAD));
    	pnlSearch.setLayout(new GridBagLayout());
   	 
        // Add the label and search text area.
    	c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.0;
        pnlSearch.add(lblSearch, c);        
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        pnlSearch.add(txtSearch, c);

        // Add the label and the search type button group.
        c.weightx = 0.0;
        c.gridx = 0;
        c.gridy += 1;
        pnlSearch.add(new JLabel("Search by: "), c);
        c.gridx = 1;
        pnlSearch.add(pnlRadioSearch, c);

        // Add the search button.
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL; 
        c.gridx = 0;
        c.gridy += 1;        
        pnlSearch.add(btnFind, c);  	   	
    }

    /**
     * Private Method - Sets up the middle panel with the list.
     */
    private void setUpMiddle() {
        // Initialize the registration list.
        scrollRegistrations.setBorder(new TitledBorder(REGISTRATIONS));
        scrollRegistrations.setPreferredSize(new Dimension(FRAME_WIDTH / 2 - 4 * (PAD), 
                RESERVATION_LENGTH));

        // Lay out the middle panel with the scrolling list.
        pnlMiddle.setLayout(new BorderLayout());
        pnlMiddle.setBorder((EmptyBorder) BorderFactory
                .createEmptyBorder(PAD, PAD / 2, PAD, PAD));
        pnlMiddle.add(scrollRegistrations);   	
    }
 
    /**
     * Private Method - Sets up the bottom panel with two buttons.
     */
    private void setUpBottom() {
    	// Establish the constraints
    	GridBagConstraints constraints = new GridBagConstraints();
        pnlBottom.setLayout(new GridBagLayout());
        pnlBottom.setBorder((EmptyBorder) BorderFactory.createEmptyBorder( PAD, PAD / 2, PAD, PAD));
        constraints.fill = GridBagConstraints.HORIZONTAL;

        // Add the delete button, horizontal bar, quit button
        constraints.gridwidth = 2;
        constraints.weightx = 1.0;
        constraints.gridy = 0;
        pnlBottom.add(btnDelete, constraints);
        
        constraints.gridy += 1;
        pnlBottom.add(new JSeparator(JSeparator.HORIZONTAL), constraints);

        constraints.fill = GridBagConstraints.PAGE_END;
        constraints.gridy += 1;
        pnlBottom.add(btnQuit,constraints);   	
    }

    /**
     * Private Method - Adds listeners to the buttons.
     */
    private void addListeners() {
        cmbPetKind.setEditable(true);
        cmbPetKind.addActionListener(this);
    	btnDelete.addActionListener(this);
    	btnAdd.addActionListener(this);
    	btnFind.addActionListener(this);
    	btnQuit.addActionListener(this);
    	for (JRadioButton rbtn : rbtnSearch)
    		rbtn.addActionListener(this);
    }

    /**
     * Private method - Clears all text fields, combo box.
     */
    private void clearFields() {
        txtPhone.setText("");
        txtName.setText("");
        txtSearch.setText("");
        cmbPetKind.setSelectedIndex(0);
    }

    /**
     * Private Method - Loads the reservation list model from the database.
     */
    private void loadReservationList() {
        dlmRegistrations.clear();
        Scanner sc = new Scanner(database.search("",""));
        while(sc.hasNext())
        	dlmRegistrations.addElement(sc.nextLine());
    }
    
    /**
     * Private Method - Loads the reservation list model from a string, with
     * newline tokenizers.
     */
    private void loadReservationList(String s) {
        dlmRegistrations.clear();
        Scanner sc = new Scanner(s);
        while(sc.hasNext())
        	dlmRegistrations.addElement(sc.nextLine());
    }

    /**
     * Private Method - Ends program execution and saves reservations to a file.
     */
    private void endExecution() {
        database.saveToFile();
        System.exit(0);
    }

    // ------------- End Private Methods -------------------

    /**
     * Begins program execution.
     * 
     * @param args arg[0] is the data file to initialize the registration list.
     */
    public static void main(String[] args) {
    	if (args.length == 0)
    		new RecoveryGUI();   		
    	else {
    		try {
    			new RecoveryGUI(args[0]);
    		}
    		catch (Exception e) {
    			new RecoveryGUI();
    		}
    	}
    }
}
