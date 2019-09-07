package edu.udacity.java.nano.chat;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.websocket.RemoteEndpoint;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class WebSocketChatServerTest {

	Basic endpoint;
	Session session;
	WebSocketChatServer server;
	ArgumentCaptor<String> captor;
	List<Session> openSessions = new ArrayList();

	@Before
	public void setUp() {
		server = new WebSocketChatServer();
		captor = ArgumentCaptor.forClass(String.class);
		endpoint = mock(Basic.class);
		session = createSession("an id", endpoint);
	}

	@After
	public void tearDown() {
		openSessions.forEach(session -> server.onClose(session));
	}

	@Test
	public void sendENTERMessageOnOpen() throws IOException {
		server.onOpen(session);

		verify(endpoint).sendText(captor.capture());
		assertEquals("ENTER", sentObject().getString("type"));
		assertEquals(1, sentObject().getIntValue("onlineCount"));
	}

	@Test
	public void canOpenMoreThanOneSession() throws IOException {
		Session anotherSession = createSession("another id", mock(Basic.class));

		server.onOpen(session);
		server.onOpen(anotherSession);

		verify(endpoint, times(2)).sendText(captor.capture());
		assertEquals("ENTER", sentObject().getString("type"));
		assertEquals(2, sentObject().getIntValue("onlineCount"));
	}

	@Test
	public void cannotOpenSameSessionTwice() throws IOException {
		server.onOpen(session);
		server.onOpen(session);

		verify(endpoint, times(1)).sendText(anyString());
	}

	@Test
	public void sendSPEAKMessageOnMessage() throws IOException {
		Map<String, String> message = new HashMap<>();
		message.put("username","a username");
		message.put("message","a message");

		server.onOpen(session);
		server.onMessage(session, JSON.toJSONString(message));

		verify(endpoint, times(2)).sendText(captor.capture());
		assertEquals("SPEAK", sentObject().getString("type"));
		assertEquals("a username", sentObject().getString("username"));
		assertEquals("a message", sentObject().getString("message"));
		assertEquals(1, sentObject().getIntValue("onlineCount"));
	}

	@Test
	public void sendQUITMessageOnClose() throws IOException {
		Session anotherSession = createSession("another id", mock(Basic.class));

		server.onOpen(session);
		server.onOpen(anotherSession);
		server.onClose(anotherSession);

		verify(endpoint, times(3)).sendText(captor.capture());
		assertEquals("QUIT", sentObject().getString("type"));
		assertEquals(1, sentObject().getIntValue("onlineCount"));
	}

	@Test
	public void stopsSendingMessagesToSessionOnClose() throws IOException {
		Map<String, String> message = new HashMap<>();
		message.put("username","a username");
		message.put("message","a message");

		Session anotherSession = createSession("another id", mock(Basic.class));
		server.onOpen(anotherSession);

		server.onOpen(session);
		server.onClose(session);
		server.onMessage(anotherSession, JSON.toJSONString(message));

		verify(endpoint, times(1)).sendText(anyString());
	}


	private Session createSession(String id, Basic endpoint) {
		Session session = mock(Session.class);
		when(session.getId()).thenReturn(id);
		when(session.getBasicRemote()).thenReturn(endpoint);
		openSessions.add(session);
		return session;
	}

	private JSONObject sentObject() {
		return JSON.parseObject(captor.getValue());
	}

}