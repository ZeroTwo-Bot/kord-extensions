package com.kotlindiscord.kord.extensions.test.bot

import com.kotlindiscord.kord.extensions.ExtensibleBot
import com.kotlindiscord.kord.extensions.checks.isNotbot
import com.kotlindiscord.kord.extensions.utils.env
import org.koin.core.logger.Level

suspend fun main() {
    val bot = ExtensibleBot(env("TOKEN")!!) {
        koinLogLevel = Level.DEBUG

        messageCommands {
            defaultPrefix = "?"

            check(isNotbot)

            prefix { default ->
                if (guildId?.asString == "787452339908116521") {
                    "!"
                } else {
                    default  // "?"
                }
            }
        }

        slashCommands {
            enabled = true
        }

        extensions {
            add(::TestExtension)

            help {
                paginatorTimeout = 5
            }
        }
    }

    bot.start()
}
