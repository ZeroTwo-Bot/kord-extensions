package com.kotlindiscord.kord.extensions.events

import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.ExtensionState
import kotlin.coroutines.CoroutineContext

/**
 * Event fired when an extension's state changes.
 *
 * @property extension Extension that has a state change
 * @property state Extension's new state
 */
public data class ExtensionStateEvent(
    public val extension: Extension,
    public val state: ExtensionState,
    override val coroutineContext: CoroutineContext = extension.kord.coroutineContext,
) : KordExEvent
