import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IBinding;

public class ReplaceHandler {
	private static ReplaceHandler replaceHandler;
	CompatibilityChecker compatibilityChecker;
	PatchListUpdater patchListUpdater;
	private ReplaceHandler() {
		this.compatibilityChecker = CompatibilityChecker.createCompatibilityChecker(); 
		this.patchListUpdater = PatchListUpdater.createPatchListUpdater();
	}
	
	public static ReplaceHandler createReplaceHandler() {
		if(replaceHandler == null){
			replaceHandler = new ReplaceHandler();
		}

		return replaceHandler;
	}
	
	public void replace(IBinding binding, FaultyNode faultyNode, double frequency) {
		IngredientCollector ingredientCollector = IngredientCollector.createIngredientCollector();
		for(int i = 0; i<ingredientCollector.fixingIngredients.size(); i++) {
			FixingIngredient fixingIngredient = ingredientCollector.fixingIngredients.get(i);
			if(fixingIngredient.node instanceof Expression) {
				Expression fixingIngredientExpression = (Expression) fixingIngredient.node;
				IBinding fixingIngredientBinding = fixingIngredientExpression.resolveTypeBinding();
				
				if(fixingIngredientBinding!=null && fixingIngredientBinding.equals(binding) && !faultyNode.node.toString().equals(fixingIngredient.node.toString())) {
					if((faultyNode.type.equals("ASSIGNMENT") && !fixingIngredient.type.equals("ASSIGNMENT"))||(!faultyNode.type.equals("ASSIGNMENT") && fixingIngredient.type.equals("ASSIGNMENT"))) {
						continue;
					}
					if(this.compatibilityChecker.checkCompatibility(faultyNode.node, fixingIngredient, "replace")==true) {
						ModelExtractor modelExtractor = ModelExtractor.createModelExtractor();
//						System.out.println("FAULT "+ faultyNode.node.toString() + " line:"+faultyNode.startLine+ "," + modelExtractor.getNodeType(faultyNode.node));
//						System.out.println("FIX " + fixingIngredient.node.toString()+ " line:"+fixingIngredient.startLine+ "," + modelExtractor.getNodeType(fixingIngredient.node));
						CandidatePatch candidatePatch = new CandidatePatch();
						candidatePatch.faultyNode = faultyNode.node;
						candidatePatch.fixingIngredient = fixingIngredient.node;
						
						candidatePatch.LCS = modelExtractor.getNormalizedLongestCommonSubsequence(candidatePatch.faultyNode.toString(), candidatePatch.fixingIngredient.toString());
						candidatePatch.genealogyScore = modelExtractor.getGenealogySimilarityScore(faultyNode.genealogy, fixingIngredient.genealogy);
						candidatePatch.variableScore = modelExtractor.getVariableSimilarityScore(faultyNode.variableAccessed, fixingIngredient.variableAccessed);
//						candidatePatch.tokenScore = modelExtractor.getTokenSimilarityScore(faultyNode.tokens, fixingIngredient.tokens);
//						System.out.println(candidatePatch.genealogyScore);
//						System.out.println();
						double dependencyScore = 1;
						candidatePatch.mutationOperation = "replace";
						candidatePatch.score = faultyNode.suspiciousValue*frequency*((0.4*candidatePatch.genealogyScore*candidatePatch.variableScore)+(0.6*candidatePatch.LCS));//+candidatePatch.variableScore+candidatePatch.LCS);//faultyNode.suspiciousValue*frequency*SimilarityScoreCalculator.calculateSimilarityScore(candidatePatch.mutationOperation, genealogyScore, variableScore, dependencyScore);
//						candidatePatch.scorewithLCS = faultyNode.suspiciousValue*frequency*(0.9*SimilarityScoreCalculator.calculateSimilarityScore(candidatePatch.mutationOperation, genealogyScore, variableScore, dependencyScore)+0.1*candidatePatch.LCS);
						if(candidatePatch.LCS>0 && candidatePatch.genealogyScore>0 && candidatePatch.variableScore>0) {
							this.patchListUpdater.updatePatchList(candidatePatch);
//							PatchGenerator.candidatePatchesList.add(candidatePatch);
//							System.out.println(faultyNode.type);
//							System.out.println(fixingIngredientBinding);
						}

						//					if(faultyNode.type.equals("INFIX_EXPRESSION")) {
//						System.out.println(fixingIngredientBinding);
						
//						System.out.print("Gen SCORE "+genealogyScore);
//						System.out.print(" Var SCORE "+ variableScore);
//						System.out.print(" LCS SCORE "+ candidatePatch.LCS);
//						System.out.println();
//						System.out.println();
						//					}
					}
				}
			}
		}
	}
}
