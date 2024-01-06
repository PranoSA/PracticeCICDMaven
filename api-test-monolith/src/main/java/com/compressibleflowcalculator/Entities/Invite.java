package com.compressibleflowcalculator.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;

import java.util.UUID;

import com.compressibleflowcalculator.Jackson_Views.Invite_View;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Invite {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "invite_id", nullable = false, columnDefinition = "UUID default gen_random_uuid()")
    private UUID invite_id;

    @OneToOne(targetEntity = Group.class, orphanRemoval = true, cascade = CascadeType.ALL, fetch = jakarta.persistence.FetchType.LAZY)
    @JsonView({ Invite_View.Invite_Response_View.class })
    private Group group_id;

    @Column(name = "email")
    @JsonView({ Invite_View.Invite_Response_View.class })
    private String email;

    @Column(name = "invite_hash")
    @JsonView({ Invite_View.Invite_Response_View.class })
    private String invite_hash;

    @Column(name = "invite_accepted")
    @JsonView({ Invite_View.Invite_Response_View.class })
    private Boolean invite_accepted;

    public boolean VerifyInviteCode(String invite_code, String email) {
        // Verify invite_code SHA256 hash against invite_hash
        if (invite_code == invite_hash && email == this.email) {
            invite_accepted = true;
        } else {
            invite_accepted = false;
        }

        return invite_accepted;
    }
}
