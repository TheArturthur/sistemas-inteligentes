/**
 * 
 */
package inteligentes.chat.agentes;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.lang.NullPointerException;

import inteligentes.chat.basics.EncodedMessage;
import inteligentes.chat.behaviours.AnalyzerAgentBehaviour;

import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;

import org.apache.commons.text.similarity.*;

public class AnalyzerAgent extends Agent {

	private static final long serialVersionUID = 1L;
	
	private LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
	
	public static final String NAME = "analyzer";
	
	private final String FILEDIR = "./dict";
	private HashMap<Character, File> dictionary = new HashMap<>();

	/**
	 * List containing the insults to be analyzed in the message.
	 */
	private ArrayList<String> insults = new ArrayList<>(Arrays.asList(
			"puta", "zorra", "cabron", "capullo", "guarra", "gilipollas",
			"imbecil", "inutil", "gordo", "obeso", "feo", "subnormal"));
	
	@Override
	protected void setup() {
		try {
		initDictionary();
		} catch (NullPointerException e) {
			System.out.println(e.getMessage() + "\n!!!MIRA A VER SI LOS ARCHIVOS DE DICCIONARIO EXISTEN¡¡¡");
		}
		//Crear servicios proporcionados por el agente y registrarlos en la plataforma
		DFAgentDescription agentDescription = new DFAgentDescription();
		agentDescription.setName(getAID());
		ServiceDescription serviceDescriptor = new ServiceDescription();
		serviceDescriptor.setName(NAME);
		//establezco el tipo del servicio "coordinador" para poder localizarlo cuando haga una busqueda
		serviceDescriptor.setType(NAME);
		serviceDescriptor.addOntologies(NAME);
		serviceDescriptor.addLanguages(new SLCodec().getName());
		agentDescription.addServices(serviceDescriptor);

		addBehaviour(new AnalyzerAgentBehaviour(this));

		try {
			DFService.register(this, agentDescription);
		} catch(FIPAException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks if the message contains an insult from the list.
	 * @param encodedMessage the message sent.
	 * @return a map with all insults (if any) present in the message, along with their positions inside it.
	 */
	public HashMap<String, ArrayList<int[]>> checkIfOffensive(EncodedMessage encodedMessage) {
		String[] msg = encodedMessage.getMessage().replace('\n',' ').split(" ");
		String[] origMsg = msg.clone();
		
		for (int i = 0; i < msg.length; i++) {
			msg[i] = msg[i].replaceAll("[^\\w]", "");
		}
		
		HashMap<String, ArrayList<int[]>> res = new HashMap<>();
		
		for (int j = 0; j < msg.length; j++) {
			String pal = msg[j];
			
			for (int i = 0; i < this.insults.size(); i++) {
				
				if (this.levenshteinDistance.apply(this.insults.get(i),pal.toLowerCase()) < 3 &&
						!isWord(pal.toLowerCase(), this.insults.get(i))) {
					String message = encodedMessage.getMessage();
					ArrayList<int[]> positions = new ArrayList<int[]>();
					List<Integer> indexes;
					
					System.out.println("Indexes: " + pal + " =>" + getIndexes(message,pal));
					System.out.println("Indexes: " + origMsg[j] + " =>" + getIndexes(message, origMsg[j]));
					
					if ((indexes = getIndexes(message, pal)) != null) {
						indexes = getIndexes(message, origMsg[j]);
						pal = origMsg[j];
					}
					
					for (int index : indexes) {
						if (!res.containsKey(this.insults.get(i))) {
							positions.add(new int[] {index, index + pal.length() - 1});
						} else {
							ArrayList<int[]> prevAddedIndexes = res.get(this.insults.get(i));
							prevAddedIndexes.add(new int[] {index, index + pal.length() -1});
							positions = prevAddedIndexes;
						}
					}
					
					res.put(this.insults.get(i), positions);
				}
			}
		}
		
		if (res.size() > 0) {
			encodedMessage.setOffensive(true);					
		}
		
		return res;
	}
	
	/**
	 * Gets all the initial and final positions of subStr in str.
	 * @param str the string containing subStr.
	 * @param subStr the substring inside str.
	 * @return a List of arrays with all the indexes of subStr inside str.
	 */
	private List<Integer> getIndexes(String str, String subStr) {
		int lastIndex = 0;
		List<Integer> result = new ArrayList<Integer>();

		while(lastIndex != -1) {

		    lastIndex = str.indexOf(subStr,lastIndex);

		    if(lastIndex != -1){
		        result.add(lastIndex);
		        lastIndex += 1;
		    }
		}
		return result;
	}
	
	/**
	 * Checks if pal is a word similar to insult in the dictionary files.
	 * @param pal the string to check with the dictionary.
	 * @param insult the insult pal is similar to.
	 * @return true if pal is a word, false otherwise.
	 */
	private boolean isWord (String pal, String insult) {
		char startLetter = pal.toCharArray()[0];
		try {
			RandomAccessFile file = new RandomAccessFile(this.dictionary.get(startLetter), "r");
			String line;
			while ((line = file.readLine()) != null) {
				line = line.replaceAll(", \\w*", "");
				if (!insult.equals(line) && this.levenshteinDistance.apply(pal, line) < 2 && (this.levenshteinDistance.apply(pal, line) < this.levenshteinDistance.apply(pal, insult))) {
					return true; // It's a word similar to the insult
				}
			}
			file.close();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}		
	}
	
	/**
	 * Initialize the dictionary map with the files containing the words.
	 */
	private void initDictionary() {
		File fileDir = new File(FILEDIR);
		if (!fileDir.exists()) {
			fileDir.mkdir();
		}
		
		File[] files = fileDir.listFiles();
		for (File file : files) {
			if (!file.isDirectory() && file.canRead()) {
				this.dictionary.put(file.getName().toCharArray()[0], file);
			}
		}
	}
	
}
