package kr.apo2073.aShop.utilities

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration

fun String.str2Component(): Component {
    return Str2Component.toComponent(this.replace("&", "§"))
}

object Str2Component {
    fun toComponent(input: String): Component {
        val components = mutableListOf<Component>()
        val currentText = StringBuilder()
        var color: TextColor = NamedTextColor.WHITE
        var bold = false
        var italic = false
        var underlined = false
        var strikethrough = false
        var obfuscated = false

        var isCode = false

        for (character in input) {
            if (character == '§') {
                if (currentText.isNotEmpty()) {
                    components.add(createComponent(currentText.toString(), color, bold, italic, underlined, strikethrough, obfuscated))
                    currentText.clear()
                }
                isCode = true
                continue
            }

            if (isCode) {
                when (character) {
                    'l' -> bold = true
                    'o' -> italic = true
                    'n' -> underlined = true
                    'm' -> strikethrough = true
                    'k' -> obfuscated = true
                    'r' -> {
                        color = NamedTextColor.WHITE
                        bold = false
                        italic = false
                        underlined = false
                        strikethrough = false
                        obfuscated = false
                    }
                    '0' -> color = NamedTextColor.BLACK
                    '1' -> color = NamedTextColor.DARK_BLUE
                    '2' -> color = NamedTextColor.DARK_GREEN
                    '3' -> color = NamedTextColor.DARK_AQUA
                    '4' -> color = NamedTextColor.DARK_RED
                    '5' -> color = NamedTextColor.DARK_PURPLE
                    '6' -> color = NamedTextColor.GOLD
                    '7' -> color = NamedTextColor.GRAY
                    '8' -> color = NamedTextColor.DARK_GRAY
                    '9' -> color = NamedTextColor.BLUE
                    'a' -> color = NamedTextColor.GREEN
                    'b' -> color = NamedTextColor.AQUA
                    'c' -> color = NamedTextColor.RED
                    'd' -> color = NamedTextColor.LIGHT_PURPLE
                    'e' -> color = NamedTextColor.YELLOW
                    'f' -> color = NamedTextColor.WHITE
                }
                isCode = false
                continue
            }

            currentText.append(character)
        }

        if (currentText.isNotEmpty()) {
            components.add(createComponent(currentText.toString(), color, bold, italic, underlined, strikethrough, obfuscated))
        }

        return components.fold(Component.empty()) { acc, component -> acc.append(component) }
    }

    private fun createComponent(
        text: String,
        color: TextColor,
        bold: Boolean,
        italic: Boolean,
        underlined: Boolean,
        strikethrough: Boolean,
        obfuscated: Boolean,
    ): Component {
        val style = Style.style(color)
            .decoration(TextDecoration.BOLD, if (bold) TextDecoration.State.TRUE else TextDecoration.State.FALSE)
            .decoration(TextDecoration.ITALIC, if (italic) TextDecoration.State.TRUE else TextDecoration.State.FALSE)
            .decoration(TextDecoration.UNDERLINED, if (underlined) TextDecoration.State.TRUE else TextDecoration.State.FALSE)
            .decoration(TextDecoration.STRIKETHROUGH, if (strikethrough) TextDecoration.State.TRUE else TextDecoration.State.FALSE)
            .decoration(TextDecoration.OBFUSCATED, if (obfuscated) TextDecoration.State.TRUE else TextDecoration.State.FALSE)

        return Component.text(text).style(style)
    }
}