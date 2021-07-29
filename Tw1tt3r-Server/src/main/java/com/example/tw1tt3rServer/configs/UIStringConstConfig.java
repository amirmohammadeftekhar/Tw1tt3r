package com.example.tw1tt3rServer.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "ui-string-const-config")
@Getter
@Setter
@Validated
public class UIStringConstConfig {
    @NotNull
    private String userNameNotAvailable;
    @NotNull
    private String addressNotAvailable;
    @NotNull
    private String following;
    @NotNull
    private String requested;
    @NotNull
    private String follow;
    @NotNull
    private String wrongPassword;
    @NotNull
    private String deletedAccount;
    @NotNull
    private String mouseMoved;
    @NotNull
    private String selected;
    @NotNull
    private String personalPage;
    @NotNull
    private String choosingWindow;
    @NotNull
    private String blackList;
    @NotNull
    private String followers;
    @NotNull
    private String confirm;
    @NotNull
    private String rejectAndNotify;
    @NotNull
    private String rejectAndNotNotify;
    @NotNull
    private String entering;
    @NotNull
    private String tweetmaking;
    @NotNull
    private String retweet;
    @NotNull
    private String categoryMessaging;
    @NotNull
    private String edit;
    @NotNull
    private String delete;
}
