package org.carrat.demo.tictactoe

import org.carrat.demo.tictactoe.site.myDocument
import org.carrat.web.css.render
import java.io.Writer

private const val jsPath = "/static/js.js"

fun renderPage(out: Writer) {
    out.append("<!DOCTYPE html>\n")
    val context = rootContext()
    out.render(myDocument, context, jsPath)
}