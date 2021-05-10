package org.carrat.demo.tictactoe

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType

fun main(args: Array<String>) {
    val parser = ArgParser("carrat-demo-tictactoe", useDefaultHelpShortName = false)
    val host by parser.option(ArgType.String, "host", "h")
    val port by parser.option(ArgType.Int, "port", "p")
    parser.parse(args)

    serve(host ?: "127.0.0.1", port ?: 8080)
}

fun serve(host: String, port: Int) {
    embeddedServer(Netty, host = host, port = port) {
        routing {
            get("/") {
                call.respondTextWriter(ContentType.Text.Html.withCharset(Charsets.UTF_8)) {
                    renderPage(this)
                }
            }
            static("/static") {
                resources("static")
            }
        }
    }.start(wait = true)
}
