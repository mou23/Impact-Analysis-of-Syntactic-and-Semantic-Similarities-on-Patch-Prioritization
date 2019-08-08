import java.io.Serializable;

import org.eclipse.jdt.core.dom.ASTNode;

public class CandidatePatch implements Serializable, Comparable <CandidatePatch> {
	ASTNode faultyNode;
	ASTNode fixingIngredient;
	double score;
	String mutationOperation;
	double tokenScore;
	double genealogyScore;
	double variableScore;
	double LCS;

	@Override
	public int compareTo(CandidatePatch candidatePatch) {
		// TODO Auto-generated method stub
		return Double.compare(candidatePatch.score, this.score);
	}

	@Override
	public String toString() {
		ModelExtractor modelExtractor = ModelExtractor.createModelExtractor();
		PatchGenerator patchGenerator = PatchGenerator.createPatchGenerator();
		String modifiedFaultyNode = faultyNode.toString().replaceAll("[\\t\\n\\r,]+"," ") + "," + modelExtractor.getNodeType(faultyNode);
		String modifiedFixingIngredient = fixingIngredient.toString().replaceAll("[\\t\\n\\r,]+"," ") + "," + modelExtractor.getNodeType(fixingIngredient);
		return modifiedFaultyNode + ", line no: " + patchGenerator.compilationUnit.getLineNumber(faultyNode.getStartPosition()) + "," + modifiedFixingIngredient + ", line no: " + patchGenerator.compilationUnit.getLineNumber(fixingIngredient.getStartPosition()) + ", " +score + ", " + mutationOperation + ", " + genealogyScore+", "+variableScore+", "+LCS;
	}
}