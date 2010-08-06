import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;


public class VerbWordManager implements WordManager{

	private static List<String> words = new LinkedList<String>();
	private static VerbWordManager instance;

	public VerbWordManager() {
		File source = new File("../verbWords.txt");
		
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
			System.out.println("No se pudo cargar los verbos. " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static VerbWordManager getInstance(){
		if(instance == null){
			instance = new VerbWordManager();
		}
		return instance;
	}
	
	public Boolean containWord(String word){
		return words.contains(word.toLowerCase());
	}
	
	public String getName(){
		return Constant.KEY_NAME_VE;
	}
	
}
