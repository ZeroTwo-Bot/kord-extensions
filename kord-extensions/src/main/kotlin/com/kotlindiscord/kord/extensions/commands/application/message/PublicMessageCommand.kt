@file:Suppress("TooGenericExceptionCaught")

package com.kotlindiscord.kord.extensions.commands.application.message

import com.kotlindiscord.kord.extensions.CommandException
import com.kotlindiscord.kord.extensions.commands.events.PublicMessageCommandFailedChecksEvent
import com.kotlindiscord.kord.extensions.commands.events.PublicMessageCommandFailedWithExceptionEvent
import com.kotlindiscord.kord.extensions.commands.events.PublicMessageCommandInvocationEvent
import com.kotlindiscord.kord.extensions.commands.events.PublicMessageCommandSucceededEvent
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.interactions.respond
import dev.kord.core.behavior.interaction.respondPublic
import dev.kord.core.event.interaction.MessageCommandInteractionCreateEvent
import dev.kord.rest.builder.message.create.PublicInteractionResponseCreateBuilder

public typealias InitialPublicMessageResponseBuilder =
    (suspend PublicInteractionResponseCreateBuilder.(MessageCommandInteractionCreateEvent) -> Unit)?

/** Public message command. **/
public class PublicMessageCommand(
    extension: Extension
) : MessageCommand<PublicMessageCommandContext>(extension) {
    /** @suppress Internal guilder **/
    public var initialResponseBuilder: InitialPublicMessageResponseBuilder = null

    /** Call this to open with a response, omit it to ack instead. **/
    public fun initialResponse(body: InitialPublicMessageResponseBuilder) {
        initialResponseBuilder = body
    }

    override suspend fun call(event: MessageCommandInteractionCreateEvent) {
        emitEventAsync(PublicMessageCommandInvocationEvent(this, event))

        try {
            if (!runChecks(event)) {
                emitEventAsync(
                    PublicMessageCommandFailedChecksEvent(
                        this,
                        event,
                        "Checks failed without a message."
                    )
                )

                return
            }
        } catch (e: CommandException) {
            event.interaction.respondPublic { content = e.reason }

            emitEventAsync(PublicMessageCommandFailedChecksEvent(this, event, e.reason))

            return
        }

        val response = if (initialResponseBuilder != null) {
            event.interaction.respondPublic { initialResponseBuilder!!(event) }
        } else {
            event.interaction.acknowledgePublic()
        }

        val context = PublicMessageCommandContext(event, this, response)

        context.populate()

        firstSentryBreadcrumb(context)

        try {
            checkBotPerms(context)
        } catch (e: CommandException) {
            respondText(context, e.reason)
            emitEventAsync(PublicMessageCommandFailedChecksEvent(this, event, e.reason))

            return
        }

        try {
            body(context)
        } catch (t: Throwable) {
            if (t is CommandException) {
                respondText(context, t.reason)
            }

            emitEventAsync(PublicMessageCommandFailedWithExceptionEvent(this, event, t))
            handleError(context, t)

            return
        }

        emitEventAsync(PublicMessageCommandSucceededEvent(this, event))
    }

    override suspend fun respondText(context: PublicMessageCommandContext, message: String) {
        context.respond { content = message }
    }
}
