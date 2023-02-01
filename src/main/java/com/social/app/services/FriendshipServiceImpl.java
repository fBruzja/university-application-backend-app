package com.social.app.services;

import com.social.app.domain.Friendship;
import com.social.app.exception.UaBadRequestException;
import com.social.app.exception.UaResourceNotFoundException;
import com.social.app.repository.FriendshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FriendshipServiceImpl implements FriendshipService{

    @Autowired
    FriendshipRepository friendshipRepository;

    @Override
    public List<Friendship> findAllByUserId(Integer userId) throws UaResourceNotFoundException {
        return friendshipRepository.findAllByUserId(userId);
    }

    @Override
    public List<Friendship> findAllByFriendId(Integer friendId) throws UaResourceNotFoundException {
        return friendshipRepository.findAllByFriendId(friendId);
    }

    @Override
    public Integer createFriendship(Integer userId, Integer friendId) throws UaBadRequestException {
        return friendshipRepository.createFriendship(userId, friendId);
    }
}
