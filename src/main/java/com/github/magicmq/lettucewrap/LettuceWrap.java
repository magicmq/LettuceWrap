package com.github.magicmq.lettucewrap;

import com.google.common.collect.Lists;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class LettuceWrap extends JavaPlugin {

    private static LettuceWrap instance;

    private List<RedisClientWrapper> registeredclients;

    @Override
    public void onEnable() {
        instance = this;

        registeredclients = Lists.newArrayList();
    }

    @Override
    public void onDisable() {
        for (RedisClientWrapper wrapper : registeredclients) {
            wrapper.deregister();
        }
    }

    /**
     * Used to create a new instance of the redis client wrapper.
     * @param owner The plugin wanting to create a new client
     * @param ip The IP of the redis server
     * @param port The port of the redis server
     * @param password The password of the redis server
     * @return The instance of the RedisClientWrapper that was created
     * @see RedisClientWrapper
     */
    public RedisClientWrapper createClient(JavaPlugin owner, String ip, String port, String password) {
        RedisClientWrapper wrapper = new RedisClientWrapper(owner, ip, port, password);
        registeredclients.add(wrapper);
        return wrapper;
    }

    /**
     * Used to deregister an instance of the redis client wrapper.
     * This would be used if a new client needed to be created.
     * @param client The client that should be deregistered
     */
    public void deregisterClient(RedisClientWrapper client) {
        registeredclients.remove(client);
        client.deregister();
    }

    /**
     * Used to get the instance of this API for making all API calls to this plugin.
     * @return The API instance
     */
    public static LettuceWrap get() {
        return instance;
    }
}
