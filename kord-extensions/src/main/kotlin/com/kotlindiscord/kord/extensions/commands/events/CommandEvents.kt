package com.kotlindiscord.kord.extensions.commands.events

import com.kotlindiscord.kord.extensions.commands.Command
import com.kotlindiscord.kord.extensions.events.KordExEvent
import dev.kord.core.event.Event

/**
 * Sealed interface representing a basic command event.
 *
 * @param C Command type
 * @param E Event type
 */
public sealed interface CommandEvent<C : Command, E : Event> : KordExEvent {
    /** Command object that this event concerns. **/
    public val command: C

    /** Event object that triggered this invocation. **/
    public val event: E
}

/** Basic event emitted when a command is invoked. **/
public sealed interface CommandInvocationEvent<C : Command, E : Event> : CommandEvent<C, E>

/** Basic event emitted when a command's invocation succeeds. **/
public sealed interface CommandSucceededEvent<C : Command, E : Event> : CommandEvent<C, E>

/** Basic event emitted when a command's invocation fails, for one reason or another. **/
public sealed interface CommandFailedEvent<C : Command, E : Event> : CommandEvent<C, E>

/** Basic event emitted when a command's checks fail, including for required bot permissions. **/
public sealed interface CommandFailedChecksEvent<C : Command, E : Event> : CommandFailedEvent<C, E> {
    /** Human-readable failure reason. **/
    public val reason: String
}

/** Basic event emitted when a command's argument parsing fails. **/
public sealed interface CommandFailedParsingEvent<C : Command, E : Event> : CommandFailedEvent<C, E> {
    /** Human-readable failure reason. **/
    public val reason: String
}

/** Basic event emitted when a command's body invocation fails with an exception. **/
public sealed interface CommandFailedWithExceptionEvent<C : Command, E : Event> : CommandFailedEvent<C, E> {
    /** Exception thrown for this failure. **/
    public val throwable: Throwable
}
