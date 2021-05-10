package org.carrat.demo.tictactoe.site

import kotlinx.html.TITLE
import org.carrat.demo.tictactoe.style.GlobalStyleSheet
import org.carrat.web.builder.document.DocumentTemplate
import org.carrat.web.builder.unsafeTag
import org.carrat.web.css.importStyleSheet

val myDocument = DocumentTemplate(
    head = {
        importStyleSheet(GlobalStyleSheet)
        importStyleSheet("https://fonts.googleapis.com/css2?family=Inter:wght@100;400&display=swap")
        unsafeTag(::TITLE) {
            onTagContentUnsafe { +"Tic Tac Toe - Carrat Framework Demo" }
        }
    },
    body = index
)