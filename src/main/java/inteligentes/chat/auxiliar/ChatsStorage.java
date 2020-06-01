package inteligentes.chat.auxiliar;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import inteligentes.chat.basics.EncodedMessage;
import inteligentes.chat.basics.Report;
import javafx.util.*;

public class ChatsStorage {
	
	
	private Map<Integer,LinkedList<EncodedMessage>> chats;
	private Map<Pair<String, String>, Integer> chatsid;
	private int id;
	
	public ChatsStorage() {		
		chats = new HashMap<Integer,LinkedList<EncodedMessage>>();
		chatsid = new HashMap<Pair<String,String>,Integer>();
		this.id = 10;
	}
	
	public void listAllChats() {
		for (int i = 0; i < chats.size(); i++) {
			LinkedList<EncodedMessage> fkchats = chats.get(i+11);
			if (fkchats == null)  {
				System.out.println("\n\nNo hay chats almacenados...");
				return;
			}
			EncodedMessage mess = fkchats.getFirst();
			System.out.println("\n\n_/_/_/_/_/Chat de " + mess.getFrom().toUpperCase() + " y " +
			mess.getSendTo().toUpperCase() + "\\_\\_\\_\\_\\_\n");
			String main = mess.getFrom();
			for (int j = 0; j < fkchats.size(); j++) {
				String from = fkchats.get(j).getFrom();
				
				if (from.equals(main)) {
					System.out.println(from.toUpperCase() + ": " + fkchats.get(j).getMessage());
					if (fkchats.get(j).isOffensive()) {
						System.out.println("**_Mensaje marcado como ofensivo_**\n");
					}
				} else {
					System.out.println("\t\t\t\t"+from.toUpperCase() + ": " + fkchats.get(j).getMessage());
					if (fkchats.get(j).isOffensive()) {
						System.out.println("\t\t\t\t**_Mensaje marcado como ofensivo_**\n");
					}
				}
				

			}
		}
	}
	
	public void addChatMessage(EncodedMessage em) {
		int idr = this.addChatId(em);
		LinkedList<EncodedMessage> ml = (LinkedList<EncodedMessage>) chats.get(idr);
		
		ml.add(em);
		LinkedList<EncodedMessage> toSave = copyList(ml);
		chats.put(idr, toSave);
	}
	
	public EncodedMessage getChatMessage(Report rep) {
		EncodedMessage required = new EncodedMessage();
		
		EncodedMessage em = new EncodedMessage();
		em.setFrom(rep.getEmisor());
		em.setSendTo(rep.getReceptor());
		
		int idr = this.getChatId(em);
		LinkedList<EncodedMessage> ml = (LinkedList<EncodedMessage>) chats.get(idr);
		
		boolean done = false;
		for (int i = 0; i < ml.size() && !done; i++) {
			EncodedMessage res = ml.get(i);
			if(res.getMessage().equals(rep.getMensaje())) {
				required = res;
				done = true;
			}
		}
		return required;
	}
	
	public void removeChat(EncodedMessage em) {
		int idr = this.getChatId(em);
		this.removeChatId(em);
		chats.remove(idr);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private int addChatId(EncodedMessage em) {
		int id = 0;
		Pair pareja1 = new Pair<String,String>(em.getFrom(),em.getSendTo());
		Pair pareja2 = new Pair<String,String>(em.getSendTo(),em.getFrom());
		if(chatsid.get(pareja1) == null) {
			if(chatsid.get(pareja2) == null) {
				id = ++this.id;
				chatsid.put(pareja1, id);
				chats.put(id, new LinkedList<EncodedMessage>());
				return id;
			} else {
				return chatsid.get(pareja2);
			}
		} else {
			return chatsid.get(pareja1);
		}
	}
	
	@SuppressWarnings("rawtypes")
	private int getChatId(EncodedMessage em) {
		Pair pareja1 = new Pair<String,String>(em.getFrom(),em.getSendTo());
		Pair pareja2 = new Pair<String,String>(em.getSendTo(),em.getFrom());
		
		if(chatsid.get(pareja1) != null) return chatsid.get(pareja1);
		if(chatsid.get(pareja2) != null) return chatsid.get(pareja2);
			
		return 0;
	}
	
	@SuppressWarnings("rawtypes")
	private void removeChatId(EncodedMessage em) {
		Pair pareja1 = new Pair<String,String>(em.getFrom(),em.getSendTo());
		Pair pareja2 = new Pair<String,String>(em.getSendTo(),em.getFrom());
		
		if(chatsid.get(pareja1) != null) chatsid.remove(pareja1);
		if(chatsid.get(pareja2) != null) chatsid.remove(pareja2);
	}
	
	private LinkedList<EncodedMessage> copyList(LinkedList<EncodedMessage> list) {	
		
		LinkedList<EncodedMessage> result = new LinkedList<EncodedMessage>();
		for (int i = 0; i < list.size(); i++) {
			result.add(list.get(i));
		}
		return result;
	}
	
}
