import java.util.ArrayList;

public class MatrixDriver2 {
	public static void main(String args[]){
		ArrayList<ElementaryRowOperation> changes = new ArrayList<ElementaryRowOperation>();
		Matrix check = new Matrix("1 8 -8 5 -3 -3 -4 4 1",3,3);
		Matrix id = new Matrix("1 0 0 0 1 0 0 0 1",3,3);
		
		changes.add(new ElementaryRowOperation(0,1,-5));
		changes.add(new ElementaryRowOperation(0,2,4));
		changes.add(new ElementaryRowOperation(1,-(double)1/43));
		changes.add(new ElementaryRowOperation(1,2,-36));
		changes.add(new ElementaryRowOperation(2,(double) -43));
		changes.add(new ElementaryRowOperation(2,1,(double)37/43));
		changes.add(new ElementaryRowOperation(2,0,8));
		changes.add(new ElementaryRowOperation(1,0,-8));
		
		while(!changes.isEmpty()){
			ElementaryRowOperation current = changes.remove(0);
			
			switch(current.type){
			case 1:
				id.multiplyRowbyScalar(current.firstRow, current.multiple);
				break;
			
			case 2:
				id.swapRows(current.firstRow, current.secondRow);
				break;
			
			case 3:
				id.AddMultipleOfRow(current.firstRow, current.secondRow, current.multiple);
			}
			
			System.out.println(id);
		}
	}
}
