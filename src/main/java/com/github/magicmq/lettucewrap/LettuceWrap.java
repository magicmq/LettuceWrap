/*
 *    Copyright 2020 magicmq
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.github.magicmq.lettucewrap;

import com.google.common.collect.Lists;
import io.lettuce.core.RedisException;
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
        try {
            client.deregister();
        } catch (RedisException ignored) {}
    }

    /**
     * Used to get the instance of this API for making all API calls to this plugin.
     * @return The API instance
     */
    public static LettuceWrap get() {
        return instance;
    }
}
