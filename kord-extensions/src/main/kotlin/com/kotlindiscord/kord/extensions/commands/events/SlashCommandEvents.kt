package com.kotlindiscord.kord.extensions.commands.events

import com.kotlindiscord.kord.extensions.ArgumentParsingException
import com.kotlindiscord.kord.extensions.commands.application.slash.EphemeralSlashCommand
import com.kotlindiscord.kord.extensions.commands.application.slash.PublicSlashCommand
import com.kotlindiscord.kord.extensions.commands.application.slash.SlashCommand
import dev.kord.core.event.interaction.ChatInputCommandInteractionCreateEvent
import kotlin.coroutines.CoroutineContext

// region Invocation events

/** Basic event emitted when a slash command is invoked. **/
public interface SlashCommandInvocationEvent<C : SlashCommand<*, *>> :
    CommandInvocationEvent<C, ChatInputCommandInteractionCreateEvent>

/** Event emitted when an ephemeral slash command is invoked. **/
public data class EphemeralSlashCommandInvocationEvent(
    override val command: EphemeralSlashCommand<*>,
    override val event: ChatInputCommandInteractionCreateEvent,
    override val coroutineContext: CoroutineContext = event.coroutineContext,
) : SlashCommandInvocationEvent<EphemeralSlashCommand<*>>

/** Event emitted when a public slash command is invoked. **/
public data class PublicSlashCommandInvocationEvent(
    override val command: PublicSlashCommand<*>,
    override val event: ChatInputCommandInteractionCreateEvent,
    override val coroutineContext: CoroutineContext = event.coroutineContext,
) : SlashCommandInvocationEvent<PublicSlashCommand<*>>

// endregion

// region Succeeded events

/** Basic event emitted when a slash command invocation succeeds. **/
public interface SlashCommandSucceededEvent<C : SlashCommand<*, *>> :
    CommandSucceededEvent<C, ChatInputCommandInteractionCreateEvent>

/** Event emitted when an ephemeral slash command invocation succeeds. **/
public data class EphemeralSlashCommandSucceededEvent(
    override val command: EphemeralSlashCommand<*>,
    override val event: ChatInputCommandInteractionCreateEvent,
    override val coroutineContext: CoroutineContext = event.coroutineContext,
) : SlashCommandSucceededEvent<EphemeralSlashCommand<*>>

/** Event emitted when a public slash command invocation succeeds. **/
public data class PublicSlashCommandSucceededEvent(
    override val command: PublicSlashCommand<*>,
    override val event: ChatInputCommandInteractionCreateEvent,
    override val coroutineContext: CoroutineContext = event.coroutineContext,
) : SlashCommandSucceededEvent<PublicSlashCommand<*>>

// endregion

// region Failed events

/** Basic event emitted when a slash command invocation fails. **/
public sealed interface SlashCommandFailedEvent<C : SlashCommand<*, *>> :
    CommandFailedEvent<C, ChatInputCommandInteractionCreateEvent>

/** Basic event emitted when a slash command's checks fail. **/
public interface SlashCommandFailedChecksEvent<C : SlashCommand<*, *>> :
    SlashCommandFailedEvent<C>, CommandFailedChecksEvent<C, ChatInputCommandInteractionCreateEvent>

/** Event emitted when an ephemeral slash command's checks fail. **/
public data class EphemeralSlashCommandFailedChecksEvent(
    override val command: EphemeralSlashCommand<*>,
    override val event: ChatInputCommandInteractionCreateEvent,
    override val reason: String,
    override val coroutineContext: CoroutineContext = event.coroutineContext,
) : SlashCommandFailedChecksEvent<EphemeralSlashCommand<*>>

/** Event emitted when a public slash command's checks fail. **/
public data class PublicSlashCommandFailedChecksEvent(
    override val command: PublicSlashCommand<*>,
    override val event: ChatInputCommandInteractionCreateEvent,
    override val reason: String,
    override val coroutineContext: CoroutineContext = event.coroutineContext,
) : SlashCommandFailedChecksEvent<PublicSlashCommand<*>>

/** Basic event emitted when a slash command's argument parsing fails'. **/
public interface SlashCommandFailedParsingEvent<C : SlashCommand<*, *>> :
    SlashCommandFailedEvent<C>, CommandFailedParsingEvent<C, ChatInputCommandInteractionCreateEvent>

/** Event emitted when an ephemeral slash command's argument parsing fails'. **/
public data class EphemeralSlashCommandFailedParsingEvent(
    override val command: EphemeralSlashCommand<*>,
    override val event: ChatInputCommandInteractionCreateEvent,
    override val exception: ArgumentParsingException,
    override val coroutineContext: CoroutineContext = event.coroutineContext,
) : SlashCommandFailedParsingEvent<EphemeralSlashCommand<*>>

/** Event emitted when a public slash command's argument parsing fails'. **/
public data class PublicSlashCommandFailedParsingEvent(
    override val command: PublicSlashCommand<*>,
    override val event: ChatInputCommandInteractionCreateEvent,
    override val exception: ArgumentParsingException,
    override val coroutineContext: CoroutineContext = event.coroutineContext,
) : SlashCommandFailedParsingEvent<PublicSlashCommand<*>>

/** Basic event emitted when a slash command invocation fails with an exception. **/
public interface SlashCommandFailedWithExceptionEvent<C : SlashCommand<*, *>> :
    SlashCommandFailedEvent<C>, CommandFailedWithExceptionEvent<C, ChatInputCommandInteractionCreateEvent>

/** Event emitted when an ephemeral slash command invocation fails with an exception. **/
public data class EphemeralSlashCommandFailedWithExceptionEvent(
    override val command: EphemeralSlashCommand<*>,
    override val event: ChatInputCommandInteractionCreateEvent,
    override val throwable: Throwable,
    override val coroutineContext: CoroutineContext = event.coroutineContext,
) : SlashCommandFailedWithExceptionEvent<EphemeralSlashCommand<*>>

/** Event emitted when a public slash command invocation fails with an exception. **/
public data class PublicSlashCommandFailedWithExceptionEvent(
    override val command: PublicSlashCommand<*>,
    override val event: ChatInputCommandInteractionCreateEvent,
    override val throwable: Throwable,
    override val coroutineContext: CoroutineContext = event.coroutineContext,
) : SlashCommandFailedWithExceptionEvent<PublicSlashCommand<*>>

// endregion
