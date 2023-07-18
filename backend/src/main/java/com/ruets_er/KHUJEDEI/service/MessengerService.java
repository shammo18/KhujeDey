package com.ruets_er.KHUJEDEI.service;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.exception.MessengerApiException;
import com.github.messenger4j.exception.MessengerIOException;
import com.github.messenger4j.send.MessagePayload;
import com.github.messenger4j.send.MessagingType;
import com.github.messenger4j.send.NotificationType;
import com.github.messenger4j.send.message.TextMessage;
import com.github.messenger4j.send.recipient.IdRecipient;
import com.github.messenger4j.webhook.event.TextMessageEvent;
import com.ruets_er.KHUJEDEI.controller.MessengerController;
import com.ruets_er.KHUJEDEI.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static java.util.Optional.empty;
import static java.util.Optional.of;

@Service
public class MessengerService{

    private static final Logger logger = LoggerFactory.getLogger(MessengerController.class);
    private final Messenger messenger;

    @Autowired
    public MessengerService(final Messenger messenger) {
        this.messenger = messenger;
    }

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    public JwtUtil jwtUtil;

    public void handleTextMessageEvent(TextMessageEvent event) {
        logger.debug("Received TextMessageEvent: {}", event);

        final String messageId = event.messageId();
        final String messageText = event.text();
        final String senderId = event.senderId();
        final Instant timestamp = event.timestamp();

        logger.info("Received message '{}' with text '{}' from user '{}' at '{}'", messageId, messageText, senderId, timestamp);
        logger.info("Received Message: {}", messageText);

        String message = messageText.trim();

        String[] text = message.replaceAll(" ","").split(":");
        String action = text[0].toLowerCase();
        String actionId = text[1];

        if(text.length==2)
        {
            String response = doMessageAction(action ,actionId , senderId);
            sendTextMessage(senderId, response);
        }

    }

    public void sendMessageToUsers(List<String> recipients, String message) {
        for(String recipientId: recipients) {
            sendTextMessage(recipientId,message);
        }
    }

    public void sendTextMessage(String recipientId, String text) {
        try {
            final IdRecipient recipient = IdRecipient.create(recipientId);
            final NotificationType notificationType = NotificationType.REGULAR;
            final String metadata = "DEVELOPER_DEFINED_METADATA";

            final TextMessage textMessage = TextMessage.create(text, empty(), of(metadata));
            final MessagePayload messagePayload = MessagePayload.create(recipient, MessagingType.RESPONSE, textMessage,
                    of(notificationType), empty());
            this.messenger.send(messagePayload);
        } catch (MessengerApiException | MessengerIOException e) {
            handleSendException(e);
        }
    }


    private void handleSendException(Exception e) {
        logger.error("Message could not be sent. An unexpected error occurred.", e);
    }

    private String doMessageAction(String action ,String actionId , String senderId)
    {



        if(action.equals("verify"))
        {
            return registrationService.setUserActive(jwtUtil.decodeMessageToken(actionId) , senderId);
        }
        else if (action.equals("accept")) {
            return notificationService.notifyProductFound(Integer.valueOf(jwtUtil.decodeMessageToken(actionId)) , senderId);
        }
        return ".";
    }
}
