/* Name: Jackson Vaughn
Course: CNT 4714 – Fall 2023
Assignment title: Project 1 – Event-driven Enterprise Simulation
Date: Sunday September 17, 2023


*/



import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.TimeZone;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUI extends JFrame implements ActionListener{


	private JFrame window = new JFrame("Nile.com");
	
	//static varibles
	private static final int HEIGHT = 600;
	private static final int WIDTH = 1000;
	private static final int INPUT_LAYOUT = 50;
	
	private static final int BUTTON_LENGTH = 100;
	private static final int BUTTON_WIDTH = 50;
	
	//varibles
	private JButton findItem,viewCart,emptyCart,addItem,checkOut,exit;
	
	int curItem = 1;	//the current item being searches
	
	int prevItem = 1;	//the previous item details
	
	int totalItem = 0;	//the totam alount of items
	
	Color topColor = new Color(96, 197, 224);

	Color bottomColor = new Color(26, 42, 46);
	
	JLabel itemInputLable = new JLabel("Please enter item ID for item #" + curItem + "\n");
	JTextField itemInput = new JTextField(INPUT_LAYOUT);
	
	JLabel itemQuantLabel = new JLabel("Please enter the quantity of item #" + curItem+"\n");
	JTextField inputQuant = new JTextField(INPUT_LAYOUT);
	
	JLabel itemDetailsLabel = new JLabel("Item details for item #1 \n");
	

	
	JTextField itemDetails = new JTextField(INPUT_LAYOUT);
	
	JLabel subTotalLabel = new JLabel("Subtotal of "+totalItem+" item(s)\n");
	JTextField subTotal = new JTextField(INPUT_LAYOUT);
	
	ArrayList <items> cart = new ArrayList<items>();
	
	Double subTotalAll = 0.0;
	items i1;
	
	
	
	String inHand = "";	//when we lookup a value and its valid. we add it to this string
	
	String File = "inventory.csv";
	
	String ID = "";
	

	//Constructor
	//*********************************************

	public GUI(){
		//making main frame
		//*********************************************

		window.setSize(WIDTH,HEIGHT);
		window.setResizable(false);
		
		
		window.setLayout(null);
		
		//*********************************************
		itemDetailsLabel.setForeground(Color.red);
		subTotalLabel.setForeground(Color.red);
		
		//creating top and bottom panels
		//*********************************************
		JPanel topLeft = new JPanel();
		
		JPanel topRight = new JPanel();
		
		
		topLeft.setBounds(0, 0, WIDTH/2, HEIGHT/2);
		topLeft.setBackground(topColor);
	
		topLeft.setLayout(new GridLayout(8,4));
		window.add(topLeft);
		
		window.setBackground(Color.blue);
		
		
		topRight.setBounds(WIDTH/2, 0, WIDTH/2, HEIGHT/2);
		topRight.setBackground(topColor);
		topRight.setLayout(new GridLayout(8,4));
		window.add(topRight);
		

		itemDetails.setEditable(false);
		subTotal.setEditable(false);
		
		
		
		topLeft.add(itemInputLable);
		topRight.add(itemInput);
		
		topLeft.add(itemQuantLabel);
		topRight.add(inputQuant);
		
		topLeft.add(itemDetailsLabel);
		topRight.add(itemDetails);
		
		topLeft.add(subTotalLabel);
		topRight.add(subTotal);
		

		
		//working on bottom
		//*********************************************
		JPanel bottom = new JPanel();
		bottom.setBounds(0, HEIGHT/2, WIDTH, (HEIGHT/2)-40);
		bottom.setBackground(bottomColor);
		window.add(bottom);
		
		bottom.setLayout(new GridLayout(3,4));
		
		
		findItem = new JButton("Find Item #" + curItem);
		addItem = new JButton("Add item #" + curItem);
		viewCart = new JButton("View Cart");
		checkOut = new JButton("Check Out");
		emptyCart = new JButton("Empty Cart - Start over");
		
		
		exit = new JButton("Exit");
		
		
		findItem.addActionListener(this);
		addItem.addActionListener(this);
		viewCart.addActionListener(this);
		checkOut.setEnabled(false);
		emptyCart.addActionListener(this);
		
		checkOut.addActionListener(this);
		exit.addActionListener(this);
		
		addItem.setEnabled(false);	
		
		viewCart.setEnabled(false);
		

		
		findItem.setPreferredSize(new Dimension(BUTTON_LENGTH, BUTTON_WIDTH));
		viewCart.setPreferredSize(new Dimension(BUTTON_LENGTH, BUTTON_WIDTH));
		emptyCart.setPreferredSize(new Dimension(BUTTON_LENGTH, BUTTON_WIDTH));
		addItem.setPreferredSize(new Dimension(BUTTON_LENGTH, BUTTON_WIDTH));
		checkOut.setPreferredSize(new Dimension(BUTTON_LENGTH, BUTTON_WIDTH));
		exit.setPreferredSize(new Dimension(BUTTON_LENGTH, BUTTON_WIDTH));
		
		bottom.add(findItem);
		bottom.add(addItem);
		bottom.add(viewCart);
		bottom.add(checkOut);
		bottom.add(emptyCart);
		
		
		bottom.add(exit);
		
		//*********************************************
		
		window.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	
		
		
		
		if(e.getSource() == findItem) {
			
			findItem find = new findItem();

			itemDetailsLabel.setText("Item details for item #" + (curItem) + "\n");

			find.actionPerformed(e);
		}
		
		
		else if(e.getSource() == viewCart) {
			
			viewCart view = new viewCart();
			view.actionPerformed(e);
			
		}
		
		else if(e.getSource() == emptyCart) {
			
			emptyCart empty = new emptyCart();
			empty.actionPerformed(e);
					
				}
				
		else if(e.getSource() == addItem) {
			
			addItem add = new addItem();
			//updating the buttons
			totalItem++;
			curItem++;
			itemInputLable.setText("Please enter item ID for item #" + curItem + "\n");
			itemQuantLabel.setText("Please enter the quantity of item #" + curItem+"\n");
			subTotalLabel.setText("Subtotal of "+totalItem+" item(s)\n");
			findItem.setText("Find Item #" + curItem);
			addItem.setText("Add item #" + curItem);
			
			
			
			
			add.actionPerformed(e);
			
		}
				
				
				
		else if(e.getSource() == checkOut) {
			
			checkOut check = new checkOut();
			check.actionPerformed(e);
			
		}
		

		
		else if(e.getSource() == exit) {
			//we dont need a class for just one line of code. that would be silly!
			System.exit(0);
		}
		
		

		
	}
	
	private Double calcPrice(int quant, Double price) {
		Double totalPrice = 0.0;
		
		if(quant <= 4) {	//4 or less items
			totalPrice = quant * price;
			return totalPrice;
		}
		
		else if( quant >= 5 && quant <= 9) {	//betwren 5 and 9 items
			totalPrice = (quant * price) * 0.90;
			return totalPrice;
		}
		
		
		else if( quant >= 10 && quant <= 14) {	//between 10 and 14 items
			
			totalPrice = (quant * price) * 0.85;
			return totalPrice;
			
		}
		
		else {	//greater then 15 items
			
			totalPrice = (quant * price) * 0.80;
			return totalPrice;
			
		}
	}
	
	
	private String returnDiscount(int quant, Double price) {
		Double totalPrice = 0.0;
		
		if(quant <= 4) {	//4 or less items
			return "%0";
		}
		
		else if( quant >= 5 && quant <= 9) {	//betwren 5 and 9 items
			return "%10";
		}
		
		
		else if( quant >= 10 && quant <= 14) {	//between 10 and 14 items
			return "%15";
			
			
		}
		
		else {	//greater then 15 items
			return "%20";
			
			
		}
	}
	
	private class addItem implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			//re enable the buttons
			addItem.setEnabled(true);	
			findItem.setEnabled(true);
			checkOut.setEnabled(true);
			viewCart.setEnabled(true);
			
			//we will made the item object in the find item class. we need to move it to this class now
			String subTotal2 = subTotalAll.toString();
			subTotal.setText("$ " + subTotal2);
			
			
			cart.add(i1);	//we found iten N in the list and added it to the caet
			
			JOptionPane.showMessageDialog(null, "Item #: " + (curItem - 1) + ", ID:" + ID + " added to cart", "Item Confirmed", JOptionPane.INFORMATION_MESSAGE);
			
			//reset the text boxes
			itemInput.setText(null);
			inputQuant.setText(null);
			
			addItem.setEnabled(false);
			
		}

	}
	
	
	private class findItem implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e)   {
			
			//redudanct checking. if the user has inouted only one valur or inputs a string
			
		
			
			String line = "";
			
			BufferedReader readIn;
			
			//the id is stored in the item input text
			ID = itemInput.getText();
			
			Boolean found = false;
			
			
			try {	//if unable to find file
				readIn = new BufferedReader(new FileReader(File));
				
				line = readIn.readLine();
				
				
				while(line != null) {
					
					String[] lineSep = line.split(",");	//splits the string into an array by csv
			
					if(lineSep[0].equals(ID)) {	//if the id is found, we will add it to the cart
						
						
						//we need to do some checks. if the item is in stock, if the user enters a valid amount of numbers
						
						//remove ths spaces from any non string varibles
						lineSep[2] = lineSep[2].replaceAll("\\s", "");
						lineSep[3] = lineSep[3].replaceAll("\\s", "");
						lineSep[4] = lineSep[4].replaceAll("\\s", "");
						
						//convert from string to respected values
					    Double price = Double.parseDouble(lineSep[4]);
						
						boolean inStock = Boolean.parseBoolean(lineSep[2]); //convert to a bool
						int amount = Integer.parseInt(lineSep[3]);	//converting the item amount to a int
						
						
						int quant = Integer.parseInt(inputQuant.getText());	//the amount the user requested
						
						String name = lineSep[1];
						
						//fixing he bool value

						
						if(inStock != false) {	//if the item is in stock
							
							inHand = line;	//the current value you have in had is equal to the line found
							
							//set the details line to the item values
							
							Double totalPrice = 0.0;
						    
						    //amount the user wants
							
						    
						    //determie the price with any discounts
						    totalPrice = calcPrice(quant,price);
						    
						    String priceDiscount = returnDiscount(quant,price);
						    
						    i1 = new items(ID,name,inStock,amount,price,quant,priceDiscount,totalPrice);
							
							
							
						
							//convet the price to a double
							subTotalAll += totalPrice;
							
							subTotalAll = Math.round(subTotalAll * 100) /100.0;	//used to round to nearest deicmal
							
							//converty the new subtotal doulbe to a string ti display
							
							
							
							
							
							
							//check to see if the user inputs a valid amount
							
							if(quant > amount) {	//if they entered too many items
								JOptionPane.showMessageDialog(null,"Insufficent stock. Only " + amount + " on hand.", "Error", JOptionPane.ERROR_MESSAGE);
								
								inputQuant.setText("");
								found = true;
								break;
							}
							
							else {	//if the user request a valid amount of stock
								JOptionPane.showMessageDialog(null,"Item: " + ID + " found", "Item Confirmed", JOptionPane.INFORMATION_MESSAGE);
								
								addItem.setEnabled(true);	
								findItem.setEnabled(false);
								itemDetails.setText(i1.toString());
								
								found = true;
								break;
							}
							
						}
						
						else {	//if we habe the item but its not in stock. 
							JOptionPane.showMessageDialog(null,"Item " + ID + " is not in stock", "Error", JOptionPane.ERROR_MESSAGE);
							
							
							found = true;
							break;

						}

					}
					
					//set to the next line
					line = readIn.readLine();
				}
				
				if(found!= true) {
					JOptionPane.showMessageDialog(null,"Item " + ID + " is not in file", "Error", JOptionPane.ERROR_MESSAGE);
				}

				
			} catch (FileNotFoundException a) {	//cant find file
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null,"Unable to find file", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
			catch(IOException a){	//reading from file erroe
				JOptionPane.showMessageDialog(null, "Problem reading from file","Error",JOptionPane.ERROR_MESSAGE);
			}
			
			
		}
		
		
		
		
	}
	
	private class viewCart implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			int i = 1;
			
			String inCart = "";
			
			for(items order: cart) {
				inCart +=  "\n" +i +". " + order.toString();
				i++;
			}

			JOptionPane.showMessageDialog(null,"Your cart:" + inCart, "Cart", JOptionPane.INFORMATION_MESSAGE);
			
			
			}
			
		}

	private class emptyCart implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			cart.clear();
			
			
			JOptionPane.showMessageDialog(null,"Cart was sucessufly emptied", "Empty Cart", JOptionPane.INFORMATION_MESSAGE);
			
			addItem.setEnabled(false);	
			checkOut.setEnabled(false);
			viewCart.setEnabled(false);
			itemDetails.setText("");
			subTotal.setText("");
			
			
			
			curItem = 1;
			totalItem = 1;
			
			itemInputLable.setText("Please enter item ID for item #" + curItem + "\n");
			itemQuantLabel.setText("Please enter the quantity of item #" + curItem+"\n");
			itemDetailsLabel.setText("Item details for item #1 \n");
			subTotalLabel.setText("Subtotal of 0 item(s)\n");
			findItem.setText("Find Item #" + curItem);
			addItem.setText("Add item #" + curItem);
			findItem.setEnabled(true);
		}
		
	}
	
	
	private class checkOut implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			
			TimeZone defaultTimeZone = TimeZone.getDefault();
	        
	        // Get the time zone abbreviation
	        String abbreviation = defaultTimeZone.getDisplayName(false, TimeZone.SHORT);
	        
	        // Create a SimpleDateFormat object with the desired format
			 Date currentDate = new Date(System.currentTimeMillis());
			 
			 
			 SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy, hh:mm:ss a");

		        // Format the date and time and print it
		     String formattedDateTime = sdf.format(currentDate);
		     
		     LocalDateTime currentDateTime = LocalDateTime.now();

		        // Define the desired format pattern
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");

		        // Format the current date and time using the pattern
		        String formattedDateTime2 = currentDateTime.format(formatter);
		     
		     
		     String header = "Date: " + formattedDateTime +" " + abbreviation+ "\n \n" +  "Number of items: " + (curItem-1) + "\n \n Item# / ID / TItle / Price / Qty / Disc % / Subtotal: \n \n";
		     
		     //creating the area with the items
		     int i  = 1;
		     String inCart = "";
				
				for(items order: cart) {
					inCart +=  "\n" +i +". " + order.toString();
					i++;
				}
				
			
				
				
			Double taxAmount = subTotalAll * 0.06;	//6% tax rate
			
			taxAmount = Math.round(taxAmount * 100) /100.0;	//used to round to nearest deicmal
			
			Double orderTotal = taxAmount+subTotalAll;
			
			orderTotal = Math.round(orderTotal * 100) / 100.0;
				
			String totals = " \n \n Order subtotal: $" + subTotalAll + " \n \n Tax Rate: 	6%" + " \n \n  Tax Amount: $" + taxAmount + 
					"\n \n Order Total: $" + orderTotal + "\n \n Thanks for shopping at Nile.com!";		     
			
			JOptionPane.showMessageDialog(null, header + inCart + totals, "Nile.com - Final Invoice", JOptionPane.INFORMATION_MESSAGE);
			
			FileWriter fileWriter;
			try {
				fileWriter = new FileWriter("transactions.csv", true);
			    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
	            PrintWriter printWriter = new PrintWriter(bufferedWriter);

					String line = "";
					for(items order: cart) {
						line = order.makeCSV();
						printWriter.println(formattedDateTime2 + ", " + line + " " + formattedDateTime + " " + abbreviation + "");
					}
					
					printWriter.println("");
					
					printWriter.close();//close the writer
				
					
					
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        
				
				addItem.setEnabled(false);	
				findItem.setEnabled(false);
				checkOut.setEnabled(false);
				
		
			
		}
		
	}
	
	
}


