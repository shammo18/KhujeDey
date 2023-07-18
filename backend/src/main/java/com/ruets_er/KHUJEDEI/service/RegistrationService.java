package com.ruets_er.KHUJEDEI.service;

import com.ruets_er.KHUJEDEI.model.Member;
import com.ruets_er.KHUJEDEI.model.Registry;
import com.ruets_er.KHUJEDEI.model.UserCredential;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService extends ApiService{

    @Transactional
    public String createUser(String memberName ,String memberPhone , String memberAddress ,  String userName , String userPassword , String userRole )
    {
        Member m = new Member();

        UserCredential uc = new UserCredential();

        // get Primary key of member table
        Registry r = registryRepository.getReferenceById(3);
        Integer id = r.getPrimaryId() + 1;
        r.setPrimaryId(id);
        registryRepository.save(r);

        //save values
        m.setId(id);
        m.setMemberName(memberName);
        m.setMemberPhone(memberPhone);
        m.setMemberAdderss(memberAddress);

        memberRepository.save(m);

        uc.setMember(memberRepository.getReferenceById(id));
        uc.setUserName(userName);
        uc.setUserPassword(userPassword);
        uc.setUserRole(userRole);
        uc.setUserStatus(0);

        userCredentialRepository.save(uc);

        String jwt = jwtUtil.generateMessageToken(userName);

        return jwt;
    }

    public boolean checkUserNameAvailablity(String userName)
    {
        // find in userName already exist or not in database
        if(userCredentialRepository.findStatusByUsername(userName) == null ) return true;

        return false;
    }

    public String setUserActive(String userName , String messengerId)
    {
        try
        {
            if(userCredentialRepository.findUserByUsername(userName) == null) return  "Invalid Verify Token!!";
            UserCredential uc =  userCredentialRepository.findUserByUsername(userName);
            uc.setUserStatus(1);
            uc.setUserMessengerId(messengerId);

            userCredentialRepository.save(uc);
            return "Congratulations!! Your Account is Activated!!";
        } catch (Exception e)
        {
            return "Something Went Wrong!!";
        }
    }
}
