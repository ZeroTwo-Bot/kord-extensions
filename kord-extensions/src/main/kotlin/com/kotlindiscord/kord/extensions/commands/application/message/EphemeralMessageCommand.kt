@file:Suppress("TooGenericExceptionCaught")

package com.kotlindiscord.kord.extensions.commands.application.message

import com.kotlindiscord.kord.extensions.DiscordRelayedException
import com.kotlindiscord.kord.extensions.commands.events.EphemeralMessageCommandFailedChecksEvent
import com.kotlindiscord.kord.extensions.commands.events.EphemeralMessageCommandFailedWithExceptionEvent
import com.kotlindiscord.kord.extensions.commands.events.EphemeralMessageCommandInvocationEvent
import com.kotlindiscord.kord.extensions.commands.events.EphemeralMessageCommandSucceededEvent
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.types.respond
import dev.kord.core.behavior.interaction.respondEphemeral
import dev.kord.core.event.interaction.MessageCommandInteractionCreateEvent
import dev.kord.rest.builder.message.create.EphemeralInteractionResponseCreateBuilder

public typealias InitialEphemeralMessageResponseBuilder =
    (suspend EphemeralInteractionResponseCreateBuilder.(MessageCommandInteractionCreateEvent) -> Unit)?

/** Ephemeral message command. **/
public class EphemeralMessageCommand(
    extension: Extension
) : MessageCommand<EphemeralMessageCommandContext>(extension) {
    /** @suppress Internal guilder **/
    public var initialResponseBuilder: InitialEphemeralMessageResponseBuilder = null

    /** Call this to open with a response, omit it to ack instead. **/
    public fun initialResponse(body: InitialEphemeralMessageResponseBuilder) {
        initialResponseBuilder = body
    }

    override suspend fun call(event: MessageCommandInteractionCreateEvent) {
        emitEventAsync(EphemeralMessageCommandInvocationEvent(this, event))

        try {
            if (!runChecks(event)) {
                emitEventAsync(
                    EphemeralMessageCommandFailedChecksEvent(
                        this,
                        event,
                        "Checks failed without a message."
                    )
                )

                return
            }
        } catch (e: DiscordRelayedException) {
            event.interaction.respondEphemeral { content = e.reason }

            emitEventAsync(EphemeralMessageCommandFailedChecksEvent(this, event, e.reason))

            return
        }

        val response = if (initialResponseBuilder != null) {
            event.interaction.respondEphemeral { initialResponseBuilder!!(event) }
        } else {
            event.interaction.acknowledgeEphemeral()
        }

        val context = EphemeralMessageCommandContext(event, this, response)

        context.populate()

        firstSentryBreadcrumb(context)

        try {
            checkBotPerms(context)
        } catch (e: DiscordRelayedException) {
            respondText(context, e.reason)
            emitEventAsync(EphemeralMessageCommandFailedChecksEvent(this, event, e.reason))

            return
        }

        try {
            body(context)
        } catch (t: Throwable) {
            if (t is DiscordRelayedException) {
                respondText(context, t.reason)
            }

            emitEventAsync(EphemeralMessageCommandFailedWithExceptionEvent(this, event, t))
            handleError(context, t)

            return
        }

        emitEventAsync(EphemeralMessageCommandSucceededEvent(this, event))
    }

    override suspend fun respondText(context: EphemeralMessageCommandContext, message: String) {
        context.respond { content = message }
    }
}
