/*
 * Copyright (c) 2022.
 * Document    : NotificationService.java
 * Created On  : 7/20/22, 11:46 PM
 * Author      : NAHID RUET CSE'18
 */

package com.ruets_er.KHUJEDEI.service;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.exception.MessengerApiException;
import com.github.messenger4j.exception.MessengerIOException;
import com.github.messenger4j.send.MessagePayload;
import com.github.messenger4j.send.MessagingType;
import com.github.messenger4j.send.NotificationType;
import com.github.messenger4j.send.message.TextMessage;
import com.github.messenger4j.send.recipient.IdRecipient;
import com.ruets_er.KHUJEDEI.model.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService extends ApiService{
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);


    @Autowired
    Messenger messenger;
    @Lazy // to prevent cycle
    @Autowired
    MessengerService messengerService;

    public void checkPoststoNotify(Integer catagoryId, Integer modelId , String username, Double latitude, Double longitude, Integer postStatus ,
                           String description , String catagoryName , String modelName , String Areaname , Integer id){
        if(postStatus==1)
        {
            String Msg = "Someone search the product you have found which \n"
                    + "Catagory Name : " + catagoryName +"\n"
                    + "Model Name : " + modelName +"\n"
                    + "Identification Mark : " + description +"\n \n \n"
                    + "To if this is the product \n you have found around "+Areaname +"\n"
                    + "Type Accept : "+jwtUtil.generateMessageToken(id.toString());
            List<String> users = postRepository.findUsersMessengerIdByFilteringFoundPosts(catagoryId, modelId ,latitude, longitude, 2);

            messengerService.sendMessageToUsers(users , Msg);
        }
        else
        {
            List <Integer> postIds = postRepository.findPostIdsByFilteringLostPosts(catagoryId,modelId,description);
            String messengerIdOfWhoFound = memberRepository.findByUsername(username).getUserCredential().getUserMessengerId();
            for (Integer postId:postIds) {
                notifyProductFound(postId,messengerIdOfWhoFound);
            }
        }

    }

    public String notifyProductFound(Integer postId , String acceptorMessengerId)
    {
        String recepientId = postRepository.findUserOfSpesificPost(Integer.valueOf(postId));
        Member m = memberRepository.findByUserByMessengerId(acceptorMessengerId);
        String Msg = "Congratulations!! \n"+
                     "Your product is found!!\n"+
                     "Contact With "+m.getMemberName()+"\n"+
                     "Lived in "+m.getMemberAdderss()+"\n"+
                     "Mobile No: "+m.getMemberPhone();
        messengerService.sendTextMessage(recepientId , Msg);
        return "Thanks For Accepting the request!!! We'll inform the woner!! He will contact with you Soon!!";
    }



}
