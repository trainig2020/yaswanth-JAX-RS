package org.yaswanth.messenger.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.yaswanth.messenger.model.Message;
import org.yaswanth.messenger.service.MessageService;
@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {
    
	MessageService messageservice = new MessageService();
	
	@GET
	public List<Message> GetMessage(@QueryParam("year") int year,
			                        @QueryParam(value = "start") int start, 
			                        @QueryParam("size") int size) {
		if ( year > 0) {
			return messageservice.getAllMessagesForYear(year);
		}
		if ( start > 0 && size > 0 ) {
			return messageservice.getAllMessagesPaginated(start, size);
		}
		return messageservice.getAllMessages();
	}
	
	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") long messageId) {
		return messageservice.getMessage(messageId);	
	}
	
	@POST
	public Message addMessage(Message message) {
		return messageservice.addMessage(message);
	}
	
	@DELETE
	@Path("/{messageId}")
	public Message deleteMessage(@PathParam("messageId") long messageId) {
		return messageservice.removeMessage(messageId);	
	}
	
	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long id, Message message) {
		message.setId(id);
		return messageservice.updateMessage(message);
	}
	
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}
	
}
