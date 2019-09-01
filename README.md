# FalconHeavy
An updated [FalconBot](https://discordbots.org/bot/511949995776147466) with it's own command structure and framework.
This bot makes your discord community experience better by adding convenience, fun and a tad bit of utility!

[![ForTheBadge built-with-love](http://ForTheBadge.com/images/badges/built-with-love.svg)](https://GitHub.com/ThatXuxe/)

## About
FalconBot is built around parsing the internet within discord. With commands such as google, youtube and wiki, FalconBot helps you find information you need instantaneously.

FalconBot also has some fun features such as emotify, cat and trivia commands and more.

The Bot has some moderation features like Ban and Kick, with more coming soon.

FalconBot also has music features which are currently in Beta.

With features like math and lyrics, you can do a lot of extra tasks and get a lot of information without ever having to leave discord.

## Getting Started

You can invite the bot to a discord server where you have the Permission: MANAGE_SERVER.
Follow [this](https://discordapp.com/oauth2/authorize?client_id=511949995776147466&scope=bot&permissions=36826176) OAuth Link.

### Installing

The bot is ready to go once it joins your server.
However, this bot has a minimal amount of permissions, meaning !!ban and !!kick will not work by default. However, you can easily modify this by adding those permissions to its roles.

```
!!ban <@Mention>
!!kick <@Mention>
!!purge <number>
```


## Using the bot

For commands, `!!` is the prefix.

### Some commands

FalconBot is equipped with a bunch of commands that adds functionality to your server.

```
!!music - Plays YouTube audio in voice channels. Usage: !music p <track name>. Use !!music skip to skip/pause tracks.
!!g - Gets the first link from a google search.
!!lyrics <songName> - Gets the lyrics to a song
!!yt <searchTerm> - Gets the first search result on Youtube.
!!joindate - Gets most recent join date for a member in the server.
!!emotify - Converts an alpha numeric string into its emote equivalent. Limited to 12 characters.
!!servercreationdate - Gets the date on which the server was created.
!!info - Fetches data from existing Wikipedia infoboxes.
!!cat - Gets a random picture of a cat
!!mindblow - Gets a random fact
!!learnjava - Fetches a series of helpful links to Java Tutorials
!!math - Calculates a math expression
!!avatar - Gets a mentioned user's profile picture. Alternatively, gets sender's profile picture if no one is mentioned.
!!help - Shows more information about XuccBot's commands.
!!ping - Fetches XuccBot's ping in milliseconds
!!rep - Fetches a link to XuccBOt's GitHub repository.
!!usercount - Fetches the number of XuccBot users.
!!guildcount - Gets the number of discord servers XuccBot is a part of.
```


## Built With

* [Java](https://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html) - The base programming language.
* [Maven](https://maven.apache.org/) - Dependency Management
* [JDA](https://github.com/DV8FromTheWorld/JDA) - Used to communicate with Discord
* [Jsoup](https://jsoup.org/) - Used to parse through the Internet

## Authors

* **ThatXuxe** -  [Github](https://github.com/ThatXuxe)

## Acknowledgments

* sedmelluq, DV8FromTheWorld and other Authors of the libraries used.
