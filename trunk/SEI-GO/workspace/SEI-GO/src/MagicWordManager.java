import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public class MagicWordManager extends WordManager {

	private static MagicWordManager instance;
	
	private List<String> suspenseWords;
	private List<String> acceptedWords;
	
	public static MagicWordManager getInstance(){
		if(instance == null){
			instance = new MagicWordManager();
		}
		return instance;
	}
	
	public MagicWordManager() {
		this.suspenseWords = new LinkedList<String>();
		this.acceptedWords = new LinkedList<String>();
		super.loadWords();
	}
	
	public Boolean containWord(String word){
		word = word.toLowerCase();
		Boolean newWord = !super.containWord(word);
		newWord &= !suspenseWords.contains(word);
		newWord &= EmptyWordManager.getInstance().containWord(word);
		newWord &= !acceptedWords.contains(word);
		 
		if ( newWord ) {
			suspenseWords.add(word);
		}
		return newWord;
	}
	
	public void persistWords() throws IOException {
		if (acceptedWords.size() == 0) return;
		File stemmerFile = new File(getPathOut());
		FileWriter fstream = new FileWriter(stemmerFile);
		BufferedWriter out = new BufferedWriter(fstream);
		
		for (String word : acceptedWords) {
			out.write(word+"\t\t:"+ParserComments.getStemmerDic().get(word));
			out.newLine();
		}
		out.close();
		System.out.print("Che fijate que tenés palabras en: \""+getPathOut()+"\"\n");
	}
	
	public void cleanWords()
	{
		suspenseWords = new LinkedList<String>();
	}
	
	public void acceptWords()
	{
		acceptedWords.addAll(suspenseWords);
	}
	
	@Override
	public String getName() {
		return Constant.KEY_NAME_MG;
	}

	public String getPathOut() {
		return "../findedWords.txt";
	}

	@Override
	public String getPath() {
		// TODO Auto-generated method stub
		return "../exceptedWords.txt";
	}
}
