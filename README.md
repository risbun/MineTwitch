
<img src="https://github.com/ThaCheeseBun/MineTwitch/blob/master/docs/MineTwitchWordmark.png?raw=true" style="width:100%;">

# MineTwitch

MineTwitch is a Minecraft Twitch Integration.

Every minute<a href="#note1">¹</a> viewers can vote on command they want to happen ingame.
<br>
After 15 seconds<a href="#note1">¹</a> the command with the most votes will be executed and ran on all players (Multiplayer Compatible).
<br>
The command could either be bad or good. It's all up to the viewers.

// add screenshots here

# Getting started

<ol>
	<li><a href="https://github.com/ThaCheeseBun/MineTwitch/releases">Download</a> the latest plugin</li>
	<li>Put the plugin jar inside the plugins folder of your spigot/paperspigot powered server (If you want to play this in singleplayer you can create a local server by following <a href="https://www.youtube.com/watch?v=ez2NgYtcaNc">this guide</a>).</li>
	<li>Now start your server up. When it's done loading (you should see a "Done (13.37s)" in the console) shut it back down.</li>
	<li>Go to the plugins folder in the server directory. Here there should be a new folder named "MineTwitch" with two files inside, config.yml and commands.json. Open the config.yml file.</li>
	<li>Go to <a href="https://twitchapps.com/tmi/">this site</a> in a browser and acquire a oauth token. Put this token in the config file after <pre>ouath:</pre>Example: <pre>ouath: 'g534uy5g3u4y5g3o4634f6o346t3fu'</pre></li>
	<li>Type in the name of the account you used when acquiring the oauth token after <pre>username:</pre>Example: <pre>username: 'iReeZBot'</pre></li>
	<li>Type in your Twitch channles name (can be the same as your username) after <pre>channel:</pre>Example: <pre>channel: 'iReeZey'</pre></li>
	<li>Now go to your server terminal and type <pre>/reload confirm</pre></li>
	<li>Open up Minecraft and connect to your server.</li>
	<li>When you are ready, type this command in chat: <pre>/mt</pre></li>
</ol>

# Config
<h4> CHAT BOT </h4>

Bot oauth token:
<pre>oauth: 'oauth:xxxx'</pre>

Bot username:
<pre>username: 'examplename'</pre>

Twitch channel:
<pre>channel: 'examplechannel'</pre>

<h4> GENERAL SETTINGS </h4>

Vote delay (amount of time between vote end and next vote) in seconds
<pre>delay: (Default 60)</pre>

Vote time (amount of time viewers have to vote) in seconds
<pre>time: (Default 15)</pre>

Hide commands from ingame (commands will be sent in twitch chat, might be hard to catch up for large streamers)
<pre>hide: (Default false)</pre>

# Custom commands

// add custom commands guide

## Copyright &copy; 2020 - MineTwitch Authors, Twitch Logo &copy; 2020 Twitch, Minecraft Logo &copy; 2020 Mojang
