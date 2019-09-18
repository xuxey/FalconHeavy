package com.xuxe.falconHeavy.commands.fun;

import com.xuxe.falconHeavy.commands.Category;
import com.xuxe.falconHeavy.constants.Responses;
import com.xuxe.falconHeavy.framework.command.Command;
import com.xuxe.falconHeavy.framework.command.cooldown.CooldownScope;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;
import com.xuxe.falconHeavy.utils.IncorrectSyntaxException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;

public class FactCommand extends Command {
    public FactCommand() {
        this.name = "fact";
        this.help = "Gets a random fact";
        this.syntax = "fact";
        this.aliases = new String[]{"funfact"};
        this.category = Category.Fun;
        this.cooldown = new int[]{3, 1};
        this.cooldownScope = CooldownScope.USER;
    }

    @Override
    public void run(CommandTrigger trigger) throws IncorrectSyntaxException {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://uselessfacts.jsph.pl/random.json")
                    .build();
            Response response = client.newCall(request).execute();
            assert response.body() != null;
            JSONObject jsonObject = (JSONObject) JSONValue.parse(response.body().string());
            trigger.respond("> " + jsonObject.get("text").toString());
        } catch (IOException io) {
            reactFail();
            trigger.respond(Responses.ERROR);
        }
    }
}
