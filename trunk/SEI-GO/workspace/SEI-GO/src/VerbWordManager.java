

public class VerbWordManager extends WordManager{

	private static VerbWordManager instance;

	public VerbWordManager() {
		super.loadWords();
	}
	
	public static VerbWordManager getInstance(){
		if(instance == null){
			instance = new VerbWordManager();
		}
		return instance;
	}
	
	public String getName(){
		return Constant.KEY_NAME_VE;
	}
	
	public String getPath(){
		return "../verbWords.txt";
	}
}
