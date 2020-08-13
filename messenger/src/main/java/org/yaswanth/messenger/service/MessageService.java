package org.yaswanth.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.yaswanth.messenger.database.DatabaseClass;
import org.yaswanth.messenger.exception.DataNotFoundException;
import org.yaswanth.messenger.model.Message;
import org.yaswanth.messenger.model.Profile;

public class MessageService {
    
	private Map<Long, Message> messages = DatabaseClass.getMessages();
	
	
//	public List<Message> getAllMessages(){
//		Message m1 = new Message(1L, "helloworld", "yaswanth");
//		Message m2 = new Message(2L, "HelloJersey", "yaswanth");
//		List<Message> lst =  new ArrayList<>();
//		lst.add(m1);
//		lst.add(m2);
//		return lst;
//	}
	
	public MessageService() {
		messages.put(1L,  new Message(1L, "helloworld", "yaswanth"));
		messages.put(2L,  new Message(2L, "HelloJersey", "yaswanth"));
	}
	
	
	public List<Message> getAllMessagesForYear(int year){
		List<Message> messageForYear = new ArrayList<Message>();
		Calendar cal = Calendar.getInstance();
		for(Message message :messages.values()) {
			cal.setTime(message.getCreated());
			if(cal.get(Calendar.YEAR) == year) {
				messageForYear.add(message);
			}
		}
		return messageForYear;
	}
	public List<Message> getAllMessagesPaginated(int start , int size){
		ArrayList<Message> list = new ArrayList<Message>(messages.values());
		if (start +size >list.size()) return new ArrayList<Message>();
		return list.subList(start, start+size);
	}
	public List<Message> getAllMessages(){
		return new ArrayList<Message>(messages.values());
	}
	
	public Message getMessage(long id) {
		Message message= messages.get(id);
		if(message == null) {
			throw new DataNotFoundException("message with id  " + id + " not found");
		}
		return message;
	}
	
	public Message addMessage(Message message) {
		message.setId(messages.size() + 1);
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message updateMessage(Message message) {
		if(message.getId()<=0) {
			return null;
		}
		messages.put(message.getId(), message);
		return message;
	}
	public Message removeMessage(long id) {
		return messages.remove(id);
	}
	
}
