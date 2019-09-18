package com.xuxe.falconHeavy.framework.command;

import com.xuxe.falconHeavy.FalconHeavy;
import com.xuxe.falconHeavy.commands.Category;
import com.xuxe.falconHeavy.constants.Responses;
import com.xuxe.falconHeavy.framework.UserRank;
import com.xuxe.falconHeavy.framework.command.cooldown.CooldownScope;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;
import com.xuxe.falconHeavy.utils.IncorrectSyntaxException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;

public abstract class Command {
    protected String name = "";
    protected String[] aliases = new String[]{};
    protected String help = "Help not available.";
    protected String extraHelp = "";
    protected String helpImageLink = "";
    protected int[] cooldown = {0, 0};
    protected CooldownScope cooldownScope = CooldownScope.USER;
    protected Permission[] userPermissions = new Permission[]{};
    protected boolean privateAccessible = true;
    protected Category category = Category.Miscellaneous;
    private CommandTrigger trigger;
    protected String syntax = "";
    protected UserRank rank = UserRank.DEFAULT;

    void checkRun(CommandTrigger trigger) {
        this.trigger = trigger;
        try {
            run(trigger);
        } catch (InsufficientPermissionException perms) {
            trigger.getChannel().sendMessage(Responses.NO_BOT_PERMISSION + perms.getPermission().getName()).queue();
            reactFail(trigger.getMessage());
        } catch (IncorrectSyntaxException exception) {
            if (exception.getSpecialSyntax().isEmpty()) {
                reactFail();
                trigger.respond("Incorrect syntax" + ((syntax.isEmpty()) ? "." : ", use the following: ```yaml\n" + FalconHeavy.getConfig().prefix + syntax + "```"));
                return;
            }
            trigger.respond("Incorrect syntax, use the following: ```yaml\n" + FalconHeavy.getConfig().prefix + exception.getSpecialSyntax() + "```");
        } catch (Exception e) {
            reactFail(trigger.getMessage());
            e.printStackTrace();
        }
    }

    public UserRank getRank() {
        return rank;
    }

    public String getSyntax() {
        return syntax;
    }

    public Category getCategory() {
        return category;
    }

    public abstract void run(CommandTrigger trigger) throws IncorrectSyntaxException;

    public String getName() {
        return name;
    }

    public String[] getAliases() {
        return aliases;
    }

    public String getHelp() {
        return help;
    }

    public String getExtraHelp() {
        return extraHelp;
    }

    public String getHelpImageLink() {
        return helpImageLink;
    }

    public int[] getCooldown() {
        return cooldown;
    }

    public CooldownScope getCooldownScope() {
        return cooldownScope;
    }

    public Permission[] getUserPermissions() {
        return userPermissions;
    }

    public boolean isPrivateAccessible() {
        return privateAccessible;
    }

    protected void reactFail() {
        reactFail(trigger.getMessage());
    }

    protected void reactFail(final Message message) {
        message.addReaction("\u274C").queue();
    }

    protected void reactSuccess() {
        reactSuccess(trigger.getMessage());
    }

    protected void reactSuccess(final Message message) {
        message.addReaction("\u2705").queue();
    }
}
