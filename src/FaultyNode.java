import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.jdt.core.dom.ASTNode;

public class FaultyNode {
	ASTNode node;
	double suspiciousValue;
	int startLine;
	int endLine;
	String type;
	HashMap<Integer,Integer>genealogy;
	HashSet<Variable> variableAccessed;
	HashMap<String,Integer> tokens;
	
	public FaultyNode() {
		this.genealogy = null;
		this.tokens = null;
	}
	
	@Override
	public String toString() {
		return "FaultyNode [node=" + node + ", suspiciousValue=" + suspiciousValue + ", startLine=" + startLine
				+ ", endLine=" + endLine + ", type=" + type + "]";
	}	
}
