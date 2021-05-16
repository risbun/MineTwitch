![MineTwitch](https://github.com/risbun/MineTwitch/blob/master/docs/MineTwitchWordmark.png?raw=true)

# MineTwitch

MineTwitch is a Minecraft Twitch Integration.

Every minute viewers can vote on command they want to happen ingame.\
After 15 seconds the command with the most votes will be executed and ran on all players (Multiplayer Compatible).\
The command could either be bad or good. It's all up to the viewers.

// add screenshots here

# Getting started

* [Download](https://github.com/risbun/MineTwitch/releases) the latest plugin
* Put the plugin jar inside the plugins folder of your Spigot powered server (If you want to play this in singleplayer you can create a local server by following [this guide](https://www.youtube.com/watch?v=ez2NgYtcaNc)).
* Now start your server up. When it's done loading (you should see a "Done (13.37s)" in the console) continue to the next step.
* Go to the plugins folder in the server directory. Here there should be a new folder named "MineTwitch" with two files inside, config.yml and commands.json. Open the config.yml file.
* Go to [this site](https://twitchapps.com/tmi/) in a browser and acquire a oauth token. Put this token in the config file after `oauth:`
  ```
  oauth: 'oauth:g534uy5g3u4y5g3o4634f6o346t3fu'
  ```
* Type in the name of the account you used when acquiring the oauth token after `username:`
  ```
  username: 'iReeZBot'
  ```
* Type in your Twitch channels name (can be the same as your username) after `channels:` on a new line:
  ```
  channels:
  - 'iReeZey'
  ```
  - If you want to vote from multiple channels you can add them using this syntax:
    ```
    channels:
    - 'iReeZey'
    - 'ThaCheeseBun'
    ```
* Save the file, go to your server terminal and type `/mt reload`
* Open up Minecraft and connect to your server.
* When you are ready, type this command in chat: `/mt`

# Config
**CHAT BOT**

Bot OAuth token:
```
oauth: 'oauth:xxxx'
```

Bot username:
```
username: 'examplename'
```

Twitch channels:
```
channels:
- 'examplechannel'
- 'otherchannel'
```

**VOTE SETTINGS**

Delay between vote end and next vote in seconds
```
vote: 60
```
    
Amount of time viewers have to vote in seconds
```
time: 15
```

**INGAME SETTINGS**

Hide commands from ingame (commands will be sent in twitch chat, might be hard to catch up for large streamers)
```
hide: false
```

Show Twitch chat in your ingame chat (half not working for multiple channels)
```
chat: true
```

# Custom commands

custom commands guide coming soon™

## Copyright &copy; 2021 - MineTwitch Authors, Twitch Logo &copy; 2021 Twitch, Minecraft Logo &copy; 2021 Mojang
