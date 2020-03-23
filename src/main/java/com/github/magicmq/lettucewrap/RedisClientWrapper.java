package com.github.magicmq.lettucewrap;

import com.google.common.collect.Maps;
import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

public class RedisClientWrapper {

    private JavaPlugin owner;
    private RedisClient client;
    private StatefulRedisPubSubConnection<String, String> connectionincoming;
    private StatefulRedisPubSubConnection<String, String> connectionoutgoing;
    private HashMap<String, RedisListenerWrapper> listeners;

    RedisClientWrapper(JavaPlugin owner, String ip, String port, String password) {
        this.owner = owner;
        try {
            client = RedisClient.create("redis://" + URLEncoder.encode(password, "UTF-8") + "@" + ip + ":" + port + "/0");
        } catch (UnsupportedEncodingException ignored) {}
        connectionincoming = client.connectPubSub();
        connectionoutgoing = client.connectPubSub();
        listeners = Maps.newHashMap();
    }

    /**
     * Used to register a new listener with this client.
     * @param listener A RedisListenerWrapper that represents what will be called when a message is received from its channel.
     */
    public void addListener(RedisListenerWrapper listener) {
        listeners.put(listener.getChannel(), listener);
        connectionincoming.addListener(listener);
        connectionincoming.sync().subscribe(listener.getChannel());
    }

    /**
     * Used to remove (deregister) a listener within this client.
     * @param channel The channel that should be deregistered
     * @return True if the listener was successfully deregistered, false if the listener was not registered in this client in the first place
     */
    public boolean removeListener(String channel) {
        RedisListenerWrapper listener = listeners.remove(channel);
        if (listener != null) {
            connectionincoming.removeListener(listener);
            connectionincoming.sync().unsubscribe(channel);
            return true;
        } else return false;
    }

    /**
     * Used to publish a message using this client.
     * @param channel The channel on which the message should be published
     * @param message The message content
     */
    public void sendMessage(String channel, String message) {
        connectionoutgoing.async().publish(channel, message);
    }

    void deregister() {
        connectionincoming.close();
        connectionoutgoing.close();
        client.shutdown();
    }

    /**
     * Used to get all registered listeners
     * @return A HashMap containing all channels registered and their respective listeners
     */
    public HashMap<String, RedisListenerWrapper> getListeners() {
        return listeners;
    }

    /**
     * Used to get the plugin owner of this client.
     * @return The plugin that created and owns this client
     */
    public JavaPlugin getOwner() {
        return owner;
    }
}
