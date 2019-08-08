import org.eclipse.jdt.core.dom.IVariableBinding;

public class Variable {
	String name;
	String type;
	IVariableBinding binding;
	int startLine;
	int endLine;
	
	@Override
	public String toString() {
		return "Variable [name=" + name + ", type=" + type + ", binding=" + binding + ", startLine=" + startLine
				+ ", endLine=" + endLine + "]";
	}
}
