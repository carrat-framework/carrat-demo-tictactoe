package org.carrat.demo.tictactoe.site

import org.carrat.web.builder.html.BlockContent

val index: BlockContent = {
    +displayGame
    +displayHistory
    +loadingOverlay
}

