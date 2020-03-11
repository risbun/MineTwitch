
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
	<li><a href="https://github.com/ThaCheeseBun/MineTwitch/releases">Download</a> the latest plugin or bundled server zip.</li>
	<li>If you downloaded the bundled server zip, follow these steps:<ol>
		<li>Extract the zip somewhere</li>
		<li>On Windows, run <pre>ServerStart.bat</pre>On Linux, run <pre>ServerStart.sh</pre></li>
		<li>A terminal window should popup. When you see that "Done (xx seconds)" has been printed in the terminal, run <pre>op &lt;your name&gt;</pre></li>
	</ol>If you downloaded the plugin jar, put in in your servers plugins folder and restart it.</li>
	<li>Go to the plugins folder in the server directory. Here there should be a new folder named MineTwitch with two files inside, config.yml and commands.json. Open the config.yml file.</li>
	<li>Go to <a href="https://twitchapps.com/tmi/">this site</a> in a browser and acquire a oauth token. Put this token in the config file after <pre>ouath:</pre>Example: <pre>ouath: 'g534uy5g3u4y5g3o4634f6o346t3fu'</pre></li>
	<li>Type in the name of the account you used when acquiring the oauth token after <pre>username:</pre>Example: <pre>username: 'iReeZBot'</pre></li>
	<li>Type in your Twitch channles name (can be the same as your username) after <pre>channel:</pre>Example: <pre>channel: 'iReeZey'</pre></li>
	<li>Now go to your server terminal and type <pre>/reload</pre></li>
	<li>Open up Minecraft and connect to your server. if you used the bundled zip, type <pre>localhost</pre>in the address field</li>
	<li>When you are ready, type this command in chat: <pre>/mt</pre></li>
</ol>

# Config

HOW TO SETUP <br>
go to https://twitchapps.com/tmi/ and connect with Twitch <br>
put the ouath:xxxx key under oauth down below <br>
put the accounts username under username <br>
put your twitch channel under channel <br>
done <br>


<h4> CHAT BOT </h4>
You can use your own Twitch account if you don't have a dedicated bot.

Bot OAuth (grab at https://twitchapps.com/tmi/)
<pre>oauth: 'oauth:xxxx'</pre>

Bot username
<pre>username: 'examplename'</pre>

Twitch channel
<pre>channel: 'examplechannel'</pre>

<h4> GENERAL SETTINGS </h4>

Vote delay (amount of time between vote end and next vote) in seconds
<pre>delay: 60</pre>

Vote time (amount of time viewers have to vote) in seconds
<pre>time: 15</pre>

Hide commands from ingame (commands will be sent in twitch chat)
<pre>hide: false</pre>

# Custom commands

// add custom commands guide

# Made by ThaCheeseBun and ReeZey


