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

import io.lettuce.core.pubsub.RedisPubSubListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class RedisListenerWrapper implements RedisPubSubListener<String, String> {

    private String channel;
    private JavaPlugin owner;

    /**
     * This class represents a listener. When a message is received on the channel specified, the message will be called via the messageReceived method.
     * @param channel The name of the channel that should be registered
     */
    public RedisListenerWrapper(String channel) {
        this.channel = channel;
    }

    @Override
    public void message(String channel, String message) {
        if (this.channel.equals(channel)) {
            Bukkit.getScheduler().runTask(owner, () -> messageReceived(message));
        }
    }

    /**
     * Called when a message is received on the channel specified when creating this listener.
     * @param message The message that was received on the channel specified
     */
    public abstract void messageReceived(String message);

    @Override
    public void message(String pattern, String channel, String message) {
        //Should be empty
    }

    @Override
    public void subscribed(String channel, long count) {
        //Should be empty
    }

    @Override
    public void psubscribed(String pattern, long count) {
        //Should be empty
    }

    @Override
    public void unsubscribed(String channel, long count) {
        //Should be empty
    }

    @Override
    public void punsubscribed(String pattern, long count) {
        //Should be empty
    }

    protected void setOwner(JavaPlugin owner) {
        this.owner = owner;
    }

    protected String getChannel() {
        return channel;
    }
}
