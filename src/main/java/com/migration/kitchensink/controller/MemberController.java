/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.migration.kitchensink.controller;

import com.migration.kitchensink.data.MemberListProducer;
import com.migration.kitchensink.model.Member;
import com.migration.kitchensink.service.MemberRegistration;
import jakarta.annotation.PostConstruct;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://www.cdi-spec.org/faq/#accordion6
@Controller
public class MemberController {


    @Autowired
    private MemberRegistration memberRegistration;

    @Autowired
    private MemberListProducer mems;

    private Member newMember;

    @PostConstruct
    public void initNewMember() {
        newMember = new Member();
    }

    @GetMapping("/")
    public String initial(Model model){
        model.addAttribute("newMember",newMember);
        mems.retrieveAllMembersOrderedByName();
       model.addAttribute("members", mems.getMembers());
        return "index";
    }

    @PostMapping(value="/register")

    public String register(@Valid @ModelAttribute("newMember") Member newMember, BindingResult result, Model model) throws Exception {
        try {
            memberRegistration.register(newMember);
            model.addAttribute("Registered!", "Registration successful");
            mems.onMemberListChanged(newMember);
            model.addAttribute("members",mems.getMembers());
            initNewMember();
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            model.addAttribute( errorMessage, "Registration unsuccessful");
        }return "index";
    }

    private String getRootErrorMessage(Exception e) {
        // Default to general error message that registration failed.
        String errorMessage = "Registration failed. See server log for more information";
        if (e == null) {
            // This shouldn't happen, but return the default messages
            return errorMessage;
        }

        // Start with the exception and recurse to find the root cause
        Throwable t = e;
        while (t != null) {
            // Get the message from the Throwable class instance
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
        // This is the root cause message
        return errorMessage;
    }

}
