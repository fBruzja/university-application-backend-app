package com.social.app.repository;

import com.social.app.domain.Friendship;
import com.social.app.exception.UaBadRequestException;
import com.social.app.exception.UaResourceNotFoundException;

import java.util.List;

public interface FriendshipRepository {

    List<Friendship> findAllByUserId(Integer userId) throws UaResourceNotFoundException;

    List<Friendship> findAllByFriendId(Integer friendId) throws UaResourceNotFoundException;

    Integer createFriendship(Integer userId, Integer friendId) throws UaBadRequestException;
}
