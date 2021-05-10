package org.carrat.demo.tictactoe.static

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import org.carrat.demo.tictactoe.renderPage
import java.nio.file.Files
import java.nio.file.Paths

fun main(args: Array<String>) {
    val parser = ArgParser("carrat-demo-tictactoe-static", useDefaultHelpShortName = false)
    val pathArgument by parser.argument(ArgType.String, "path")
    parser.parse(args)

    val path = Paths.get(pathArgument)
    Files.createDirectories(path.parent)

    Files.newBufferedWriter(path).use {
        renderPage(it)
    }
}
