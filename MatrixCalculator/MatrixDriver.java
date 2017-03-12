import java.io.*;
import java.util.Scanner;

public class MatrixDriver {
	public static void main(String args[]) throws IOException{
		int rows = 0;
		int columns = 0;
		String name = "";
		String again;
		String storeChoice;
		MatrixArray multipleMatrices = new MatrixArray();
		
		Scanner input = new Scanner(System.in);
		Scanner numbers = new Scanner(System.in);
		
		
		do{
			System.out.println("Do you want to create an original matrix? (yes or no)");
			String answer = input.next().toLowerCase().substring(0,1);
		
			if(answer.equals("y")){
				System.out.println("How many rows do you want your matrix to be?");
				rows = numbers.nextInt();
			
				System.out.println("How many columns do you want your matrix to be?");
				columns = numbers.nextInt();
			
				input.nextLine(); //Simply done to flush the scanner.
			
				System.out.println("Input the elements of your matrix row by row.");
				String elements = input.nextLine();
				
				System.out.println("What do you want to name your matrix?");
				name = input.nextLine();
			
				multipleMatrices.addMatrix(name,new Matrix(elements,rows,columns,name));
			} else {
				input.nextLine(); //Simply done to flush the scanner.
				
				System.out.println("What file would you like to open?");
				File problem = new File(input.nextLine());
			
				multipleMatrices.fillFromFile(problem);
			}
			
			System.out.println("Do you want to do create any more matrices? (yes or no)");
			again = input.next().toLowerCase().substring(0,1);
		} while(again.equals("y"));
		
		do{
			System.out.println("What do you want to do to your matrix/matrices (input in a number)?\n");
			
			System.out.println("0. View a matrix.");
			System.out.println("1. Find the determinant.");
			System.out.println("2. Find the inverse.");
			System.out.println("3. Find the adjoint.");
			System.out.println("4. Find the transpose.");
			System.out.println("5. Multiply by a scalar.");
			System.out.println("6. Find the corresponding cartesian product of homogenous linear "
					+ "functions in multiple variables written in a form appropriate for latex.");
			System.out.println("7. Find the reduced row echelon matrix.");
			System.out.println("8. If you downloaded an augmented matrix then reduce the coefficient matrix.");
			System.out.println("9. Find the trace of the matrix.");
			System.out.println("10. Multiply two matrices.");
			System.out.println("11. Add two matrices.");
			System.out.println("12. Do you want to save your matrices.");
			
			int choice = numbers.nextInt();
			
			if(choice <= 9){
				input.nextLine();
				
				System.out.println("What matrix would you like to use?");
				String matrixChosen = input.nextLine();
				
				
				switch(choice){
					case 0:
						System.out.println("The matrix you requested to see is:\n");
						System.out.println(multipleMatrices.get(matrixChosen));
						break;
						
					case 1:
						double det = multipleMatrices.get(matrixChosen).determinant();
						System.out.println("The determinant of your matrix is " + det);
						break;
				
					case 2:
						Matrix inverse = multipleMatrices.get(matrixChosen).Inverse();
						
						System.out.println("The inverse matrix to your matrix is:\n");
						System.out.println(inverse);
						
						System.out.println("Would you like to store this matrix? (yes/no)");
						storeChoice = input.next().toLowerCase().substring(0,1);
						
						if(storeChoice.equals("y")){
							System.out.println("What would you like to name the inverse matrix?");
							name = input.nextLine();
							
							multipleMatrices.addMatrix(name,inverse);
						} 
						
						break;
					
					case 3:
						Matrix adj = multipleMatrices.get(matrixChosen).Adjoint();
						System.out.println("The adjoint matrix to your matrix is:\n");
						System.out.println(adj);
						
						System.out.println("Would you like to store this matrix? (yes/no)");
						storeChoice = input.next().toLowerCase().substring(0,1);
						
						if(storeChoice.equals("y")){
							System.out.println("What would you like to name the adjoint matrix?");
							name = input.nextLine();
							
							multipleMatrices.addMatrix(name,adj);
						} 
						
						break;
				
					case 4:
						Matrix transpose = multipleMatrices.get(matrixChosen).Transpose();
						System.out.println("The tranpose matrix to your matrix is:\n");
						System.out.println(transpose);
						
						System.out.println("Would you like to store this matrix? (yes/no)");
						storeChoice = input.next().toLowerCase().substring(0,1);
						
						if(storeChoice.equals("y")){
							System.out.println("What would you like to name the transpose matrix?");
							name = input.nextLine();
							
							multipleMatrices.addMatrix(name,transpose);
						}
						
						break;
				
					case 5:
						System.out.println("What scalar do you want to multiply your matrix by?");
						double scalar = numbers.nextDouble();
						
						Matrix multiple = multipleMatrices.get(matrixChosen).MultiplyByScalar(scalar);
						System.out.println("The result of multiplying your matrix by " + scalar + "is:\n");
						System.out.println(multiple);
						
						System.out.println("Would you like to store this matrix? (yes/no)");
						storeChoice = input.next().toLowerCase().substring(0,1);
						
						if(storeChoice.equals("y")){
							System.out.println("What would you like to name the resulting matrix?");
							name = input.nextLine();
							
							multipleMatrices.addMatrix(name,multiple);
						}
						
						break;
					
					case 6:
						System.out.println("Here is the corresponding cartesian product of linear"
							+ " functions:\n");
						System.out.println(multipleMatrices.get(matrixChosen).latexEquation());
						break;
					
					case 7:
						Matrix reduced = multipleMatrices.get(matrixChosen).reduceMatrix();
						System.out.println("Here is the reduced row echelon matrix of your matrix:\n ");
						System.out.println(reduced);
						
						System.out.println("Would you like to store this matrix? (yes/no)");
						storeChoice = input.next().toLowerCase().substring(0,1);
						
						if(storeChoice.equals("y")){
							System.out.println("What would you like to name the reduced matrix?");
							name = input.nextLine();
							
							multipleMatrices.addMatrix(name,reduced);
						}
						
						break;
				
					case 8:
						Matrix reduced2 = multipleMatrices.get(matrixChosen).reduceCoefficientMatrix();
						System.out.println("Here the coefficient matrix has been changed to reduced row echelon form for your matrix:\n ");
						System.out.println(reduced2);
						
						System.out.println("Would you like to store this matrix? (yes/no)");
						storeChoice = input.next().toLowerCase().substring(0,1);
						
						if(storeChoice.equals("y")){
							System.out.println("What would you like to name the reduced coefficient matrix?");
							name = input.nextLine();
							
							multipleMatrices.addMatrix(name,reduced2);
						}
						
						break;
						
					case 9:
						double trace = multipleMatrices.get(matrixChosen).trace();
						System.out.println("The trace of your matrix is " + trace);
						break;
					
					default:
						System.out.println("Please choose a valid choice in the future if you want to"
							+ " do something to your matrix.");
				}
			} else if(choice <= 11){
				input.nextLine();
				
				System.out.println("What is the first matrix you would like to use?");
				String matrixChosen1 = input.nextLine();
				
				System.out.println("What is the second matrix you would like to use?");
				String matrixChosen2 = input.nextLine();
				
				if(choice == 10){
					Matrix product =  multipleMatrices.get(matrixChosen1).multiplyMatrix(multipleMatrices.get(matrixChosen2));
					System.out.println("Here is the sum of the two matrices that you chose:\n");
					System.out.println(product);
					
					System.out.println("Would you like to store this matrix? (yes/no)");
					storeChoice = input.next().toLowerCase().substring(0,1);
					
					if(storeChoice.equals("y")){
						System.out.println("What would you like to name the resulting matrix?");
						name = input.nextLine();
						
						multipleMatrices.addMatrix(name,product);
					}
					
				} else {
					Matrix sum =  multipleMatrices.get(matrixChosen1).addMatrix(multipleMatrices.get(matrixChosen2));
					System.out.println("Here is the sum of the two matrices that you chose:\n");
					System.out.println(sum);
					
					System.out.println("Would you like to store this matrix? (yes/no)");
					storeChoice = input.next().toLowerCase().substring(0,1);
					
					if(storeChoice.equals("y")){
						System.out.println("What would you like to name the resulting matrix?");
						name = input.nextLine();
						
						multipleMatrices.addMatrix(name,sum);
					}
				}
				
			} else if(choice == 12){
				input.nextLine();

				System.out.println("What do you want to save the file as?");
				String fileName = input.nextLine() + ".matrix";

				multipleMatrices.writeToFile(new File(fileName));
			} else {
				System.out.println("Please choose a valid choice in the future if you want to"
						+ " do something to your matrix.");
			}
			
			System.out.println("Do you want to do any more actions to your matrix? (yes or no)");
			again = input.next().toLowerCase().substring(0,1);
		}while(again.equals("y"));
		
		input.close();
		numbers.close();
	}
}