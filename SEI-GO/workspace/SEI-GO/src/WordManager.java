import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;


public abstract class WordManager {

	private List<String> words = new LinkedList<String>();
	
	public abstract String getPath();
	public abstract String getName();
	
	protected void loadWords(){
		File source = new File(getPath());
		
		try {
			BufferedReader buffer = new BufferedReader( new FileReader( source ) );
			String line;
			while ( (line = buffer.readLine())!=null) {
				String[] sourceWords = line.split(";");
				for (int i = 0; i < sourceWords.length; i++) {
					words.add(sourceWords[i].toLowerCase());
				}
			}
		} catch (Exception e) {
			System.out.println("No se pudo cargar las palabras. " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public Boolean containWord(String word){
		return words.contains(word.toLowerCase());
	}
	
	public Boolean getAspect(String stemmerWord) {
		return Boolean.TRUE;
	}
	
	public void setName(String name){
		
	}
	
	
}
