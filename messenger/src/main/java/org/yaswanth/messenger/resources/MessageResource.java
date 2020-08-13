package org.yaswanth.messenger.resources;

import java.net.URI;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

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
	public Message getMessage(@PathParam("messageId") long messageId, @Context UriInfo uriInfo) {
		Message message =messageservice.getMessage(messageId);
		message.addLink( getUriForSelf(uriInfo, message), "self");
		message.addLink( getUriForProfile(uriInfo, message), "profile");
		return message;
	}

	private String getUriForProfile(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getBaseUriBuilder()
				.path(ProfileResource.class)
				.path(message.getAuthor())
				.build();
		return uri.toString();
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
		.path(MessageResource.class)
		.path(Long.toString(message.getId()))
		.build()
		.toString();
		return uri;
	}
	
	@POST
	public Response addMessage(Message message, @Context UriInfo uriInfo) {
		Message newMessage = messageservice.addMessage(message);
		String newId = String.valueOf(newMessage.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
		return Response.created(uri).entity(newMessage).build();
		
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
