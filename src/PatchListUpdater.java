public class PatchListUpdater {
	private static PatchListUpdater patchListUpdater;
	PatchGenerator patchGenerator;
	
	private PatchListUpdater() {
		this.patchGenerator = PatchGenerator.createPatchGenerator();
	}
	
	public static PatchListUpdater createPatchListUpdater() {
		if(patchListUpdater == null) {
			patchListUpdater = new PatchListUpdater();
		}

		return patchListUpdater;
	}
	
	public void updatePatchList(CandidatePatch candidatePatch) {
		long length = this.patchGenerator.candidatePatchesList.size();
		for(int i=0; i<length; i++) {
			CandidatePatch currentPatch = this.patchGenerator.candidatePatchesList.get(i);
			if(currentPatch.faultyNode==candidatePatch.faultyNode && currentPatch.fixingIngredient.toString().equals(candidatePatch.fixingIngredient.toString()) && currentPatch.mutationOperation.equals(candidatePatch.mutationOperation)) {
//				System.out.println("EXISTS " +this.patchGenerator.candidatePatchesList.get(i));
				if(currentPatch.score<candidatePatch.score) {
					currentPatch.score = candidatePatch.score;
				}
				return;
			}
		}
//		System.out.println("NEW CP " +candidatePatch);
		this.patchGenerator.candidatePatchesList.add(candidatePatch);
	}
}
