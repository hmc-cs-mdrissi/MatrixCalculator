package chapter3;
import java.text.DecimalFormat;
import java.util.*;
import java.io.*;

public class Matrix {
	public double[][] MatrixValues;
	int rows;
	int columns;
	private String name;
	
	public Matrix(int rows, int columns){
		MatrixValues = new double[rows][columns];
		this.rows = rows;
		this.columns = columns;
	}
	
	public Matrix(File fileName, int rows, int columns, String name) throws IOException{
		Scanner input = new Scanner(fileName);
		MatrixValues = new double[rows][columns];
		this.rows = rows;
		this.columns = columns;
		this.name = name;
		
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				MatrixValues[i][j] = input.nextDouble();
			}
		}
		
		input.close();
	}
	
	public Matrix(String elements, int rows, int columns, String name){
		Scanner input = new Scanner(elements);
		input.useDelimiter("[ ,]+");
		MatrixValues = new double[rows][columns];
		this.rows = rows;
		this.columns = columns;
		this.name = name;
		
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				MatrixValues[i][j] = input.nextDouble();
			}
		}
		
		input.close();
	}
	
	public Matrix(File fileName, int rows, int columns) throws IOException{
		Scanner input = new Scanner(fileName);
		MatrixValues = new double[rows][columns];
		this.rows = rows;
		this.columns = columns;
		
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				MatrixValues[i][j] = input.nextDouble();
			}
		}
		
		input.close();
	}
	
	public Matrix(String elements, int rows, int columns){
		Scanner input = new Scanner(elements);
		input.useDelimiter("[ ,]+");
		MatrixValues = new double[rows][columns];
		this.rows = rows;
		this.columns = columns;
		
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				MatrixValues[i][j] = input.nextDouble();
			}
		}
		
		input.close();
	}
	
	public Matrix(int size){
		rows = size;
		columns = size;
		
		for(int i = 0; i < rows; i++){
			MatrixValues[i][i] = 1;
		}
	}
	
	public Matrix Random(){
		int numberOfRows = (int)(Math.random()*10)+1;
		int numberOfColumns = (int)(Math.random()*10)+1;
		Matrix rand = new Matrix(numberOfRows,numberOfColumns);
		
		for(int i = 0; i < rand.rows; i++){
			for(int j = 0; j < rand.columns; j++){
				rand.MatrixValues[i][j] = Math.random()*100;
				if(Math.random() > 0.5){
					rand.MatrixValues[i][j] *= -1;
				}
			}
		}
		
		return rand;
	}
	
	public double trace(){
		double sum = 0;
		int diagonalLength = Math.min(rows, columns);
		
		for(int i = 0; i < diagonalLength; i++){
			sum += MatrixValues[i][i];
		}
		
		return sum;
	}
	
	public Matrix Transpose(){
		Matrix transpose = new Matrix(this.rows,this.columns);
		
		for(int i = 0; i < this.rows; i++){
			for(int j = 0; j < this.columns; j++){
				transpose.MatrixValues[i][j] = this.MatrixValues[j][i];
			}
		}
		
		return transpose;
	}
	
	public Matrix addMatrix(Matrix other){
		if(other.rows != this.rows || other.columns != this.columns){
			System.out.println("You can't add two matrices that have differnent dimensions.");
			return null;
		}
		
		Matrix result = new Matrix(rows,columns);
		
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				result.MatrixValues[i][j] = this.MatrixValues[i][j] + other.MatrixValues[i][j];
			}
		}
		
		return result;
	}
	
	public Matrix multiplyMatrix(Matrix other){
		if(this.columns != other.rows){
			System.out.println("You can't multiply two matrices if the second does not have rows equal to the columns of the first.");
			return null;
		}
		
		Matrix result = new Matrix(rows,columns);
		
		for(int i = 0; i < this.rows; i++){
			for(int j = 0; j < other.columns; j++){
				int sum = 0;
				
				for(int k = 0; k < this.columns; k++){
					sum += this.MatrixValues[i][k]*other.MatrixValues[k][j];
				}
				
				result.MatrixValues[i][j] = sum;
			}
		}
		
		return result;
	}
	
	public double determinant(){
		if(rows != columns){
			System.out.println("You can't find the determinant of a matrix that isn't a square matrix.");
			return 0;
		}
		
		double det = 1;
		
		Matrix reduced = this.clone();
		
		for(int i = 0; i < rows; i++){
			if(reduced.checkIfRowZero((i))){
				return 0;
			}
			
			if(reduced.MatrixValues[i][i] == 0){
				for(int j = rows - 1; j > i; j--){
					if(reduced.MatrixValues[j][i] != 0){
						reduced.swapRows(i,j);
						det *= -1;
						break;
					}
				}
				
				continue;
			}
			
			det *= 1/reduced.MatrixValues[i][i];
			reduced.multiplyRowbyScalar(i,1/reduced.MatrixValues[i][i]);
			
			
			for(int j = i+1; j < rows; j++){
				reduced.AddMultipleOfRow(i, j, -1*reduced.MatrixValues[j][i]);
			}
		}
		
		return det;
	}
	
	public double cofactor(int i, int j){
		if(MinorMatrix(i,j).determinant() == -0.0){
			return 0;
		}
		
		return MinorMatrix(i,j).determinant()*Math.pow(-1, i+j);
	}
	
	public Matrix Adjoint(){
		if(rows != columns){
			System.out.println("You can't make make the adjoint of a matrix that isn't a square matrix.");
			return null;
		}
		
		Matrix Adjoint = new Matrix(this.rows,this.columns);
		
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				Adjoint.MatrixValues[i][j] = cofactor(j,i);
			}
		}
		
		return Adjoint;
	}
	
	//Make more efficient.
	public Matrix Inverse(){
		if(rows != columns){
			System.out.println("You can't make make the adjoint of a matrix that isn't a square matrix.");
			return null;
		}
		
		if(determinant() == 0){
			System.out.println("You can't find the inverse of a matrix whose determinant is zero.");
			return null;
		}
		
		return Adjoint().MultiplyByScalar((double) 1 / determinant());
	}
	
	public Matrix MultiplyByScalar(double scalar){
		Matrix multiple = new Matrix(this.rows,this.columns);
		
		for(int i = 0; i < this.rows; i++){
			for(int j = 0; j < this.columns; j++){
				multiple.MatrixValues[i][j] = this.MatrixValues[i][j]*scalar;
			}
		}
		
		return multiple;
	}
	
	public Matrix MinorMatrix(int ignoredRow, int ignoredColumn){
		Matrix Minor = new Matrix(this.rows-1,this.columns-1);
		
		for(int i = 0; i < this.rows; i++){
			for(int j = 0; j < this.columns; j++){
				if(i != ignoredRow && j != ignoredColumn){
					if(i < ignoredRow && j < ignoredColumn){
						Minor.MatrixValues[i][j] = this.MatrixValues[i][j];
					} else if(i < ignoredRow){
						Minor.MatrixValues[i][j-1] = this.MatrixValues[i][j];
					} else if(j < ignoredColumn){
						Minor.MatrixValues[i-1][j] = this.MatrixValues[i][j];
					} else {
						Minor.MatrixValues[i-1][j-1] = this.MatrixValues[i][j];
					}
				}
			}
		}
		
		return Minor;
	}
	
	public boolean checkIfSymmetric(){
		return this.equals(this.Transpose());
	}
	
	public Matrix reduceMatrix(){
		Matrix reduced = this.clone();
		
		for(int i = 0; i < rows && i < columns; i++){
			if(reduced.checkIfRowZero((i))){
				for(int j = rows-1; j > i; j--){
					if(!reduced.checkIfRowZero(j)){
						reduced.swapRows(i, j);
						break;
					}
				}
				
				continue;
			}
			
			if(reduced.MatrixValues[i][i] == 0){
				for(int j = rows - 1; j > i; j--){
					if(reduced.MatrixValues[j][i] != 0){
						reduced.swapRows(i,j);
						break;
					}
				}
				
				continue;
			}
			
			reduced.multiplyRowbyScalar(i,1/reduced.MatrixValues[i][i]);
			
			
			for(int j = i+1; j < rows; j++){
				reduced.AddMultipleOfRow(i, j, -1*reduced.MatrixValues[j][i]);
			}
		}
		
		if(rows >= columns){
			for(int i = columns-1; i > 0; i--){
				if(reduced.MatrixValues[i][i] == 0){
					continue;
				}
				
				for(int j = i-1; j >= 0; j--){
					reduced.AddMultipleOfRow(i, j, -1*reduced.MatrixValues[j][i]);
				}
			}
		} else {
			for(int i = columns-1; i > 0; i--){
				if(i > rows-1){
					if(reduced.MatrixValues[rows-1][i] == 0){
						continue;
					}
					
					reduced.multiplyRowbyScalar(rows-1,1/reduced.MatrixValues[rows-1][i]);
					
					for(int j = rows-2; j >= 0; j--){
						reduced.AddMultipleOfRow(rows-1, j, -1*reduced.MatrixValues[j][i]);
					}
				} else {
					if(reduced.MatrixValues[i][i] == 0){
						continue;
					}
					
					for(int j = i-1; j >= 0; j--){
						reduced.AddMultipleOfRow(i, j, -1*reduced.MatrixValues[j][i]);
					}
				}
			}
		}
		
		return reduced;
	}
	
	public Matrix RemoveLastColumn(){
		Matrix Minor = new Matrix(this.rows,this.columns-1);
		
		for(int i = 0; i < Minor.rows; i++){
			for(int j = 0; j < Minor.columns; j++){
				Minor.MatrixValues[i][j] = this.MatrixValues[i][j];
			}
		}
		
		return Minor;
	}
	
	
	public Matrix reduceCoefficientMatrix(){
		Matrix result = this.clone();
		Matrix coefficient = result.RemoveLastColumn();
		ArrayList<ElementaryRowOperation> sequenceOfActions = new ArrayList<ElementaryRowOperation>();
		
		for(int i = 0; i < rows; i++){
			if(coefficient.checkIfRowZero((i))){
				for(int j = rows-1; j > i; j--){
					if(!coefficient.checkIfRowZero(j)){
						sequenceOfActions.add(new ElementaryRowOperation(i,j));
						coefficient.swapRows(i, j);
						break;
					}
				}
				
				continue;
			}
			
			if(coefficient.MatrixValues[i][i] == 0){
				for(int j = rows - 1; j > i; j--){
					if(coefficient.MatrixValues[j][i] != 0){
						sequenceOfActions.add(new ElementaryRowOperation(i,j));
						coefficient.swapRows(i,j);
						break;
					}
				}
				
				continue;
			}
			
			sequenceOfActions.add(new ElementaryRowOperation(i,1/coefficient.MatrixValues[i][i]));
			coefficient.multiplyRowbyScalar(i,1/coefficient.MatrixValues[i][i]);
			
			
			for(int j = i+1; j < rows; j++){
				sequenceOfActions.add(new ElementaryRowOperation(i, j, -1*coefficient.MatrixValues[j][i]));
				coefficient.AddMultipleOfRow(i, j, -1*coefficient.MatrixValues[j][i]);
			}
		}
		
		if(rows >= columns){
			for(int i = columns-1; i > 0; i--){
				if(coefficient.MatrixValues[i][i] == 0){
					continue;
				}
				
				for(int j = i-1; j >= 0; j--){
					sequenceOfActions.add(new ElementaryRowOperation(i, j, -1*coefficient.MatrixValues[j][i]));
					coefficient.AddMultipleOfRow(i, j, -1*coefficient.MatrixValues[j][i]);
				}
			}
		} else {
			for(int i = columns-1; i > 0; i--){
				if(i > rows-1){
					if(coefficient.MatrixValues[rows-1][i] == 0){
						continue;
					}
					
					sequenceOfActions.add(new ElementaryRowOperation(rows-1,1/coefficient.MatrixValues[rows-1][i]));
					coefficient.multiplyRowbyScalar(rows-1,1/coefficient.MatrixValues[rows-1][i]);
					
					for(int j = rows-2; j >= 0; j--){
						sequenceOfActions.add(new ElementaryRowOperation(rows-1, j, -1*coefficient.MatrixValues[j][i]));
						coefficient.AddMultipleOfRow(rows-1, j, -1*coefficient.MatrixValues[j][i]);
					}
				} else {
					if(coefficient.MatrixValues[i][i] == 0){
						continue;
					}
					
					for(int j = i-1; j >= 0; j--){
						sequenceOfActions.add(new ElementaryRowOperation(i, j, -1*coefficient.MatrixValues[j][i]));
						coefficient.AddMultipleOfRow(i, j, -1*coefficient.MatrixValues[j][i]);
					}
				}
			}
		}
		
		while(!sequenceOfActions.isEmpty()){
			ElementaryRowOperation current = sequenceOfActions.remove(0);
			
			
			switch(current.type){
				case 1:
					result.multiplyRowbyScalar(current.firstRow, current.multiple);
					break;
				
				case 2:
					result.swapRows(current.firstRow, current.secondRow);
					break;
				
				case 3:
					result.AddMultipleOfRow(current.firstRow, current.secondRow, current.multiple);
			}
		}
			
		return result;
	}
	
	public void printRow(int i){
		for(int j = 0; j < columns; j++){
			System.out.print(MatrixValues[i][j] + " ");
		}
		System.out.println();
	}
	
	public void swapRows(int i, int j){
		for(int k = 0; k < columns; k++){
			double temp = MatrixValues[i][k];
			MatrixValues[i][k] = MatrixValues[j][k];
			MatrixValues[j][k] = temp;
		}
	}
	
	public void multiplyRowbyScalar(int i, double scalar){
		for(int j = 0; j < columns; j++){
			MatrixValues[i][j] *= scalar;
		}
	}
	
	public void AddMultipleOfRow(int rowToAdd, int rowToChange, double scalar){
		for(int j = 0; j < columns; j++){
			MatrixValues[rowToChange][j] += scalar*MatrixValues[rowToAdd][j];
		}
	}
	
	public boolean checkIfRowZero(int i){
		for(int j = 0; j < columns; j++){
			if(MatrixValues[i][j] != 0){
				return false;
			}
		}
		
		return true;
	}
	
	public Matrix clone(){
		Matrix copy = new Matrix(this.rows,this.columns);
		
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				copy.MatrixValues[i][j] = this.MatrixValues[i][j];
			}
		}
		
		return copy;
	}
	
	public boolean equals(Object other){
		if(other != null && other instanceof Matrix){
			Matrix toCompare = (Matrix) other;
			
			if(this.rows != toCompare.rows || this.columns != toCompare.columns){
				return false;
			}
			
			for(int i = 0; i < this.rows; i++){
				for(int j = 0; j < this.columns; j++){
					if(this.MatrixValues[i][j] != toCompare.MatrixValues[i][j]){
						return false;
					}
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	public String toString(){
		String result = "";
		DecimalFormat rounding = new DecimalFormat("0.00");
		
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				double curr = MatrixValues[i][j];
				
				if(curr == -0.0){
					curr = 0.0;
				}
				
				
				result += rounding.format(curr) + "\t";
			}
			
			result += "\n";
		}
		
		return result;
	}
	
	public String latexEquation(){
		String result = "";
		
		for(int i = 0; i < rows; i++){
			result += "\\left( ";
			for(int j = 0; j < columns; j++){
				result += MatrixValues[i][j] + "\\overset{" + j + "}{p}_1";
				
				if(j < columns - 1){
					result += " + ";
				}
			}
			
			result += "\\right) ";
			
			if(i < rows-1){
				result += "\\times ";
			}
		}
		
		return result;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}

class ElementaryRowOperation{
	public int type;
	public double multiple; //Optional
	public int firstRow;
	public int secondRow; //Optional
	
	public ElementaryRowOperation(int rowToMultiply, double c){
		type = 1;
		multiple = c;
		firstRow = rowToMultiply;
	}
	
	public ElementaryRowOperation(int firstToSwap, int secondToSwap){
		type = 2;
		firstRow = firstToSwap;
		secondRow = secondToSwap;
	}
	
	public ElementaryRowOperation(int toAdd, int toChange, double c){
		type = 3;
		multiple = c;
		firstRow = toAdd;
		secondRow = toChange;
	}
}