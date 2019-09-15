package com.xuxe.falconHeavy.commands.utilities;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.xuxe.falconHeavy.FalconHeavy;
import com.xuxe.falconHeavy.commands.Category;
import com.xuxe.falconHeavy.framework.UserRank;
import com.xuxe.falconHeavy.framework.command.Command;
import com.xuxe.falconHeavy.framework.command.cooldown.CooldownScope;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;

public class YoutubeCommand extends Command {
    public YoutubeCommand() {
        this.name = "yt";
        this.aliases = new String[]{"youtube", "vid"};
        this.syntax = "yt <stuff>";
        this.help = "Gets the first search result from Youtube";
        this.extraHelp = help + " and sends its link here";
        this.category = Category.Utilities;
        this.cooldownScope = CooldownScope.USER;
        this.cooldown = new int[]{5, 1};
        this.rank = UserRank.DONATOR;
    }

    @Override
    public void run(CommandTrigger trigger) {
        final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
        final JsonFactory JSON_FACTORY = new JacksonFactory();
        try {
            YouTube youTube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, httpRequest -> {
            }).setApplicationName("FalconBot").build();

            String query = trigger.getString();

            YouTube.Search.List search = youTube.search().list("id,snippet");
            search.setKey(FalconHeavy.getConfig().getYouTubeKey());

            search.setQ(query);
            search.setType("video");
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults(Long.parseLong("1"));
            SearchListResponse searchResponse = search.execute();

            SearchResult searchResult = searchResponse.getItems().get(0);
            if (searchResult.getId().getKind().equals("youtube#video")) {
                trigger.respond("Top search result: https://www.youtube.com/watch?v=" + searchResult.getId().getVideoId() + "\n" + trigger.getUser().getAsMention());
            } else {
                trigger.respond("Something went wrong");//add loop if this is true
            }

        } catch (Exception e) {
            trigger.respond("Something went wrong.");
            e.printStackTrace();
        }
    }
}
