import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

public class MatrixDriverGUI2 extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JPanel stuff, save, scalarMult;
	private JTextArea messages, matrix1, matrix2;
	private JPanel opening, makeMatrix,loadMatrix, doOperation;
	private JTextField row, column, matrix,name, enterFile,saveToFile,m1,m2, newName, multiplier;
	private JButton makeM, loadM, make, contuniue, loadFile, makeM2, contuniue2,saveButton,perform, addAnswer;
	private CardLayout card;
	private MatrixArray array=new MatrixArray();
	private JComboBox<String> operations;
	private Matrix answer=null;
	private JLabel scalar;
	private String[] oper = {"Select an Operation", "Find the determinant", "Find the inverse", 
			"Find the adjoint", "Find the transpose", "Multiply by a scalar", 
			"Find the corresponding cartesian product of homogenous linear functions in multiple variables "
			+ "written in a form appropriate for Latex", 
			"Find the reduced row echelon matrix.", 
			"If you downloaded an augmented matrix then reduce the coefficient matrix."};;
	
	public MatrixDriverGUI2()
	{
		super();
		setLayout(new GridLayout(3,1));
		
		//set up stuff
		stuff= new JPanel();
		
		//Set up Layout
		card= new CardLayout();
		stuff.setLayout(card);
		
		//set up opening
		opening= new JPanel();
		makeM= new JButton("Click here to make a new matrix");
		makeM.addActionListener
	    (
	    	new ActionListener()
	    	{
	            public void actionPerformed(ActionEvent event)
	            {
	            	card.show(stuff, "makeMatrix");
	            }
	        }	
	    );
		loadM= new JButton("Click here to load a matrices");
		loadM.addActionListener
	    (
	    	new ActionListener()
	    	{
	            public void actionPerformed(ActionEvent event)
	            {
	            	card.show(stuff, "loadMatrix");
	            }
	        }	
	    );
		opening.add(makeM);
		opening.add(loadM);
		
		
		//set up makeMatrix
		makeMatrix=new JPanel();
		makeMatrix.setLayout(new GridLayout(6,1));
		String[] numbers= new String[16];
		for(int i=0; i<numbers.length; i++)
		{
			numbers[i]= i + "";
		}
		row= new JTextField("Enter the number of rows here");
		column= new JTextField("Enter the number of columns here");
		matrix= new JTextField("Enter the elements here");
		name= new JTextField("Enter the name of the Matrix");
		make= new JButton("Make the Matrix");
		make.addActionListener
	    (
	    	new ActionListener()
	    	{
	            public void actionPerformed(ActionEvent event)
	            {
	            	String elements= matrix.getText();
	            	int rows=Integer.parseInt(row.getText());
	            	int columns= Integer.parseInt(column.getText());
	            	Matrix temp= new Matrix(elements,rows,columns);
	            	print(temp.toString());
	            	array.addMatrix(name.getText(), temp);
	            	row.setText("Enter the number of rows here");
	            	column.setText("Enter the number of columns here");
	            	matrix.setText("Enter the elements here");
	            	name.setText("Enter the name of the Matrix");
	            }
	        }	
	    );
		contuniue= new JButton("Click here to continue on");
		contuniue.addActionListener
	    (
	    	new ActionListener()
	    	{
	            public void actionPerformed(ActionEvent event)
	            {
	            	print("Your options of arrays are " + array.printtKeys()+ "\n");
	            	card.show(stuff, "doOperation");
	            }
	        }	
	    );
		makeMatrix.add(row);
		makeMatrix.add(column);
		makeMatrix.add(matrix);
		makeMatrix.add(name);
		makeMatrix.add(make);
		makeMatrix.add(contuniue);
		
		//set up load matrix
		loadMatrix=new JPanel();
		loadMatrix.setLayout(new GridLayout(4,1));
		enterFile= new JTextField("Enter the file to load from here");
		loadFile= new JButton("Click here to load the matrices in this file");
		loadFile.addActionListener
	    (
	    	new ActionListener()
	    	{
	            public void actionPerformed(ActionEvent event)
	            {
	            	try {
						array.fillFromFile(new File(enterFile.getText()));
					} catch (IOException e) {
						e.printStackTrace();
					}
	            	print(array.toString());
	            }
	        }	
	    );
		makeM2= new JButton("Click here to make a new matrix");
		makeM2.addActionListener
	    (
	    	new ActionListener()
	    	{
	            public void actionPerformed(ActionEvent event)
	            {
	            	card.show(stuff, "makeMatrix");
	            }
	        }	
	    );
		contuniue2= new JButton("Click here to continue on");
		contuniue2.addActionListener
	    (
	    	new ActionListener()
	    	{
	            public void actionPerformed(ActionEvent event)
	            {
	            	print("Your options of arrays are " + array.printtKeys()+ "\n");
	            	card.show(stuff, "doOperation");
	            }
	        }	
	    );
		loadMatrix.add(enterFile);
		loadMatrix.add(loadFile);
		loadMatrix.add(makeM2);
		loadMatrix.add(contuniue2);
		
		//set up the operation area
		doOperation= new JPanel();
		doOperation.setLayout(new GridLayout(3,3));
		matrix1= new JTextArea();
		matrix1.setEditable(false);
		m1= new JTextField();
		m1.addActionListener
	    (
	    	new ActionListener()
	    	{
	            public void actionPerformed(ActionEvent event)
	            {
	            	SwingUtilities.invokeLater
	        		(
	        			new Runnable()
	        			{
	        	            public void run()
	        	            {
	        	            	matrix1.setText("");
	        	            	matrix1.append(array.get(m1.getText()).toString());
	        		        }
	        		    }
	        		);
	            }
	        }	
	    );
		matrix2= new JTextArea();
		matrix2.setEditable(false);
		m2= new JTextField();
		m2.addActionListener
	    (
	    	new ActionListener()
	    	{
	            public void actionPerformed(ActionEvent event)
	            {
	            	SwingUtilities.invokeLater
	        		(
	        			new Runnable()
	        			{
	        	            public void run()
	        	            {
	        	            	matrix2.setText("");
	        	            	matrix2.append(array.get(m2.getText()).toString());
	        		        }
	        		    }
	        		);
	            }
	        }	
	    );
	
		operations= new JComboBox<String>(oper);
		perform= new JButton("Click here to perform an operation");
		perform.addActionListener
	    (
		    	new ActionListener()
		    	{
		            public void actionPerformed(ActionEvent event)
		            {
		            	String choice = (String) operations.getSelectedItem();
		            	
		            	if(choice.equals(oper[1]))
		            	{
		            		print("The determinant is " + array.get(m1.getText()).determinant());
		            	}
		            	else if(choice.equals(oper[2]))
		            	{
		            		answer= array.get(m1.getText()).Inverse();
		            		print("The inverse is \n" + answer);
		            	}
		            	else if(choice.equals(oper[3]))
		            	{
		            		answer= array.get(m1.getText()).Adjoint();
		            		print("The adjoint is \n" + answer);
		            	}
		            	else if(choice.equals(oper[4]))
		            	{
		            		answer= array.get(m1.getText()).Transpose();
		            		print("The transpose is \n" + answer);
		            	}
		            	else if(choice.equals(oper[5]))
		            	{
		            		answer= array.get(m1.getText()).MultiplyByScalar(Double.parseDouble(multiplier.getText()));
		            		print("The Matrix multiplied by " + multiplier.getText() + " is \n" + answer);
		            	}
		            	else if(choice.equals(oper[6]))
		            	{
		            		print("The answer is" + array.get(m1.getText()).latexEquation());
		            	}
		            	else if(choice.equals(oper[7]))
		            	{
		            		answer= array.get(m1.getText()).reduceMatrix();
		            		print("The reduced row echolan form of this matrix is \n" + answer);
		            	}
		            	else if(choice.equals(oper[8]))
		            	{
		            		answer= array.get(m1.getText()).reduceCoefficientMatrix();
		            		print("The reduced coefficent matrix is \n" + answer);
		            	}
		            	else
		            		print("This is an invalid operation");
		            	print("Your options of arrays are " + array.printtKeys()+ "\n");
		            }
		            
		        }	
		);
		newName= new JTextField("Enter the name you want your new Matrix to have");
		addAnswer = new JButton("Click here to add the answer to your current Matrices");
		addAnswer.addActionListener
	    (
		    	new ActionListener()
		    	{
		            public void actionPerformed(ActionEvent event)
		            {
		            	if(answer!=null)
		            		array.addMatrix(newName.getText(), answer);
		            }
		        }	
		);
		multiplier= new JTextField("Enter the multiplier here");
		scalar= new JLabel("This box is used only if multipling by a scalar");
		scalarMult=new JPanel();
		scalarMult.setLayout(new GridLayout(3,1));
		scalarMult.add(scalar);
		scalarMult.add(multiplier);
		doOperation.add(m1);
		doOperation.add(operations);
		doOperation.add(m2);
		doOperation.add(matrix1);
		doOperation.add(perform);
		doOperation.add(matrix2);
		doOperation.add(addAnswer);
		doOperation.add(newName);
		doOperation.add(scalarMult);
		
		//Set up the message area
		messages=new JTextArea();
		messages.setEditable(false);
		JScrollPane messageArea = new JScrollPane(messages);
		
		//add to stuff
		stuff.add(opening,"opening");
		stuff.add(makeMatrix,"makeMatrix");
		stuff.add(loadMatrix, "loadMatrix");
		stuff.add(doOperation, "doOperation");
		card.show(stuff, "opening");
		
		//Set up save panel
		save= new JPanel();
		save.setLayout(new GridLayout(1,2));
		saveToFile= new JTextField("Enter the file you wish to save to here");
		saveButton= new JButton("Click here to save the matrices");
		saveButton.addActionListener
	    (
	    	new ActionListener()
	    	{
	            public void actionPerformed(ActionEvent event)
	            {
	            	try {
						array.writeToFile(new File(saveToFile.getText()));
					} catch (IOException e) {
						e.printStackTrace();
					}
	            }
	        }	
	    );
		
		save.add(saveToFile);
		save.add(saveButton);
		
		//add all to panel
		add(stuff);
		add(messageArea);
		add(save);
	}
	
	public static void main(String[] args)
	{
		JFrame frame= new JFrame();
		frame.setSize(700,700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new MatrixDriverGUI2());
		frame.setVisible(true);
	}
	
	private void print(final String text)
	{
		SwingUtilities.invokeLater
		(
			new Runnable()
			{
	            public void run()
	            {
	            	messages.append(text + "\n");
		        }
		    }
		);
	 }
}
