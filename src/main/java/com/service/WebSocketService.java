package com.service;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutionException;

public class WebSocketService extends TextWebSocketHandler {

    private final String outputPath;

    private final WebSocketSession clientSession;
    private String response;

    public WebSocketService(String uri,String outputPath) throws ExecutionException, InterruptedException {
        this.outputPath =outputPath;
        StandardWebSocketClient webSocketClient = new StandardWebSocketClient();
        this.clientSession = webSocketClient.doHandshake(this, new WebSocketHttpHeaders(), URI.create(uri)).get();
    }

    public String getResponse() {
        return response;
    }

    public WebSocketSession getClientSession() {
        return clientSession;
    }
    public void sendMessage(String textMessage) throws IOException {
        clientSession.sendMessage(new TextMessage(textMessage));
    }
    public void closeClientSession() throws IOException {
         clientSession.close();
    }
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        System.out.println(message.getPayload());
        response = message.getPayload();
        ResultFormatter.writeAndFormatResults(response,outputPath);
    }


}