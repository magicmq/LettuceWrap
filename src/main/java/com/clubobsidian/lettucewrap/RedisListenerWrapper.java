package com.clubobsidian.lettucewrap;

import io.lettuce.core.pubsub.RedisPubSubListener;

public abstract class RedisListenerWrapper implements RedisPubSubListener<String, String> {

    private String channel;

    public RedisListenerWrapper(String channel) {
        this.channel = channel;
    }

    @Override
    public void message(String channel, String message) {
        if (this.channel.equals(channel)) {
            messageReceived(message);
        }
    }

    /**
     * Called when a message is received on the channel specified when creating this listener.
     * @param message The message that was received on the channel specified
     */
    public abstract void messageReceived(String message);

    @Override
    public void message(String pattern, String channel, String message) {}

    @Override
    public void subscribed(String channel, long count) {}

    @Override
    public void psubscribed(String pattern, long count) {}

    @Override
    public void unsubscribed(String channel, long count) {}

    @Override
    public void punsubscribed(String pattern, long count) {}

    String getChannel() {
        return channel;
    }
}
