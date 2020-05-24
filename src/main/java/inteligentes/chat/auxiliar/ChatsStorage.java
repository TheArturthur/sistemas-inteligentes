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
		this.chatsid = new HashMap<Pair<String,String>,Integer>();
		this.chats = new HashMap<Integer,LinkedList<EncodedMessage>>();
		
		this.id = 10;
	}
	
	public void addChatMessage(EncodedMessage em) {
		int idr = this.addChatId(em);
		LinkedList<EncodedMessage> ml = (LinkedList<EncodedMessage>) this.chats.get(idr);
		
		ml.add(em);
		LinkedList<EncodedMessage> toSave = copyList(ml);
		this.chats.put(idr, toSave);
	}
	
	public EncodedMessage getChatMessage(Report rep) {
		EncodedMessage required = new EncodedMessage();
		
		EncodedMessage em = new EncodedMessage();
		em.setFrom(rep.getEmisor());
		em.setSendTo(rep.getReceptor());
		
		int idr = this.getChatId(em);
		LinkedList<EncodedMessage> ml = (LinkedList<EncodedMessage>) this.chats.get(idr);
		
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
		this.chats.remove(idr);
	}
	
	
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
	
	private int getChatId(EncodedMessage em) {
		Pair pareja1 = new Pair<String,String>(em.getFrom(),em.getSendTo());
		Pair pareja2 = new Pair<String,String>(em.getSendTo(),em.getFrom());
		
		if(chatsid.get(pareja1) != null) return chatsid.get(pareja1);
		if(chatsid.get(pareja2) != null) return chatsid.get(pareja2);
			
		return 0;
	}
	
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
