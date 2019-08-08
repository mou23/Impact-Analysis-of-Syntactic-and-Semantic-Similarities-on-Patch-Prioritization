import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.jdt.core.dom.ASTNode;

public class FixingIngredient {
	ASTNode  node;
	int startLine;
	int endLine;
	String type;
	HashMap<Integer,Integer> genealogy;
	HashSet<Variable> variableAccessed;
	HashMap<String,Integer> tokens;
	
	public FixingIngredient() {
		this.genealogy = null;
		this.tokens = null;
	}
	
	@Override
	public String toString() {
		return "FixingIngredient [node=" + node + ", startLine=" + startLine + ", endLine=" + endLine + ", type=" + type
				+ "]";
	}
}
