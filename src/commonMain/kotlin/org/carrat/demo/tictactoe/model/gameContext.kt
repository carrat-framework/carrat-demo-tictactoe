package org.carrat.demo.tictactoe.model

import org.carrat.context.Contextual
import org.carrat.context.HasContext
import org.carrat.flow.flow

val HasContext.game by Contextual { Game(flow) }