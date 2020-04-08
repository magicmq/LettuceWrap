# LettuceWrap
[![](https://jitpack.io/v/magicmq/LettuceWrap.svg)](https://jitpack.io/#magicmq/LettuceWrap)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/bddfaf15df234538bab532806a8f1843)](https://app.codacy.com/manual/magicmq/LettuceWrap?utm_source=github.com&utm_medium=referral&utm_content=magicmq/LettuceWrap&utm_campaign=Badge_Grade_Dashboard)

A lightweight Bukkit plugin designed to make utilizing PubSub messaging from lettuce.io clean and easy. For more information, see [lettuce.io](http://lettuce.io). This plugin is only designed to subscribe (listen) to channels and publish messages to channels. Patterns and listening for subscribing/unsubscribing is NOT supported.

## Builds
Builds are hosted on [JitPack](https://jitpack.io/#magicmq/LettuceWrap).

## Adding LettuceWrap as a Dependency
### Maven
Add the following repository:
``` maven
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```
Then, add the following dependency:
``` maven
	<dependency>
	    <groupId>com.github.magicmq</groupId>
	    <artifactId>LettuceWrap</artifactId>
	    <version>{VERSION}</version>
	</dependency>
```
Replace `{VERSION}` with the version that JitPack shows above.
### Gradle
Add the following repository:
``` groovy
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
```
Then, add the following dependency:
``` groovy
	dependencies {
	        implementation 'com.github.magicmq:LettuceWrap:{VERSION}'
	}
```
Replace `{VERSION}` with the version that JitPack shows above.

## Usage
With LettuceWrap, listeners are created individually per channel that you need to use. To create the client and add listeners, run the following code:
``` java
RedisClientWrapper client = LettuceWrap.get().createClient(plugin, ip, port, password);
client.addListener(new RedisListenerWrapper(channelname) {
    @Override
    public void messageReceived(String message) {
        //Do something with the message
    }
}
```
To deregister/remove a listener, run the following code:
``` java
client.removeListener(channelname);
```
To publish a message to a channel, run the following code:
``` java
client.sendMessage(channelname, message);
```
To deregister an entire client, run the following code:
``` java
LettuceWrap.get().deregisterClient(client);
```
JavaDocs is provided for each API method.

## Caveats
* Do **NOT** reload LettuceWrap with PlugMan! All plugins that utilize this API will break if you do this.
* If you plan to use PlugMan to reload, enable, or disable your plugin without restarting the server, you **MUST** deregister the client in your plugin's ```onDisable()``` method. See the usage section above for how to do this.
