@file:OptIn(UnsafeAPI::class)

package com.kotlindiscord.kord.extensions.modules.unsafe.commands

import com.kotlindiscord.kord.extensions.ArgumentParsingException
import com.kotlindiscord.kord.extensions.commands.events.*
import com.kotlindiscord.kord.extensions.modules.unsafe.annotations.UnsafeAPI
import dev.kord.core.event.interaction.ChatInputCommandInteractionCreateEvent
import dev.kord.core.event.interaction.MessageCommandInteractionCreateEvent
import dev.kord.core.event.interaction.UserCommandInteractionCreateEvent
import kotlin.coroutines.CoroutineContext

// region Message commands

/** Event emitted when an unsafe message command is invoked. **/
public data class UnsafeMessageCommandInvocationEvent(
    override val command: UnsafeMessageCommand,
    override val event: MessageCommandInteractionCreateEvent,
    override val coroutineContext: CoroutineContext = event.coroutineContext,
) : MessageCommandInvocationEvent<UnsafeMessageCommand>

/** Event emitted when an unsafe message command invocation succeeds. **/
public data class UnsafeMessageCommandSucceededEvent(
    override val command: UnsafeMessageCommand,
    override val event: MessageCommandInteractionCreateEvent,
    override val coroutineContext: CoroutineContext = event.coroutineContext,
) : MessageCommandSucceededEvent<UnsafeMessageCommand>

/** Event emitted when an unsafe message command's checks fail. **/
public data class UnsafeMessageCommandFailedChecksEvent(
    override val command: UnsafeMessageCommand,
    override val event: MessageCommandInteractionCreateEvent,
    override val reason: String,
    override val coroutineContext: CoroutineContext = event.coroutineContext,
) : MessageCommandFailedChecksEvent<UnsafeMessageCommand>

/** Event emitted when an unsafe message command invocation fails with an exception. **/
public data class UnsafeMessageCommandFailedWithExceptionEvent(
    override val command: UnsafeMessageCommand,
    override val event: MessageCommandInteractionCreateEvent,
    override val throwable: Throwable,
    override val coroutineContext: CoroutineContext = event.coroutineContext,
) : MessageCommandFailedWithExceptionEvent<UnsafeMessageCommand>

// endregion

// region Slash commands

/** Event emitted when an unsafe slash command is invoked. **/
public data class UnsafeSlashCommandInvocationEvent(
    override val command: UnsafeSlashCommand<*>,
    override val event: ChatInputCommandInteractionCreateEvent,
    override val coroutineContext: CoroutineContext = event.coroutineContext,
) : SlashCommandInvocationEvent<UnsafeSlashCommand<*>>

/** Event emitted when an unsafe slash command invocation succeeds. **/
public data class UnsafeSlashCommandSucceededEvent(
    override val command: UnsafeSlashCommand<*>,
    override val event: ChatInputCommandInteractionCreateEvent,
    override val coroutineContext: CoroutineContext = event.coroutineContext,
) : SlashCommandSucceededEvent<UnsafeSlashCommand<*>>

/** Event emitted when an unsafe slash command's checks fail. **/
public data class UnsafeSlashCommandFailedChecksEvent(
    override val command: UnsafeSlashCommand<*>,
    override val event: ChatInputCommandInteractionCreateEvent,
    override val reason: String,
    override val coroutineContext: CoroutineContext = event.coroutineContext,
) : SlashCommandFailedChecksEvent<UnsafeSlashCommand<*>>

/** Event emitted when an unsafe slash command's argument parsing fails. **/
public data class UnsafeSlashCommandFailedParsingEvent(
    override val command: UnsafeSlashCommand<*>,
    override val event: ChatInputCommandInteractionCreateEvent,
    override val exception: ArgumentParsingException,
    override val coroutineContext: CoroutineContext = event.coroutineContext,
) : SlashCommandFailedParsingEvent<UnsafeSlashCommand<*>>

/** Event emitted when an unsafe slash command invocation fails with an exception. **/
public data class UnsafeSlashCommandFailedWithExceptionEvent(
    override val command: UnsafeSlashCommand<*>,
    override val event: ChatInputCommandInteractionCreateEvent,
    override val throwable: Throwable,
    override val coroutineContext: CoroutineContext = event.coroutineContext,
) : SlashCommandFailedWithExceptionEvent<UnsafeSlashCommand<*>>

// endregion

// region User commands

/** Event emitted when an unsafe user command is invoked. **/
public data class UnsafeUserCommandInvocationEvent(
    override val command: UnsafeUserCommand,
    override val event: UserCommandInteractionCreateEvent,
    override val coroutineContext: CoroutineContext = event.coroutineContext,
) : UserCommandInvocationEvent<UnsafeUserCommand>

/** Event emitted when an unsafe user command invocation succeeds. **/
public data class UnsafeUserCommandSucceededEvent(
    override val command: UnsafeUserCommand,
    override val event: UserCommandInteractionCreateEvent,
    override val coroutineContext: CoroutineContext = event.coroutineContext,
) : UserCommandSucceededEvent<UnsafeUserCommand>

/** Event emitted when an unsafe user command's checks fail. **/
public data class UnsafeUserCommandFailedChecksEvent(
    override val command: UnsafeUserCommand,
    override val event: UserCommandInteractionCreateEvent,
    override val reason: String,
    override val coroutineContext: CoroutineContext = event.coroutineContext,
) : UserCommandFailedChecksEvent<UnsafeUserCommand>

/** Event emitted when an unsafe user command invocation fails with an exception. **/
public data class UnsafeUserCommandFailedWithExceptionEvent(
    override val command: UnsafeUserCommand,
    override val event: UserCommandInteractionCreateEvent,
    override val throwable: Throwable,
    override val coroutineContext: CoroutineContext = event.coroutineContext,
) : UserCommandFailedWithExceptionEvent<UnsafeUserCommand>

// endregion
