package com.compressibleflowcalculator.Controller;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.compressibleflowcalculator.Entities.Group;
import com.compressibleflowcalculator.Jackson_Views.Group_View;
import com.compressibleflowcalculator.Service.GroupService;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.websocket.server.PathParam;

@RestController
public class GroupController {

    @Autowired
    private GroupService groupService;

    /*
     * @GetMapping("/")
     * public String root() {
     * return "Hello, world!";
     * }
     */

    @GetMapping("/group/path")
    public String getMethodName() {
        return "Hello World";
    }

    @GetMapping("/getgroups")
    public List<Group> getAllGroups() {
        System.err.println("Get all Groups");
        List<Group> newList = new ArrayList<Group>();
        newList.add(new Group("Group 1", "Group 1 Description"));
        newList.add(new Group("Group 2", "Group 2 Description"));

        return newList;
        // return groupService.getAllGroups();
    }

    //
    /*
     * @GetMapping("/group/{groupid}")
     * public Group getMethodName(@PathParam("groupid") String groupid) {
     * 
     * return groupService.getGroup(UUID.fromString(groupid));
     * 
     * }
     */

    @PostMapping("/")
    public Group createGroup(@JsonView(Group_View.Group_Request_View.class) @RequestBody Group group,
            @AuthenticationPrincipal Jwt principal) {

        principal.getClaimAsString("sub");
        UUID userid = UUID.fromString(principal.getClaimAsString("sub"));

        return groupService.createGroup(group);

    }

}
