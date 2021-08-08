package com.example.tw1tt3rServer.service;

import com.example.tw1tt3rServer.aspects.NoLogging;
import com.example.tw1tt3rServer.repository.ActionRepository;
import com.example.tw1tt3rServer.repository.entity.Action;
import com.example.tw1tt3rServer.repository.entity.Person;
import entities.enums.ActionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActionService {

    //------------------------------------------------------------------------------------------------------------

    @Autowired
    ActionRepository actionRepository;

    //------------------------------------------------------------------------------------------------------------

    private Action save(Action action) {
        return (actionRepository.save(action));
    }

    private void delete(Action action) {
        if (action == null) {
            return;
        }
        actionRepository.delete(action);
    }

    //------------------------------------------------------------------------------------------------------------

    @NoLogging
    public List<Action> getFollowers(Person person) {
        return (actionRepository.findByActionTypeAndDestinationPerson(ActionType.FOLLOW, person));
    }

    @NoLogging
    public List<Person> getFollowersPersons(Person person) {
        return (getFollowers(person).stream().map(Action::getSourcePerson).collect(Collectors.toList()));
    }

    @NoLogging
    public List<Action> getFollowings(Person person) {
        return (actionRepository.findByActionTypeAndSourcePerson(ActionType.FOLLOW, person));
    }

    @NoLogging
    public List<Person> getFollowingsPersons(Person person) {
        return (getFollowings(person).stream().map(Action::getDestinationPerson).collect(Collectors.toList()));
    }

    @NoLogging
    public List<Action> getBlockings(Person person) {
        return (actionRepository.findByActionTypeAndSourcePerson(ActionType.BLOCK, person));
    }

    @NoLogging
    public List<Person> getBlockingsPersons(Person person) {
        return (getBlockings(person).stream().map(Action::getDestinationPerson).collect(Collectors.toList()));
    }

    @NoLogging
    public List<Action> getUnfollowers(Person person) {
        return (actionRepository.findByActionTypeAndDestinationPerson(ActionType.UNFOLLOW, person));
    }

    @NoLogging
    public List<Action> getRejecters(Person person) {
        return (actionRepository.findByActionTypeAndDestinationPerson(ActionType.REJECTFOLLOW, person));
    }

    @NoLogging
    public List<Action> getFollowRequestings(Person person) {
        return (actionRepository.findByActionTypeAndSourcePerson(ActionType.FOLLOW_REQUEST, person));
    }

    @NoLogging
    public List<Person> getFollowRequestingsPerson(Person person) {
        return (getFollowRequestings(person).stream().map(Action::getDestinationPerson).collect(Collectors.toList()));
    }

    @NoLogging
    public List<Action> getMutings(Person person) {
        return (actionRepository.findByActionTypeAndSourcePerson(ActionType.MUTE, person));
    }

    @NoLogging
    public List<Person> getMutingsPersons(Person person) {
        return (getMutings(person).stream().map(Action::getDestinationPerson).collect(Collectors.toList()));
    }

    //------------------------------------------------------------------------------------------------------------

    @NoLogging
    public boolean exist(ActionType actionType, Person source, Person destination) {
        return (actionRepository.existsActionByActionTypeAndSourcePersonAndDestinationPerson(actionType, source,
                destination));
    }

    private Action makeAction(Person source, Person destination, ActionType actionType) {
        if (exist(actionType, source, destination)) {
            return (null);
        }
        if (destination.equals(source)) {
            return (null);
        }
        Action action = new Action();
        action.setActionType(actionType);
        action.setSourcePerson(source);
        action.setDestinationPerson(destination);
        action.setNotified(false);
        return (save(action));
    }

    public void changeNotified(Action action, boolean notified) {
        action.setNotified(notified);
        save(action);
    }


    //------------------------------------------------------------------------------------------------------------

    public void makeFollowRequest(Person source, Person destination) {
        if (isBlockState(source, destination)) {
            return;
        }
        if (!destination.isPrivate()) {
            makeFollow(source, destination);
            return;
        }
        makeAction(source, destination, ActionType.FOLLOW_REQUEST);
    }

    public void makeFollow(Person source, Person destination) {
        if (isBlockState(source, destination)) {
            return;
        }
        makeAction(source, destination, ActionType.FOLLOW);
    }

    public void makeBlock(Person source, Person destination) {
        if(source==destination){
            return;
        }
        for (ActionType actionType : ActionType.values()) {
            if (actionType == ActionType.BLOCK) {
                continue;
            }
            delete(actionRepository.findActionByActionTypeAndSourcePersonAndDestinationPerson(actionType, source,
                    destination));
            delete(actionRepository.findActionByActionTypeAndSourcePersonAndDestinationPerson(actionType, destination
                    , source));
        }
        makeAction(source, destination, ActionType.BLOCK);
    }

    public void unblock(Person source, Person destination) {
        deleteBlock(source, destination);
    }

    public void makeMute(Person source, Person destination) {
        if (isBlockState(source, destination)) {
            return;
        }
        makeAction(source, destination, ActionType.MUTE);
    }

    public void deleteAction(Action action) {
        delete(action);
    }

    public void makeReject(Person source, Person destination) {
        if (isBlockState(source, destination)) {
            return;
        }
        makeAction(source, destination, ActionType.REJECTFOLLOW);
    }

    public Action makeUnfollow(Person source, Person destination) {
        if (isBlockState(source, destination)) {
            return (null);
        }
        if (exist(ActionType.FOLLOW, source, destination)) {
            deleteFollow(source, destination);
            return (makeAction(source, destination, ActionType.UNFOLLOW));
        }
        if (exist(ActionType.FOLLOW_REQUEST, source, destination)) {
            deleteFollowRequest(source, destination);
            return (makeAction(source, destination, ActionType.UNFOLLOW));
        }
        return (null);
    }

    //------------------------------------------------------------------------------------------------------------

    public void deleteFollow(Person source, Person destination) {
        if (exist(ActionType.FOLLOW, source, destination)) {
            actionRepository.delete(actionRepository.findActionByActionTypeAndSourcePersonAndDestinationPerson(ActionType.FOLLOW, source, destination));
        }
    }

    public void deleteFollowRequest(Person source, Person destination) {
        if (exist(ActionType.FOLLOW_REQUEST, source, destination)) {
            actionRepository.delete(actionRepository.findActionByActionTypeAndSourcePersonAndDestinationPerson(ActionType.FOLLOW_REQUEST, source, destination));
        }
    }

    public void deleteBlock(Person source, Person destination) {
        if (exist(ActionType.BLOCK, source, destination)) {
            actionRepository.delete(actionRepository.findActionByActionTypeAndSourcePersonAndDestinationPerson(ActionType.BLOCK, source, destination));
        }
    }

    public void deleteMute(Person source, Person destination) {
        if (exist(ActionType.MUTE, source, destination)) {
            actionRepository.delete(actionRepository.findActionByActionTypeAndSourcePersonAndDestinationPerson(ActionType.MUTE, source, destination));
        }
    }

    //------------------------------------------------------------------------------------------------------------

    @NoLogging
    public boolean isFollowing(Person source, Person destination) {
        return (getFollowingsPersons(source).contains(destination));
    }

    @NoLogging
    public boolean isBlocking(Person source, Person destination){
        return(getBlockingsPersons(source).contains(destination));
    }

    @NoLogging
    public boolean isSourceMuting(Person source, Person destination) {
        return (getMutingsPersons(source).contains(destination));
    }

    @NoLogging
    public boolean isFollowRequesting(Person source, Person destination){
        return(getFollowRequestingsPerson(source).contains(destination));
    }

    @NoLogging
    public boolean isBlockState(Person person1, Person person2) {
        return (getBlockingsPersons(person1).contains(person2) || getBlockingsPersons(person2).contains(person1));
    }

    @NoLogging
    public boolean isMessagingAllowed(Person person1, Person person2) {
        return (isFollowing(person1, person2) || isFollowing(person2, person1));
    }

    @NoLogging
    public List<Action> getFollowRequesters(Person person) {
        return (actionRepository.findByActionTypeAndDestinationPerson(ActionType.FOLLOW_REQUEST, person));
    }

    @NoLogging
    public Action findById(int id){
        return(actionRepository.findById(id).get());
    }


}
