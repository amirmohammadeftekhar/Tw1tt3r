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
@ConfigurationProperties(prefix = "path-config")
@Getter
@Setter
@Validated
public class PathConfig {
    @NotNull
    private String entering_fxml;
    @NotNull
    private String signin_fxml;
    @NotNull
    private String signup_fxml;
    @NotNull
    private String mainmenu_fxml;
    @NotNull
    private String tweet_fxml;
    @NotNull
    private String timeline_fxml;
    @NotNull
    private String roomoutview_fxml;
    @NotNull
    private String choosingmenu_fxml;
    @NotNull
    private String personalpage_fxml;
    @NotNull
    private String messagingmainmenu_fxml;
    @NotNull
    private String message_fxml;
    @NotNull
    private String roomchatbox_fxml;
    @NotNull
    private String like_icon;
    @NotNull
    private String unlike_icon;
    @NotNull
    private String profile_fxml;
    @NotNull
    private String showlist_fxml;
    @NotNull
    private String personoutview_fxml;
    @NotNull
    private String explore_fxml;
    @NotNull
    private String setting_fxml;
    @NotNull
    private String tweetmaking_fxml;
    @NotNull
    private String categorymessaging_fxml;
}
