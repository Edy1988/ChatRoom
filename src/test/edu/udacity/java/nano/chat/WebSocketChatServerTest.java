package edu.udacity.java.nano.chat;

import edu.udacity.java.nano.WebSocketChatApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.websocket.Session;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {WebSocketChatServer.class, Message.class, Session.class})

public class WebSocketChatServerTest {
//
//	@Autowired
//	private MockMvc mockMvc;
//
//	@MockBean
//	public WebSocketChatServer webSocketChatServer;
//
	@MockBean
	public Message message;


	@MockBean
	public Session session;

	private WebSocketChatServer webSocketChatServer = new WebSocketChatServer();
	@Test
	public void onOpenTest() throws Exception  {
		webSocketChatServer.onOpen(session);
		assertThat(message.toString().contains("ENTER"));


	}

	@Test
	public void onMessage() {
	}

	@Test
	public void onClose() {
	}

	@Test
	public void onError() {
	}
}