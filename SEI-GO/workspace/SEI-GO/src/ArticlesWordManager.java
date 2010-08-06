import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;


public class ArticlesWordManager implements WordManager{

	private static List<String> words = new LinkedList<String>();
	private static ArticlesWordManager instance;

	public ArticlesWordManager() {
		File source = new File("../articlesWords.txt");
		
		try {
			BufferedReader buffer = new BufferedReader( new FileReader( source ) );
			String line;
			while ( (line = buffer.readLine())!=null) {
				String[] sourceWords = line.split(";");
				for (int i = 0; i < sourceWords.length; i++) {
					words.add(sourceWords[i]);
				}
			}
		} catch (Exception e) {
			System.out.println("No se pudo cargar los articulos. " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static ArticlesWordManager getInstance(){
		if(instance == null){
			instance = new ArticlesWordManager();
		}
		return instance;
	}
	
	public Boolean containWord(String word){
		return words.contains(word.toLowerCase());
	}
	
	public String getName(){
		return Constant.KEY_NAME_AR;
	}
	
}
