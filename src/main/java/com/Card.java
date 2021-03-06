package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Card {
    private Connection connect() { 
        Connection con = null; 
        try { 
        Class.forName("com.mysql.jdbc.Driver"); 
    
        //Provide the correct details: DBServer/DBName, username, password 
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/electrogrid", "root", ""); 
        System.out.println("database connected");
        } catch (Exception e) {

            e.printStackTrace();
        } 
            return con; 
    } 
    
    public String readCard() { 
		String output = ""; 

		try { 

			Connection con = connect(); 
			if (con == null) {
				return "Error while connecting to the database for reading."; 
			} 

			// Prepare the html table to be displayed
			output = "<table border='1'>"
					+"<tr><th>accNo</th>"
					+"<th>cardNo</th>" 
					+"<th>expire</th>" 
					+"<th>cvc</th>"
					+"<th>Update</th>"
					+"<th>Remove</th></tr>"; 
	 
			String query = "select * from card"; 
			Statement stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery(query); 

			// iterate through the rows in the result set
			while (rs.next()) { 
                
				String id = Integer.toString(rs.getInt("id")); 
				String accNo = Integer.toString(rs.getInt("Accno")); 
				String cardNo = rs.getString("cardNo"); 
				String expire = rs.getString("Expire"); 
				String cvc = rs.getString("CVC"); 

				// Add into the html table
				output += "<tr><td><input id='hidcardIDUpdate'"
						+ " name='hidcardIDUpdate'"
						+ " type='hidden' value='" +  id + "'>" ;
				output += "<tr><td>" + accNo + "</td>"; 
				output += "<td>" + cardNo + "</td>"; 
				output += "<td>" + expire + "</td>"; 
				output += "<td>" + cvc + "</td>"; 

				// buttons
				output += "<td><input id='btnUpdate' name='btnUpdate' type='button' value='Update' "
						 + "class='btnUpdate btn btn-secondary' data-itemid='" + id + "'></td>"
						 + "<td><input id='btnRemove' name='btnRemove' type='button' value='Remove' "
						 + "class='btnRemove btn btn-danger' data-itemid='" + id + "'></td></tr>";

			} 
			con.close(); 

			// Complete the html table
			output += "</table>"; 

		} catch (Exception e) { 
			output = "Error while reading the card details."; 
			System.err.println(e.getMessage()); 
		} 

		return output; 
	}
    
    public String insertCard(String accNo, String cardNo, String expire, String cvc) { 
    	System.out.println(accNo + cardNo +  expire+ cvc);
        String output = ""; 
        try { 
            Connection con = connect(); 
            if (con == null) {
                return "Error while connecting to the database for inserting."; 
            } 

            // create a prepared statement
            String query = " insert into card (`id`,`Accno`,`cardNo`,`Expire`,`CVC`)"
                            + " values (?, ?, ?, ?, ?)"; 

            PreparedStatement preparedStmt = con.prepareStatement(query); 

            // binding values
            preparedStmt.setInt(1, 0); 
            preparedStmt.setString(2, accNo); 
            preparedStmt.setString(3, cardNo); 
            preparedStmt.setString(4, expire); 
            preparedStmt.setInt(5, Integer.parseInt(cvc)); 

            // execute the statement
            preparedStmt.execute(); 
            con.close(); 
			/* output = "Inserted successfully"; */ 
            String newcards = readCard(); 
            output = "{\"status\":\"success\", \"data\": \"" + 
            		newcards + "\"}"; 


        } catch (Exception e) 
        { 
        output = "{\"status\":\"error\", \"data\": "
        		+ "\"Error while inserting the card details.\"}"; 
        System.err.println(e.getMessage()); 
        } 

        return output; 
    } 
    
    public String updateCard(String id, String accNo, String cardNo, String expire, String cvc) { 

        String output = ""; 

        try { 

            Connection con = connect(); 

            if (con == null) {
                return "Error while connecting to the database for updating."; 
            } 

            // create a prepared statement
            String query = "UPDATE card SET Accno=?, cardNo=?, Expire=?, CVC=? WHERE id=?"; 
            PreparedStatement preparedStmt = con.prepareStatement(query); 

            // binding values
            preparedStmt.setString(1, accNo); 
            preparedStmt.setString(2, cardNo); 
            preparedStmt.setString(3, expire); 
            preparedStmt.setInt(4, Integer.parseInt(cvc));
            preparedStmt.setInt(5, Integer.parseInt(id));

            // execute the statement
            preparedStmt.execute(); 
            con.close(); 
			/* output = "Updated successfully"; */
            String newcards = readCard(); 
            output = "{\"status\":\"success\", \"data\": \"" + 
            		newcards + "\"}"; 

        } catch (Exception e)
            { 
        	 output = "{\"status\":\"error\", \"data\": "
        	 		+ "\"Error while updating the card details.\"}"; 
        			 System.err.println(e.getMessage()); 
        			 } 

        return output; 
    }
    
    public String deleteCard(String id) { 

        String output = ""; 

        try { 

            Connection con = connect(); 

            if (con == null) {
                return "Error while connecting to the database for deleting."; 
            }

            // create a prepared statement
            String query = "delete from card where id=?"; 
            PreparedStatement preparedStmt = con.prepareStatement(query); 

            // binding values
            preparedStmt.setInt(1, Integer.parseInt(id)); 

            // execute the statement
            preparedStmt.execute(); 
            con.close(); 
			/* output = "Deleted successfully"; */
            String newcards = readCard(); 
            output = "{\"status\":\"success\", \"data\": \"" + 
            		newcards + "\"}"; 

        } catch (Exception e) 
        { 
        	 output = "{\"status\":\"error\", \"data\": "
        	 		+ "\"Error while deleting the card details.\"}"; 
        	 System.err.println(e.getMessage()); 
        	 } 
        return output; 
    } 
}
