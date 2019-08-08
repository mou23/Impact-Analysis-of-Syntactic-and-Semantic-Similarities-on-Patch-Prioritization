import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;


import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.QualifiedName;

public class CompatibilityChecker {
	private static CompatibilityChecker compatibilityChecker;
	PatchGenerator patchGenerator;
	
	private CompatibilityChecker() {
		this.patchGenerator = PatchGenerator.createPatchGenerator();
	}
	
	public static CompatibilityChecker createCompatibilityChecker() {
		if(compatibilityChecker == null){
			compatibilityChecker = new CompatibilityChecker();
		}

		return compatibilityChecker;
	}

	public boolean checkCompatibility(ASTNode faultyNode, FixingIngredient fixingIngredient, String operation) {
		//		CompatibilityChecker.compatible = false;
		HashSet<Variable> variablesToBeIgnored = new HashSet<Variable>();
//		System.out.println("Fault "+faultyNode);
//		System.out.println("Fix "+fixingIngredient.node);
		fixingIngredient.node.accept(new ASTVisitor() {
			@Override
			public void preVisit(ASTNode node) {
//				System.out.println("VISTING "+ node.toString());
				if(node instanceof QualifiedName) {
					//					System.out.println("QUA!!!!!!!!!!!");
					//					CompatibilityChecker.compatible = true;
					QualifiedName qualifiedName = (QualifiedName) node;
					IBinding binding = qualifiedName.getName().resolveBinding();
					if(binding!=null) {
						for(Variable variable : VariableCollector.variables) {
							if(variable.binding.toString().equals(binding.toString())) {
								variablesToBeIgnored.add(variable);
								break;
							}
						}
					}
				}
			}
		});

//		System.out.println("IGNORE "+variablesToBeIgnored);	
		int currentLine = this.patchGenerator.compilationUnit.getLineNumber(faultyNode.getStartPosition());
		Iterator<Variable> iterator = fixingIngredient.variableAccessed.iterator(); 
		while(iterator.hasNext())  
		{  
			Variable variableAccessed = iterator.next();  
			if(!variablesToBeIgnored.contains(variableAccessed)) {
				for(Variable variable : VariableCollector.variables) {
					if(variable.binding.toString().equals(variableAccessed.binding.toString())) {
						if(operation.equals("insert")) {
							if(variable.startLine>currentLine || variable.endLine<currentLine) {
//								System.out.println("FAULT "+faultyNode + " "+currentLine);
//								System.out.println("FIX "+fixingIngredient.node+ " " +fixingIngredient.startLine);
//								System.out.println( operation + " INCOMPATIBLE!!!!!!!!!!!!!!!!");
								return false;
							}
						}
						else {
							if(variable.startLine>=currentLine || variable.endLine<=currentLine) {
//								System.out.println("FAULT "+faultyNode + " "+currentLine);
//								System.out.println("FIX "+fixingIngredient.node + " " +fixingIngredient.startLine);
//								System.out.println( operation + " INCOMPATIBLE!!!!!!!!!!!!!!!!");
								return false;
							}
						}
						break;
					}
				}
			}
		}

//		System.out.println("FAULT "+faultyNode+ " "+currentLine);
//		System.out.println("FIX "+fixingIngredient.node+ " " +fixingIngredient.startLine);
//		System.out.println( operation + " COMPATIBLE!!!!!!!!!!!!!!!!");
		return true;
	}
}
