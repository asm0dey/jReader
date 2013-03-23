package com.github.asm0dey.client;

import com.gwtplatform.mvp.client.proxy.Gatekeeper;

/**
 * User: finkel
 * <p/>
 * Date: 22.03.13
 * <p/>
 * Time: 18:18
 */
public class LoggedInGatekeeper implements Gatekeeper{
    /**
     * Checks whether or not the {@link com.gwtplatform.mvp.client.proxy.Place} controlled by this gatekeeper can
     * be revealed.
     *
     * @return {@code true} if the {@link com.gwtplatform.mvp.client.proxy.Place} can be revealed, {@code false}
     *         otherwise.
     */
    @Override
    public boolean canReveal() {
        return true;
    }
}
