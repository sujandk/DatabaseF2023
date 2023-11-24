/**
 * Author: Lon Smith, Ph.D.
 * Description: This is the framework for the database program. Additional requirements and functionality
 *    are to be built by you and your group.
 */

import java.awt.EventQueue;
import java.util.Properties;
import java.io.FileReader;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.sql.*;


// imported packages
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.ListSelectionModel;

import java.util.ArrayList;
import java.util.Arrays;


public class EmployeeSearchFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtDatabase;
	private JList<String> lstDepartment;
	private DefaultListModel<String> department = new DefaultListModel<String>();
	private JList<String> lstProject;
	private DefaultListModel<String> project = new DefaultListModel<String>();
	private JTextArea textAreaEmployee;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					EmployeeSearchFrame frame = new EmployeeSearchFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EmployeeSearchFrame() {
        
		boolean [] selectedDept ={false};             // t0 find out if checkNotDept button is selected
		boolean [] selectedProject ={false};		  //  // t0 find out if checkNotProj button is selected
		String[] selectedProjectItem = new String[1];  // to store the selected project item from the list of project
        String[] selectedDeptItem = new String[1];	   // // to store the selected project item from the list of project
		

		setTitle("Employee Search");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 347);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Database:");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblNewLabel.setBounds(21, 23, 59, 14);
		contentPane.add(lblNewLabel);
		
		txtDatabase = new JTextField();
		txtDatabase.setBounds(90, 20, 193, 20);
		contentPane.add(txtDatabase);
		txtDatabase.setColumns(10);
		
		
		
		
		
		JButton btnDBFill = new JButton("Fill");
		/**
		 * The btnDBFill should fill the department and project JList with the 
		 * departments and projects from your entered database name.
		 */
		btnDBFill.addActionListener(new ActionListener() {
			ArrayList<String> dept = new ArrayList<>();   // to store the names of departments
			ArrayList<String> prj = new ArrayList<>();    // to store the names of projects


			public void actionPerformed(ActionEvent e) {
				try
				{
					FileReader reader = new FileReader("database.properties");
	  				Properties p = new Properties();
					p.load(reader);
					String dbUser = p.getProperty("db.user");
					String dbPass = p.getProperty("db.password");
					String dbURL = p.getProperty("db.url");
					

					Class.forName(p.getProperty("db.driver")).newInstance();
					Connection con = DriverManager.getConnection(dbURL, dbUser, dbPass);
					Statement statement = con.createStatement();
					ResultSet resultSet = statement.executeQuery("SELECT * FROM DEPARTMENT");
					ResultSet resultSet2 = statement.executeQuery("SELECT * FROM PROJECT");

					while(resultSet.next() || resultSet2.next()){

						if (resultSet.next()){
						String deptName= resultSet.getString("Dname");
						dept.add(deptName);
						}

						if (resultSet2.next()){
							String prjName= resultSet.getString("Pname");
							prj.add(prjName);
							}

					}

		
			
				}
				catch(Exception ex)
				{		
					System.err.println("Exception: "+ ex.getMessage());
				}


				
				for(int i = 0; i < dept.size(); i++) {
					department.addElement(dept.get(i));
				}
				
				for(int j = 0; j < prj.size(); j++) {
					project.addElement(prj.get(j));
				}
				
			}
		});
		
		btnDBFill.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnDBFill.setBounds(307, 19, 68, 23);
		contentPane.add(btnDBFill);
		
		JLabel lblDepartment = new JLabel("Department");
		lblDepartment.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblDepartment.setBounds(52, 63, 89, 14);
		contentPane.add(lblDepartment);
		
		JLabel lblProject = new JLabel("Project");
		lblProject.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblProject.setBounds(255, 63, 47, 14);
		contentPane.add(lblProject);

		//scroll for the project to display the names of all the project 

		JScrollPane projectScrollPane = new JScrollPane();
        
        projectScrollPane.setBounds(225, 84, 150, 42);
        contentPane.add(projectScrollPane);

		lstProject = new JList<String>(new DefaultListModel<String>());
		lstProject.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lstProject.setModel(project);
		lstProject.setBounds(225, 84, 150, 42);
		contentPane.add(lstProject);
		projectScrollPane.setViewportView(lstProject);

		// selectionListener for the PROJECT names

		lstProject.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		lstProject.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent s) {
                if (!s.getValueIsAdjusting()) {
                    // Selection change is complete
					selectedProjectItem[0] =lstProject.getSelectedValue();
					
                }
            }
        });



		
		JCheckBox chckbxNotDept = new JCheckBox("Not");
		chckbxNotDept.setBounds(71, 133, 59, 23);
		contentPane.add(chckbxNotDept);


		// eventListener for the NOT button on DEPARTMENT
		chckbxNotDept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				selectedDept[0]= true ;
			
			}
		});

		// eventListener for the NOT button on PROJECT
		JCheckBox chckbxNotProject = new JCheckBox("Not");
		chckbxNotProject.setBounds(270, 133, 59, 23);
		contentPane.add(chckbxNotProject);

		chckbxNotProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				
				selectedProject[0]= true ;
	
			}
		});

		//scrollable for department to display the names of all the departments

		JScrollPane departmentScrollPane = new JScrollPane();
        departmentScrollPane.setBounds(36, 84, 172, 40);
        contentPane.add(departmentScrollPane);


		lstDepartment = new JList<String>(new DefaultListModel<String>());
		lstDepartment.setBounds(36, 84, 172, 40);
		contentPane.add(lstDepartment);
		lstDepartment.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lstDepartment.setModel(department);
		departmentScrollPane.setViewportView(lstDepartment);


		//selectionListener for DEPARTMENT names
		lstDepartment.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstDepartment.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent d) {
                if (!d.getValueIsAdjusting()) {

					selectedDeptItem[0] = lstDepartment.getSelectedValue();
					
                }
            }
        });

		
			
		

		
	
		JLabel lblEmployee = new JLabel("Employee");
		lblEmployee.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblEmployee.setBounds(52, 179, 89, 14);
		contentPane.add(lblEmployee);

		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				
				//System.out.println(selectedDeptItem[0]);
				String query = "SELECT FNAME, LNAME FROM DEPARTMENT , PROJECT"  +
								" WHERE " +
								(selectedDept[0]? " NOT ": " ") +
								selectedDeptItem[0] + 
								" AND " +
								(selectedProject[0]?  " NOT ": " ") +
								selectedProjectItem[0] + ";" ;



				textAreaEmployee.setText("John Smith\nFranklin Wong\nSyjan\nxaxa\nxasewqe\nsfljaf");
			}
		});
		btnSearch.setBounds(80, 276, 89, 23);
		contentPane.add(btnSearch);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textAreaEmployee.setText("");
			}
		});
		btnClear.setBounds(236, 276, 89, 23);
		contentPane.add(btnClear);

		//scroll for textArea to display the result of the query
		JScrollPane textAreaScrollable = new JScrollPane();
		textAreaScrollable.setBounds(36,197,339,68);
		contentPane.add(textAreaScrollable);
		
		textAreaEmployee = new JTextArea();
		textAreaEmployee.setBounds(36, 197, 339, 68);
		textAreaScrollable.setViewportView(textAreaEmployee);

		

        

        
	}
}
