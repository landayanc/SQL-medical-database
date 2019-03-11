//package layout;
import java.awt.* ;
import java.awt.event.ActionListener ;
import java.awt.event.ActionEvent ;
import javax.swing.* ;
import javax.swing.border.Border ;
import java.sql.* ;
import oracle.jdbc.pool.OracleDataSource ;

/* This code is a representation of the Medical Clinic Information System database assignment for CPS510
 * SIDE NOTE: The system will only connect to the database using the Ryerson 'RU-Secure' WiFi connection.
 * @authors: Charlene Landayan, Ivanna Francesca Rasay
 */

public class CPS510UI {

	//frame components
	private static final int FRAME_WIDTH = 660 ;
	private static final int FRAME_HEIGHT = 380 ;
	JFrame UIFrame ;
	JPanel buttonPanel, bPanel, textAreaPanel, textFieldPanel ;
	FlowLayout bLayout, panelLayout ;
	JButton DropButton, ExitButton, CreateButton, Insert, Update,
		doctor, patient, medClinic, worksFor, patientOf, patronOf ;
	JOptionPane instruct;
	//for connections
	Connection conn ;
	Statement stmt ;
	ResultSet rs ;
	String textInput ;

	public CPS510UI() throws SQLException, NullPointerException {

		//Frame, panel
		UIFrame = new JFrame("Medical Clinic Information System") ;
		buttonPanel = new JPanel() ;
		panelLayout = new FlowLayout() ;
		buttonPanel.setLayout(panelLayout) ;
		panelLayout.setAlignment(FlowLayout.LEADING) ;
		bPanel = new JPanel() ;
		bLayout = new FlowLayout() ;
		bPanel.setLayout(bLayout) ;
		bLayout.setAlignment(FlowLayout.TRAILING) ;
		textAreaPanel = new JPanel() ;
		textAreaPanel.setLayout(new FlowLayout()) ;
		textFieldPanel = new JPanel() ;
		textFieldPanel.setLayout(new FlowLayout()) ;

		//Frame bounds
		UIFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT) ;
		UIFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;

		//Panel bounds
		buttonPanel.setBounds(0, 285, FRAME_WIDTH, FRAME_HEIGHT - 315) ;
		bPanel.setBounds(0, 320, FRAME_WIDTH, FRAME_HEIGHT - 305) ;

		//Buttons
		CreateButton = new JButton("Create Table") ;
		buttonPanel.add(CreateButton) ;
		DropButton = new JButton("Drop Table") ;
		buttonPanel.add(DropButton) ;
		Insert = new JButton("Insert") ;
		buttonPanel.add(Insert) ;
		Update = new JButton("Update") ;
		buttonPanel.add(Update) ;
		ExitButton = new JButton("Exit Connection") ;
		buttonPanel.add(ExitButton) ;

		doctor = new JButton("Doctor") ;
		bPanel.add(doctor) ;
		patient = new JButton("Patient") ;
		bPanel.add(patient) ;
		medClinic = new JButton("Medical Clinic") ;
		bPanel.add(medClinic) ;
		worksFor = new JButton("Works For") ;
		bPanel.add(worksFor) ;
		patientOf = new JButton("Patient Of") ;
		bPanel.add(patientOf) ;
		patronOf = new JButton("Patron Of") ;
		bPanel.add(patronOf) ;
		bPanel.setVisible(true) ;

		UIFrame.add(buttonPanel) ;
		UIFrame.add(bPanel) ;

		final JTextField input = new JTextField("Type table name or SQL statement here...", 25) ;
		textFieldPanel.setBounds(0, 250, FRAME_WIDTH, FRAME_HEIGHT - 310) ;
		UIFrame.add(textFieldPanel) ;

		//input.setBounds(150, 30, 200, 50) ;
		textFieldPanel.add(input) ;

		textAreaPanel.setBounds(0, 80, FRAME_WIDTH, FRAME_HEIGHT) ;
		UIFrame.add(textAreaPanel) ;

		final JTextArea screen = new JTextArea(15, 50) ;
		//screen.setBounds(100, 100, FRAME_WIDTH - 100, FRAME_HEIGHT - 100) ;
		//Text Area bounds
		screen.setLineWrap(true) ;
		screen.setEnabled(true) ;
		Border border = BorderFactory.createLineBorder(Color.GRAY) ;
		screen.setBorder(border) ;
		screen.setEditable(false) ;
		textAreaPanel.add(screen) ;

		JScrollPane scroll = new JScrollPane() ;
		scroll.setViewportView(screen) ;
		//scroll.setPreferredSize(screen.getSize()) ;
		textAreaPanel.add(scroll) ;

		UIFrame.setVisible(true) ;

		String db = "(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(Host=141.117.57.159)(Port=1521))(CONNECT_DATA=(SID=orcl)))";
		String jdbcUrl = "jdbc:oracle:thin:@" + db;
		String userid = "irasay";
		String password = "05267962";
		conn = null;

		OracleDataSource ds ;
		try{
			ds = new OracleDataSource();
        	ds.setURL(jdbcUrl);
        	conn = ds.getConnection(userid, password) ;
        	if (!conn.isClosed()) {
        		System.out.println("werk it") ;
        	}
		}
		catch(Exception e1){
			System.out.println("Didnt work") ;
			screen.append("Unable to connect to database") ;
		}

		//drops tables by typing table name into the textfield
		DropButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stmt = null ;
				textInput = input.getText() ;
				try {
					screen.append("Deleting table in given database...\n");
					stmt = conn.createStatement();

					String sql = "DROP TABLE " + textInput ;

					stmt.executeUpdate(sql);
					screen.append("Table " + textInput + " deleted\n---\n");
				}
				catch(SQLException se){
					screen.append("No such Table exists") ;
					//se.printStackTrace();
				}
				catch(Exception ek){
					screen.append("No such Table exists") ;
					//ek.printStackTrace();
				}
				finally{
			      try{
			         if(stmt!=null)
			            stmt.close();
			      }
			      catch(SQLException se){
			      }
			      try{
			         if(conn!=null)
			            stmt.close();
			      }
			      catch(SQLException se){
			         se.printStackTrace();
			      }
				}
			}

		});

		//updates table by typing TABLE <NAME ADD/DROP COLUMN/ETC> (VALUES)
		Update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stmt = null ;
				textInput = input.getText() ;
				instruct = new JOptionPane() ;
				try {
					stmt = conn.createStatement();
					stmt.executeUpdate("ALTER TABLE " + textInput) ;
					screen.append("Table updated\n") ;
				}
				catch(SQLException se){
					screen.append("Unable to update table\n---\n") ;
					//se.printStackTrace();
					instruct.showMessageDialog(null, "Try again.\nMake sure to follow the format\n"
							+ "TABLE NAME <ADD/DROP COLUMN/ETC> (VALUE1. VALUE2, ... , VALUEn)",
							"ERROR", JOptionPane.PLAIN_MESSAGE) ;
				}
				catch(Exception ek){
					screen.append("Unable to update table\n---\n") ;
					//ek.printStackTrace();
				}
				finally{
			      try{
			         if(stmt!=null)
			            stmt.close();
			      }
			      catch(SQLException se){
			      }
			      try{
			         if(conn!=null)
			            stmt.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }
				}

			}

		});

		//Creates new table by typing table name in the texfield
		CreateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stmt = null ;
				textInput = input.getText() ;
				try {
				      screen.append("Creating table in given database...\n");
				      stmt = conn.createStatement();

				      String sql = "CREATE TABLE " + textInput
				    		  + " (tempVAR VARCHAR2(10))";

				      stmt.executeUpdate(sql);
				      screen.append("Table " + textInput + " created\n---\n") ;
				}
			 	catch(SQLException se){
			      screen.append("Table already exists\n---\n") ;
			      //se.printStackTrace();
			 	}
				catch(Exception es){
					screen.append("Table already exists\n---\n") ;
			     	//es.printStackTrace();
				}
				finally{
			      try{
			         if(stmt!=null)
			            stmt.close();
			      }catch(SQLException se){
			      }
			      try{
			         if(conn!=null)
			            stmt.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }
				}
			}

		});

		//inserts values into the given table by typing TABLE NAME VALUES (values)
		Insert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stmt = null ;
				textInput = input.getText() ;
				instruct = new JOptionPane() ;
				try {
				      stmt = conn.createStatement();

				      String sql = "INSERT INTO " + textInput ;

				      stmt.executeUpdate(sql);
				      screen.append("Inserted values into table\n---\n") ;
				}
			 	catch(SQLException se){
			      screen.append("Unable to Insert given values\n---\n") ;
			      se.printStackTrace();
			      instruct.showMessageDialog(null, "Try again.\nMake sure to follow the format\n"
							+ "TABLE NAME VALUES (values)",
							"ERROR", JOptionPane.PLAIN_MESSAGE);
			 	}
				catch(Exception es){
				screen.append("Unable to Insert given values\n---\n") ;
			      es.printStackTrace();
				}
				finally{
			      try{
			         if(stmt!=null)
			            stmt.close();
			      }
			      catch(SQLException se){
			      }
			      try{
			         if(conn!=null)
			            stmt.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }
				}
			}

		});

		//For select statement.. this only applies for DOCTOR table
		doctor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stmt = null ;
				screen.append("\n\nDoctors:\n") ;
				try {
					stmt = conn.createStatement() ;
					String query = "select DOCTOR_NAME, EMPL_NUMBER, EXT_NUMBER, SPECIALTY, DOCTOR_LOCATION from DOCTOR" ;

					rs = stmt.executeQuery(query) ;
					while (rs.next()) {
						String docName = rs.getString("DOCTOR_NAME") ;
						int emplNum = rs.getInt("EMPL_NUMBER") ;
						int extNum = rs.getInt("EXT_NUMBER") ;
						String spec = rs.getString("SPECIALTY") ;
						String docLoc = rs.getString("DOCTOR_LOCATION") ;

						screen.append("---\nDoctor Name: " + docName + "\nEmpl Number: " + emplNum
								+ "\nExt Number: " + extNum + "\nSpecialty: " + spec + "\nDoctor Address: "
								+ docLoc + "\n---\n") ;
					}
					rs.close() ;
				}
				catch(SQLException se) {
					screen.append("Unable to obtain information. Sawry!\n---\n") ;
					//se.printStackTrace() ;
				}
				catch (Exception o) {
					screen.append("Unable to obtain information. Sawry!\n---\n") ;
					//o.printStackTrace() ;
				}
				finally {
					try {
						if (stmt != null)
							stmt.close() ;
					}
					catch (SQLException se) {}
					try {
						if (conn != null)
							stmt.close() ;
					}
					catch (SQLException se) {}
				}
			}
		});


		patient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					stmt = conn.createStatement();
					screen.append("\n\nPatients:\n") ;
					String sql = "SELECT patient_name, OHIP, Birthday, Patient_age, Patient_location, Patient_phone_number "
							+ "FROM Patient" ;
					ResultSet rs = stmt.executeQuery(sql);
					while(rs.next()){
						String name  = rs.getString("patient_name");
						String ohip = rs.getString("OHIP");
						int age = rs.getInt("Patient_age") ;
						String add = rs.getString("Patient_location") ;
						Date bday = rs.getDate("Birthday") ;
						//int pnum = rs.getInt("Patient_phone_number") ;

						screen.append("---\nPatient Name: " + name + "\nOHIP Number: " + ohip + "\nBirthdate: " + bday
								+ "\nAge: " + age + "\nPatient Address: " + add + "\n---\n") ;
			      }
			      rs.close();
				}
				catch(SQLException se){
				      se.printStackTrace();
				}
				catch(Exception ae){
				      ae.printStackTrace();
				}
				finally{
				      try{
				         if(stmt!=null)
				            stmt.close();
				      }
				      catch(SQLException se){
				      }
				      try{
				          if(conn!=null)
				             stmt.close();
				      }
				      catch(SQLException se){
				          se.printStackTrace();
				      }
				}
			}
		});

		medClinic.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
				      stmt = conn.createStatement();
				      screen.append("\n\nMedical Clinics:\n") ;
				      String sql = "select clinic_name, clinic_number, clinic_location, clinic_phone_number "
				      		+ "from medical_clinic";
				      ResultSet rs = stmt.executeQuery(sql);
				      while(rs.next()){
				         String namae  = rs.getString("clinic_name");
				         int cnum = rs.getInt("clinic_number");
				         String location = rs.getString("clinic_location");
				         //int pnum = rs.getInt("clinic_phone_number");

				         screen.append("---\nClinic Name: " + namae + "\nClinic Number: " + cnum
				        		 + "\nAddress: " + location + "\n---\n");
				      }
				      rs.close();
				   }catch(SQLException se){
				      se.printStackTrace();
				   }catch(Exception pe){
				      pe.printStackTrace();
				   }finally{
				      try{
				    	  if(stmt!=null)
				            stmt.close();
				      }catch(SQLException se){
				      }
				      try{
				         if(conn!=null)
				            stmt.close();
				      }catch(SQLException se){
				         se.printStackTrace();
				      }
				   }
			}
		});

		worksFor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
				      stmt = conn.createStatement();
				      screen.append("\nWorks for:\n") ;
				      String sql = "SELECT start_date, empl_number, clinic_number FROM works_for";
				      ResultSet rs = stmt.executeQuery(sql);

				      while(rs.next()){
				         Date startDate  = rs.getDate("start_date");
				         int emplNum = rs.getInt("empl_number");
				         int clinNum = rs.getInt("clinic_number");

				         //Display values
				         screen.append("---\nStart Date of Doctor: " + startDate + "\nEmployee Number: "
				        		 + emplNum + "\nClinic Number: " + clinNum + "\n---\n") ;
				      }
				      rs.close();
				}
				catch(SQLException se){
				      //Handle errors for JDBC
				      se.printStackTrace();
				}
				catch(Exception ke){
				      //Handle errors for Class.forName
				      ke.printStackTrace();
				}
				finally{
					   try{
					         if(stmt!=null)
					            stmt.close();
					   }
					   catch(SQLException se){
					   }
					   try{
					         if(conn!=null)
					            stmt.close();
					   }
					   catch(SQLException se){
					         se.printStackTrace();
					   }
				}
			}
		});

		patronOf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
				      stmt = conn.createStatement();
				      screen.append("\nPatron Of:\n") ;
				      String sql = "SELECT register_date, ohip, clinic_number FROM patron_of" ;
				      ResultSet rs = stmt.executeQuery(sql);
				      while(rs.next()){
				         Date registerDate  = rs.getDate("register_date");
				         String patOhip = rs.getString("ohip");
				         int clinNum = rs.getInt("clinic_number");

				         //Display values
				         screen.append("---\nPatient Register Date: " + registerDate + "\nOHIP Number: "
				        		 + patOhip + "\nClinic Number: " + clinNum + "\n---\n") ;
				      }
				      rs.close();
				}
				catch(SQLException se){
				      //Handle errors for JDBC
				      se.printStackTrace();
				}
				catch(Exception ke){
				      //Handle errors for Class.forName
				      ke.printStackTrace();
				}
				finally{
					   try{
					         if(stmt!=null)
					            stmt.close();
					      }catch(SQLException se){
					      }// do nothing
					      try{
					         if(conn!=null)
					            stmt.close();
					      }catch(SQLException se){
					         se.printStackTrace();
					      }
				}
			}
	});

		patientOf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
				      stmt = conn.createStatement();
				      screen.append("/nPatient of:/n") ;
				      String sql = "SELECT ohip, empl_number FROM patient_of";
				      ResultSet rs = stmt.executeQuery(sql);
				      while(rs.next()){
				         String ohip  = rs.getString("ohip");
				         int emplNum = rs.getInt("empl_number");

				         screen.append("---\nPatient OHIP Number: " + ohip + "\nDoctor in Charge(Num): "
				        		 + emplNum + "\n---\n") ;
				      }
				      rs.close();
				}
				catch(SQLException se){
				      se.printStackTrace();
				}
				catch(Exception ke){
				      ke.printStackTrace();
				}
				finally{
					   try{
					         if(stmt!=null)
					            stmt.close();
					      }
					   catch(SQLException se){
					      }
					      try{
					         if(conn!=null)
					            stmt.close();
					      }catch(SQLException se){
					         se.printStackTrace();
					      }
					   }
				}
		});

		//exits the connection
		ExitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (!conn.isClosed()) {
						conn.close() ;
						screen.append("\nConnection closed\n") ;
						//UIFrame.getDefaultCloseOperation() ;
					}
				} catch (SQLException e1) {
					screen.append("\nFailed to close connection\n") ;
					e1.printStackTrace();
				}
			}

		});
	}

	public static void main(String[] args) throws SQLException, NullPointerException {

		CPS510UI frame = new CPS510UI() ;
		//JOptionPane LOL = new JOptionPane() ;
		LOL.showMessageDialog(null, "Welcome to the Medical Clinic Information System\n\n"
				+ "To begin, type a TABLE NAME to drop or create an existing or new table\n"
				+ "            and click the corresponding button.\n\n"
				+ "To insert, type in the proper format: \n"
				+ "            TABLENAME VALUES (values)\n\n"
				+ "To update, type in the proper format: \n"
				+ "            TABLENAME ADD/DROP COLUMN/ETC (VALUES)", "INSTRUCTION", JOptionPane.PLAIN_MESSAGE) ;

	}

}
