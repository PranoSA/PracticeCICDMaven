package com.compressibleflowcalculator.Service;

import com.compressibleflowcalculator.Repository.GroupRepository;
import com.compressibleflowcalculator.Entities.Group;

import java.util.UUID;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    public Group getGroup(UUID groupid) {
        return groupRepository.findById(groupid)
                .orElseThrow(() -> new ResourceAccessException("Group"));
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }
}
