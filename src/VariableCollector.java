import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ChildPropertyDescriptor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.StructuralPropertyDescriptor;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class VariableCollector extends ASTVisitor {
	static ArrayList<Variable> variables = new ArrayList<Variable>();
	//		static int stmt = 0;
	//		static int type = 0;
	//		static int exp = 0;
	public boolean visit(VariableDeclarationFragment node) {
		Variable variable = new Variable();
		variable.name = ((VariableDeclarationFragment)node).getName().toString();
		variable.type = getVariableType(node.getParent());
		variable.binding = ((VariableDeclarationFragment)node).resolveBinding();
		ASTNode currentNode = node.getParent().getParent();
//		System.out.println(variable.name + " " +variable.type + " "+variable.binding);
		PatchGenerator patchGenerator = PatchGenerator.createPatchGenerator();
		variable.startLine = patchGenerator.compilationUnit.getLineNumber(currentNode.getStartPosition());
		variable.endLine = patchGenerator.compilationUnit.getLineNumber(currentNode.getStartPosition()+currentNode.getLength());
		variables.add(variable);

        return false;
    }

    public boolean visit(MethodDeclaration methodDeclaration) {
        methodDeclaration.accept(new ASTVisitor() {
            public boolean visit(VariableDeclarationFragment var) {
            	Variable variable = new Variable();
        		variable.name = ((VariableDeclarationFragment)var).getName().toString();
        		variable.type = getVariableType(var.getParent());
        		variable.binding = ((VariableDeclarationFragment)var).resolveBinding();
//        		System.out.println(var.getName() + " " +var.getName().isVar());
        		ASTNode currentNode = var.getParent();
        		while(currentNode!= null && currentNode.getNodeType()!=ASTNode.BLOCK) {
        			currentNode = currentNode.getParent();
        		}
        		PatchGenerator patchGenerator = PatchGenerator.createPatchGenerator();
        		variable.startLine = patchGenerator.compilationUnit.getLineNumber(currentNode.getStartPosition());
        		variable.endLine = patchGenerator.compilationUnit.getLineNumber(currentNode.getStartPosition()+currentNode.getLength());
        		variables.add(variable);
 
                return false;
            }
        });
        
        return false;
    }
    
    public String getVariableType(ASTNode node) {
		List list = node.structuralPropertiesForType();
		for (int i = 0; i < list.size(); i++) {
			Object child = node.getStructuralProperty((StructuralPropertyDescriptor) list.get(i));
			if((StructuralPropertyDescriptor) list.get(i) instanceof ChildPropertyDescriptor) {
				ChildPropertyDescriptor childPropertyDescriptor = (ChildPropertyDescriptor) list.get(i);
				if(childPropertyDescriptor.getChildType().toString().equals("class org.eclipse.jdt.core.dom.Type")) {
					return child.toString();
				}
			}
		}
		return "unknown";
	}  
}
